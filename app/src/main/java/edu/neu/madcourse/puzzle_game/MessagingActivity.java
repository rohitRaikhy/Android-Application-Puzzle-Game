package edu.neu.madcourse.puzzle_game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.neu.madcourse.puzzle_game.Fragments.ChatFeatureFragment;
import edu.neu.madcourse.puzzle_game.Fragments.UsersFragment;

/**
 * TODO: Need to fix the menu button for sign out on auth for google sign in. Not yet set up for
 * other authentication on firebase.
 */

public class MessagingActivity extends AppCompatActivity {

    private CircleImageView profileImage;
    private TextView username;
    private FirebaseUser firebaseUser;
    private FirebaseAuth mAuth;
    private TabLayout tabLayout;
    private ViewPager viewpager;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        profileImage = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        tabLayout = findViewById(R.id.tab_layout);
        viewpager = findViewById(R.id.view_pager);

        mAuth = FirebaseAuth.getInstance();
        /**
         * check to see if username is null, if null then use username/email otherwise use
         * third party auth service.
         */
        if (getIntent().getStringExtra("username") != null) {
            username.setText(getIntent().getStringExtra("username"));
        } else {
            if (firebaseUser == null)
                return;

            String userId = firebaseUser.getUid();
            // Change the username of the icon.
            FirebaseDatabase.getInstance().getReference().child("users").child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    } else {
                        Log.d("firebase", String.valueOf(task.getResult().getValue()));
                        username.setText(task.getResult().child("username").getValue().toString());
                    }
                }
            });
        }
        /**
         * check to see if profile pic is null
         */
        if (getIntent().getStringExtra("profilePic") == null) {
//            profileImage.setImageResource(R.mipmap.ic_launcher);

            FirebaseDatabase.getInstance().getReference().child("users").child(firebaseUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    } else {
                        // Decode string -> bitmap
                        Object obj = task.getResult().child("profileBitmap").getValue();
                        if (obj == null)
                            return;

                        String stringEncoded = obj.toString();
                        if (stringEncoded == null)
                            return;

                        byte[] bytes = Base64.decode(stringEncoded, Base64.URL_SAFE);
                        Bitmap profileBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                        profileImage.setImageBitmap(profileBitmap);
                    }
                }
            });
        }

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new ChatFeatureFragment(), "chats");
        viewPagerAdapter.addFragments(new UsersFragment(), "users");
        viewpager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewpager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * TOdO This causes a crash, need to fix.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.logout) {
            mAuth.signOut();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Todo: this does not work yet. Showing errors in the console.
     */
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        mAuth.signOut();
//    }

    /**
     * adapter for the page adapter to swap between users and messages.
     */
    class ViewPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragments(Fragment fragment, String title) {
            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}