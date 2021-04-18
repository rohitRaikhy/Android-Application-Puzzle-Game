package edu.neu.madcourse.puzzle_game;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
        profileImage  = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        tabLayout = findViewById(R.id.tab_layout);
        viewpager = findViewById(R.id.view_pager);
        /**
         * check to see if username is null
         */
        if(getIntent().getStringExtra("username") != null) {
            username.setText(getIntent().getStringExtra("username"));
        }
        /**
         * check to see if profile pic is null
         */
        if(getIntent().getStringExtra("profilePic") == null) {
            profileImage.setImageResource(R.mipmap.ic_launcher);
        }

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new ChatFeatureFragment(), "chats");
        viewPagerAdapter.addFragments(new UsersFragment(), "users");

        viewpager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewpager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.logout) {
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