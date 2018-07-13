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
import android.widget.Toast;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unisa.tirocinio.gazzaladra.ExpandableListAdapter;
import it.unisa.tirocinio.gazzaladra.R;
import it.unisa.tirocinio.gazzaladra.database.Session;
import it.unisa.tirocinio.gazzaladra.database.User;
import it.unisa.tirocinio.gazzaladra.database.UserViewModel;

public class SessionActivity extends AppCompatActivity {
	private UserViewModel uvm;
	private User utente;
	private ImageView avatar;

	private List<Session> sessioniUtente;
	private List<String> moltiQuiz;
	private Map<Session, List<String>> sessionCollector;
	private ExpandableListView expListView;

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
				int lastSessionNumber = 0;
				if (sessioniUtente.size() != 0)
					lastSessionNumber = sessioniUtente.get(sessioniUtente.size() - 1).getNumSession();

				uvm.insertSession(new Session(utente.getUid(), lastSessionNumber + 1, "10/10/10"));
			}
		});

		sessioniUtente = new ArrayList<>();
		expListView = findViewById(R.id.expandableListView);
		final ExpandableListAdapter adapter = new ExpandableListAdapter(this, sessioniUtente, sessionCollector);
		expListView.setAdapter(adapter);

		uvm = ViewModelProviders.of(this).get(UserViewModel.class);
		uvm.getSessionByUser(utente.getUid()).observe(this, new Observer<List<Session>>() {
			@Override
			public void onChanged(@Nullable List<Session> session) {
				sessioniUtente = session;
				sessionCollector = new HashMap<Session, List<String>>();
				for (Session s : sessioniUtente) {
					List<String> mom = new ArrayList<>();
					mom.add("quiz1");
					mom.add("quiz2");
					mom.add("quiz3");
					sessionCollector.put(s, mom);
				}
				adapter.setSessions(sessioniUtente, sessionCollector);

				if (sessioniUtente.size() != 0)
					Toast.makeText(SessionActivity.this, sessioniUtente.get(sessioniUtente.size() - 1).toString(), Toast.LENGTH_SHORT).show();
			}
		});

	}
}
