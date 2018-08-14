package com.mukhayy.retrofit.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mukhayy.retrofit.Models.User;
import com.mukhayy.retrofit.R;
import com.mukhayy.retrofit.database.dbManager;

public class ProfileActivivty extends AppCompatActivity {

    private EditText phone, firstName, lastName;
    private TextView login;
    dbManager db;
    FirebaseAuth auth;
    DatabaseReference ref;
    User user;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        phone = findViewById(R.id.phone);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        Button register = findViewById(R.id.register);
        login = findViewById(R.id.login);


        db = new dbManager(ProfileActivivty.this);
        db.open();

        FirebaseApp.initializeApp(ProfileActivivty.this);
        ref = FirebaseDatabase.getInstance().getReference("User");
        auth = FirebaseAuth.getInstance();
        user = new User();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fName = firstName.getText().toString().trim();
                String lName = lastName.getText().toString().trim();
                String phoneNumber = phone.getText().toString().trim();

                if (phoneNumber.isEmpty()) {
                    phone.setError("Valid number is required");
                    phone.requestFocus();
                    return;
                }

                if (fName.isEmpty()) {
                    firstName.setError("First name is required");
                    firstName.requestFocus();
                    return;
                }

                if (lName.isEmpty()) {
                    lastName.setError("Last name is required");
                    lastName.requestFocus();
                    return;
                }

                db.insert(fName, lName, phoneNumber);

                getValues();
                ref.push().setValue(user);


                Intent intent = new Intent(ProfileActivivty.this, ConfirmationActivity.class);
                intent.putExtra("phoneNumber", phoneNumber);
                startActivity(intent);

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivivty.this, ProfileLoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getValues(){
        //user.setFirstName(firstName.getText().toString().trim());
        //user.setLastName(lastName.getText().toString().trim());
        user.setPhone(phone.getText().toString().trim());
    }
}
