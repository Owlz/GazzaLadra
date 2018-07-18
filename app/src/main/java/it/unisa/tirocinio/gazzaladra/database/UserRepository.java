package it.unisa.tirocinio.gazzaladra.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class UserRepository {
	UserDAO userDAO;
	LiveData<List<User>> userList;

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

	public void insert(User user) {
		new InsertAsyncTask().execute(user);
	}

	public void insert(Session s) {
		new InsertSessionAsyncTask().execute(s);
	}

	public void insert(Topic t) {
		new InsertTopicAsyncTask().execute(t);
	}

	private class InsertSessionAsyncTask extends AsyncTask<Session, Void, Void> {
		@Override
		protected Void doInBackground(Session... s) {
			userDAO.insert(s[0]);
			return null;
		}
	}

	private class InsertAsyncTask extends AsyncTask<User, Void, Void> {
		@Override
		protected Void doInBackground(User... users) {
			userDAO.insert(users[0]);
			return null;
		}
	}

	private class InsertTopicAsyncTask extends AsyncTask<Topic, Void, Void> {
		@Override
		protected Void doInBackground(Topic... t) {
			userDAO.insert(t[0]);
			return null;
		}
	}
}



