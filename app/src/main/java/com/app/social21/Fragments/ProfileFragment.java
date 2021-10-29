package com.app.social21.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.social21.Adapter.FriendAdapter;
import com.app.social21.Model.FriendModel;
import com.app.social21.R;

import java.util.ArrayList;


public class ProfileFragment extends Fragment {


    RecyclerView recyclerView;
    ArrayList<FriendModel> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        recyclerView = view.findViewById(R.id.friendRv);


        list = new ArrayList<>();

        list.add(new FriendModel(R.drawable.img_default));
        list.add(new FriendModel(R.drawable.img_default));
        list.add(new FriendModel(R.drawable.img_default));
        list.add(new FriendModel(R.drawable.img_default));


        FriendAdapter adapter =  new FriendAdapter(list ,getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext() , LinearLayoutManager.HORIZONTAL , false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        return  view;
    }
}