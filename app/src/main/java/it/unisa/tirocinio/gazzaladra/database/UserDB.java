package it.unisa.tirocinio.gazzaladra.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {User.class, Session.class, Topic.class}, version = 1)
public abstract class UserDB extends RoomDatabase {
	public abstract UserDAO UserDAO();

	private static UserDB INSTANCE = null;

	static public UserDB getDatabase(final Context context) {
		if (INSTANCE == null) {
			synchronized (UserDB.class) {
				if (INSTANCE == null) {
					INSTANCE = Room
							.databaseBuilder(
									context.getApplicationContext(),
									UserDB.class,
									"user-db7-testing")
							.allowMainThreadQueries()
							.build();

				}
			}
		}
		return INSTANCE;
	}
}
