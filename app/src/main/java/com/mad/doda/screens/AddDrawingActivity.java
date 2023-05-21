package com.mad.doda.screens;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.mad.doda.R;
import com.mad.doda.model.Drawing;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class AddDrawingActivity extends AppCompatActivity {

    EditText drawing_title;
    Button drawing_upload,drawing_submit;
    ImageView imageView;
    public static final int GET_FROM_GALLERY = 3;
    FirebaseStorage storage;
    Bitmap bitmap;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drawing);

        imageView = findViewById(R.id.imageview);

        drawing_title = findViewById(R.id.drawing_title);
        drawing_upload = findViewById(R.id.drawing_upload);
        drawing_submit = findViewById(R.id.drawing_submit);

        storage = FirebaseStorage.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("drawings");

        drawing_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
            }
        });

        drawing_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(drawing_title.getText().toString().trim().isEmpty()){
                    Toast.makeText(AddDrawingActivity.this, "Add Title", Toast.LENGTH_SHORT).show();
                } else if(imageView.getDrawable().toString().isEmpty()) {
                    Toast.makeText(AddDrawingActivity.this, "Upload Image", Toast.LENGTH_SHORT).show();
                } else {



                Drawing drawing = new Drawing();
                drawing.setTitle(drawing_title.getText().toString().trim());
                drawing.setId(ref.push().getKey());
                drawing.setMarkers("0");
                ref.child(drawing.getId()).setValue(drawing);

                bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = storage.getReference("drawings/"+drawing.getId()).putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        // ...
                    }
                });

                Intent i = new Intent(AddDrawingActivity.this,HomeActivity.class);
                startActivity(i);
                }

            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            imageView.setImageURI(null);
            imageView.setImageURI(selectedImage);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}