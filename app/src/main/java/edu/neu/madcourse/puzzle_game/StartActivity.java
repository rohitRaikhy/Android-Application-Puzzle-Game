package edu.neu.madcourse.puzzle_game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {

    private Button signInButton;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        signInButton = findViewById(R.id.login);
        user = FirebaseAuth.getInstance().getCurrentUser();

        // check to see if the user is null
        if(user != null) {
            Intent intent = new Intent(StartActivity.this, HomePage.class);
            startActivity(intent);
            finish();
        }

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, login.class);
                startActivity(intent);
            }
        });
    }
}