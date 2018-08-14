package com.mukhayy.retrofit.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.mukhayy.retrofit.R;
import com.mukhayy.retrofit.database.dbManager;

import java.util.concurrent.TimeUnit;

public class ProfileLoginActivity extends AppCompatActivity {

    private EditText phoneNumber;
    TextView signUp;
    Button next;
    dbManager db;
    Session session;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_login);

        phoneNumber = findViewById(R.id.phone);
        next = findViewById(R.id.login);
        signUp = findViewById(R.id.signUp);
       // session = new Session(this);
        phone = phoneNumber.getText().toString();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db = new dbManager(ProfileLoginActivity.this);
                db.open();

                        if(db.getUser(phone)){
                          //  session.setLoggedin(true);
                            Intent intent = new Intent(ProfileLoginActivity.this, ConfirmationActivity.class);
                            startActivity(intent);
                            finish();
                        }else
                            Toast.makeText(ProfileLoginActivity.this, "Incorrect ", Toast.LENGTH_SHORT).show();


               }
        });

/*
        if(session.loggedin()){
            Intent intent = new Intent(ProfileLoginActivity.this, ConfirmationActivity.class);
            startActivity(intent);
            finish();
        }
*/
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileLoginActivity.this, ProfileActivivty.class);
                startActivity(intent);
            }
        });
    }
}
