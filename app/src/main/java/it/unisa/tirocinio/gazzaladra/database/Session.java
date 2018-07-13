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
				parentColumns = "uid",
				childColumns = "uidUser"
		)
)
public class Session implements Parcelable {
	@PrimaryKey(autoGenerate = true)
	private long uid;

	@ColumnInfo(name = "data")
	private String data;
	@ColumnInfo(name = "numSession")
	private int numSession;
	@ColumnInfo(name = "uidUser")
	private long uidUser;

	public Session() {
	}

	@Ignore
	public Session(long uidUser, int numSession, String data) {
		this.data = data;
		this.numSession = numSession;
		this.uidUser = uidUser;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
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

	public long getUidUser() {
		return uidUser;
	}

	public void setUidUser(long uidUser) {
		this.uidUser = uidUser;
	}

	@Override
	public String toString() {
		return "Session{" +
				"uid=" + uid +
				", data='" + data + '\'' +
				", numSession=" + numSession +
				", uidUser=" + uidUser +
				'}';
	}

	protected Session(Parcel in) {
		uid = in.readLong();
		data = in.readString();
		numSession = in.readInt();
		uidUser = in.readLong();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(uid);
		dest.writeString(data);
		dest.writeInt(numSession);
		dest.writeLong(uidUser);
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