package it.unisa.tirocinio.gazzaladra.data;

import android.os.Parcel;
import android.os.Parcelable;

public class SensorData implements Parcelable {
	public String sensorName;
	public long timeEvent;
	public long relativeToStartTimeEvent;
	public String activityId;
	public String fragmentId;
	public String x, y, z;
	public int deviceOrientation;

	public SensorData(String sensorName, long timeEvent, long relativeToStartTimeEvent, String activityId, String fragmentId, String x, String y, String z, int deviceOrientation) {
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

	public String[] toStringArray() {
		return new String[]{
				sensorName,
				"" + timeEvent,
				"" + relativeToStartTimeEvent,
				activityId,
				fragmentId,
				x, y, z,
				"" + deviceOrientation
		};
	}

	protected SensorData(Parcel in) {
		sensorName = in.readString();
		timeEvent = in.readLong();
		relativeToStartTimeEvent = in.readLong();
		activityId = in.readString();
		fragmentId = in.readString();
		x = in.readString();
		y = in.readString();
		z = in.readString();
		deviceOrientation = in.readInt();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(sensorName);
		dest.writeLong(timeEvent);
		dest.writeLong(relativeToStartTimeEvent);
		dest.writeString(activityId);
		dest.writeString(fragmentId);
		dest.writeString(x);
		dest.writeString(y);
		dest.writeString(z);
		dest.writeInt(deviceOrientation);
	}

	@SuppressWarnings("unused")
	public static final Parcelable.Creator<SensorData> CREATOR = new Parcelable.Creator<SensorData>() {
		@Override
		public SensorData createFromParcel(Parcel in) {
			return new SensorData(in);
		}

		@Override
		public SensorData[] newArray(int size) {
			return new SensorData[size];
		}
	};
}
