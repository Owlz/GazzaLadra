package it.unisa.tirocinio.gazzaladra.activity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.unisa.tirocinio.gazzaladra.R;

public class RiepologFragment extends FragmentTemplate {

	private RiepilogoFragmentCallback mListener;

	public RiepologFragment() {
		// Required empty public constructor
	}

	public static RiepologFragment newInstance() {
		return new RiepologFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_riepilog, container, false);

		/* codice */
		mListener.riepilogoCallback();
		return v;
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof RiepilogoFragmentCallback) {
			mListener = (RiepilogoFragmentCallback) context;
		} else {
			throw new RuntimeException(context.toString()
					+ " must implement RiepilogoFragmentCallback");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public String getFragmentIdNoCheck() {
		return "RiepilogoFragment";
	}

	public interface RiepilogoFragmentCallback {
		void riepilogoCallback();
	}
}