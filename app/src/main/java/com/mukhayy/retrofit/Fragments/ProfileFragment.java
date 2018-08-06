package com.mukhayy.retrofit.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

public class ProfileFragment extends Fragment {

    private EditText phone, name;
    FirebaseAuth auth;
    DatabaseReference ref;
    User user;

    public ProfileFragment() {// Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        phone = view.findViewById(R.id.phone);
        name = view.findViewById(R.id.Name);

        Button register = view.findViewById(R.id.register);
        //FirebaseApp.initializeApp(getContext());

        ref = FirebaseDatabase.getInstance().getReference("User");
        auth = FirebaseAuth.getInstance();
        user = new User();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValues();
                ref.push().setValue(user);
                String phoneNumber = phone.getText().toString().trim();

                if (phoneNumber.isEmpty()) {
                    phone.setError("Valid number is required");
                    phone.requestFocus();
                    return;
                }

                Intent intent = new Intent(getContext(), ConfirmationFragment.class);
                intent.putExtra("phoneNumber", phoneNumber);
                startActivity(intent);
            }
        });
        return view;
    }

    private void getValues(){
        user.setName(name.getText().toString().trim());
        user.setPhone(phone.getText().toString().trim());
    }
}