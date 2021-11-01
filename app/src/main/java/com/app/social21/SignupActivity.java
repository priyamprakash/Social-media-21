package com.app.social21;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void gotoLogin(View view) {
        Intent i = new Intent(SignupActivity.this , LoginActivity.class);
        startActivity(i);
    }
}