package it.unisa.tirocinio.gazzaladra.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.Pair;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import it.unisa.tirocinio.gazzaladra.QuizMaker;
import it.unisa.tirocinio.gazzaladra.R;
import it.unisa.tirocinio.gazzaladra.Roba;
import it.unisa.tirocinio.gazzaladra.activity.fragment.FragmentComunicator;
import it.unisa.tirocinio.gazzaladra.activity.fragment.IntermediateFragment;
import it.unisa.tirocinio.gazzaladra.activity.fragment.RiepologFragment;
import it.unisa.tirocinio.gazzaladra.database.Session;
import it.unisa.tirocinio.gazzaladra.database.Topic;
import it.unisa.tirocinio.gazzaladra.database.UserViewModel;
import it.unisa.tirocinio.gazzaladra.file_writer.AsyncFileWriter;

public class QuizActivity extends TemplateActivity implements IntermediateFragment.IntermediateFragmentCallback, FragmentComunicator {
	private FragmentManager fm;
	private List<String> fragments;
	private List<String> scenari;
	private Session session;
	private long activityStart;
	private int index;
	private String currScenario;

	private ArrayList<Roba> roba;

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putLong("start", activityStart);
		outState.putInt("index", index);
		outState.putString("currScenario", currScenario);
		outState.putParcelableArrayList("roba", roba);
	}

	@Override
	protected void onCreate(Bundle saved) {
		super.onCreate(saved);
		setContentView(R.layout.activity_quiz);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);


		Intent i = getIntent();
		session = i.getParcelableExtra("session");
		super.setSession(session);

		Log.w("QuizActivity", "inside quizActy");


		fm = getSupportFragmentManager();

		if (saved != null) {
			activityStart = saved.getLong("start");
			index = saved.getInt("index");
			currScenario = saved.getString("currScenario");
			roba = saved.getParcelableArrayList("roba");
			Log.w("QuizActivity", "onCreateAgain");

		} else {
			Log.w("QuizActivity", "init new quiz");

			roba = new ArrayList<>();
			activityStart = System.currentTimeMillis();
			index = 0;

			Pair<List<String>, List<String>> p = QuizMaker.getQuizList(1);
			fragments = p.first;
			scenari = p.second;

			currScenario = scenari.get(index);
			index++;

			Bundle b = new Bundle();
			b.putString("scenario", currScenario);
			Log.w("QuizActivity", "pre begin transition");

			fm.beginTransaction()
					.replace(R.id.fragmentContainer,
							Fragment.instantiate(
									getApplicationContext(),
									IntermediateFragment.class.getName(),
									b
							)
					)
					.commit();
			Log.w("QuizActivity", "post begin transition");

		}
	}

	@Override
	public void intermediateCallback() {
		String fragName = fragments.get(index - 1);
		Fragment frag = Fragment.instantiate(getApplicationContext(), fragName);

		fm.beginTransaction()
				.replace(R.id.fragmentContainer, frag)
				.commit();
	}

	@Override
	public void onFragmentEnd(Roba robaNuova) {
		Log.w("QuizActivity", "onFragmentEnd");

		roba.add(robaNuova);

		if (index < fragments.size()) {
			Log.w("QuizActivity", "index < fragment.size(): " + index);

			currScenario = scenari.get(index);
			index++;

			Bundle b = new Bundle();
			b.putString("scenario", currScenario);

			fm.beginTransaction()
					.replace(R.id.fragmentContainer,
							Fragment.instantiate(
									getApplicationContext(),
									IntermediateFragment.class.getName(),
									b
							)
					)
					.commit();
		} else {
			Log.w("QuizActivity", "else writing");
			fm.beginTransaction()
					.replace(R.id.fragmentContainer,
							Fragment.instantiate(getApplicationContext(),
									RiepologFragment.class.getName()))
					.commit();


			UserViewModel uvm = new UserViewModel(getApplication());
			uvm.insertSession(session);


			List<Session> l = uvm.getSessionByUser(session.getUidUser()).getValue();
			session = l.get(l.size() - 1);

			for (Roba r : roba) {
				AsyncFileWriter.write(new String[]{
						r.getIdFragment(),
						"" + r.getTimeStart(),
						"" + r.getTimeEnd(),
						r.getRispostaData(),
						r.getRispostaCorretta(),
						"" + session.getUidUser(),
						currScenario
				}, super.getSessionFolder(), "topic.txt");


				uvm.insertTopic(
						new Topic(
								session.getUid(),
								r.getIdFragment(),
								(r.getRispostaData().equals(r.getRispostaCorretta()))
						)
				);

				Log.w("QuizActivity", "aggiunto elemento nel db e nel file");

			}
		}
	}

	public void writeActivity() {
		AsyncFileWriter.write(new String[]{
				"" + session.getUidUser(),
				"" + session.getUid(),
				"" + session.getNumSession(),
				"" + session.getData(),
				"" + activityStart,
				"" + System.currentTimeMillis()
		}, super.getSessionFolder(), "activity.txt");
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	public void onBackPressed() {
		return;
	}

}
