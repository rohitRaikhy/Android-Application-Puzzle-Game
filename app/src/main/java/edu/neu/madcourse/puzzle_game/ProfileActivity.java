package edu.neu.madcourse.puzzle_game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.io.ByteArrayOutputStream;

public class ProfileActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;

    TextView usernameView;
    TextView emailView;
    ImageView profilePicView;
    Bitmap profileBitmap;

    FirebaseUser firebaseUser;
    DatabaseReference dbRef;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Cache UI Views
        usernameView = findViewById(R.id.profile_username_field);
        emailView = findViewById(R.id.profile_email_field);
        profilePicView = findViewById(R.id.profile_pic);

        // Cache Firebase data
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        dbRef = FirebaseDatabase.getInstance().getReference();
        userID = firebaseUser.getUid();

        updateProfileViewData();
    }

    void updateProfileViewData() {
        dbRef.child("users").child(userID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    usernameView.setText(task.getResult().child("username").getValue().toString());
                    emailView.setText(task.getResult().child("email").getValue().toString());

                    // Decode string -> bitmap
                    Object obj = task.getResult().child("profileBitmap").getValue();
                    if (obj == null)
                        return;

                    String stringEncoded = obj.toString();
                    if (stringEncoded == null)
                        return;

                    byte[] bytes = Base64.decode(stringEncoded, Base64.URL_SAFE);
                    profileBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                    profilePicView.setImageBitmap(profileBitmap);
                }
            }
        });
    }

    // Create take picture intent
    public void onTakePicture(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(view.getContext(), "Could not find camera sensor", Toast.LENGTH_SHORT);
        }
    }

    // Receive new picture from camera
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            profileBitmap = imageBitmap;
            savePictureOnDB();
        }
    }

    // Save picture into database
    void savePictureOnDB() {
        // Compress & encode into string (so we can still use realtime database)
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        profileBitmap.compress(Bitmap.CompressFormat.PNG, 100, bao); // bmp is bitmap from user image file
        profileBitmap.recycle();
        byte[] byteArray = bao.toByteArray();
        String imageB64 = Base64.encodeToString(byteArray, Base64.URL_SAFE);

        dbRef.child("users").child(userID).child("profileBitmap").setValue(imageB64);

        updateProfileViewData();
    }
}