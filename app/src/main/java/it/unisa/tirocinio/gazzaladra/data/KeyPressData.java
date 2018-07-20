package it.unisa.tirocinio.gazzaladra.data;

import android.os.Parcel;
import android.os.Parcelable;

public class KeyPressData implements Parcelable {
	public long timeEvent;
	public long relativeToStartTimeEvent;
	public String activityId;
	public String fragmentId;
	public String keyCode;
	public String position;
	public String orientation;

	public KeyPressData(long timeEvent, long relativeToStartTimeEvent, String activityId, String fragmentId, String keyCode, String position, String orientation) {
		this.timeEvent = timeEvent;
		this.relativeToStartTimeEvent = relativeToStartTimeEvent;
		this.activityId = activityId;
		this.fragmentId = fragmentId;
		this.keyCode = keyCode;
		this.position = position;
		this.orientation = orientation;
	}

	public String[] toStringArray() {
		return new String[]{
				"" + this.timeEvent,
				"" + this.relativeToStartTimeEvent,
				this.activityId,
				this.fragmentId,
				this.keyCode,
				this.position,
				this.orientation
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
		dest.writeString(this.keyCode);
		dest.writeString(this.position);
		dest.writeString(this.orientation);
	}

	protected KeyPressData(Parcel in) {
		this.timeEvent = in.readLong();
		this.relativeToStartTimeEvent = in.readLong();
		this.activityId = in.readString();
		this.fragmentId = in.readString();
		this.keyCode = in.readString();
		this.position = in.readString();
		this.orientation = in.readString();
	}

	public static final Parcelable.Creator<KeyPressData> CREATOR = new Parcelable.Creator<KeyPressData>() {
		@Override
		public KeyPressData createFromParcel(Parcel source) {
			return new KeyPressData(source);
		}

		@Override
		public KeyPressData[] newArray(int size) {
			return new KeyPressData[size];
		}
	};
}
