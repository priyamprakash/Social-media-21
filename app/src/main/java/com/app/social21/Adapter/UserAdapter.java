package com.app.social21.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.app.social21.Model.Follow;
import com.app.social21.Model.Notification;
import com.app.social21.Model.User;
import com.app.social21.R;
import com.app.social21.databinding.UsersSampleBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.viewHolder> {
    Context context;
    ArrayList<User> list;

    public UserAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.users_sample , parent , false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        User user = list.get(position);
        Picasso.get().load(user.getProfilePic())
                .placeholder(R.drawable.img_default)
                .into(holder.binding.profileImage);
        holder.binding.name.setText(user.getName());
        holder.binding.profession.setText(user.getProfession());


        FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .child(user.getUserID())
                .child("followers")
                .child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    holder.binding.followButton.setBackground(ContextCompat.getDrawable(context , R.drawable.follow_active_button));
                    holder.binding.followButton.setText("Following");
                    holder.binding.followButton.setTextColor(context.getResources().getColor(R.color.grey));
                    holder.binding.followButton.setEnabled(false);
                }
                else {
                    holder.binding.followButton.setOnClickListener(new View.OnClickListener() {
                        //            in the node of the user I started following
                        @Override
                        public void onClick(View v) {
                            Follow follow =  new Follow();
                            follow.setFollowedBy(FirebaseAuth.getInstance().getUid());
                            follow.setFollowedAt(new Date().getTime());


                            FirebaseDatabase.getInstance().getReference()
                                    .child("Users")
                                    .child(user.getUserID())
                                    .child("followers")
                                    .child(FirebaseAuth.getInstance().getUid())
                                    .setValue(follow).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    FirebaseDatabase.getInstance().getReference()
                                            .child("Users")
                                            .child(user.getUserID())
                                            .child("followerCount")
                                            .setValue(user.getFollowerCount() + 1)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    holder.binding.followButton.setBackground(ContextCompat.getDrawable(context , R.drawable.follow_active_button));
                                                    holder.binding.followButton.setText("Following");
                                                    holder.binding.followButton.setTextColor(context.getResources().getColor(R.color.grey));
                                                    holder.binding.followButton.setEnabled(false);
                                                    Toast.makeText(context, "You Followed : "  + user.getName(), Toast.LENGTH_SHORT).show();

                                                    Notification notification = new Notification();
                                                    notification.setNotificationBy(FirebaseAuth.getInstance().getUid());
                                                    notification.setType("follow");

                                                    FirebaseDatabase.getInstance().getReference()
                                                            .child("notification")
                                                            .child(user.getUserID())
                                                            .push()
                                                            .setValue(notification);
                                                }
                                            });
                                }
                            });

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        UsersSampleBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = UsersSampleBinding.bind(itemView);

        }
    }
}
