package it.unisa.tirocinio.gazzaladra.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "topics")
public class Topic implements Parcelable {
	@PrimaryKey(autoGenerate = true)
	private long uidTopic;

	@ColumnInfo(name = "uidS")
	private long uidS;
	@ColumnInfo(name = "topicName")
	private String name;
	@ColumnInfo(name = "passed")
	private boolean isPassed;

	public Topic() {
	}

	@Ignore
	public Topic(long uidS, String name, boolean isPassed) {
		this.uidS = uidS;
		this.name = name;
		this.isPassed = isPassed;
	}

	public long getUidTopic() {
		return uidTopic;
	}

	public void setUidTopic(long uidTopic) {
		this.uidTopic = uidTopic;
	}

	public long getUidS() {
		return uidS;
	}

	public void setUidS(long uidS) {
		this.uidS = uidS;
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
		uidTopic = in.readLong();
		uidS = in.readLong();
		name = in.readString();
		isPassed = in.readByte() != 0x00;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(uidTopic);
		dest.writeLong(uidS);
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

	@Override
	public String toString() {
		return "Topic{" +
				"uidTopic=" + uidTopic +
				", uidS=" + uidS +
				", name='" + name + '\'' +
				", isPassed=" + isPassed +
				'}';
	}
}