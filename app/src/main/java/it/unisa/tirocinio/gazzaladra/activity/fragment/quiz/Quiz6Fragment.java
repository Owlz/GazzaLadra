package it.unisa.tirocinio.gazzaladra.activity.fragment.quiz;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;

import it.unisa.tirocinio.gazzaladra.R;
import it.unisa.tirocinio.gazzaladra.activity.fragment.FragmentComunicator;
import it.unisa.tirocinio.gazzaladra.activity.fragment.FragmentTemplate;

public class Quiz6Fragment extends FragmentTemplate {
	@Override
	public String getFragmentIdNoCheck() {
		return "QuizImmagine";
	}

	private boolean isTimeExpired;
	private ProgressBar progressBar;
	private TextView tvTimer;
	private static final int REQUEST_TIMER = 10;
	private Timer timer;
	private Handler handler;
	private long timeStart;
	private Button next;

	private FragmentComunicator mListener;

	private ImageView iv;

	private ScaleGestureDetector sgd;

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
	String savedItemClicked;

	public Quiz6Fragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		isTimeExpired = false;
		timer = new Timer();
		handler = new Handler();

		matrix = new Matrix();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_quiz6, container, false);

		iv = v.findViewById(R.id.imageView);
		sgd = new ScaleGestureDetector(getContext(), new ScaleGestureDetector.SimpleOnScaleGestureListener() {
			@Override
			public boolean onScale(ScaleGestureDetector detector) {
				return false;
			}
		});
		iv.setScaleType(ImageView.ScaleType.MATRIX);
		iv.setOnTouchListener(new View.OnTouchListener() {
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
				return true;
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

}
