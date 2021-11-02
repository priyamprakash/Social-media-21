package com.app.social21.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.social21.Adapter.FollowersAdapter;
import com.app.social21.Model.Follow;
import com.app.social21.Model.User;
import com.app.social21.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {


    RecyclerView recyclerView;
    ArrayList<Follow> list;
    CircleImageView dp;
    ImageView change_cover_photo , cover_photo ,edit_pic;
    FirebaseAuth auth;
    FirebaseStorage  storage;
    FirebaseDatabase database;
    TextView userName , profession , followers;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        recyclerView = view.findViewById(R.id.friendRv);
        cover_photo = view.findViewById(R.id.cover_photo);
        edit_pic = view.findViewById(R.id.edit_pic);
        dp = view.findViewById(R.id.dp);
        userName = view.findViewById(R.id.userName);
        profession = view.findViewById(R.id.profession);
        followers = view.findViewById(R.id.followers);


        change_cover_photo = view.findViewById(R.id.change_cover_photo);

//        getting user cover photo from the users node >> uid >> cover photo
        database.getReference().child("Users").child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    User user = snapshot.getValue(User.class);
                    Picasso.get()
                            .load(user.getCoverPhoto())
                            .placeholder(R.drawable.img_default)
                            .into(cover_photo);

                    Picasso.get()
                            .load(user.getProfilePic())
                            .placeholder(R.drawable.img_default)
                            .into(dp);

                    userName.setText(user.getName());
                    profession.setText(user.getProfession());
                    followers.setText(user.getFollowerCount() + "");
                    //fine
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        list = new ArrayList<>();



        FollowersAdapter adapter =  new FollowersAdapter(list ,getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext() , LinearLayoutManager.HORIZONTAL , false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        database.getReference().child("Users")
                .child(auth.getUid())
                .child("followers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Follow follow = dataSnapshot.getValue(Follow.class);
                    list.add(follow);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        change_cover_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent , 11);
            }
        });

        edit_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent , 22);
            }
        });

        return  view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11) {
//      CHANGE COVER PHOTO

            if (data.getData() != null) {
                Uri uri = data.getData();
                cover_photo.setImageURI(uri);


                final StorageReference reference = storage.getReference().child("cover_photo")
                        .child(FirebaseAuth.getInstance().getUid());//fine

                reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getContext(), "Cover photo saved", Toast.LENGTH_SHORT).show();
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                database.getReference().child("Users").child(auth.getUid()).child("coverPhoto").setValue(uri.toString());

                            }
                        });
                    }
                });
            }
        }

        if(requestCode == 22){
            //      CHANGE DISPLAY  PROFILE

            if (data.getData() != null) {
                Uri uri = data.getData();
                dp.setImageURI(uri);

                final StorageReference reference = storage.getReference().child("profile_pic")
                        .child(FirebaseAuth.getInstance().getUid());//fine

                reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getContext(), "Profile pic saved", Toast.LENGTH_SHORT).show();
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                database.getReference().child("Users").child(auth.getUid()).child("profilePic").setValue(uri.toString());

                            }
                        });
                    }
                });
            }

        }


    }
}