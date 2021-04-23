package edu.neu.madcourse.puzzle_game.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.neu.madcourse.puzzle_game.MessageActivity;
import edu.neu.madcourse.puzzle_game.Model.GameUser;
import edu.neu.madcourse.puzzle_game.R;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context mContext;
    private List<GameUser> mUsers;

    /**
     * Constructor to make user adapter.
     *
     * @param mContext the context.
     * @param mUsers   list of GameUsers.
     */
    public UserAdapter(Context mContext, List<GameUser> mUsers) {
        this.mContext = mContext;
        this.mUsers = mUsers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GameUser user = mUsers.get(position);
        holder.username.setText(user.getUsername());

//        if (user.getImageUrl().equals("default")) {
//            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
//        }

        // Decode string -> bitmap
        String stringEncoded = user.getProfileBitmap();
        if (stringEncoded == null)
            return;

        byte[] bytes = Base64.decode(stringEncoded, Base64.URL_SAFE);
        Bitmap profileBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        holder.profile_image.setImageBitmap(profileBitmap);

        //TODO: add the glide package to load image url of signed in user.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MessageActivity.class);
                intent.putExtra("userId", user.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    /**
     * View holder for username and profile image.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView username;
        private ImageView profile_image;

        public ViewHolder(View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            profile_image = itemView.findViewById(R.id.profile_image);
        }
    }
}
