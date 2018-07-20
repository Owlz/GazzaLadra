package it.unisa.tirocinio.gazzaladra.data;

import android.os.Parcel;
import android.os.Parcelable;

public class SingleFingerEventData implements Parcelable {
	public long timeEvent;
	public long relativeToStartTimeEvent;
	public String activityId;
	public String fragmentId;
	public String eventCaptured;
	public String x, y;
	public String pressure;
	public String clickedWidget;
	public int deviceOrientation;

	public SingleFingerEventData(long timeEvent, long relativeToStartTimeEvent, String activityId, String fragmentId, String eventCaptured, String x, String y, String pressure, String clickedWidget, int deviceOrientation) {
		this.timeEvent = timeEvent;
		this.relativeToStartTimeEvent = relativeToStartTimeEvent;
		this.activityId = activityId;
		this.fragmentId = fragmentId;
		this.eventCaptured = eventCaptured;
		this.x = x;
		this.y = y;
		this.pressure = pressure;
		this.clickedWidget = clickedWidget;
		this.deviceOrientation = deviceOrientation;
	}

	public String[] toStringArray() {
		return new String[]{
				this.activityId,
				this.fragmentId,
				this.eventCaptured,
				"" + this.timeEvent,
				"" + this.relativeToStartTimeEvent,
				this.x,
				this.y,
				this.pressure,
				this.clickedWidget,
				"" + this.deviceOrientation
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
		dest.writeString(this.eventCaptured);
		dest.writeString(this.x);
		dest.writeString(this.y);
		dest.writeString(this.pressure);
		dest.writeString(this.clickedWidget);
		dest.writeInt(this.deviceOrientation);
	}

	protected SingleFingerEventData(Parcel in) {
		this.timeEvent = in.readLong();
		this.relativeToStartTimeEvent = in.readLong();
		this.activityId = in.readString();
		this.fragmentId = in.readString();
		this.eventCaptured = in.readString();
		this.x = in.readString();
		this.y = in.readString();
		this.pressure = in.readString();
		this.clickedWidget = in.readString();
		this.deviceOrientation = in.readInt();
	}

	public static final Parcelable.Creator<SingleFingerEventData> CREATOR = new Parcelable.Creator<SingleFingerEventData>() {
		@Override
		public SingleFingerEventData createFromParcel(Parcel source) {
			return new SingleFingerEventData(source);
		}

		@Override
		public SingleFingerEventData[] newArray(int size) {
			return new SingleFingerEventData[size];
		}
	};
}
