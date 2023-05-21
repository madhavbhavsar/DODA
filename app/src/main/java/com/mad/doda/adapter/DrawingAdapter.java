package com.mad.doda.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.mad.doda.R;
import com.mad.doda.model.Drawing;
import com.mad.doda.screens.MarkerActivity;

public class DrawingAdapter extends FirebaseRecyclerAdapter<Drawing, DrawingAdapter.DrawingVH> {
    Context context;

    public DrawingAdapter(@NonNull FirebaseRecyclerOptions<Drawing> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull DrawingVH holder, int position, @NonNull Drawing model) {

        holder.title.setText(model.getTitle());
        holder.markers.setText(model.getMarkers()+" Markers");


        FirebaseStorage.getInstance().getReference().child("drawings/" + model.getId())
                .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(context).load(uri).into(holder.imgView);
                    }
                });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, MarkerActivity.class);
                i.putExtra("drawingId", model.getId());
                i.putExtra("markers", model.getMarkers());
                context.startActivity(i);

            }
        });
        //holder.markers.setText(model.getMarkers());

    }

    @NonNull
    @Override
    public DrawingVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_dashboard, parent, false);
        return new DrawingVH(view);
    }

    public class DrawingVH extends RecyclerView.ViewHolder {

        TextView title;
        ImageView imgView;
        TextView markers;

        public DrawingVH(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.card_tv);
            markers = itemView.findViewById(R.id.card_marker);
            imgView = itemView.findViewById(R.id.card_img);

        }
    }
}
