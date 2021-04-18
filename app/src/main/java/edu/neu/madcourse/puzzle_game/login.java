package edu.neu.madcourse.puzzle_game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class login extends AppCompatActivity {

//    private TextView emailText;
//    private TextView password;
    private SignInButton signIn;
    private GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN = 0;
    private FirebaseAuth mAuth;

    private EditText signInEmail;
    private EditText signInPw;
    private String email;
    private String password;

    private final String TAG = "CUSTOM MSG: ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        emailText = findViewById(R.id.signInEmail);
//        password = findViewById(R.id.signInPw);
        signIn = findViewById(R.id.sign_in_button);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                    // ...
                }
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();

        signInEmail = findViewById(R.id.signInEmail);
        signInPw = findViewById(R.id.signInPw);

    }

    /**
     * Sign in functionality for google oauth firebase.
     */
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    /**
     * Handles the result of the sign in google oath firebase.
     *
     * @param completedTask the signed in user account google oath firebase.
     */
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Log.d("Sign-In", "firebaseAuthWithGoogle:" + account.getId());
            Toast toast = Toast.makeText(getApplicationContext(), "Sign in successful.",
                    Toast.LENGTH_SHORT);
            toast.show();

            //TODO: If the sign in is successful then add data of user to the home screen intent/profile intent
            // Open a new activity and go to the main page.
            //TODO: Add to the activity user info
            googleSignInActivity(account);

        } catch (ApiException e) {
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
            Toast toast = Toast.makeText(getApplicationContext(), "Sign in failed. Please " +
                    "try again.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }


    /**
     * sign in button functionality for google oath.
     *
     * @param idToken the id token of the use google oath firebase.
     */
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("Sign-in", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            Log.w("Fail", "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }

    /**
     * Used to open a new intent when google auth sign in succeeds.
     */
    private void googleSignInActivity(GoogleSignInAccount account) {
        Intent intent = new Intent(this, HomePage.class);
        intent.putExtra("username",  account.getDisplayName());
        intent.putExtra("email", account.getEmail());
        intent.putExtra("account", account.getAccount());
        startActivity(intent);
    }

    /**
     * Navigate to the Register page when the register button is clicked
     */
    public void goToRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }


    /**
     * Use Firebase Auth to log into the user account
     */
    public void logInOnClick(View view) {

        email = signInEmail.getText().toString();
        password = signInPw.getText().toString();

        if (email.length() == 0 || password.length() == 0) {
            Toast.makeText(login.this, "Empty credentials are not allowed.",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                TextView welcomeMsg = findViewById(R.id.welcomeMsg);
                                welcomeMsg.setText("Welcome " + user.getEmail());
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(login.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    /**
     * Navigate to the profile page when the profile button is clicked
     */
    public void goToProfile(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAuth.signOut();
    }



}