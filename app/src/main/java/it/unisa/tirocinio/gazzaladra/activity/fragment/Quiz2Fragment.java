package it.unisa.tirocinio.gazzaladra.activity.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import it.unisa.tirocinio.gazzaladra.R;
import it.unisa.tirocinio.gazzaladra.Roba;


public class Quiz2Fragment extends Fragment {
	int[] randNumbers;
	private ButtonGameLogic bgl;

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
			randNumbers[i] = i;
		}
		for (int i = 0; i < 50; i++) {
			int swp = r.nextInt(50);

			int mom = randNumbers[swp];
			randNumbers[swp] = randNumbers[i];
			randNumbers[i] = mom;
		}

		bgl = new ButtonGameLogic();

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
			b.setOnClickListener(bgl);
			gl.addView(b);
		}

		progressBar = v.findViewById(R.id.progressBar);
		tvTimer = v.findViewById(R.id.countdown_tv);
		tvTimer.setText(30 + "");
		progressBar.setProgress(0);
		progressBar.setMax(REQUEST_TIMER);
		timer.scheduleAtFixedRate(task, 500, 1000);

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

	private class ButtonGameLogic implements View.OnClickListener {
		int i = 0;
		private int[] ordered;

		public ButtonGameLogic() {
			ordered = new int[9];
			for (int i = 0; i < 9; i++) {
				ordered[i] = randNumbers[i];
			}
			Arrays.sort(ordered);
		}

		@Override
		public void onClick(View v) {
			Button b = (Button) v;
			int actualNumber = Integer.parseInt(b.getText().toString());
			if (actualNumber == ordered[i] && i != 8) {
				i++;
				b.setTextColor(Color.GREEN);
				b.setOnClickListener(null);
			} else if (actualNumber != ordered[i]) {
				b.setTextColor(Color.RED);
			} else {
				i++;
				b.setTextColor(Color.GREEN);
				b.setOnClickListener(null);
				Toast.makeText(getContext(), "vittoria", Toast.LENGTH_SHORT).show();
				timer.cancel();
				mListener.onFragmentEnd(new Roba("frammento01", "scen", "aaaaa", "bbbbb", false, 41585l, 4241l));

			}
		}
	}

}
