package edu.neu.madcourse.puzzle_game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessagingActivity extends AppCompatActivity {

    private CircleImageView profileImage;
    private TextView username;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);

        profileImage  = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(getIntent().getStringExtra("username") != null) {
            username.setText(getIntent().getStringExtra("username"));
        }

    }
}