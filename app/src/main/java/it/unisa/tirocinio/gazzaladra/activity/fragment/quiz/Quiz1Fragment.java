package it.unisa.tirocinio.gazzaladra.activity.fragment.quiz;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import it.unisa.tirocinio.gazzaladra.R;
import it.unisa.tirocinio.gazzaladra.activity.fragment.FragmentComunicator;
import it.unisa.tirocinio.gazzaladra.activity.fragment.FragmentTemplate;
import it.unisa.tirocinio.gazzaladra.data.FragmentData;

public class Quiz1Fragment extends FragmentTemplate {
	@Override
	public String getFragmentIdNoCheck() {
		return "QuizNomeColore";
	}


	private TextView colortext;
	private EditText edit;
	private Button buttonConferma;
	private ProgressBar progressBar;
	private TextView tvTimer;
	private static final int REQUEST_TIMER = 40;
	private Timer timer;
	private Handler handler;
	private int counter;
	private CustomTimer task;
	private String[] wordsColor;
	private int countColor = 1;
	private int numQuizes = 10;

	private int[] colors;
	private long startTime;
	private boolean isCompleted;

	private FragmentComunicator mListener;

	public Quiz1Fragment() {
		// Required empty public constructor
	}

	public static Quiz1Fragment newInstance() {
		Quiz1Fragment fragment = new Quiz1Fragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		timer = new Timer();
		handler = new Handler();
		task = new CustomTimer();
		wordsColor = new String[]{"Nero", "Grigio", "Rosso", "Giallo", "Verde", "Blu", "Bianco"};
		colors = new int[]{Color.RED, Color.BLACK, Color.BLUE, Color.GREEN, Color.YELLOW, Color.GRAY, Color.WHITE};
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_quiz1, container, false);

		startTime = System.currentTimeMillis();
		isCompleted = true;
		colortext = v.findViewById(R.id.colorText);
		edit = v.findViewById(R.id.edit);
		buttonConferma = v.findViewById(R.id.buttonConferma);

		int rand = new Random().nextInt(wordsColor.length);

		colortext.setText(wordsColor[rand]);
		randomColor();
		buttonConferma.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				controlColor();
			}
		});


		progressBar = v.findViewById(R.id.progressBar);
		tvTimer = v.findViewById(R.id.countdown);
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
					tvTimer.setText("" + (REQUEST_TIMER - counter));
					counter++;
					progressBar.setProgress(counter);
					if (counter >= REQUEST_TIMER) {
						timer.cancel();
						isCompleted = false;
						checkWin();
					}
				}
			});
		}
	}

	public void randomColor() {
		Random r = new Random();
		colortext.setTextColor(colors[r.nextInt(colors.length)]);
		colortext.setShadowLayer(1, 0, 0, Color.BLACK);
	}

	public void controlColor() {
		if (edit.getText().toString().equals("")) {
			return;
		}
		if (colortext.getCurrentTextColor() == Color.RED && edit.getText().toString().equalsIgnoreCase("Rosso")) {
			Toast.makeText(getContext(), "Esatto!", Toast.LENGTH_SHORT).show();
		} else if (colortext.getCurrentTextColor() == Color.GREEN && edit.getText().toString().equalsIgnoreCase("Verde")) {
			Toast.makeText(getContext(), "Esatto!", Toast.LENGTH_SHORT).show();
		} else if (colortext.getCurrentTextColor() == Color.BLUE && edit.getText().toString().equalsIgnoreCase("Blu")) {
			Toast.makeText(getContext(), "Esatto!", Toast.LENGTH_SHORT).show();
		} else if (colortext.getCurrentTextColor() == Color.YELLOW && edit.getText().toString().equalsIgnoreCase("Giallo")) {
			Toast.makeText(getContext(), "Esatto!", Toast.LENGTH_SHORT).show();
		} else if (colortext.getCurrentTextColor() == Color.BLACK && edit.getText().toString().equalsIgnoreCase("Nero")) {
			Toast.makeText(getContext(), "Esatto!", Toast.LENGTH_SHORT).show();
		} else if (colortext.getCurrentTextColor() == Color.GRAY && edit.getText().toString().equalsIgnoreCase("Grigio")) {
			Toast.makeText(getContext(), "Esatto!", Toast.LENGTH_SHORT).show();
		} else if (colortext.getCurrentTextColor() == Color.WHITE && edit.getText().toString().equalsIgnoreCase("Bianco")) {
			Toast.makeText(getContext(), "Esatto!", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(getContext(), "Sbagliato!", Toast.LENGTH_SHORT).show();
		}
		checkWin();
	}

	private void checkWin() {
		if (countColor > numQuizes || !isCompleted) {
			timer.cancel();
			Toast.makeText(getContext(), "hai finito il quiz!", Toast.LENGTH_SHORT).show();
			mListener.onFragmentEnd(new FragmentData(super.getFragmentId(), null, isCompleted, startTime, System.currentTimeMillis()));
		} else {
			countColor++;
			edit.setText("");
			int rand = new Random().nextInt(wordsColor.length);
			colortext.setText(wordsColor[rand]);
			randomColor();
		}
	}
}
