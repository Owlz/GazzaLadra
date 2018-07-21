package it.unisa.tirocinio.gazzaladra.data;

import android.os.Parcel;
import android.os.Parcelable;

import it.unisa.tirocinio.gazzaladra.Utils;

public class MoveEventData implements Parcelable {
	public long timeEvent;
	public long relativeToStartTimeEvent;
	public String activityId;
	public String fragmentId;
	public String eventCaptured;
	public float x, y;
	public float distX, distY;
	public float pressure;
	public String clickedWidget;
	public int deviceOrientation;

	public MoveEventData(long timeEvent, long relativeToStartTimeEvent, String activityId, String fragmentId, String eventCaptured, float x, float y, float distX, float distY, float pressure, String clickedWidget, int deviceOrientation) {
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
				this.activityId,
				this.fragmentId,
				"" + this.timeEvent,
				"" + this.relativeToStartTimeEvent,
				this.eventCaptured,
				Utils.getFormatted(this.x),
				Utils.getFormatted(this.y),
				Utils.getFormatted(this.distX),
				Utils.getFormatted(this.distY),
				Utils.getFormatted(this.pressure),
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
		dest.writeFloat(this.x);
		dest.writeFloat(this.y);
		dest.writeFloat(this.distX);
		dest.writeFloat(this.distY);
		dest.writeFloat(this.pressure);
		dest.writeString(this.clickedWidget);
		dest.writeInt(this.deviceOrientation);
	}

	protected MoveEventData(Parcel in) {
		this.timeEvent = in.readLong();
		this.relativeToStartTimeEvent = in.readLong();
		this.activityId = in.readString();
		this.fragmentId = in.readString();
		this.eventCaptured = in.readString();
		this.x = in.readFloat();
		this.y = in.readFloat();
		this.distX = in.readFloat();
		this.distY = in.readFloat();
		this.pressure = in.readFloat();
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
