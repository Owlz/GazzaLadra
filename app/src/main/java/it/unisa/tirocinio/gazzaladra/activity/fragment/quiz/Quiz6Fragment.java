package it.unisa.tirocinio.gazzaladra.activity.fragment.quiz;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import it.unisa.tirocinio.gazzaladra.DomandeImg;
import it.unisa.tirocinio.gazzaladra.R;
import it.unisa.tirocinio.gazzaladra.Utils;
import it.unisa.tirocinio.gazzaladra.activity.TemplateActivity;
import it.unisa.tirocinio.gazzaladra.activity.fragment.FragmentComunicator;
import it.unisa.tirocinio.gazzaladra.activity.fragment.FragmentTemplate;
import it.unisa.tirocinio.gazzaladra.callbacks.CustomTextWatcher;
import it.unisa.tirocinio.gazzaladra.data.FragmentData;

public class Quiz6Fragment extends FragmentTemplate {
	@Override
	public String getFragmentIdNoCheck() {
		return "QuizImmagine";
	}

	private boolean isTimeExpired;
	private ProgressBar progressBar;
	private TextView tvTimer;
	private static final int REQUEST_TIMER = 40;
	private Timer timer;
	private Handler handler;
	private long timeStart;
	private CustomTimer task;

	private FragmentComunicator mListener;

	private ImageView iv;
	private Button next;
	private EditText edit;
	private TextView domanda;


	// These matrices will be used to move and zoom image
	Matrix matrix = new Matrix();
	Matrix savedMatrix = new Matrix();

	// We can be in one of these 3 states
	static final int NONE = 0;
	static final int DRAG = 1;
	static final int ZOOM = 2;
	int mode = NONE;

	// Remember some things for zooming
	PointF start = new PointF();
	PointF mid = new PointF();
	float oldDist = 1f;

	private final static DomandeImg[] domande = new DomandeImg[]{
			new DomandeImg("Cosa si trova sulla testa del bufalo?",
					new String[]{
							"rana",
							"rospo",
							"anfibio"
					}),
			new DomandeImg("Di che colore sono i robot sotto l'ombrellone bianco e rosso?",
					new String[]{
							"argento",
							"grigio",
							"metallo"
					}),
			new DomandeImg("Quanti dolci sta sollevando il pasticcere?",
					new String[]{
							"due",
							"2",
							"sei",
							"6"
					})
	};
	private Random rand;
	private DomandeImg domandaScelta;

	public Quiz6Fragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		isTimeExpired = false;
		timer = new Timer();
		handler = new Handler();
		task = new CustomTimer();

		matrix = new Matrix();
		rand = new Random();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_quiz6, container, false);

		progressBar = v.findViewById(R.id.progressBar);
		tvTimer = v.findViewById(R.id.countdown_tv);
		tvTimer.setText(REQUEST_TIMER + "");
		progressBar.setProgress(0);
		progressBar.setMax(REQUEST_TIMER);
		timer.scheduleAtFixedRate(task, 500, 1000);
		isTimeExpired = false;

		iv = v.findViewById(R.id.imageView);
		iv.setScaleType(ImageView.ScaleType.MATRIX);

		next = v.findViewById(R.id.next);
		edit = v.findViewById(R.id.edit);
		domanda = v.findViewById(R.id.domanda);

		int numDomanda = rand.nextInt(domande.length);
		domandaScelta = domande[numDomanda];

		domanda.setText(domandaScelta.getDomanda());

		next.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				timer.cancel();

				boolean hasMatch = false;
				if (!isTimeExpired) {
					for (String risp : domandaScelta.getRisposte()) {
						if (risp.equalsIgnoreCase(edit.getText().toString())) {
							hasMatch = true;
							break;
						}
					}
				}
				if (hasMatch) {
					Toast.makeText(getContext(), "Risposta esatta", Toast.LENGTH_SHORT).show();
					mListener.onFragmentEnd(new FragmentData(Quiz6Fragment.super.getFragmentId(), null, true, timeStart, System.currentTimeMillis()));
				} else {
					Toast.makeText(getContext(), "Risposta errata", Toast.LENGTH_SHORT).show();
					mListener.onFragmentEnd(new FragmentData(Quiz6Fragment.super.getFragmentId(), null, false, timeStart, System.currentTimeMillis()));
				}
			}
		});

		edit.addTextChangedListener(new CustomTextWatcher((TemplateActivity) getActivity()));
		for (View child : Utils.getAllChildrenBFS(v)) {
			if (child instanceof ImageView) {
				child.setOnTouchListener(new View.OnTouchListener() {
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						ImageView view = (ImageView) v;

						// Handle touch events here...
						switch (event.getAction() & MotionEvent.ACTION_MASK) {
							case MotionEvent.ACTION_DOWN:
								savedMatrix.set(matrix);
								start.set(event.getX(), event.getY());
								mode = DRAG;
								break;
							case MotionEvent.ACTION_POINTER_DOWN:
								oldDist = spacing(event);
								if (oldDist > 10f) {
									savedMatrix.set(matrix);
									midPoint(mid, event);
									mode = ZOOM;
								}
								break;
							case MotionEvent.ACTION_UP:
							case MotionEvent.ACTION_POINTER_UP:
								mode = NONE;
								break;
							case MotionEvent.ACTION_MOVE:
								if (mode == DRAG) {
									// ...
									matrix.set(savedMatrix);
									matrix.postTranslate(event.getX() - start.x, event.getY()
											- start.y);
								} else if (mode == ZOOM) {
									float newDist = spacing(event);
									if (newDist > 10f) {
										matrix.set(savedMatrix);
										float scale = newDist / oldDist;
										matrix.postScale(scale, scale, mid.x, mid.y);
									}
								}
								break;
						}

						view.setImageMatrix(matrix);
						((TemplateActivity) getActivity()).widgetTouchDispatcher(v, event);
						return true;
					}
				});
			} else {
				child.setOnTouchListener(new View.OnTouchListener() {
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						return ((TemplateActivity) getActivity()).widgetTouchDispatcher(v, event);
					}
				});
			}
		}
		timeStart = System.currentTimeMillis();
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

	/**
	 * Determine the space between the first two fingers
	 */
	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return Float.parseFloat(Math.sqrt(x * x + y * y) + "");
	}

	/**
	 * Calculate the mid point of the first two fingers
	 */
	private void midPoint(PointF point, MotionEvent event) {
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);
	}

	private int counter = 0;

	private class CustomTimer extends TimerTask {
		@Override
		public void run() {
			handler.post(new Runnable() {
				@Override
				public void run() {
					counter++;
					progressBar.setProgress(counter);

					tvTimer.setText((REQUEST_TIMER - counter) + "");

					if (counter >= REQUEST_TIMER) {
						timer.cancel();

						tvTimer.setText("0 - Tempo scaduto!");
						isTimeExpired = true;
					}
				}
			});
		}
	}

}
