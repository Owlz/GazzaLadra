package it.unisa.tirocinio.gazzaladra.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class UserRepository {
	private UserDAO userDAO;

	public UserRepository(Application app) {
		UserDB db = UserDB.getDatabase(app);
		userDAO = db.UserDAO();
	}

	public LiveData<List<User>> getAllUsers() {
		return userDAO.getAllUsers();
	}

	public LiveData<List<Session>> getSessionByUser(long id) {
		return userDAO.getAllSessionByUser(id);
	}

	public LiveData<List<Topic>> getTopicBySession(long id) {
		return userDAO.getAllTopicBySession(id);
	}

	public void insert(User user) {
		new InsertAsyncTask(userDAO).execute(user);
	}

	private class InsertAsyncTask extends AsyncTask<User, Void, Void> {
		private UserDAO dao;

		public InsertAsyncTask(UserDAO dao) {
			this.dao = dao;
		}

		@Override
		protected Void doInBackground(User... users) {
			dao.insert(users[0]);
			return null;
		}
	}

	public void insertSession(Session s) {
		new InsertSessionAsyncTask(userDAO).execute(s);
	}

	private class InsertSessionAsyncTask extends AsyncTask<Session, Void, Void> {
		private UserDAO dao;

		public InsertSessionAsyncTask(UserDAO dao) {
			this.dao = dao;
		}

		@Override
		protected Void doInBackground(Session... s) {
			dao.insertSession(s[0]);
			return null;
		}
	}

	public void insertTopic(Topic t) {
		new InsertTopicAsyncTask(userDAO).execute(t);
	}

	private class InsertTopicAsyncTask extends AsyncTask<Topic, Void, Void> {
		private UserDAO dao;

		public InsertTopicAsyncTask(UserDAO dao) {
			this.dao = dao;
		}

		@Override
		protected Void doInBackground(Topic... t) {
			dao.insertTopic(t[0]);
			return null;
		}
	}
}
