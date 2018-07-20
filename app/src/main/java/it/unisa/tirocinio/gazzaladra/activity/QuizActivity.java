package it.unisa.tirocinio.gazzaladra.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.Pair;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import it.unisa.tirocinio.gazzaladra.QuizMaker;
import it.unisa.tirocinio.gazzaladra.R;
import it.unisa.tirocinio.gazzaladra.activity.fragment.FragmentComunicator;
import it.unisa.tirocinio.gazzaladra.activity.fragment.FragmentTemplate;
import it.unisa.tirocinio.gazzaladra.activity.fragment.IntermediateFragment;
import it.unisa.tirocinio.gazzaladra.activity.fragment.RiepologFragment;
import it.unisa.tirocinio.gazzaladra.data.FragmentData;
import it.unisa.tirocinio.gazzaladra.data.SensorData;
import it.unisa.tirocinio.gazzaladra.database.Session;
import it.unisa.tirocinio.gazzaladra.database.Topic;
import it.unisa.tirocinio.gazzaladra.database.UserViewModel;
import it.unisa.tirocinio.gazzaladra.file_writer.AsyncFileWriter;

public class QuizActivity extends TemplateActivity implements IntermediateFragment.IntermediateFragmentCallback, FragmentComunicator, RiepologFragment.RiepilogoFragmentCallback {
	private FragmentManager fm;
	private List<String> fragments;
	private List<String> scenari;
	private Session session;
	private long timeActivityStart;
	private int fragmentIndex;
	private String currScenario;

	private ArrayList<FragmentData> fragmentResultData;

	private ProgressDialog dialog;
	private Handler handler;

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putLong("start", timeActivityStart);
		outState.putInt("fragmentIndex", fragmentIndex);
		outState.putString("currScenario", currScenario);
		outState.putParcelableArrayList("fragmentResultData", fragmentResultData);
	}

	@Override
	protected void onCreate(Bundle saved) {
		super.onCreate(saved);
		setContentView(R.layout.activity_quiz);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		super.setActivityId("QuizActivity");


		Intent i = getIntent();
		session = i.getParcelableExtra("session");
		super.setSessionFolder(session);

		fm = getSupportFragmentManager();

		if (saved != null) {
			timeActivityStart = saved.getLong("start");
			fragmentIndex = saved.getInt("fragmentIndex");
			currScenario = saved.getString("currScenario");
			fragmentResultData = saved.getParcelableArrayList("fragmentResultData");

		} else {
			fragmentResultData = new ArrayList<>();
			timeActivityStart = System.currentTimeMillis();
			fragmentIndex = 0;

			Pair<List<String>, List<String>> p = QuizMaker.getQuizList(1);
			fragments = p.first;
			scenari = p.second;

			currScenario = scenari.get(fragmentIndex);
			fragmentIndex++;

			Bundle b = new Bundle();
			b.putString("scenario", currScenario);

			FragmentTemplate frag = (FragmentTemplate) Fragment.instantiate(
					getApplicationContext(),
					IntermediateFragment.class.getName(),
					b
			);
			super.setFragmentId(frag.getFragmentId());

			fm.beginTransaction()
					.replace(R.id.fragmentContainer, frag)
					.commit();
		}
	}

	@Override
	public void intermediateCallback() {
		String fragName = fragments.get(fragmentIndex - 1);
		FragmentTemplate frag = (FragmentTemplate) Fragment.instantiate(getApplicationContext(), fragName);

		fm.beginTransaction()
				.replace(R.id.fragmentContainer, frag)
				.commit();
		super.setFragmentId(frag.getFragmentId());


	}

	@Override
	public void onFragmentEnd(FragmentData fragmentDataNew) {
		fragmentDataNew.setScenario(currScenario);
		fragmentResultData.add(fragmentDataNew);

		//next activity
		if (fragmentIndex < fragments.size()) {
			currScenario = scenari.get(fragmentIndex);
			fragmentIndex++;

			Bundle b = new Bundle();
			b.putString("scenario", currScenario);

			FragmentTemplate frag = (FragmentTemplate) Fragment.instantiate(
					getApplicationContext(),
					IntermediateFragment.class.getName(),
					b
			);
			fm.beginTransaction()
					.replace(R.id.fragmentContainer, frag)
					.commit();
			super.setFragmentId(frag.getFragmentId());
			return;
		}

		saveDataAndShowRiepilogo();

	}

	private void saveDataAndShowRiepilogo() {
		dialog = new ProgressDialog(this);
		dialog.setMessage("Salvando le informazioni ottenute, attendere");
		dialog.setCancelable(false);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setIndeterminate(true);
		dialog.show();

		new Thread() {
			public void run() {
				UserViewModel uvm = new UserViewModel(getApplication());
				long val = uvm.insert(session);
				session.setUidSession(val);

				for (SensorData sd : QuizActivity.super.getSensorDataCollected()) {
					AsyncFileWriter.write(sd.toStringArray(), QuizActivity.super.getSessionFolder(), sd.sensorName);
				}


				for (FragmentData r : fragmentResultData) {
					AsyncFileWriter.write(new String[]{
							r.getIdFragment(),
							"" + r.getTimeStart(),
							"" + r.getTimeEnd(),
							r.getRispostaData(),
							r.getRispostaCorretta(),
							"" + session.getUidU(),
							r.getScenario()
					}, QuizActivity.super.getSessionFolder(), "topic");

					uvm.insert(new Topic(session.getUidSession(), r.getIdFragment(), r.isComplete()));
				}
				AsyncFileWriter.write(new String[]{
						"" + session.getUidU(),
						"" + session.getUidSession(),
						"" + session.getNumSession(),
						"" + session.getData(),
						"" + timeActivityStart,
						"" + System.currentTimeMillis()
				}, QuizActivity.super.getSessionFolder(), "activity");

				//// todo: salvare tutto il resto

				runOnUiThread(new Runnable() {

					public void run() {
						FragmentTemplate frag = (FragmentTemplate) Fragment.instantiate(
								getApplicationContext(),
								RiepologFragment.class.getName()
						);
						fm.beginTransaction()
								.replace(R.id.fragmentContainer, frag)
								.commit();

						QuizActivity.super.setFragmentId(frag.getFragmentId());
					}
				});

				handler.sendEmptyMessage(0);
			}
		}.start();

		handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				dialog.dismiss();
			}

			;
		};
	}

	@Override
	public void riepilogoCallback() {
		/*code*/
	}
}
