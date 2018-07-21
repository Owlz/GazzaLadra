package it.unisa.tirocinio.gazzaladra.activity;

import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import it.unisa.tirocinio.gazzaladra.Utils;
import it.unisa.tirocinio.gazzaladra.callbacks.CustomGestureListener;
import it.unisa.tirocinio.gazzaladra.callbacks.CustomScaleDetectorListener;
import it.unisa.tirocinio.gazzaladra.callbacks.WriteDataCallback;
import it.unisa.tirocinio.gazzaladra.data.KeyPressData;
import it.unisa.tirocinio.gazzaladra.data.MoveEventData;
import it.unisa.tirocinio.gazzaladra.data.RawTouchData;
import it.unisa.tirocinio.gazzaladra.data.ScaleEventData;
import it.unisa.tirocinio.gazzaladra.data.SensorData;
import it.unisa.tirocinio.gazzaladra.data.SingleFingerEventData;
import it.unisa.tirocinio.gazzaladra.database.Session;

public abstract class TemplateActivity extends AppCompatActivity implements SensorEventListener, WriteDataCallback {
	/**
	 * Per inizializzare un templateactivity c'è bisogno di:
	 * - session folder setSessionFolder()
	 * - activityID setActivityId()
	 * - fragmentID setFragmentId()
	 */
	private String sessionFolder = null;
	private String activityId = null;
	private String fragmentId = null;

	public void setSessionFolder(Session s) {
		String userName = String.format(Locale.ITALY, "%03d", s.getUidU());
		String sessionNum = String.format(Locale.ITALY, "%03d", s.getNumSession());

		sessionFolder = "GazzaLadra" + "/" + userName + "/" + sessionNum;
	}

	public String getSessionFolder() {
		if (sessionFolder == null || sessionFolder.equals(""))
			throw new RuntimeException("Impossibile ottenere la cartella, l'hai inizializzata?");
		return sessionFolder;
	}

	public void setActivityId(String id) {
		activityId = id;
	}

	public String getActivityId() {
		if (activityId == null || activityId.equals(""))
			throw new RuntimeException("Impossibile ottenere l'activity id, l'hai inizializzato?");
		return activityId;
	}

	public void setFragmentId(String id) {
		fragmentId = id;
	}

	public String getFragmentId() {
		if (fragmentId == null || fragmentId.equals(""))
			throw new RuntimeException("Impossibile ottenere il fragment id, l'hai inizializzato?");
		return fragmentId;
	}

	private long startActivityTime;

	//Sensors related data
	private SensorManager sm;
	private Sensor accelerometer;
	private Sensor gyroscope;
	private Sensor magnetometer;

	//Gesture Detector
	private GestureDetector gd;
	private ScaleGestureDetector sgd;

	//Data Structures
	private ArrayList<SensorData> sensorDataCollected;
	private ArrayList<RawTouchData> rawTouchDataCollected;
	private ArrayList<ScaleEventData> scaleEventDataCollected;
	private ArrayList<SingleFingerEventData> singleFingerEventDataCollected;
	private ArrayList<KeyPressData> keyPressDataCollected;
	private ArrayList<MoveEventData> moveEventDataCollected;

	public ArrayList<SensorData> getSensorDataCollected() {
		return sensorDataCollected;
	}

	public ArrayList<RawTouchData> getRawTouchDataCollected() {
		return rawTouchDataCollected;
	}

	public ArrayList<ScaleEventData> getScaleEventDataCollected() {
		return scaleEventDataCollected;
	}

	public ArrayList<SingleFingerEventData> getSingleFingerEventDataCollected() {
		return singleFingerEventDataCollected;
	}

	public ArrayList<KeyPressData> getKeyPressDataCollected() {
		return keyPressDataCollected;
	}

	public ArrayList<MoveEventData> getMoveEventDataCollected() {
		return moveEventDataCollected;
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		if (savedInstanceState != null) {
			startActivityTime = savedInstanceState.getLong("startActivityTime");
			sensorDataCollected = savedInstanceState.getParcelableArrayList("sensorDataCollected");
			rawTouchDataCollected = savedInstanceState.getParcelableArrayList("rawTouchDataCollected");
			scaleEventDataCollected = savedInstanceState.getParcelableArrayList("scaleEventDataCollected");
			singleFingerEventDataCollected = savedInstanceState.getParcelableArrayList("singleFingerEventDataCollected");
			keyPressDataCollected = savedInstanceState.getParcelableArrayList("keyPressDataCollected");
			moveEventDataCollected = savedInstanceState.getParcelableArrayList("moveEventDataCollected");
			sessionFolder = savedInstanceState.getString("sessionFolder");
			activityId = savedInstanceState.getString("activityId");
			fragmentId = savedInstanceState.getString("fragmentId");
		} else {
			startActivityTime = System.currentTimeMillis();
			sensorDataCollected = new ArrayList<>();
			singleFingerEventDataCollected = new ArrayList<>();
			scaleEventDataCollected = new ArrayList<>();
			rawTouchDataCollected = new ArrayList<>();
			keyPressDataCollected = new ArrayList<>();
			moveEventDataCollected = new ArrayList<>();
		}

		gd = new GestureDetector(this, new CustomGestureListener(this));
		sgd = new ScaleGestureDetector(this, new CustomScaleDetectorListener(this));

		sm = (SensorManager) getSystemService(SENSOR_SERVICE);
		accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		gyroscope = sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
		magnetometer = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

		if (accelerometer == null) {
			Toast.makeText(this, "No accelerometer found :c", Toast.LENGTH_SHORT).show();
		}
		if (gyroscope == null) {
			Toast.makeText(this, "No gyroscope found :c", Toast.LENGTH_SHORT).show();
		}
		if (magnetometer == null) {
			Toast.makeText(this, "No magnetometer found :c", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		sm.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
		sm.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
		sm.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onPause() {
		super.onPause();
		sm.unregisterListener(this);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putLong("startActivityTime", startActivityTime);
		outState.putParcelableArrayList("sensorDataCollected", sensorDataCollected);
		outState.putParcelableArrayList("rawTouchDataCollected", rawTouchDataCollected);
		outState.putParcelableArrayList("singleFingerEventDataCollected", singleFingerEventDataCollected);
		outState.putParcelableArrayList("scaleEventDataCollected", scaleEventDataCollected);
		outState.putParcelableArrayList("keyPressDataCollected", keyPressDataCollected);
		outState.putParcelableArrayList("moveEventDataCollected", moveEventDataCollected);
		outState.putString("sessionFolder", sessionFolder);
		outState.putString("activityId", activityId);
		outState.putString("fragmentId", fragmentId);
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		String fileName;
		switch (event.sensor.getType()) {
			case Sensor.TYPE_ACCELEROMETER:
				fileName = "accelerometer";
				break;
			case Sensor.TYPE_GYROSCOPE:
				fileName = "gyroscope";
				break;
			case Sensor.TYPE_MAGNETIC_FIELD:
				fileName = "magnetic";
				break;
			default:
				return;
		}

		SensorData sd = new SensorData(
				fileName,
				System.currentTimeMillis(),
				Utils.getTimeRelativeTo(startActivityTime),
				getActivityId(),
				getFragmentId(),
				event.values[0],
				event.values[1],
				event.values[2],
				Utils.getOrientation(this)
		);
		sensorDataCollected.add(sd);
	}

	String viewClicked = null;
	long millisecTouchEventStart = 0;
	long millisecOffset = 0;

	//we get info about the event
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		millisecOffset = System.currentTimeMillis();
		millisecTouchEventStart = Utils.getTimeRelativeTo(startActivityTime);

		gd.onTouchEvent(event);
		sgd.onTouchEvent(event);

		int actionId = -1;
		switch (event.getActionMasked()) {
			case MotionEvent.ACTION_DOWN:           //first finger on the screen
				actionId = 0;
				break;
			case MotionEvent.ACTION_POINTER_DOWN:   //new finger on the screen and isn't the first
				actionId = 5;
				break;
			case MotionEvent.ACTION_MOVE:           //a finger on the screen moves
				actionId = 2;
				break;
			case MotionEvent.ACTION_POINTER_UP:     // a finger leaves the screen
				actionId = 6;
				break;
			case MotionEvent.ACTION_UP:             //last finger leaves the screen
				actionId = 1;
				break;
		}

		RawTouchData rtd = new RawTouchData(
				millisecOffset,
				millisecTouchEventStart,
				this.getActivityId(),
				this.getFragmentId(),
				event.getX(),
				event.getY(),
				event.getPressure(),
				event.getSize(),
				"" + actionId,
				getWidgetIfAny(),
				Utils.getOrientation(this)
		);
		rawTouchDataCollected.add(rtd);

		viewClicked = null;
		millisecOffset = -1;
		millisecTouchEventStart = -1;

		return super.onTouchEvent(event);
	}

	// we get info about the widget clicked
	public boolean widgetTouchDispatcher(View v, MotionEvent event) {

		if (!(v instanceof ViewGroup)) {
//			String[] a = v.getClass().getName().split("\\.");
//			viewClicked = a[a.length-1];
			viewClicked = v.getClass().getName();
		}
		return onTouchEvent(event);
	}

	@Override
	public void fireSingleEvent(MotionEvent e, int eventType) {
		String event = "";
		switch (eventType) {
			case CustomGestureListener.SINGLE_TAP:
				event = "Single tap";
				break;
			case CustomGestureListener.DOUBLE_TAP:
				event = "Double tap";
				break;
			case CustomGestureListener.LONG_PRESS:
				event = "Long press";
				break;
		}

		SingleFingerEventData sf = new SingleFingerEventData(
				millisecTouchEventStart,
				millisecOffset,
				this.getActivityId(),
				this.getFragmentId(),
				event,
				e.getX(),
				e.getY(),
				e.getPressure(),
				getWidgetIfAny(),
				Utils.getOrientation(this)
		);

		singleFingerEventDataCollected.add(sf);

	}

	@Override
	public void fireKeyPress(int keyCode, int position) {
		KeyPressData kpd = new KeyPressData(
				millisecTouchEventStart,
				millisecOffset,
				this.getActivityId(),
				this.getFragmentId(),
				"" + keyCode,
				"" + position,
				"" + Utils.getOrientation(this)
		);
		keyPressDataCollected.add(kpd);
	}

	@Override
	public void fireDoubleFingerEvent(ScaleGestureDetector scaleDetector) {
		ScaleEventData scaleEventData = new ScaleEventData(
				millisecTouchEventStart,
				millisecOffset,
				this.getActivityId(),
				this.getFragmentId(),
				scaleDetector.getTimeDelta(),
				scaleDetector.getScaleFactor(),
				scaleDetector.getCurrentSpanX(),
				scaleDetector.getCurrentSpanY(),
				scaleDetector.getCurrentSpan(),
				scaleDetector.getEventTime(),
				scaleDetector.getFocusX(),
				scaleDetector.getFocusY(),
				scaleDetector.getPreviousSpan(),
				scaleDetector.getPreviousSpanX(),
				scaleDetector.getPreviousSpanY()
		);

		scaleEventDataCollected.add(scaleEventData);
	}

	@Override
	public void fireMoveEvent(MotionEvent e, float distX, float distY, int eventType) {
		String event = "";
		switch (eventType) {
			case CustomGestureListener.FLING:
				event = "Fling";
				break;
			case CustomGestureListener.SCROLL:
				event = "Scroll";
				break;
		}

		MoveEventData m = new MoveEventData(
				millisecTouchEventStart,
				millisecOffset,
				this.getActivityId(),
				this.getFragmentId(),
				event,
				e.getX(),
				e.getY(),
				distX,
				distY,
				e.getPressure(),
				getWidgetIfAny(),
				Utils.getOrientation(this)
		);

		moveEventDataCollected.add(m);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		//We have to keep this method even if we're not using it
	}

	private String getWidgetIfAny() {
		return (viewClicked != null) ? viewClicked : "noWidget";
	}

	public class OnTouchDispatcher implements View.OnTouchListener {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			return widgetTouchDispatcher(v, event);
		}
	}
}