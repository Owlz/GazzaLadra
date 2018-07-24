package it.unisa.tirocinio.gazzaladra.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
	private UserRepository repo;
	private LiveData<List<User>> allUsers;
	private LiveData<List<SessionTopic>> allSessions;

	public UserViewModel(@NonNull Application application) {
		super(application);
		repo = new UserRepository(application);
		allUsers = repo.getAllUsers();
	}

	public void insert(User user) {
		repo.insert(user);
	}

	public long insert(Session session) {
		return repo.insert(session);
	}

	public void insert(Topic topic) {
		repo.insert(topic);
	}

	public LiveData<List<User>> getAllUsers() {
		return allUsers;
	}

	public LiveData<List<Session>> getSessionByUser(long id) {
		return repo.getSessionByUser(id);
	}

	public LiveData<List<Topic>> getTopicBySession(long id) {
		return repo.getTopicBySession(id);
	}

	public LiveData<List<SessionTopic>> getCompleteSessionByUser(long id) {
		return repo.getLastSessionTopic(id);
	}

	public void deleteUser(User u) {
		repo.removeUser(u);
	}
}
