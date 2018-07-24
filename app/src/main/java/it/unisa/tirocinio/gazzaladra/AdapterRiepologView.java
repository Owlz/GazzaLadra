package it.unisa.tirocinio.gazzaladra;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import it.unisa.tirocinio.gazzaladra.activity.fragment.RiepologFragment;
import it.unisa.tirocinio.gazzaladra.data.FragmentData;

public class AdapterRiepologView extends RecyclerView.Adapter<AdapterRiepologView.ViewHolder> {

    public static List<FragmentData> fragmentResultData;

    private Context context;
    private Drawable passed;
    private Drawable reject;

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameQuiz;
        private ImageView img;

        private ViewHolder(View v) {
            super(v);
            nameQuiz = v.findViewById(R.id.nomeQuiz);
            img = v.findViewById(R.id.imageResult);

        }

        public TextView getTextView() {

            return nameQuiz;
        }


        public ImageView getImage() {

            return img;
        }

    }


    public AdapterRiepologView(List<FragmentData> fragmentResultData, Context context) {
        this.fragmentResultData = fragmentResultData;
        this.context = context;
    }


    @Override
    public AdapterRiepologView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.riepilog_row, parent, false);
        passed = ContextCompat.getDrawable(context, R.drawable.ic_check_green_24dp);
        reject = ContextCompat.getDrawable(context, R.drawable.ic_clear_red_24dp);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterRiepologView.ViewHolder holder, int position) {
        holder.getTextView().setText(fragmentResultData.get(position).idFragment);
        boolean bool = fragmentResultData.get(position).isComplete;
        if(bool == true){
            holder.getImage().setImageDrawable(passed);
        } else {
            holder.getImage().setImageDrawable(reject);
        }
    }

    @Override
    public int getItemCount() {
        return fragmentResultData.size();
    }

}
