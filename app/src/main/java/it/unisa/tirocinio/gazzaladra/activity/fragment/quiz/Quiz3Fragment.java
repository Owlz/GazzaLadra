package it.unisa.tirocinio.gazzaladra.activity.fragment.quiz;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import it.unisa.tirocinio.gazzaladra.R;
import it.unisa.tirocinio.gazzaladra.Utils;
import it.unisa.tirocinio.gazzaladra.activity.AdapterRecyclerView;
import it.unisa.tirocinio.gazzaladra.activity.TemplateActivity;
import it.unisa.tirocinio.gazzaladra.activity.fragment.FragmentComunicator;
import it.unisa.tirocinio.gazzaladra.activity.fragment.FragmentTemplate;

public class Quiz3Fragment extends FragmentTemplate {
	@Override
	public String getFragmentIdNoCheck() {
		return "QuizSinonimo";
	}

	private TextView parola;
	private List<String> sinonimi;
	private RecyclerView rv;
	private AdapterRecyclerView adapter;
	private RecyclerView.LayoutManager mLayoutManager;

	private ProgressBar progressBar;
	private TextView tvTimer;
	private static final int REQUEST_TIMER = 20;
	private Timer timer;
	private Handler handler;
	private int counter;
	private CustomTimer task;

	private FragmentComunicator mListener;
	private long startTime;

	public Quiz3Fragment() {
		// Required empty public constructor
	}

	public static Quiz3Fragment newInstance() {
		Quiz3Fragment fragment = new Quiz3Fragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		createList();
		timer = new Timer();
		handler = new Handler();
		task = new CustomTimer();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_quiz3, container, false);
		parola = v.findViewById(R.id.parola);
		parola.setText("Procrastinare");

		startTime = System.currentTimeMillis();

		rv = v.findViewById(R.id.recycler_list);

		progressBar = v.findViewById(R.id.progressBar);
		tvTimer = v.findViewById(R.id.countdown);
		progressBar.setMax(REQUEST_TIMER);
		timer.scheduleAtFixedRate(task, 500, 1000);

		mLayoutManager = new LinearLayoutManager(getActivity());
		rv.setLayoutManager(mLayoutManager);
		adapter = new AdapterRecyclerView(sinonimi, mListener, super.getFragmentId(), startTime);
		rv.setAdapter(adapter);

		for (View child : Utils.getAllChildrenBFS(v)) {
			child.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View view, MotionEvent event) {
					return ((TemplateActivity) getActivity()).widgetTouchDispatcher(view, event);
				}
			});
		}
		return v;
	}

	private class CustomTimer extends TimerTask {
		@Override
		public void run() {
			handler.post(new Runnable() {
				@Override
				public void run() {
					tvTimer.setText("" + (REQUEST_TIMER - counter));
					counter++;
					progressBar.setProgress(counter);
					if (counter >= REQUEST_TIMER) {
						timer.cancel();
					}
				}
			});
		}
	}

	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof FragmentComunicator) {
			mListener = (FragmentComunicator) context;
		} else {
			throw new RuntimeException(context.toString()
					+ " must implement FragmentComunicator");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		timer.cancel();
		mListener = null;
	}

	private void createList() {
		sinonimi = new ArrayList<>();
		sinonimi.add("Perseguire");
		sinonimi.add("Anticipare");
		sinonimi.add("Precedere");
		sinonimi.add("Prorogabile");
		sinonimi.add("Analogo");
		sinonimi.add("Superficiale");
		sinonimi.add("Indugiare");
		sinonimi.add("Porgere");
		sinonimi.add("Ammaliare");
		sinonimi.add("Abbacinare");
		sinonimi.add("Auspicare");
		sinonimi.add("Rimandare");
		sinonimi.add("Invecchiare");
		sinonimi.add("Dormire");
		sinonimi.add("Sonnecchiare");
		sinonimi.add("Precisare");
		sinonimi.add("Precipitare");
		sinonimi.add("Abboccare");
		sinonimi.add("Abolire");
		sinonimi.add("Distruggere");
		sinonimi.add("Creare");
		sinonimi.add("Aggredire");
		sinonimi.add("Accettare");
		sinonimi.add("Macinare");
	}
}
