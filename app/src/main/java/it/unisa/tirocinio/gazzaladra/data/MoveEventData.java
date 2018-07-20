package it.unisa.tirocinio.gazzaladra.data;

import android.os.Parcel;
import android.os.Parcelable;

public class MoveEventData implements Parcelable {
	public long timeEvent;
	public long relativeToStartTimeEvent;
	public String activityId;
	public String fragmentId;
	public String eventCaptured;
	public String x, y;
	public String distX, distY;
	public String pressure;
	public String clickedWidget;
	public int deviceOrientation;

	public MoveEventData(long timeEvent, long relativeToStartTimeEvent, String activityId, String fragmentId, String eventCaptured, String x, String y, String distX, String distY, String pressure, String clickedWidget, int deviceOrientation) {
		this.timeEvent = timeEvent;
		this.relativeToStartTimeEvent = relativeToStartTimeEvent;
		this.activityId = activityId;
		this.fragmentId = fragmentId;
		this.eventCaptured = eventCaptured;
		this.x = x;
		this.y = y;
		this.distX = distX;
		this.distY = distY;
		this.pressure = pressure;
		this.clickedWidget = clickedWidget;
		this.deviceOrientation = deviceOrientation;
	}

	public String[] toStringArray() {
		return new String[]{
				"" + this.timeEvent,
				"" + this.relativeToStartTimeEvent,
				this.activityId,
				this.fragmentId,
				this.eventCaptured,
				this.x,
				this.y,
				this.distX,
				this.distY,
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
		dest.writeString(this.distX);
		dest.writeString(this.distY);
		dest.writeString(this.pressure);
		dest.writeString(this.clickedWidget);
		dest.writeInt(this.deviceOrientation);
	}

	protected MoveEventData(Parcel in) {
		this.timeEvent = in.readLong();
		this.relativeToStartTimeEvent = in.readLong();
		this.activityId = in.readString();
		this.fragmentId = in.readString();
		this.eventCaptured = in.readString();
		this.x = in.readString();
		this.y = in.readString();
		this.distX = in.readString();
		this.distY = in.readString();
		this.pressure = in.readString();
		this.clickedWidget = in.readString();
		this.deviceOrientation = in.readInt();
	}

	public static final Parcelable.Creator<MoveEventData> CREATOR = new Parcelable.Creator<MoveEventData>() {
		@Override
		public MoveEventData createFromParcel(Parcel source) {
			return new MoveEventData(source);
		}

		@Override
		public MoveEventData[] newArray(int size) {
			return new MoveEventData[size];
		}
	};
}
