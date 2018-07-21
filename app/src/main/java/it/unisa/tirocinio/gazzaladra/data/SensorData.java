package it.unisa.tirocinio.gazzaladra.data;

import android.os.Parcel;
import android.os.Parcelable;

import it.unisa.tirocinio.gazzaladra.Utils;

public class SensorData implements Parcelable {
	public String sensorName;
	public long timeEvent;
	public long relativeToStartTimeEvent;
	public String activityId;
	public String fragmentId;
	public float x, y, z;
	public int deviceOrientation;

	public String[] toStringArray() {
		return new String[]{
				sensorName,
				"" + timeEvent,
				"" + relativeToStartTimeEvent,
				activityId,
				fragmentId,
				Utils.getFormatted(x),
				Utils.getFormatted(y),
				Utils.getFormatted(z),
				"" + deviceOrientation
		};
	}

	public SensorData(String sensorName, long timeEvent, long relativeToStartTimeEvent, String activityId, String fragmentId, float x, float y, float z, int deviceOrientation) {
		this.sensorName = sensorName;
		this.timeEvent = timeEvent;
		this.relativeToStartTimeEvent = relativeToStartTimeEvent;
		this.activityId = activityId;
		this.fragmentId = fragmentId;
		this.x = x;
		this.y = y;
		this.z = z;
		this.deviceOrientation = deviceOrientation;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.sensorName);
		dest.writeLong(this.timeEvent);
		dest.writeLong(this.relativeToStartTimeEvent);
		dest.writeString(this.activityId);
		dest.writeString(this.fragmentId);
		dest.writeFloat(this.x);
		dest.writeFloat(this.y);
		dest.writeFloat(this.z);
		dest.writeInt(this.deviceOrientation);
	}

	protected SensorData(Parcel in) {
		this.sensorName = in.readString();
		this.timeEvent = in.readLong();
		this.relativeToStartTimeEvent = in.readLong();
		this.activityId = in.readString();
		this.fragmentId = in.readString();
		this.x = in.readFloat();
		this.y = in.readFloat();
		this.z = in.readFloat();
		this.deviceOrientation = in.readInt();
	}

	public static final Creator<SensorData> CREATOR = new Creator<SensorData>() {
		@Override
		public SensorData createFromParcel(Parcel source) {
			return new SensorData(source);
		}

		@Override
		public SensorData[] newArray(int size) {
			return new SensorData[size];
		}
	};
}
