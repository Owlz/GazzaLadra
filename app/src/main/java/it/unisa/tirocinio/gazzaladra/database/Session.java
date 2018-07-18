package it.unisa.tirocinio.gazzaladra.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "sessions",
		foreignKeys = @ForeignKey(
				entity = User.class,
				parentColumns = "uidSession",
				childColumns = "uidU"
		)
)
public class Session implements Parcelable {
	@PrimaryKey(autoGenerate = true)
	private long uidSession;

	@ColumnInfo(name = "data")
	private String data;
	@ColumnInfo(name = "numSession")
	private int numSession;
	@ColumnInfo(name = "uidU")
	private long uidU;

	public Session() {
	}

	@Ignore
	public Session(long uidU, int numSession, String data) {
		this.data = data;
		this.numSession = numSession;
		this.uidU = uidU;
	}

	public long getUidSession() {
		return uidSession;
	}

	public void setUidSession(long uidSession) {
		this.uidSession = uidSession;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public int getNumSession() {
		return numSession;
	}

	public void setNumSession(int numSession) {
		this.numSession = numSession;
	}

	public long getUidU() {
		return uidU;
	}

	public void setUidU(long uidU) {
		this.uidU = uidU;
	}

	@Override
	public String toString() {
		return "Session{" +
				"uidSession=" + uidSession +
				", data='" + data + '\'' +
				", numSession=" + numSession +
				", uidU=" + uidU +
				'}';
	}

	protected Session(Parcel in) {
		uidSession = in.readLong();
		data = in.readString();
		numSession = in.readInt();
		uidU = in.readLong();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(uidSession);
		dest.writeString(data);
		dest.writeInt(numSession);
		dest.writeLong(uidU);
	}

	@SuppressWarnings("unused")
	public static final Parcelable.Creator<Session> CREATOR = new Parcelable.Creator<Session>() {
		@Override
		public Session createFromParcel(Parcel in) {
			return new Session(in);
		}

		@Override
		public Session[] newArray(int size) {
			return new Session[size];
		}
	};
}