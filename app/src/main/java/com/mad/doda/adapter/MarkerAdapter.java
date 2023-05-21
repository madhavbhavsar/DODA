package com.mad.doda.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.mad.doda.R;
import com.mad.doda.model.Markers;

public class MarkerAdapter extends FirebaseRecyclerAdapter<Markers, MarkerAdapter.MarkerVH> {

    Context context;
    public MarkerAdapter(@NonNull FirebaseRecyclerOptions<Markers> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull MarkerVH holder, int position, @NonNull Markers model) {

        holder.markerTitle.setText(model.getMarkerTitle());
        holder.markerDesc.setText(model.getMarkerDescription());

    }

    @NonNull
    @Override
    public MarkerVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_markers, parent, false);
        return new MarkerVH(view);
    }

    public class MarkerVH extends RecyclerView.ViewHolder{

        TextView markerTitle, markerDesc;

        public MarkerVH(@NonNull View itemView) {
            super(itemView);
            markerDesc = itemView.findViewById(R.id.cardMarkerDesc);
            markerTitle = itemView.findViewById(R.id.cardMarkertv);
        }
    }
}
