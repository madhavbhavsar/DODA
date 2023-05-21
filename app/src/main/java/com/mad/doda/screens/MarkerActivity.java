package com.mad.doda.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.MatrixKt;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mad.doda.R;
import com.mad.doda.adapter.DrawingAdapter;
import com.mad.doda.adapter.MarkerAdapter;
import com.mad.doda.model.Drawing;
import com.mad.doda.model.Markers;

public class MarkerActivity extends AppCompatActivity {

    StorageReference storage = FirebaseStorage.getInstance().getReference();
    ImageView drawingImg;
    RecyclerView markerRV;
    LinearLayoutManager manager1;
    MarkerAdapter adapter;
    Button addMarker;
    String drawingId,markers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker);

        drawingImg = findViewById(R.id.markerImageView);
        addMarker = findViewById(R.id.addMarkerbtn);
        markerRV = findViewById(R.id.markerRV);

        Intent i = getIntent();
        drawingId = i.getStringExtra("drawingId");
        markers = i.getStringExtra("markers");

        FirebaseStorage.getInstance().getReference().child("drawings/" + drawingId)
                .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(getApplicationContext()).load(uri).into(drawingImg);
                    }
                });

        addMarker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MarkerActivity.this, AddMarkerActivity.class);
                i.putExtra("drawingId",drawingId);
                i.putExtra("markers",markers);
                startActivity(i);

            }
        });


        manager1 = new LinearLayoutManager(this);
        manager1.setReverseLayout(true);
        manager1.setStackFromEnd(true);
        markerRV.setLayoutManager(manager1);
        Query query = FirebaseDatabase.getInstance().getReference().child("markers").orderByChild("drawingId").equalTo(drawingId);
        FirebaseRecyclerOptions<Markers> options1 = new FirebaseRecyclerOptions.Builder<Markers>().setQuery(query, Markers.class).build();

        adapter = new MarkerAdapter(options1, MarkerActivity.this);
        adapter.notifyDataSetChanged();

        adapter.startListening();
        markerRV.setAdapter(adapter);




    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent i = new Intent(MarkerActivity.this, HomeActivity.class);
        startActivity(i);
    }
}