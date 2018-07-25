package it.unisa.tirocinio.gazzaladra.activity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import it.unisa.tirocinio.gazzaladra.R;

public class IntermediateFragment extends FragmentTemplate {
	private String scenario;

	private IntermediateFragmentCallback mListener;

	private TextView actionUtente;
	private Button next;

	public IntermediateFragment() {
		// Required empty public constructor
	}

	public static IntermediateFragment newInstance(String scenario) {
		IntermediateFragment fragment = new IntermediateFragment();
		Bundle args = new Bundle();
		args.putString("scenario", scenario);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle args = getArguments();
		if (args != null) {
			scenario = args.getString("scenario");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_intermediate, container, false);

		actionUtente = v.findViewById(R.id.scenario);
		actionUtente.setText(scenario);

		next = v.findViewById(R.id.next);
		next.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mListener.intermediateCallback();
			}
		});

		if (savedInstanceState != null) {
			scenario = savedInstanceState.getString("scenario");
		}

		return v;
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof IntermediateFragmentCallback) {
			mListener = (IntermediateFragmentCallback) context;
		} else {
			throw new RuntimeException(context.toString()
					+ " must implement IntermediateFragmentCallback");
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
		outState.putString("scenario", scenario);
	}

	@Override
	public String getFragmentIdNoCheck() {
		return "IntermediateFragment";
	}

	public interface IntermediateFragmentCallback {
		void intermediateCallback();
	}
}
