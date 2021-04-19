package edu.neu.madcourse.puzzle_game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameFld;
    private EditText emailFld;
    private EditText pwFld;

    private String username;
    private String email;
    private String password;
    private FirebaseAuth mAuth;

    private final String TAG = "CUSTOM MSG: ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        usernameFld = findViewById(R.id.usernameFld);
        emailFld = findViewById(R.id.emailFld);
        pwFld = findViewById(R.id.pwFld);
    }


    public void registerOnClick(View view) {
        username = usernameFld.getText().toString();
        email = emailFld.getText().toString();
        password = pwFld.getText().toString();

        if (email.length() == 0 || password.length() == 0) {
            Toast.makeText(RegisterActivity.this, "Empty credentials are not allowed.",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                registerOnDatabase(username, email, user.getUid());

                                Toast.makeText(RegisterActivity.this, "Registration complete.",
                                        Toast.LENGTH_SHORT).show();

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

    /**
     * Push the user data to the database once a new user account is registered
     */
    private void registerOnDatabase(String username, String email, String uid) {
        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        map.put("email", email);
        map.put("uid", uid);

        if (username.isEmpty() || email.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Empty credentials are not allowed.", Toast.LENGTH_SHORT).show();
        } else {
            FirebaseDatabase.getInstance().getReference().child("Users").push().setValue(map);
        }

    }

}