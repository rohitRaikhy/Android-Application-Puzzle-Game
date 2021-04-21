package edu.neu.madcourse.puzzle_game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {
    private TextView usernameView;
    private TextView emailView;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        usernameView = findViewById(R.id.profile_username_field);
        emailView = findViewById(R.id.profile_email_field);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        String userId = firebaseUser.getUid();
        FirebaseDatabase.getInstance().getReference().child("users").child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    usernameView.setText(task.getResult().child("username").getValue().toString());
                    emailView.setText(task.getResult().child("email").getValue().toString());
                }
            }
        });
    }
}