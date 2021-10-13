package com.app.social21;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.app.social21.Fragments.AddFragment;
import com.app.social21.Fragments.HomeFragment;
import com.app.social21.Fragments.NotificaionFragment;
import com.app.social21.Fragments.ProfileFragment;
import com.app.social21.Fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottom_navigation;
    Fragment selectedfragment =  null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottom_navigation = findViewById(R.id.bottomBar);
        bottom_navigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new HomeFragment());
        transaction.commit();



    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()){
                        case R.id.nav_home:
                            selectedfragment = new HomeFragment();
                            break;
                        case R.id.nav_search:
                            selectedfragment = new SearchFragment();
                            break;
                        case R.id.nav_add:
                            selectedfragment = new AddFragment();
                            break;
                        case R.id.nav_notification:
                            selectedfragment = new NotificaionFragment();
                            break;
                        case R.id.nav_profile:

                            selectedfragment = new ProfileFragment();
                            break;

                    }
                    if (selectedfragment != null) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                selectedfragment).commit();
                    }


                    return true;
                }
            };
}