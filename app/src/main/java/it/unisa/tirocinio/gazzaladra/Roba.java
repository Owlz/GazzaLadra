package it.unisa.tirocinio.gazzaladra;

import android.os.Parcel;
import android.os.Parcelable;

public class Roba implements Parcelable {
	private String idFragment;
	private String scenario;
	private String rispostaData;
	private String rispostaCorretta;
	private boolean isComplete;
	private long timeStart;
	private long timeEnd;

	public Roba() {
	}

	public Roba(String idFragment, String scenario, String rispostaData, String rispostaCorretta, boolean isComplete, long timeStart, long timeEnd) {
		this.idFragment = idFragment;
		this.scenario = scenario;
		this.rispostaData = rispostaData;
		this.rispostaCorretta = rispostaCorretta;
		this.isComplete = isComplete;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
	}

	public String getIdFragment() {
		return idFragment;
	}

	public void setIdFragment(String idFragment) {
		this.idFragment = idFragment;
	}

	public String getScenario() {
		return scenario;
	}

	public void setScenario(String scenario) {
		this.scenario = scenario;
	}

	public String getRispostaData() {
		return rispostaData;
	}

	public void setRispostaData(String rispostaData) {
		this.rispostaData = rispostaData;
	}

	public String getRispostaCorretta() {
		return rispostaCorretta;
	}

	public void setRispostaCorretta(String rispostaCorretta) {
		this.rispostaCorretta = rispostaCorretta;
	}

	public boolean isComplete() {
		return isComplete;
	}

	public void setComplete(boolean complete) {
		isComplete = complete;
	}

	public long getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(long timeStart) {
		this.timeStart = timeStart;
	}

	public long getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(long timeEnd) {
		this.timeEnd = timeEnd;
	}

	@Override
	public String toString() {
		return "Roba{" +
				"idFragment='" + idFragment + '\'' +
				", scenario='" + scenario + '\'' +
				", rispostaData='" + rispostaData + '\'' +
				", rispostaCorretta='" + rispostaCorretta + '\'' +
				", isComplete=" + isComplete +
				", timeStart=" + timeStart +
				", timeEnd=" + timeEnd +
				'}';
	}

	protected Roba(Parcel in) {
		idFragment = in.readString();
		scenario = in.readString();
		rispostaData = in.readString();
		rispostaCorretta = in.readString();
		isComplete = in.readByte() != 0x00;
		timeStart = in.readLong();
		timeEnd = in.readLong();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(idFragment);
		dest.writeString(scenario);
		dest.writeString(rispostaData);
		dest.writeString(rispostaCorretta);
		dest.writeByte((byte) (isComplete ? 0x01 : 0x00));
		dest.writeLong(timeStart);
		dest.writeLong(timeEnd);
	}

	@SuppressWarnings("unused")
	public static final Parcelable.Creator<Roba> CREATOR = new Parcelable.Creator<Roba>() {
		@Override
		public Roba createFromParcel(Parcel in) {
			return new Roba(in);
		}

		@Override
		public Roba[] newArray(int size) {
			return new Roba[size];
		}
	};
}