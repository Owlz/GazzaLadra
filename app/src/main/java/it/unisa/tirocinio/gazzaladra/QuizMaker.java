package it.unisa.tirocinio.gazzaladra;


import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import it.unisa.tirocinio.gazzaladra.activity.fragment.quiz.Quiz1Fragment;
import it.unisa.tirocinio.gazzaladra.activity.fragment.quiz.Quiz2Fragment;
import it.unisa.tirocinio.gazzaladra.activity.fragment.quiz.Quiz3Fragment;
import it.unisa.tirocinio.gazzaladra.activity.fragment.quiz.Quiz4Fragment;
import it.unisa.tirocinio.gazzaladra.activity.fragment.quiz.Quiz5Fragment;
import it.unisa.tirocinio.gazzaladra.activity.fragment.quiz.Quiz6Fragment;

public class QuizMaker {
	public static final List<String> quizList;
	public static final List<String> scenariList;
	private static final Random r;

	static {
		quizList = new ArrayList<>();
		quizList.add(Quiz1Fragment.class.getName());
		quizList.add(Quiz2Fragment.class.getName());
		quizList.add(Quiz3Fragment.class.getName());
		quizList.add(Quiz4Fragment.class.getName());
		quizList.add(Quiz5Fragment.class.getName());
		quizList.add(Quiz6Fragment.class.getName());

		scenariList = new ArrayList<>();
		scenariList.add("seduto");
		scenariList.add("alzato");
		scenariList.add("disteso");

		r = new Random();
	}

	public static Pair<List<String>, List<String>> getQuizList(int numberOfQuizes) {
		if (numberOfQuizes > quizList.size())
			throw new RuntimeException("Numero di quiz richiesto maggiore dei quiz possibili (richiesti: " + numberOfQuizes + ")");

		List<String> scenari = new ArrayList<>();
		List<String> fragments = new ArrayList<>();

		List<String> quizListClone = new ArrayList<>(quizList);

		for (int i = 0; i < numberOfQuizes; i++) {
			String classString = quizListClone.get(r.nextInt(quizListClone.size()));
			quizListClone.remove(classString);
			fragments.add(classString);

			String scenario = scenariList.get(r.nextInt(scenariList.size()));
			scenari.add(scenario);
		}

		return Pair.create(fragments, scenari);
	}

	public static Pair<List<String>, List<String>> getQuizList() {
		int r = 3 + new Random().nextInt(3);
		return getQuizList(r);
	}
}
