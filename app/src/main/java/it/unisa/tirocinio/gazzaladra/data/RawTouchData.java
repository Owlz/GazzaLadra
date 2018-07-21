package it.unisa.tirocinio.gazzaladra.data;

import android.os.Parcel;
import android.os.Parcelable;

import it.unisa.tirocinio.gazzaladra.Utils;

public class RawTouchData implements Parcelable {
	public long timeEvent;
	public long relativeToStartTimeEvent;
	public String activityId;
	public String fragmentId;
	public float x, y;
	public float pressure;
	public float size;
	public String actionId;
	public String clickedWidget;
	public int deviceOrientation;

	public String[] toStringArray() {
		return new String[]{
				this.activityId,
				this.fragmentId,
				"" + this.timeEvent,
				"" + this.relativeToStartTimeEvent,
				Utils.getFormatted(this.x),
				Utils.getFormatted(this.y),
				Utils.getFormatted(this.pressure),
				Utils.getFormatted(this.size),
				this.actionId,
				this.clickedWidget,
				"" + this.deviceOrientation
		};
	}

	public RawTouchData(long timeEvent, long relativeToStartTimeEvent, String activityId, String fragmentId, float x, float y, float pressure, float size, String actionId, String clickedWidget, int deviceOrientation) {
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
		dest.writeFloat(this.x);
		dest.writeFloat(this.y);
		dest.writeFloat(this.pressure);
		dest.writeFloat(this.size);
		dest.writeString(this.actionId);
		dest.writeString(this.clickedWidget);
		dest.writeInt(this.deviceOrientation);
	}

	protected RawTouchData(Parcel in) {
		this.timeEvent = in.readLong();
		this.relativeToStartTimeEvent = in.readLong();
		this.activityId = in.readString();
		this.fragmentId = in.readString();
		this.x = in.readFloat();
		this.y = in.readFloat();
		this.pressure = in.readFloat();
		this.size = in.readFloat();
		this.actionId = in.readString();
		this.clickedWidget = in.readString();
		this.deviceOrientation = in.readInt();
	}

	public static final Parcelable.Creator<RawTouchData> CREATOR = new Parcelable.Creator<RawTouchData>() {
		@Override
		public RawTouchData createFromParcel(Parcel source) {
			return new RawTouchData(source);
		}

		@Override
		public RawTouchData[] newArray(int size) {
			return new RawTouchData[size];
		}
	};
}
