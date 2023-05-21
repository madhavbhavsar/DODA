package com.mad.doda.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.mad.doda.R;
import com.mad.doda.model.Markers;

import java.util.HashMap;
import java.util.Map;

public class AddMarkerActivity extends AppCompatActivity {

    String drawingId,markersr;
    EditText markerTitle;
    EditText markerDesc;
    Button markerSubmit;
    ImageView drawingImg;
    DatabaseReference ref ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_marker);

        Intent i = getIntent();
        drawingId = i.getStringExtra("drawingId");
        markersr = i.getStringExtra("markers");

        ref = FirebaseDatabase.getInstance().getReference("markers");
        markerTitle = findViewById(R.id.markerTitle);
        markerDesc = findViewById(R.id.markerDesc);
        markerSubmit =findViewById(R.id.markerSubmitbtn);
        drawingImg = findViewById(R.id.markerImageView);

        FirebaseStorage.getInstance().getReference().child("drawings/" + drawingId)
                .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(getApplicationContext()).load(uri).into(drawingImg);
                    }
                });

        markerSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(markerTitle.getText().toString().trim().isEmpty()){
                    Toast.makeText(AddMarkerActivity.this, "Enter Marker Title", Toast.LENGTH_SHORT).show();
                } else if (markerDesc.getText().toString().trim().isEmpty()){
                    Toast.makeText(AddMarkerActivity.this, "Enter Marker Description", Toast.LENGTH_SHORT).show();
                } else {
                    Markers markers = new Markers();

                    markers.setMarkerId(ref.push().getKey());
                    markers.setMarkerTitle(markerTitle.getText().toString().trim());
                    markers.setMarkerDescription(markerDesc.getText().toString().trim());
                    markers.setDrawingId(drawingId);

                    ref.child(markers.getMarkerId()).setValue(markers).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                            Map<String, Object> hashMap = new HashMap<>();
                            hashMap.put("markers", String.valueOf( Integer.parseInt(markersr)+1));

                            FirebaseDatabase.getInstance().getReference().child("drawings").child(drawingId).updateChildren(hashMap);

                            finish();
                            Intent i = new Intent(AddMarkerActivity.this,MarkerActivity.class);
                            i.putExtra("drawingId",drawingId);
                            i.putExtra("markers",markersr);
                            startActivity(i);
                        }
                    });



                }
            }
        });



    }
}