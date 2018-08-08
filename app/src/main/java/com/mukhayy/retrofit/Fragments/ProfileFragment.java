package com.mukhayy.retrofit.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mukhayy.retrofit.Models.User;
import com.mukhayy.retrofit.R;
import com.mukhayy.retrofit.database.DatabaseHelper;
import com.mukhayy.retrofit.database.dbManager;

public class ProfileFragment extends Fragment {

    private EditText phone, firstName, lastName;

    dbManager db;
    FirebaseAuth auth;
    DatabaseReference ref;
    User user;

    public ProfileFragment() {// Required empty public constructor
         }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        phone = view.findViewById(R.id.phone);
        firstName = view.findViewById(R.id.firstName);
        lastName = view.findViewById(R.id.lastName);
        Button register = view.findViewById(R.id.register);

        db = new dbManager(getContext());
        db.open();

        FirebaseApp.initializeApp(getContext());
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

                //ProfileHomeFragment homeFragment = new ProfileHomeFragment();

                ConfirmationFragment confirmationFragment = new ConfirmationFragment();
                Bundle bundle = new Bundle();
                bundle.putString("phone", phoneNumber);
                confirmationFragment.setArguments(bundle);
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame_container, confirmationFragment);
                ft.commit();

            }
        });
        return view;
    }

    private void getValues(){
        //user.setFirstName(firstName.getText().toString().trim());
        //user.setLastName(lastName.getText().toString().trim());
        user.setPhone(phone.getText().toString().trim());
    }

}