package com.app.social21;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.app.social21.Adapter.CommentAdapter;
import com.app.social21.Model.Comment;
import com.app.social21.Model.Notification;
import com.app.social21.Model.Post;
import com.app.social21.Model.User;
import com.app.social21.databinding.ActivityCommentActvityBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class CommentActvity extends AppCompatActivity {

    ActivityCommentActvityBinding binding;
    Intent intent;
    String postId;
    String postedBy;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ArrayList<Comment> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCommentActvityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intent = getIntent();


        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        postId = intent.getStringExtra("postId");
        postedBy = intent.getStringExtra("postedBy");

        database.getReference().child("posts")
                .child(postId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Post post = snapshot.getValue(Post.class);
                Picasso.get().load(post.getPostImage()).placeholder(R.drawable.img_man1).into(binding.postImage);
                binding.description.setText(post.getPostDescription());
                binding.nlike.setText(post.getPostLike() + "");
                binding.nComment.setText(post.getCommentCount()+ "");


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        database.getReference().child("Users").child(postedBy).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                Picasso.get().load(user.getProfilePic()).placeholder(R.drawable.img_man1).into(binding.profileImage);
                binding.name.setText(user.getName());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.commentPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Comment comment = new Comment();
                comment.setCommentBody(binding.commentEt.getText().toString());
                comment.setCommentedAt(new Date().getTime());
                comment.setCommentedBy(FirebaseAuth.getInstance().getUid());

                database.getReference()
                        .child("posts")
                        .child(postId)
                        .child("comments")
                        .push()
                        .setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        database.getReference()
                                .child("posts")
                                .child(postId)
                                .child("commentCount").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int commentCount = 0 ;
                                if(snapshot.exists())
                                {
                                    commentCount = snapshot.getValue(Integer.class);
                                }
                                database.getReference()
                                        .child("posts")
                                        .child(postId)
                                        .child("commentCount").setValue(commentCount + 1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        binding.commentEt.setText("");
                                        Toast.makeText(CommentActvity.this, "Commented", Toast.LENGTH_SHORT).show();

                                        Notification notification = new Notification();
                                        notification.setNotificationBy(FirebaseAuth.getInstance().getUid());
                                        notification.setNotificationAt(new Date().getTime());
                                        notification.setPostId(postId);
                                        notification.setPostedBy(postedBy);
                                        notification.setType("comment");

                                        FirebaseDatabase.getInstance().getReference()
                                                .child("notification")
                                                .child(postedBy)
                                                .push()
                                                .setValue(notification);
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });
            }
        });

        CommentAdapter adapter = new CommentAdapter(this , list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.commentsRv.setLayoutManager(layoutManager);
        binding.commentsRv.setAdapter(adapter);

        database.getReference().child("posts").child(postId).child("comments").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Comment comment = dataSnapshot.getValue(Comment.class);
                    list.add(comment);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}