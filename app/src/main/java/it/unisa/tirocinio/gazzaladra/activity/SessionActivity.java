package it.unisa.tirocinio.gazzaladra.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import java.io.FileInputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import it.unisa.tirocinio.gazzaladra.ExpandableListAdapter;
import it.unisa.tirocinio.gazzaladra.R;
import it.unisa.tirocinio.gazzaladra.database.Session;
import it.unisa.tirocinio.gazzaladra.database.SessionTopic;
import it.unisa.tirocinio.gazzaladra.database.Topic;
import it.unisa.tirocinio.gazzaladra.database.User;
import it.unisa.tirocinio.gazzaladra.database.UserViewModel;

public class SessionActivity extends AppCompatActivity {
	private UserViewModel uvm;
	private ImageView avatar;

	private List<Session> sessioniUtente;
	private Map<Session, List<Topic>> sessionCollector;
	private ExpandableListView expListView;

	private User user;
	private int numSessions;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_session);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		Toolbar toolbar = findViewById(R.id.MyToolbar);
		Intent i = getIntent();
		user = i.getParcelableExtra("user");

		toolbar.setTitle(user.getName() + " " + user.getLastName());
		setSupportActionBar(toolbar);

		avatar = findViewById(R.id.bgheader);
		FileInputStream inStream;
		try {
			inStream = openFileInput(user.getPhoto());
			Bitmap b = BitmapFactory.decodeStream(inStream);
			avatar.setImageBitmap(b);
		} catch (Exception e) {
			Log.e("SessionActivity", "Errore mentre si decoficicava la foto profilo");
			e.printStackTrace();

		}

		FloatingActionButton fab = findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String date = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.ITALY).format(new Date());
				Session s = new Session(user.getUidUser(), numSessions, date);

				Intent i = new Intent(getApplicationContext(), QuizActivity.class);
				i.putExtra("session", s);

				startActivity(i);
			}
		});

		//initialization for the first draw (empty)
		sessioniUtente = new ArrayList<>();
		sessionCollector = new HashMap<>();

		expListView = findViewById(R.id.expandableListView);
		final ExpandableListAdapter adapter = new ExpandableListAdapter(this, sessioniUtente, sessionCollector);
		expListView.setAdapter(adapter);


		uvm = ViewModelProviders.of(this).get(UserViewModel.class);
		uvm.getCompleteSessionByUser(user.getUidUser()).observe(this, new Observer<List<SessionTopic>>() {
			@Override
			public void onChanged(@Nullable List<SessionTopic> sessionTopics) {
				if (sessionTopics == null) return;

				numSessions = sessionTopics.size() + 1;

				sessioniUtente.clear();
				sessionCollector.clear();

				for (SessionTopic st : sessionTopics) {
					sessioniUtente.add(st.session);
					sessionCollector.put(st.session, st.topicList);
				}
				adapter.setSessions(sessioniUtente, sessionCollector);
			}
		});
	}
}