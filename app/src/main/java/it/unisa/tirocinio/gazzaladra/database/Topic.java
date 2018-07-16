package it.unisa.tirocinio.gazzaladra.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "topic",
		foreignKeys = @ForeignKey(
				entity = Session.class,
				parentColumns = "uid",
				childColumns = "uidSession"
		)
)
public class Topic implements Parcelable {
	@PrimaryKey(autoGenerate = true)
	private long uid;

	@ColumnInfo(name = "uidSession")
	private long uidSession;
	@ColumnInfo(name = "topicName")
	private String name;
	@ColumnInfo(name = "passed")
	private boolean isPassed;

	public Topic() {
	}

	@Ignore

	public Topic(long uidSession, String name, boolean isPassed) {
		this.uidSession = uidSession;
		this.name = name;
		this.isPassed = isPassed;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public long getUidSession() {
		return uidSession;
	}

	public void setUidSession(long uidSession) {
		this.uidSession = uidSession;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isPassed() {
		return isPassed;
	}

	public void setPassed(boolean passed) {
		isPassed = passed;
	}

	protected Topic(Parcel in) {
		uid = in.readLong();
		uidSession = in.readLong();
		name = in.readString();
		isPassed = in.readByte() != 0x00;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(uid);
		dest.writeLong(uidSession);
		dest.writeString(name);
		dest.writeByte((byte) (isPassed ? 0x01 : 0x00));
	}

	@SuppressWarnings("unused")
	public static final Parcelable.Creator<Topic> CREATOR = new Parcelable.Creator<Topic>() {
		@Override
		public Topic createFromParcel(Parcel in) {
			return new Topic(in);
		}

		@Override
		public Topic[] newArray(int size) {
			return new Topic[size];
		}
	};

}