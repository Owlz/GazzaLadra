package it.unisa.tirocinio.gazzaladra.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;


@Dao
public interface UserDAO {
	@Insert
	long insert(User u);

	@Insert
	long insert(Session s);

	@Insert
	long insert(Topic t);

	@Query("DELETE FROM users WHERE uidUser = :id")
	void deleteUser(long id);

	@Query("SELECT * FROM users ORDER BY uidUser ASC")
	LiveData<List<User>> getAllUsers();

	@Query("SELECT * FROM sessions WHERE uidU = :idUser ORDER BY uidSession ASC")
	LiveData<List<Session>> getAllSessionByUser(long idUser);

	@Query("SELECT * FROM topics WHERE uidS = :idSession ORDER BY uidTopic ASC")
	LiveData<List<Topic>> getAllTopicBySession(long idSession);

	@Query("SELECT * FROM sessions WHERE uidU = :idUser ORDER BY uidSession ASC")
	LiveData<List<SessionTopic>> getCompleteSessionsById(long idUser);
}
