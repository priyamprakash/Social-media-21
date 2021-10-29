package com.app.social21.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.social21.Adapter.DashboardAdapter;
import com.app.social21.Adapter.StoryAdapter;
import com.app.social21.Model.DashboardModel;
import com.app.social21.Model.StoryModel;
import com.app.social21.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView storyRv ,dashboardRv;
    ArrayList<StoryModel> list;
    ArrayList<DashboardModel> dashboardList;

    public HomeFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        storyRv = view.findViewById(R.id.storyRv);
        list = new ArrayList<>();

        list.add(new StoryModel(R.drawable.img_man1, R.drawable.img_live, R.drawable.profile, "Priyam"));
        list.add(new StoryModel(R.drawable.img_man1, R.drawable.img_video, R.drawable.profile, "Arsel"));
        list.add(new StoryModel(R.drawable.img_man1, R.drawable.img_live, R.drawable.profile, "Darshi"));
        list.add(new StoryModel(R.drawable.img_man1, R.drawable.img_video, R.drawable.profile, "Qwerty"));


        StoryAdapter adapter = new StoryAdapter(list, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        storyRv.setLayoutManager(linearLayoutManager);
        storyRv.setNestedScrollingEnabled(false);
        storyRv.setAdapter(adapter);




        dashboardRv = view.findViewById(R.id.dashboardRv);
        dashboardList = new ArrayList<>();

        dashboardList.add(new DashboardModel(R.drawable.profile , R.drawable.img_man1, R.drawable.ic_saved ,
                "Priyam Prakash" , "Photographer" , "123" , "22" , "37"));
        dashboardList.add(new DashboardModel(R.drawable.profile , R.drawable.img_man1, R.drawable.ic_saved ,
                "Derek" , "Traveller" , "123" , "22" , "37"));
        dashboardList.add(new DashboardModel(R.drawable.profile , R.drawable.img_man1, R.drawable.ic_saved ,
                "Javeed" , "Photographer" , "123" , "22" , "37"));
        dashboardList.add(new DashboardModel(R.drawable.profile , R.drawable.img_man1, R.drawable.ic_saved ,
                "Priyam Prakash" , "Photographer" , "123" , "22" , "37"));

        DashboardAdapter dashboardAdapter = new DashboardAdapter(dashboardList, getContext());
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        dashboardRv.setLayoutManager(linearLayoutManager2);
        dashboardRv.setNestedScrollingEnabled(false);
        dashboardRv.setAdapter(dashboardAdapter);
        return view;

    }
}