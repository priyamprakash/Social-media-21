package com.app.social21;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.app.social21.Fragments.AddFragment;
import com.app.social21.Fragments.HomeFragment;
import com.app.social21.Fragments.NotificationFragment;
import com.app.social21.Fragments.ProfileFragment;
import com.app.social21.Fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottom_navigation;
    Fragment selectedfragment =  null;
    FirebaseAuth auth;

     Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MainActivity.this.setTitle("My Profile");

        auth = FirebaseAuth.getInstance();


        bottom_navigation = findViewById(R.id.bottomBar);
        bottom_navigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        toolbar.setVisibility(View.GONE);
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
                            toolbar.setVisibility(View.GONE);
                            selectedfragment = new HomeFragment();
                            break;
                        case R.id.nav_search:
                            toolbar.setVisibility(View.GONE);
                            selectedfragment = new SearchFragment();
                            break;
                        case R.id.nav_add:
                            toolbar.setVisibility(View.GONE);
                            selectedfragment = new AddFragment();
                            break;
                        case R.id.nav_notification:
                            toolbar.setVisibility(View.GONE);
                            selectedfragment = new NotificationFragment();
                            break;
                        case R.id.nav_profile:
                            toolbar.setVisibility(View.VISIBLE);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case  R.id.setting :
                auth.signOut();
                Intent i = new Intent(MainActivity.this , LoginActivity.class);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}