package it.unisa.tirocinio.gazzaladra.database;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

public class SessionTopic {
	@Embedded
	public Session session;

	@Relation(parentColumn = "uidSession", entityColumn = "uidS", entity = Topic.class)
	public List<Topic> topicList;
}