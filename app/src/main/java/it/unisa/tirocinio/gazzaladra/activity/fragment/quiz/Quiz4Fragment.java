package it.unisa.tirocinio.gazzaladra.activity.fragment.quiz;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import it.unisa.tirocinio.gazzaladra.R;
import it.unisa.tirocinio.gazzaladra.activity.fragment.FragmentComunicator;
import it.unisa.tirocinio.gazzaladra.activity.fragment.FragmentTemplate;
import it.unisa.tirocinio.gazzaladra.data.FragmentData;

public class Quiz4Fragment extends FragmentTemplate {


	@Override
	public String getFragmentIdNoCheck() {
		return "QuizComprensioneTesto";
	}

	private TextView testoCompr;
	private EditText response;
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

	public Quiz4Fragment() {
		// Required empty public constructor
	}

	public static Quiz4Fragment newInstance() {
		Quiz4Fragment fragment = new Quiz4Fragment();
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
		View v = inflater.inflate(R.layout.fragment_quiz4, container, false);

		isCompleted = true;
		testoCompr = v.findViewById(R.id.testoComprensione);
		response = v.findViewById(R.id.editText);
		conferma = v.findViewById(R.id.buttonConferma);

		progressBar = v.findViewById(R.id.progressBar);
		tvTimer = v.findViewById(R.id.countdown);
		progressBar.setMax(REQUEST_TIMER);
		timer.scheduleAtFixedRate(task, 500, 1000);

		setTesto();
		testoCompr.setMovementMethod(new ScrollingMovementMethod());

		conferma.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (response.getText().toString().equals("")) {
					Toast.makeText(getContext(), "Scrivi la  risposta", Toast.LENGTH_SHORT).show();
				} else {
					timer.cancel();
					mListener.onFragmentEnd(new FragmentData(Quiz4Fragment.super.getFragmentId(), null, isCompleted, startTime, System.currentTimeMillis()));
				}
			}
		});

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
					}
				}
			});
		}
	}

	private void setTesto() {
		testoCompr.setText("Lo stress - il termine, preso dal linguaggio della fisica, indica lo sforzo," +
				"la tensione da carico - è l'agente di \"disturbo\" più trasversale che ci sia: affratella senza distinzioni di censo," +
				" età, sesso e rende le sue vittime solidali nella lotta contro il comune, subdolo nemico." +
				" Riconosciuto per la prima volta dalla scienza nel 1936 grazie a uno studio dell'austriaco Hans Seyle comparso su \"Nature\"," +
				" lo stress colpisce ovunque, spesso in maniera imprevedibile, anche se predilige precise situazioni-tipo." +
				" Per esempio, tra le pareti domestiche, nel traffico, in ufficio, in coda al supermercato, oppure al cinema. " +
				"Elemento scatenante è la tendenza a riempire il tempo di parole, gesti, aspettative, tensioni, " +
				"che non lasciano tregua e fanno sentire perennemente sotto pressione. " +
				"Se è difficile evitare del tutto quello che è diventato il naturale corollario della nostra frenesia quotidiana, " +
				"non resta che imparare a conviverci! Dando spazio alle strategie di difesa, sia fisiche sia psicologiche, " +
				"suggerite dal quarto volume della collana \"I manuali del benessere\", \"Combattere lo stress\". " +
				"Prima regola? Differenziare i vari tipi di stress. Per cogliere, dove possibile, " +
				"gli aspetti costruttivi e stimolanti dello stato di \"allerta\" che investe mente e corpo. " +
				"È l'\"eustress\", o stress verde, quello da sfruttare per trasformare le difficoltà in occasioni di miglioramento, " +
				"il peso della routine in passione. Mentre il \"distress\", o stress giallo, segnalato da una sottile ma persistente stanchezza, " +
				"va arginato con qualche modifica allo stile di vita (dieta, ritmo del sonno, organizzazione del tempo)." +
				" Invece a chi approda allo stress rosso - causa di tachicardia, mal di testa, gastrite - può essere di grande aiuto la filosofia delle medicine naturali." +
				" Secondo il brano, qualche piccola modifica dello stile di vita va consigliata a chi:");

	}
}
