package it.unisa.tirocinio.gazzaladra.callbacks;

import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

public interface WriteDataCallback {
	/**
	 * Key fired from a virtual keyboard
	 *
	 * @param keyCode  ascii value of the character (127 is backspace, -1 is autocorrector)
	 * @param position position of the new char/backspace (-1 is autocorrector)
	 */
	void fireKeyPress(int keyCode, int position);

	/**
	 * Double finger event get registered (pinch event)
	 *
	 * @param scaleGestureDetector
	 */
	void fireDoubleFingerEvent(ScaleGestureDetector scaleGestureDetector);


	/**
	 * Event that require single MotionEvent
	 *
	 * @param event     the event object
	 * @param eventType the type of event
	 */
	void fireSingleEvent(MotionEvent event, int eventType);
}