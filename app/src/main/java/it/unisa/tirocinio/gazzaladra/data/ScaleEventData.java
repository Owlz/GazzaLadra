package it.unisa.tirocinio.gazzaladra.data;

import android.os.Parcel;
import android.os.Parcelable;

public class ScaleEventData implements Parcelable {
	public long timeEvent;
	public long relativeToStartTimeEvent;
	public String activityId;
	public String fragmentId;

	public String timeDelta;
	public String scaleFactor;
	public String currentSpanX;
	public String currentSpanY;
	public String currentSpan;
	public String eventTime;
	public String focusX;
	public String focusY;
	public String previousSpan;
	public String previousSpanX;
	public String previousSpanY;


	public ScaleEventData(long timeEvent, long relativeToStartTimeEvent, String activityId, String fragmentId, String timeDelta, String scaleFactor, String currentSpanX, String currentSpanY, String currentSpan, String eventTime, String focusX, String focusY, String previousSpan, String previousSpanX, String previousSpanY) {
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

	public String[] toStringArray() {
		return new String[]{
				"" + this.timeEvent,
				"" + this.relativeToStartTimeEvent,
				this.activityId,
				this.fragmentId,
				this.timeDelta,
				this.scaleFactor,
				this.currentSpanX,
				this.currentSpanY,
				this.currentSpan,
				this.eventTime,
				this.focusX,
				this.focusY,
				this.previousSpan,
				this.previousSpanX,
				this.previousSpanY
		};
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
		dest.writeString(this.timeDelta);
		dest.writeString(this.scaleFactor);
		dest.writeString(this.currentSpanX);
		dest.writeString(this.currentSpanY);
		dest.writeString(this.currentSpan);
		dest.writeString(this.eventTime);
		dest.writeString(this.focusX);
		dest.writeString(this.focusY);
		dest.writeString(this.previousSpan);
		dest.writeString(this.previousSpanX);
		dest.writeString(this.previousSpanY);
	}

	protected ScaleEventData(Parcel in) {
		this.timeEvent = in.readLong();
		this.relativeToStartTimeEvent = in.readLong();
		this.activityId = in.readString();
		this.fragmentId = in.readString();
		this.timeDelta = in.readString();
		this.scaleFactor = in.readString();
		this.currentSpanX = in.readString();
		this.currentSpanY = in.readString();
		this.currentSpan = in.readString();
		this.eventTime = in.readString();
		this.focusX = in.readString();
		this.focusY = in.readString();
		this.previousSpan = in.readString();
		this.previousSpanX = in.readString();
		this.previousSpanY = in.readString();
	}

	public static final Parcelable.Creator<ScaleEventData> CREATOR = new Parcelable.Creator<ScaleEventData>() {
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
