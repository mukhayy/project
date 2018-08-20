package com.mukhayy.retrofit.Activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mukhayy.retrofit.Fragments.InfoFragment;
import com.mukhayy.retrofit.R;
import com.mukhayy.retrofit.database.dbManager;
import com.mukhayy.retrofit.utils.Session;

public class ProfileHomeActivity extends AppCompatActivity {

    private LinearLayout infoLayout, paymentLayout, exitLayout;
    private TextView firstName, firstName1, lastName, lastName1, phone;
    private Session session;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_home);

        initialize();

        session = new Session(ProfileHomeActivity.this);
        if(!session.loggedin()){
           logOut();
        }

        dbManager mydb = new dbManager(this);
        mydb.open();


        final Bundle data = new Bundle();
        final String phoneNumber = getIntent().getStringExtra("phoneNumber").trim();
        data.putString("phone", phoneNumber);

        Cursor result = mydb.getDataFromDB(phoneNumber);
        if (result.getCount() == 0){ //if so, it doesn't have records
            Toast.makeText(this, "Error no data found", Toast.LENGTH_SHORT).show();
        }else
            while (result.moveToNext()){
                firstName.setText(result.getString(1));
                lastName.setText(result.getString(2));
                firstName1.setText(result.getString(1));
                lastName1.setText(result.getString(2));
                phone.setText(result.getString(3));
            }

        infoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InfoFragment fragment = new InfoFragment();
                fragment.setArguments(data);
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container, fragment);
            }
        });

         exitLayout.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View view) {
                   logOut();
                }
            });
    }

    private void initialize(){
        firstName = findViewById(R.id.firstName);
        firstName1 = findViewById(R.id.firstName1);
        lastName = findViewById(R.id.lastName);
        lastName1 = findViewById(R.id.lastName1);
        phone = findViewById(R.id.phone);

        infoLayout = findViewById(R.id.infoLayout);
        paymentLayout = findViewById(R.id.paymentLayout);
        exitLayout = findViewById(R.id.exitLayout);
        session = new Session(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void logOut(){
        session.setLoggedin(false);
        finish();
        Intent intent = new Intent(ProfileHomeActivity.this, ProfileActivivty.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }
}
