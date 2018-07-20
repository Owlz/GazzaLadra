package it.unisa.tirocinio.gazzaladra;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

public class Utils {
	public static long getTimeRelativeTo(long time) {
		return Math.abs(time - System.currentTimeMillis());
	}

	public static int getOrientation(Activity activity) {
		return ((WindowManager) activity.getSystemService(Context.WINDOW_SERVICE))
				.getDefaultDisplay()
				.getRotation();
	}

	public static List<View> getAllChildrenBFS(View v) {
		List<View> visited = new ArrayList<>();
		List<View> unvisited = new ArrayList<>();
		unvisited.add(v);

		while (!unvisited.isEmpty()) {
			View child = unvisited.remove(0);
			if (!(child instanceof ViewGroup)) {
				visited.add(child);
				continue;
			}
			ViewGroup group = (ViewGroup) child;
			final int childCount = group.getChildCount();
			for (int i = 0; i < childCount; i++) unvisited.add(group.getChildAt(i));
		}

		return visited;
	}
}
