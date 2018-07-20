package it.unisa.tirocinio.gazzaladra.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class UserRepository {
	UserDAO userDAO;
	LiveData<List<User>> userList;
	LiveData<List<SessionTopic>> lastSessionTopic;

	public UserRepository(Application app) {
		UserDB db = UserDB.getDatabase(app);
		userDAO = db.UserDAO();
		userList = userDAO.getAllUsers();
	}

	public LiveData<List<User>> getAllUsers() {
		return userList;
	}

	public LiveData<List<Session>> getSessionByUser(long id) {
		return userDAO.getAllSessionByUser(id);
	}

	public LiveData<List<Topic>> getTopicBySession(long id) {
		return userDAO.getAllTopicBySession(id);
	}

	public LiveData<List<SessionTopic>> getLastSessionTopic(long id) {
		return userDAO.getCompleteSessionsById(id);
	}

	public void insert(User user) {
		new InsertAsyncTask().execute(user);
	}

	public long insert(Session s) {
		return userDAO.insert(s);
	}

	public void insert(Topic t) {
		new InsertTopicAsyncTask().execute(t);
	}

	private class InsertSessionAsyncTask extends AsyncTask<Session, Void, Long> {
		@Override
		protected Long doInBackground(Session... s) {
			return userDAO.insert(s[0]);
		}
	}

	private class InsertAsyncTask extends AsyncTask<User, Void, Long> {
		@Override
		protected Long doInBackground(User... users) {
			return userDAO.insert(users[0]);
		}
	}

	private class InsertTopicAsyncTask extends AsyncTask<Topic, Void, Long> {
		@Override
		protected Long doInBackground(Topic... t) {
			return userDAO.insert(t[0]);
		}
	}

}



