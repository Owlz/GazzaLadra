package it.unisa.tirocinio.gazzaladra.activity;

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

import it.unisa.tirocinio.gazzaladra.SensorData;
import it.unisa.tirocinio.gazzaladra.Utils;
import it.unisa.tirocinio.gazzaladra.callbacks.CustomGestureListener;
import it.unisa.tirocinio.gazzaladra.callbacks.CustomScaleDetectorListener;
import it.unisa.tirocinio.gazzaladra.callbacks.WriteDataCallback;
import it.unisa.tirocinio.gazzaladra.database.Session;
import it.unisa.tirocinio.gazzaladra.file_writer.AsyncFileWriter;

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
		sessionFolder = "GazzaLadra" + "/" + s.getUidU() + "/" + s.getNumSession();
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

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (savedInstanceState != null) {
			startActivityTime = savedInstanceState.getLong("startActivityTime");
			sensorDataCollected = savedInstanceState.getParcelableArrayList("sensorDataCollected");
		} else {
			startActivityTime = System.currentTimeMillis();
			sensorDataCollected = new ArrayList<>();
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
		String x = "" + event.values[0];
		String y = "" + event.values[1];
		String z = "" + event.values[2];

		SensorData sd = new SensorData(
				fileName,
				System.currentTimeMillis(),
				Utils.getTimeRelativeTo(startActivityTime),
				getActivityId(),
				getFragmentId(),
				x, y, z,
				Utils.getOrientation(this)
		);
		sensorDataCollected.add(sd);
		/*
		AsyncFileWriter.write(new String[]{
				"" + Utils.getSystime(),
				"" + Utils.getTimeRelativeTo(startActivityTime),
				//activityId,
				x, y, z,
				"" + Utils.getOrientation(this)
		}, sessionFolder, fileName);*/
	}

	String viewClicked = null;

	//we get info about the event
	@Override
	public boolean onTouchEvent(MotionEvent event) {
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

		AsyncFileWriter.write(new String[]{
				"" + Utils.getSystime(),
				"" + Utils.getTimeRelativeTo(startActivityTime),
				//activityId,
				"" + event.getX(),
				"" + event.getY(),
				"" + event.getPressure(),
				"" + actionId,
				getWidgetIfAny(),
		}, sessionFolder, "rawOnTouchEvent");

		viewClicked = null;
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
		AsyncFileWriter.write(new String[]{
				"" + Utils.getSystime(),
				"" + Utils.getTimeRelativeTo(startActivityTime),
				//	activityId,
				event,
				"" + e.getX(),
				"" + e.getX(),
				"" + e.getPressure(),
				"" + e.getSize(),
				getWidgetIfAny(),
				"" + Utils.getOrientation(this)
		}, sessionFolder, "SingleEvents");
	}

	@Override
	public void fireKeyPress(int keyCode, int position) {
		//TODO: differisce da h-mog, scrivere documentazione
		AsyncFileWriter.write(new String[]{
				"" + Utils.getSystime(),
				"" + Utils.getTimeRelativeTo(startActivityTime),
				//activityId,
				"" + keyCode,
				"" + position,
				"" + Utils.getOrientation(this)
		}, sessionFolder, "KeyPressEvent");
	}

	@Override
	public void fireDoubleFingerEvent(ScaleGestureDetector scaleGestureDetector) {
		AsyncFileWriter.write(new String[]{
				"" + Utils.getSystime(),
				"" + Utils.getTimeRelativeTo(startActivityTime),
				//activityId,
				"" + scaleGestureDetector.getCurrentSpan(),
				"" + scaleGestureDetector.getScaleFactor(),
				"" + scaleGestureDetector.getTimeDelta(),
				"" + getWidgetIfAny(),
				"" + Utils.getOrientation(this)
		}, sessionFolder, "DoubleFingerEvents");

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