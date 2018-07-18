package it.unisa.tirocinio.gazzaladra.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "users")
public class User implements Parcelable {
	@PrimaryKey(autoGenerate = true)
	private long uidUser;

	@ColumnInfo(name = "name")
	private String name;
	@ColumnInfo(name = "photo")
	private String photo;
	@ColumnInfo(name = "lastName")
	private String lastName;

	public User() {
	}

	@Ignore
	public User(String name, String lastName, String photo) {
		this.name = name;
		this.lastName = lastName;
		this.photo = photo;
	}

	public long getUidUser() {
		return uidUser;
	}

	public void setUidUser(long uidUser) {
		this.uidUser = uidUser;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}


	protected User(Parcel in) {
		uidUser = in.readLong();
		name = in.readString();
		lastName = in.readString();
		photo = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(uidUser);
		dest.writeString(name);
		dest.writeString(lastName);
		dest.writeString(photo);
	}

	@Override
	public String toString() {
		return "User{" +
				"uidUser=" + uidUser +
				", name='" + name + '\'' +
				", lastName='" + lastName + '\'' +
				", photo='" + photo + '\'' +
				'}';
	}

	@SuppressWarnings("unused")
	public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
		@Override
		public User createFromParcel(Parcel in) {
			return new User(in);
		}

		@Override
		public User[] newArray(int size) {
			return new User[size];
		}
	};
}
