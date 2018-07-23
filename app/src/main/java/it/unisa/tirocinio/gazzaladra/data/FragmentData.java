package it.unisa.tirocinio.gazzaladra.data;

import android.os.Parcel;
import android.os.Parcelable;

public class FragmentData implements Parcelable {
	public String idFragment;
	public String scenario;
	public boolean isComplete;
	public long timeStart;
	public long timeEnd;

	public FragmentData() {
	}

	public FragmentData(String idFragment, String scenario, boolean isComplete, long timeStart, long timeEnd) {
		this.idFragment = idFragment;
		this.scenario = scenario;
		this.isComplete = isComplete;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
	}

	public void setScenario(String scenario) {
		this.scenario = scenario;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.idFragment);
		dest.writeString(this.scenario);
		dest.writeByte(this.isComplete ? (byte) 1 : (byte) 0);
		dest.writeLong(this.timeStart);
		dest.writeLong(this.timeEnd);
	}

	protected FragmentData(Parcel in) {
		this.idFragment = in.readString();
		this.scenario = in.readString();
		this.isComplete = in.readByte() != 0;
		this.timeStart = in.readLong();
		this.timeEnd = in.readLong();
	}

	public static final Parcelable.Creator<FragmentData> CREATOR = new Parcelable.Creator<FragmentData>() {
		@Override
		public FragmentData createFromParcel(Parcel source) {
			return new FragmentData(source);
		}

		@Override
		public FragmentData[] newArray(int size) {
			return new FragmentData[size];
		}
	};
}