package it.unisa.tirocinio.gazzaladra.activity.fragment;

import android.support.v4.app.Fragment;

public abstract class FragmentTemplate extends Fragment {
	public abstract String getFragmentIdNoCheck();

	public String getFragmentId() {
		String id = getFragmentIdNoCheck();

		if (id == null || id.equals(""))
			throw new RuntimeException("id del fragment non inizializzato");

		return id;
	}
}
