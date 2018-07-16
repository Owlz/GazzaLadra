package it.unisa.tirocinio.gazzaladra.callbacks;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class CustomGestureListener extends GestureDetector.SimpleOnGestureListener {

	private WriteDataCallback callback;
	public final static int SINGLE_TAP = 0;
	public final static int DOUBLE_TAP = 1;
	public final static int LONG_PRESS = 2;

	public CustomGestureListener(WriteDataCallback callback) {
		super();
		this.callback = callback;
	}

	//Single time events
	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
		callback.fireSingleEvent(e, SINGLE_TAP);
		return super.onSingleTapConfirmed(e);
	}

	@Override
	public boolean onDoubleTap(MotionEvent e) {
		callback.fireSingleEvent(e, DOUBLE_TAP);
		return super.onDoubleTap(e);
	}

	@Override
	public void onLongPress(MotionEvent e) {
		callback.fireSingleEvent(e, LONG_PRESS);
		super.onLongPress(e);
	}

	//Over-time events
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		return super.onFling(e1, e2, velocityX, velocityY);
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		return super.onScroll(e1, e2, distanceX, distanceY);
	}
}
