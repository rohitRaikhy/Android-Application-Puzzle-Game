package edu.neu.madcourse.puzzle_game;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.neu.madcourse.puzzle_game.Adapter.MessageAdapter;
import edu.neu.madcourse.puzzle_game.Model.Chat;
import edu.neu.madcourse.puzzle_game.Model.GameUser;

public class MessageActivity extends AppCompatActivity {

    private CircleImageView profileImage;
    private TextView username;
    private FirebaseUser fUser;
    private DatabaseReference reference;
    private Toolbar toolbar;
    private ImageButton btn_send;
    private EditText text_send;
    private MessageAdapter messageAdapter;
    private List<Chat> mChat;
    private RecyclerView recyclerView;
    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        toolbar = findViewById(R.id.toolbar2);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        profileImage = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);
        btn_send = findViewById(R.id.btn_send);
        text_send = findViewById(R.id.text_send);
        recyclerView = findViewById(R.id.recycler_view);
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent = getIntent();
        userid = intent.getStringExtra("userId");

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = text_send.getText().toString();
                if (!msg.equals("")) {
                    sendMessage(fUser.getUid(), userid, msg);
                } else {
                    Toast.makeText(MessageActivity.this, "Message cannot be sent",
                            Toast.LENGTH_SHORT);
                }
                text_send.setText("");
            }
        });

        fUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users").child(userid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GameUser user = snapshot.getValue(GameUser.class);
                username.setText(user.getUsername());
                if (user.getImageUrl().equals("default")) {
                    profileImage.setImageResource(R.mipmap.ic_launcher);
                }
                readMessages(fUser.getUid(), userid, user.getImageUrl());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * Functionality to send messages.
     *
     * @param sender   the sender of the message.
     * @param receiver the receiver of the message.
     * @param message  the message body.
     */
    private void sendMessage(String sender, String receiver, String message) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        reference.child("Chats").push().setValue(hashMap);

        DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("ChatList")
                .child(fUser.getUid()).child(userid);

        chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    chatRef.child("id").setValue(userid);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * functionality to read messages into recycler view.
     *
     * @param myid     the id of the user.
     * @param userId   the userid of the messenger.
     * @param imageUrl the image url.
     */
    private void readMessages(String myid, String userId, String imageUrl) {
        mChat = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mChat.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Chat chat = snap.getValue(Chat.class);
                    if (chat.getReceiver().equals(myid) && chat.getSender().equals(userId) ||
                            chat.getReceiver().equals(userId) && chat.getSender().equals(myid)) {
                        mChat.add(chat);
                    }
                    messageAdapter = new MessageAdapter(MessageActivity.this, mChat,
                            imageUrl);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}