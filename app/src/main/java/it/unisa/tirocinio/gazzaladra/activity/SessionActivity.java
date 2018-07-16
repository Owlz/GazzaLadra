package it.unisa.tirocinio.gazzaladra.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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
import it.unisa.tirocinio.gazzaladra.database.Topic;
import it.unisa.tirocinio.gazzaladra.database.User;
import it.unisa.tirocinio.gazzaladra.database.UserViewModel;

public class SessionActivity extends AppCompatActivity {
	private UserViewModel uvm;
	private ImageView avatar;

	private List<Session> sessioniUtente;
	private List<String> moltiQuiz;
	private Map<Session, List<Topic>> sessionCollector;
	private ExpandableListView expListView;

	private User utente;
	private int numSessioniSvolte;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_session);

		Toolbar toolbar = findViewById(R.id.MyToolbar);
		Intent i = getIntent();
		utente = i.getParcelableExtra("user");

		toolbar.setTitle(utente.getName() + " " + utente.getLastName());
		setSupportActionBar(toolbar);

		avatar = findViewById(R.id.bgheader);
		FileInputStream inStream;
		try {
			inStream = openFileInput(utente.getPhoto());
			Bitmap b = BitmapFactory.decodeStream(inStream);
			avatar.setImageBitmap(b);
		} catch (Exception e) {
			Log.w("SessionActivity", "Errore mentre si decoficicava la foto profilo");
			e.printStackTrace();
		}

		FloatingActionButton fab = findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Session s = new Session(
						utente.getUid(),
						numSessioniSvolte++,
						DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.ITALY).format(new Date())
				);
				Intent i = new Intent(getApplicationContext(), QuizActivity.class);
				i.putExtra("session", s);

				startActivity(i);
			}
		});

		sessioniUtente = new ArrayList<>();
		sessionCollector = new HashMap<>();

		expListView = findViewById(R.id.expandableListView);
		final ExpandableListAdapter adapter = new ExpandableListAdapter(this, sessioniUtente, sessionCollector);
		expListView.setAdapter(adapter);

		uvm = ViewModelProviders.of(this).get(UserViewModel.class);
		uvm.getSessionByUser(utente.getUid()).observe(this, new Observer<List<Session>>() {
			@Override
			public void onChanged(@Nullable List<Session> session) {
				sessioniUtente = session;
				numSessioniSvolte = session.size();

				sessionCollector = new HashMap<>();

				for (Session s : sessioniUtente) {
					List<Topic> mom = uvm.getTopicBySession(s.getUid()).getValue();
					sessionCollector.put(s, mom);
				}
				adapter.setSessions(sessioniUtente, sessionCollector);

			}
		});

	}
}
