package it.unisa.tirocinio.gazzaladra.activity.fragment.quiz;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import it.unisa.tirocinio.gazzaladra.R;
import it.unisa.tirocinio.gazzaladra.Utils;
import it.unisa.tirocinio.gazzaladra.activity.TemplateActivity;
import it.unisa.tirocinio.gazzaladra.activity.fragment.FragmentComunicator;
import it.unisa.tirocinio.gazzaladra.activity.fragment.FragmentTemplate;
import it.unisa.tirocinio.gazzaladra.data.FragmentData;


public class Quiz2Fragment extends FragmentTemplate implements View.OnClickListener {
	int[] randNumbers;
	int indexWinning = 0;
	private int[] ordered;

	private FragmentComunicator mListener;

	private boolean isTimeExpired;
	private ProgressBar progressBar;
	private TextView tvTimer;
	private static final int REQUEST_TIMER = 30;
	private Timer timer;
	private Handler handler;
	private int counter;
	private CustomTimer task;
	private long timeStart;
	private Button next;


	public Quiz2Fragment() {
		// Required empty public constructor
	}

	public static Quiz2Fragment newInstance() {
		return new Quiz2Fragment();
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		randNumbers = new int[50];
		final Random r = new Random();
		for (int i = 0; i < 50; i++) {
			randNumbers[i] = i + 1;
		}
		for (int i = 0; i < 50; i++) {
			int swp = r.nextInt(50);

			int mom = randNumbers[swp];
			randNumbers[swp] = randNumbers[i];
			randNumbers[i] = mom;
		}
		ordered = new int[9];
		for (int i = 0; i < 9; i++) {
			ordered[i] = randNumbers[i];
		}
		Arrays.sort(ordered);

		isTimeExpired = false;
		timer = new Timer();
		handler = new Handler();
		task = new CustomTimer();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_quiz2, container, false);

		GridLayout gl = v.findViewById(R.id.gridContainer);
		for (int i = 0; i < 9; i++) {
			Button b = new Button(getContext());
			b.setText("" + randNumbers[i]);
			b.setOnClickListener(this);
			gl.addView(b);
		}

		progressBar = v.findViewById(R.id.progressBar);
		tvTimer = v.findViewById(R.id.countdown_tv);
		tvTimer.setText(30 + "");
		progressBar.setProgress(0);
		progressBar.setMax(REQUEST_TIMER);
		timer.scheduleAtFixedRate(task, 500, 1000);
		timeStart = System.currentTimeMillis();
		isTimeExpired = false;

		next = v.findViewById(R.id.next);
		next.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				end(v);
			}
		});

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

	@Override
	public String getFragmentIdNoCheck() {
		return "QuizTastiInOrdine";
	}

	@Override
	public void onClick(View v) {
		Button b = (Button) v;
		int actualNumber = Integer.parseInt(b.getText().toString());
		if (actualNumber == ordered[indexWinning] && indexWinning != 8) {
			indexWinning++;
			b.setTextColor(Color.GREEN);
			b.setOnClickListener(null);
		} else if (actualNumber != ordered[indexWinning]) {
			b.setTextColor(Color.RED);
		} else {
			indexWinning++;
			b.setTextColor(Color.GREEN);
			b.setOnClickListener(null);

			timer.cancel();

			next.setVisibility(View.VISIBLE);


		}
	}

	private void end(View v) {
		mListener.onFragmentEnd(
				new FragmentData(
						super.getFragmentId(),
						null,
						String.valueOf(!isTimeExpired),
						String.valueOf(!isTimeExpired),
						!isTimeExpired,
						timeStart,
						System.currentTimeMillis()
				)
		);
	}

	private class CustomTimer extends TimerTask {
		@Override
		public void run() {
			handler.post(new Runnable() {
				@Override
				public void run() {
					counter++;
					progressBar.setProgress(counter);

					tvTimer.setText((30 - counter) + "");

					if (counter >= REQUEST_TIMER) {
						timer.cancel();

						tvTimer.setText("0 - Tempo scaduto!");
						isTimeExpired = true;

						next.setVisibility(View.VISIBLE);
					}
				}
			});
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("counter", counter);
	}
}
