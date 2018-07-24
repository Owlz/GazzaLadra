package it.unisa.tirocinio.gazzaladra;

import android.os.Parcel;
import android.os.Parcelable;

public class DomandeImg implements Parcelable {
	private String domanda;
	private String[] risposte;

	public DomandeImg(String d, String[] r) {
		domanda = d;
		risposte = r;
	}

	public String getDomanda() {
		return domanda;
	}

	public void setDomanda(String domanda) {
		this.domanda = domanda;
	}

	public String[] getRisposte() {
		return risposte;
	}

	public void setRisposte(String[] risposte) {
		this.risposte = risposte;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.domanda);
		dest.writeStringArray(this.risposte);
	}

	protected DomandeImg(Parcel in) {
		this.domanda = in.readString();
		this.risposte = in.createStringArray();
	}

	public static final Creator<DomandeImg> CREATOR = new Creator<DomandeImg>() {
		@Override
		public DomandeImg createFromParcel(Parcel source) {
			return new DomandeImg(source);
		}

		@Override
		public DomandeImg[] newArray(int size) {
			return new DomandeImg[size];
		}
	};
}
