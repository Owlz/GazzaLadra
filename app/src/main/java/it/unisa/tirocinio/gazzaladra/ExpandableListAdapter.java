package it.unisa.tirocinio.gazzaladra;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import it.unisa.tirocinio.gazzaladra.database.Session;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
	private Activity activity;
	private Map<Session, List<String>> sessionCollector;
	private List<Session> sessions;

	public ExpandableListAdapter(Activity a, List<Session> sessions, Map<Session, List<String>> sessionCollector) {
		this.sessionCollector = sessionCollector;
		this.activity = a;
		this.sessions = sessions;
	}

	@Override
	public int getGroupCount() {
		return sessions.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return sessionCollector.get(sessions.get(groupPosition)).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return sessions.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		List<String> sc = sessionCollector.get(sessions.get(groupPosition));
		return sc.get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
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
		item.setText(sessione.getNumSession() + "");

		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		final String quiz = (String) getChild(groupPosition, childPosition);
		LayoutInflater inflater = activity.getLayoutInflater();

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.child_item, null);
		}

		TextView quizNome = convertView.findViewById(R.id.sessione);
		ImageView quizImage = convertView.findViewById(R.id.image);
		quizNome.setText(quiz);

		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	public void setSessions(List<Session> list, Map<Session, List<String>> child) {
		this.sessions = list;
		this.sessionCollector = child;
		notifyDataSetChanged();
	}
}
