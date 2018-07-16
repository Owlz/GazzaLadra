package it.unisa.tirocinio.gazzaladra.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;

import it.unisa.tirocinio.gazzaladra.R;
import it.unisa.tirocinio.gazzaladra.activity.fragment.IntermediateFragment;
import it.unisa.tirocinio.gazzaladra.database.Session;
import it.unisa.tirocinio.gazzaladra.file_writer.AsyncFileWriter;

public class QuizActivity extends TemplateActivity implements IntermediateFragment.IntermediateFragmentCallback {
	private FragmentManager fm;
	private ArrayList<Fragment> fragments;
	private Session session;
	private long activityStart;


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

		activityStart = System.currentTimeMillis();
		if (saved != null) {

		} else {
			fragments = new ArrayList<>();
			fragments.add(IntermediateFragment.newInstance("scenario random"));
			fm = getSupportFragmentManager();
			fm.beginTransaction()
					.replace(R.id.fragmentContainer, fragments.get(0))
					.commit();
		}
	}

	@Override
	public void intermediateCallback() {
		Log.w("IntermediateCallback", "callbackkkkkks");
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
}
