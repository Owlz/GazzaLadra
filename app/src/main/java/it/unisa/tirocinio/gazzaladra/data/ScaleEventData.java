package it.unisa.tirocinio.gazzaladra.data;

import android.os.Parcel;
import android.os.Parcelable;

import it.unisa.tirocinio.gazzaladra.Utils;

public class ScaleEventData implements Parcelable {
	public long timeEvent;
	public long relativeToStartTimeEvent;
	public String activityId;
	public String fragmentId;

	public long timeDelta;
	public float scaleFactor;
	public float currentSpanX;
	public float currentSpanY;
	public float currentSpan;
	public long eventTime;
	public float focusX;
	public float focusY;
	public float previousSpan;
	public float previousSpanX;
	public float previousSpanY;

	public String[] toStringArray() {
		return new String[]{
				"" + this.timeEvent,
				"" + this.relativeToStartTimeEvent,
				this.activityId,
				this.fragmentId,
				"" + this.timeDelta,
				Utils.getFormatted(this.scaleFactor),
				Utils.getFormatted(this.currentSpanX),
				Utils.getFormatted(this.currentSpanY),
				Utils.getFormatted(this.currentSpan),
				"" + this.eventTime,
				Utils.getFormatted(this.focusX),
				Utils.getFormatted(this.focusY),
				Utils.getFormatted(this.previousSpan),
				Utils.getFormatted(this.previousSpanX),
				Utils.getFormatted(this.previousSpanY)
		};
	}

	public ScaleEventData(long timeEvent, long relativeToStartTimeEvent, String activityId, String fragmentId, long timeDelta, float scaleFactor, float currentSpanX, float currentSpanY, float currentSpan, long eventTime, float focusX, float focusY, float previousSpan, float previousSpanX, float previousSpanY) {
		this.timeEvent = timeEvent;
		this.relativeToStartTimeEvent = relativeToStartTimeEvent;
		this.activityId = activityId;
		this.fragmentId = fragmentId;
		this.timeDelta = timeDelta;
		this.scaleFactor = scaleFactor;
		this.currentSpanX = currentSpanX;
		this.currentSpanY = currentSpanY;
		this.currentSpan = currentSpan;
		this.eventTime = eventTime;
		this.focusX = focusX;
		this.focusY = focusY;
		this.previousSpan = previousSpan;
		this.previousSpanX = previousSpanX;
		this.previousSpanY = previousSpanY;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.timeEvent);
		dest.writeLong(this.relativeToStartTimeEvent);
		dest.writeString(this.activityId);
		dest.writeString(this.fragmentId);
		dest.writeLong(this.timeDelta);
		dest.writeFloat(this.scaleFactor);
		dest.writeFloat(this.currentSpanX);
		dest.writeFloat(this.currentSpanY);
		dest.writeFloat(this.currentSpan);
		dest.writeLong(this.eventTime);
		dest.writeFloat(this.focusX);
		dest.writeFloat(this.focusY);
		dest.writeFloat(this.previousSpan);
		dest.writeFloat(this.previousSpanX);
		dest.writeFloat(this.previousSpanY);
	}

	protected ScaleEventData(Parcel in) {
		this.timeEvent = in.readLong();
		this.relativeToStartTimeEvent = in.readLong();
		this.activityId = in.readString();
		this.fragmentId = in.readString();
		this.timeDelta = in.readLong();
		this.scaleFactor = in.readFloat();
		this.currentSpanX = in.readFloat();
		this.currentSpanY = in.readFloat();
		this.currentSpan = in.readFloat();
		this.eventTime = in.readLong();
		this.focusX = in.readFloat();
		this.focusY = in.readFloat();
		this.previousSpan = in.readFloat();
		this.previousSpanX = in.readFloat();
		this.previousSpanY = in.readFloat();
	}

	public static final Creator<ScaleEventData> CREATOR = new Creator<ScaleEventData>() {
		@Override
		public ScaleEventData createFromParcel(Parcel source) {
			return new ScaleEventData(source);
		}

		@Override
		public ScaleEventData[] newArray(int size) {
			return new ScaleEventData[size];
		}
	};
}
