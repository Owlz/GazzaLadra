package it.unisa.tirocinio.gazzaladra;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unisa.tirocinio.gazzaladra.database.Session;
import it.unisa.tirocinio.gazzaladra.database.Topic;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
	private Activity activity;
	private Map<Session, List<Topic>> sessionCollector;
	private List<Session> sessions;

	private Drawable passed;
	private Drawable reject;

	public ExpandableListAdapter(Activity a, List<Session> sessions, Map<Session, List<Topic>> sessionCollector) {
		this.sessionCollector = sessionCollector;
		this.activity = a;
		this.sessions = sessions;

		this.passed = ContextCompat.getDrawable(a.getApplicationContext(), R.drawable.ic_check_green_24dp);
		this.reject = ContextCompat.getDrawable(a.getApplicationContext(), R.drawable.ic_clear_red_24dp);
	}

	@Override
	public int getGroupCount() {
		return sessions.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		Session padre = sessions.get(groupPosition);
		List<Topic> figli = sessionCollector.get(padre);
		return figli.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return sessions.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		Session padre = sessions.get(groupPosition);
		List<Topic> figli = sessionCollector.get(padre);
		return figli.get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		Session padre = sessions.get(groupPosition);

		return padre.getUidSession();
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		Session padre = sessions.get(groupPosition);
		List<Topic> figli = sessionCollector.get(padre);

		return figli.get(childPosition).getUidTopic();
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		Session sessione = (Session) getGroup(groupPosition);
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.group_item, null);
		}
		TextView item = convertView.findViewById(R.id.sessione);
		item.setTypeface(null, Typeface.BOLD_ITALIC);
		item.setText("Sessione " + sessione.getNumSession() + " - " + sessione.getData());

		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		final Topic quiz = (Topic) getChild(groupPosition, childPosition);
		LayoutInflater inflater = activity.getLayoutInflater();

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.child_item, null);
		}

		TextView quizNome = convertView.findViewById(R.id.sessioneChild);
		quizNome.setText(quiz.getName());

		ImageView quizImage = convertView.findViewById(R.id.image);
		if (quiz.isPassed())
			quizImage.setImageDrawable(passed);
		else
			quizImage.setImageDrawable(reject);

		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

	public void setSessions(List<Session> list, Map<Session, List<Topic>> child) {
		this.sessions = new ArrayList<>(list);
		this.sessionCollector = new HashMap<>(child);
		notifyDataSetChanged();
	}
}
