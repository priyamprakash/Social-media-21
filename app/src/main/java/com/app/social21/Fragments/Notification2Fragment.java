package com.app.social21.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.social21.Adapter.NotificationAdapter;
import com.app.social21.Model.NotificationModel;
import com.app.social21.R;

import java.util.ArrayList;

public class Notification2Fragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<NotificationModel> list;

    public Notification2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_notification2, container, false);
        recyclerView = view.findViewById(R.id.notification2_rv);

        list = new ArrayList<>();
        list.add(new NotificationModel(R.drawable.img_default , "<b>Elon Musk</b> mentioned you in a comment" , "just now"));
        list.add(new NotificationModel(R.drawable.img_default , "<b>Janeelona</b> commented on your pic" , "just now"));
        list.add(new NotificationModel(R.drawable.img_default , "<b>Katherin</b> tagged you in a comment" , "just now"));
        list.add(new NotificationModel(R.drawable.img_default , "<b>Alcia</b> mentioned you in a comment" , "just now"));
        list.add(new NotificationModel(R.drawable.img_default , "<b>Elon Musk</b> mentioned you in a comment" , "just now"));
        list.add(new NotificationModel(R.drawable.img_default , "<b>Viraj Sharma</b> mentioned you in a comment" , "just now"));

        NotificationAdapter adapter = new NotificationAdapter(list , getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext() , LinearLayoutManager.VERTICAL , false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }
}