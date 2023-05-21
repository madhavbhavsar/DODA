package com.mad.doda.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.mad.doda.R;
import com.mad.doda.adapter.DrawingAdapter;
import com.mad.doda.model.Drawing;

public class HomeActivity extends AppCompatActivity {

    Button logout;
    Button addDrawingbtn;

    DrawingAdapter adapter;
    RecyclerView drawing_rv;
    LinearLayoutManager manager1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //logout = findViewById(R.id.logout);
        addDrawingbtn = findViewById(R.id.addDrawingbtn);
        drawing_rv = findViewById(R.id.drawing_rv);


        manager1 = new LinearLayoutManager(this);
        manager1.setReverseLayout(true);
        manager1.setStackFromEnd(true);
        drawing_rv.setLayoutManager(manager1);

        Query query = FirebaseDatabase.getInstance().getReference().child("drawings");
        FirebaseRecyclerOptions<Drawing> options1 = new FirebaseRecyclerOptions.Builder<Drawing>().setQuery(query, Drawing.class).build();

        adapter = new DrawingAdapter(options1, HomeActivity.this);
        adapter.notifyDataSetChanged();

        adapter.startListening();
        drawing_rv.setAdapter(adapter);


        addDrawingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, AddDrawingActivity.class));
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
       // adapter.stopListening();
    }

    @Override
    public void onBackPressed() {

    }
}