package com.app.social21.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.social21.CommentActvity;
import com.app.social21.Model.Notification;
import com.app.social21.Model.Post;
import com.app.social21.Model.User;
import com.app.social21.R;
import com.app.social21.databinding.PostSampleBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.viewHolder> {

    ArrayList<Post> list;
    Context context;

    public PostAdapter(ArrayList<Post> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull

    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_sample, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.viewHolder holder, int position) {
        Post model = list.get(position);
        Picasso.get()
                .load(model.getPostImage())
                .placeholder(R.drawable.img_man1)
                .into(holder.binding.postImage);
        holder.binding.nlike.setText(model.getPostLike() + "");
        holder.binding.nComment.setText(model.getCommentCount() + "");
        String description = model.getPostDescription();

        if (description == ""){
            holder.binding.postDescription.setVisibility(View.GONE);

        }
        else {
            holder.binding.postDescription.setText(model.getPostDescription());
            holder.binding.postDescription.setVisibility(View.VISIBLE);

        }



        FirebaseDatabase.getInstance().getReference().child("Users").child(model.getPostedBy()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                Picasso.get().load(user.getProfilePic()).placeholder(R.drawable.img_man1).into(holder.binding.profileImage);
                holder.binding.username.setText(user.getName());
                holder.binding.profession.setText(user.getProfession());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference().child("posts").child(model.getPostId())
                .child("likes").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            if(snapshot.exists()){
                holder.binding.like.setImageResource(R.drawable.red_like);
            }
            else {
                holder.binding.like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseDatabase.getInstance().getReference().child("posts").child(model.getPostId())
                                .child("likes")
                                .child(FirebaseAuth.getInstance().getUid()).setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                FirebaseDatabase.getInstance().getReference()
                                        .child("posts")
                                        .child(model.getPostId())
                                        .child("postLike")
                                        .setValue(model.getPostLike() + 1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        holder.binding.like.setImageResource(R.drawable.red_like);


                                        Notification notification = new Notification();
                                        notification.setNotificationBy(FirebaseAuth.getInstance().getUid());
                                        notification.setNotificationAt(new Date().getTime());
                                        notification.setPostId(model.getPostId());
                                        notification.setPostedBy(model.getPostedBy());
                                        notification.setType("like");

                                        FirebaseDatabase.getInstance().getReference()
                                                .child("notification")
                                                .child(model.getPostedBy())
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

        holder.binding.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext() , CommentActvity.class);
                intent.putExtra("postId" ,model.getPostId());
                intent.putExtra("postedBy", model.getPostedBy());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    //---------------------------------
    public class viewHolder extends RecyclerView.ViewHolder {

        PostSampleBinding binding;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = PostSampleBinding.bind(itemView);


        }
    }
}

