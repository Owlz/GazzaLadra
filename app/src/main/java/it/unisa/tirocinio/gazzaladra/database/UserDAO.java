package it.unisa.tirocinio.gazzaladra.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;


@Dao
public interface UserDAO {
	@Insert
	void insert(User u);

	@Insert
	void insertSession(Session s);

	@Query("DELETE FROM users WHERE uid = :id")
	void deleteUser(int id);

	@Query("DELETE FROM users")
	void deleteAll();

	@Query("SELECT * FROM users ORDER BY uid ASC")
	LiveData<List<User>> getAllUsers();

	@Query("SELECT * FROM sessions WHERE uidUser = :idUser ORDER BY uid ASC")
	LiveData<List<Session>> getAllSessionByUser(long idUser);


}
