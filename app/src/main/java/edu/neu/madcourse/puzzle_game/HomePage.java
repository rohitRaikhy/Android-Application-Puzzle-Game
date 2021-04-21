package edu.neu.madcourse.puzzle_game;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class HomePage extends AppCompatActivity {

    private Button messengerButton;
    private String username;
    private String email;
    private FirebaseAuth mAuth;
    private String profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        mAuth = FirebaseAuth.getInstance();
        messengerButton = findViewById(R.id.messenger);
        username = getIntent().getStringExtra("username");
        email = getIntent().getStringExtra("email");
        profilePic = getIntent().getStringExtra("profileImage");
        messengerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMessenger();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.logout) {
            mAuth.signOut();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mAuth.signOut();
    }

    /**
     * Opens up the messenger service.
     */
    private void openMessenger() {
        System.out.println("[HOMEPAGE] ***");
        System.out.println(username);
        System.out.println(email);

        Intent intent = new Intent(HomePage.this, MessagingActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("profilePic", profilePic);
        startActivity(intent);
    }


    public void goToSelectPuzzleActivity(View view) {
        Intent intent = new Intent(this, SelectPuzzleActivity.class);
        startActivity(intent);
    }

    public void goToProfileActivity(View view) {
        System.out.println("[HOMEPAGE] ***");
        System.out.println(username);
        System.out.println(email);

        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("email", email);
        startActivity(intent);
    }
}