package it.unisa.tirocinio.gazzaladra;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import it.unisa.tirocinio.gazzaladra.activity.fragment.IntermediateFragment;

public class QuizMaker {
	public static final List<String> quizList;
	public static final List<String> scenariList;
	private static final Random r;

	static {
		quizList = new ArrayList<>();
		quizList.add(IntermediateFragment.class.getName());

		scenariList = new ArrayList<>();
		scenariList.add("seduto");
		scenariList.add("alzato");
		scenariList.add("disteso");

		r = new Random();
	}

	public static Pair<List<Fragment>, List<String>> getQuizList(Context c, int numberOfQuizes) {
		if (numberOfQuizes > quizList.size() || numberOfQuizes > scenariList.size())
			throw new RuntimeException("Numero di quiz richiesto maggiore dei quiz possibili (richiesti: " + numberOfQuizes + ")");

		List<String> scenari = new ArrayList<>();
		List<Fragment> fragments = new ArrayList<>();

		List<String> quizListClone = new ArrayList<>(quizList);
		List<String> scenariClone = new ArrayList<>(scenariList);

		for (int i = 0; i < numberOfQuizes; i++) {
			String classString = quizListClone.get(r.nextInt(quizListClone.size()));
			quizListClone.remove(classString);
			fragments.add(Fragment.instantiate(c, classString));

			String scenario = scenariClone.get(r.nextInt(scenariClone.size()));
			scenariClone.remove(scenario);
			scenari.add(scenario);
		}

		return new Pair<>(fragments, scenari);
	}

	public static Pair<List<Fragment>, List<String>> getQuizList(Context c) {
		int r = 3 + new Random().nextInt(3);
		return getQuizList(c, r);
	}
}
