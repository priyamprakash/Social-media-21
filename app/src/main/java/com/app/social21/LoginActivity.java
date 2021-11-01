
package com.app.social21;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


    }

    public void gotoSignup(View view) {
        Intent i = new Intent(LoginActivity.this , SignupActivity.class);
        startActivity(i);
    }
}