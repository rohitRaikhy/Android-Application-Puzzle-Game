package edu.neu.madcourse.puzzle_game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.neu.madcourse.puzzle_game.Model.User;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseUser user;
    private EditText usernameFld;
    private EditText emailFld;
    private EditText mobile;
    private String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // get the current user
        user = FirebaseAuth.getInstance().getCurrentUser();
        usernameFld = findViewById(R.id.usernameFld);
        emailFld = findViewById(R.id.emailFld);

        if (user != null) {
            String emailStr = user.getEmail();
            emailFld.setText(emailStr);

            String uid = user.getUid();
            findUsernameByUID(uid);
        }

    }


    private void findUsernameByUID(String uid) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    User userData = dataSnapshot.getValue(User.class);

                    System.out.println(uid);
                    System.out.println(userData.getUid());
                    System.out.println(userData.getEmail());

                    if (userData.getUid().equals(uid)) {
                        username = userData.getUsername();
                        usernameFld.setText(username);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}