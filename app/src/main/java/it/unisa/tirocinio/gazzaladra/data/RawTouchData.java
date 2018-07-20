package it.unisa.tirocinio.gazzaladra.data;

import android.os.Parcel;
import android.os.Parcelable;

public class RawTouchData implements Parcelable {
	public long timeEvent;
	public long relativeToStartTimeEvent;
	public String activityId;
	public String fragmentId;
	public String x, y;
	public String pressure;
	public String size;
	public String actionId;
	public String clickedWidget;
	public int deviceOrientation;

	public RawTouchData(long timeEvent, long relativeToStartTimeEvent, String activityId, String fragmentId, String x, String y, String pressure, String size, String actionId, String clickedWidget, int deviceOrientation) {
		this.timeEvent = timeEvent;
		this.relativeToStartTimeEvent = relativeToStartTimeEvent;
		this.activityId = activityId;
		this.fragmentId = fragmentId;
		this.x = x;
		this.y = y;
		this.pressure = pressure;
		this.size = size;
		this.actionId = actionId;
		this.clickedWidget = clickedWidget;
		this.deviceOrientation = deviceOrientation;
	}

	public String[] toStringArray() {
		return new String[]{
				this.activityId,
				this.fragmentId,
				"" + this.timeEvent,
				"" + this.relativeToStartTimeEvent,
				this.x,
				this.y,
				this.pressure,
				this.size,
				this.actionId,
				this.clickedWidget,
				"" + this.deviceOrientation
		};
	}

	protected RawTouchData(Parcel in) {
		timeEvent = in.readLong();
		relativeToStartTimeEvent = in.readLong();
		activityId = in.readString();
		fragmentId = in.readString();
		x = in.readString();
		y = in.readString();
		pressure = in.readString();
		size = in.readString();
		actionId = in.readString();
		clickedWidget = in.readString();
		deviceOrientation = in.readInt();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(timeEvent);
		dest.writeLong(relativeToStartTimeEvent);
		dest.writeString(activityId);
		dest.writeString(fragmentId);
		dest.writeString(x);
		dest.writeString(y);
		dest.writeString(pressure);
		dest.writeString(size);
		dest.writeString(actionId);
		dest.writeString(clickedWidget);
		dest.writeInt(deviceOrientation);
	}

	@SuppressWarnings("unused")
	public static final Parcelable.Creator<RawTouchData> CREATOR = new Parcelable.Creator<RawTouchData>() {
		@Override
		public RawTouchData createFromParcel(Parcel in) {
			return new RawTouchData(in);
		}

		@Override
		public RawTouchData[] newArray(int size) {
			return new RawTouchData[size];
		}
	};
}
