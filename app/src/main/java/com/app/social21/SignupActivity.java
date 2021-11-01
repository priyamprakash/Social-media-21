package com.app.social21;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.social21.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase database;
    Button signup_btn;
    EditText nameET, professionET, emailET, passwordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
//        --------------------------------------------
        signup_btn = findViewById(R.id.signup_btn);

        nameET = findViewById(R.id.nameET);
        professionET = findViewById(R.id.professionET);
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
//       ---------------------------------------------
        auth = FirebaseAuth.getInstance();
        database =FirebaseDatabase.getInstance();


        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameET.getText().toString();
                String profession = professionET.getText().toString();
                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();


                auth.createUserWithEmailAndPassword(email, password).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    User user = new User(name, profession, email, password);
                                    String id =  task.getResult().getUser().getUid();
                                    database.getReference().child("Users").child(id).setValue(user);
                                    Toast.makeText(SignupActivity.this, "User Data saved", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(SignupActivity.this , MainActivity.class);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(SignupActivity.this, "Error", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });


    }

    public void gotoLogin(View view) {
        Intent i = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(i);
    }
}