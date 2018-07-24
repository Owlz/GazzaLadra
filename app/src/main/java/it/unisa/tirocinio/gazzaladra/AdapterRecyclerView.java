package it.unisa.tirocinio.gazzaladra;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import it.unisa.tirocinio.gazzaladra.activity.fragment.FragmentComunicator;
import it.unisa.tirocinio.gazzaladra.data.FragmentData;

public class AdapterRecyclerView extends RecyclerView.Adapter<AdapterRecyclerView.ViewHolder> {

	public static List<String> sinonymous;

	FragmentComunicator callback;
	String id;
	long start;

	class ViewHolder extends RecyclerView.ViewHolder {
		private TextView world;


		private ViewHolder(View v) {
			super(v);
			world = v.findViewById(R.id.world);
			v.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Log.d("adapter", "Element " + getAdapterPosition() + " clicked.");
					if (world.getText().toString().equals("Rimandare") || world.getText().toString().equals("Indugiare"))
						callback.onFragmentEnd(new FragmentData(id, null, true, start, System.currentTimeMillis()));
					else
						callback.onFragmentEnd(new FragmentData(id, null, false, start, System.currentTimeMillis()));

				}
			});

		}

		public TextView getTextView() {

			return world;
		}
	}


	public AdapterRecyclerView(List<String> sinonymous, FragmentComunicator mListener, String idFragment, long timeStart) {

		this.sinonymous = sinonymous;
		this.callback = mListener;
		this.id = idFragment;
		this.start = timeStart;
	}

	@Override
	public AdapterRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
		return new ViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(AdapterRecyclerView.ViewHolder holder, int position) {
		holder.getTextView().setText(sinonymous.get(position).toString());
	}

	@Override
	public int getItemCount() {
		return sinonymous.size();
	}

}


