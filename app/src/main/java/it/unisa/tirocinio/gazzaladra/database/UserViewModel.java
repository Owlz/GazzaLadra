package it.unisa.tirocinio.gazzaladra.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
	private UserRepository repo;
	private LiveData<List<User>> allUsers;

	public UserViewModel(@NonNull Application application) {
		super(application);
		repo = new UserRepository(application);
		allUsers = repo.getAllUsers();
	}

	public LiveData<List<User>> getAllUsers() {
		return allUsers;
	}

	public void insertUser(User user) {
		repo.insert(user);
	}

	public LiveData<List<Session>> getSessionByUser(long id) {
		return repo.getSessionByUser(id);
	}

	public void insertSession(Session s) {
		repo.insertSession(s);
	}

	public LiveData<List<Topic>> getTopicBySession(long id) {
		return repo.getTopicBySession(id);
	}

	public void insertTopic(Topic t) {
		repo.insertTopic(t);
	}
}
