package it.unisa.tirocinio.gazzaladra.callbacks;

import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;

public class CustomScaleDetectorListener extends SimpleOnScaleGestureListener {
	WriteDataCallback callback;

	public CustomScaleDetectorListener(WriteDataCallback callback) {
		this.callback = callback;
	}

	@Override
	public boolean onScale(ScaleGestureDetector detector) {
		callback.fireDoubleFingerEvent(detector);

		return super.onScale(detector);
	}

	@Override
	public boolean onScaleBegin(ScaleGestureDetector detector) {
		return super.onScaleBegin(detector);
	}

	@Override
	public void onScaleEnd(ScaleGestureDetector detector) {
		super.onScaleEnd(detector);
	}
}
