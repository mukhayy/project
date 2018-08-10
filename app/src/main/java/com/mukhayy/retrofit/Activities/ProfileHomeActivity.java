package com.mukhayy.retrofit.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mukhayy.retrofit.R;
import com.mukhayy.retrofit.database.dbManager;

public class ProfileHomeActivity extends AppCompatActivity {

    LinearLayout infoLayout, paymentLayout, exitLayout;
    private TextView firstName, firstName1, lastName, lastName1;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_home);

        initialize();
        if(!session.loggedin()){
           logOut();
        }
        dbManager mydb = new dbManager(this);
        mydb.open();
        Cursor result = mydb.getDataFromDB();
        if (result.getCount() == 0){ //if so, it doesn't have records
            Toast.makeText(this, "Error no data found", Toast.LENGTH_SHORT).show();
        }else
            while (result.moveToNext()){
                firstName.setText(result.getString(1));
                lastName.setText(result.getString(2));
                firstName1.setText(result.getString(1));
                lastName1.setText(result.getString(2));
            }


            exitLayout.setOnClickListener(new View.OnClickListener() {
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

        infoLayout = findViewById(R.id.infoLayout);
        paymentLayout = findViewById(R.id.paymentLayout);
        exitLayout = findViewById(R.id.exitLayout);
        session = new Session(this);
    }

    private void logOut(){
        session.setLoggedin(false);
        finish();
        Intent intent = new Intent(ProfileHomeActivity.this, ProfileLoginActivity.class);
        startActivity(intent);
    }
}
