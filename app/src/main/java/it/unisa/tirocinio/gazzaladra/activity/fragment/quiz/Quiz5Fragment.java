package it.unisa.tirocinio.gazzaladra.activity.fragment.quiz;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import it.unisa.tirocinio.gazzaladra.R;
import it.unisa.tirocinio.gazzaladra.activity.fragment.FragmentComunicator;
import it.unisa.tirocinio.gazzaladra.activity.fragment.FragmentTemplate;
import it.unisa.tirocinio.gazzaladra.data.FragmentData;

public class Quiz5Fragment extends FragmentTemplate {
	@Override
	public String getFragmentIdNoCheck() {
		return "QuizCompletaTesto";
	}

	private TextView[] tv;
	private EditText[] et;
	private String[] frasi;
	private TextView parole;
	private LinearLayout ll;
	private Button conferma;
	private ProgressBar progressBar;
	private TextView tvTimer;
	private static final int REQUEST_TIMER = 60;
	private Timer timer;
	private Handler handler;
	private int counter;
	private CustomTimer task;

	private long startTime;
	private boolean isCompleted;

	private FragmentComunicator mListener;

	public Quiz5Fragment() {
		// Required empty public constructor
	}

	public static Quiz5Fragment newInstance() {
		Quiz5Fragment fragment = new Quiz5Fragment();
		Log.w("quiz5", "sto nel quiz5");
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		timer = new Timer();
		handler = new Handler();
		task = new CustomTimer();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_quiz5, container, false);

		isCompleted = true;
		parole = v.findViewById(R.id.parole);
		conferma = v.findViewById(R.id.buttonConferma);
		parole.setText("Completa il testo con le seguenti parole:\n" +
				"misterioso , cibo , pace , religione , sudra , curiosità , vita ");

		tv = new TextView[7];
		et = new EditText[6];
		frasi = new String[]{"Tutti pregavano e ringraziavano a modo loro, secondo la loro",
				", in assoluta libertà. Questo stupì Samir. Uomini e donne di religioni diverse diversi erano lì riuniti attorno al cibo, in ",
				"Adoravano ognuno il loro Dio e avevano un rapporto molto particolare con ciò che mangiavano. Il",
				"trascendeva il cibo, non sembrava nutrire solo il loro corpo. C'era qualcosa di",
				"che gli sfuggiva. Cos'era? Lo chiese, dopo il gioioso e inatteso pasto, a un vecchio",
				", col viso scavato e gli occhi dolci.Il cibo è energia che si trasforma, si mescola alla nostra energia. Il cibo vivo dà",
				"allo spirito e si integra con parsimonia e naturalezza al corpo fisico."};
		ll = v.findViewById(R.id.linearLayout);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
				(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		for (int i = 0; i < frasi.length - 1; i++) {
			tv[i] = new TextView(getContext().getApplicationContext());
			et[i] = new EditText(getContext().getApplicationContext());
			params.leftMargin = 5;
			tv[i].setText(frasi[i]);
			tv[i].setTextSize(20f);
			tv[i].setLayoutParams(params);
			et[i].setBackgroundColor(Color.WHITE);
			//et[i].setLayoutParams(params);
			ll.addView(tv[i]);
			ll.addView(et[i]);
		}
		tv[frasi.length - 1] = new TextView(getContext().getApplicationContext());
		tv[frasi.length - 1].setText(frasi[frasi.length - 1]);
		tv[frasi.length - 1].setTextSize(20f);
		tv[frasi.length - 1].setLayoutParams(params);
		ll.addView(tv[frasi.length - 1]);

		conferma.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				for (int i = 0; i < et.length; i++) {
					if (et[i].getText().toString().equals("")) {
						Toast.makeText(getContext(), "Riempi tutti gli spazi con una parola", Toast.LENGTH_SHORT).show();
					}
				}
				if (et[0].getText().toString().equalsIgnoreCase("religione") && et[1].getText().toString().equalsIgnoreCase("vita")
						&& et[2].getText().toString().equalsIgnoreCase("cibo") && et[3].getText().toString().equalsIgnoreCase("misterioso")
						&& et[4].getText().toString().equalsIgnoreCase("sudra")) {

					Toast.makeText(getContext(), "corretto", Toast.LENGTH_SHORT).show();
				} else {
					isCompleted = false;
					Toast.makeText(getContext(), "sbagliato", Toast.LENGTH_SHORT).show();
				}
				mListener.onFragmentEnd(new FragmentData(Quiz5Fragment.super.getFragmentId(), null, isCompleted, startTime, System.currentTimeMillis()));

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
			throw new RuntimeException(context.toString() + " must implement FragmentComunicator");
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
					}
				}
			});
		}
	}
}
