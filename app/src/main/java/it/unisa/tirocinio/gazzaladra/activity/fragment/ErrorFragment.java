package it.unisa.tirocinio.gazzaladra.activity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import it.unisa.tirocinio.gazzaladra.R;

public class ErrorFragment extends FragmentTemplate {

	private ErrorFragmentCallback mListener;
	private Button ritorna;

	public ErrorFragment() {
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
		View v = inflater.inflate(R.layout.fragment_error, container, false);

		ritorna = v.findViewById(R.id.ritorna);
		ritorna.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mListener.ritorna();
			}
		});

		return v;
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof ErrorFragmentCallback) {
			mListener = (ErrorFragmentCallback) context;
		} else {
			throw new RuntimeException(context.toString()
					+ " must implement ErrorFragmentCallback");
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
		return "ErrorFragment";
	}

	public interface ErrorFragmentCallback {
		void ritorna();
	}
}