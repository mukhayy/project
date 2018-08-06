package com.mukhayy.retrofit.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.mukhayy.retrofit.R;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class ConfirmationFragment extends Fragment {

    private Button next;
    private EditText code;
    private String verificationId;
    FirebaseAuth auth;

    public ConfirmationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirmation, container, false);

        code = view.findViewById(R.id.code);
        next = view.findViewById(R.id.Next);

        auth = FirebaseAuth.getInstance();
        String phoneNumber = getActivity().getIntent().getStringExtra("phoneNumber").trim();
        sendVerificationCode(phoneNumber);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String codee = code.getText().toString().trim();
                if (codee.isEmpty() || codee.length() < 6) {
                    code.setError("Enter code...");
                    code.requestFocus();
                    return;
                }
                verifySignInCode(codee);
            }
        });
        return view;
    }


    private void verifySignInCode(String codeEntered){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, codeEntered);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential){
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(getContext(), ProfileHomeFragment.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        }else {
                            Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void sendVerificationCode(String phone){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,
                60,
                TimeUnit.SECONDS,
                getActivity(),
                mCallbacks );
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String codereceived = phoneAuthCredential.getSmsCode();
            if (codereceived != null) {
                code.setText(codereceived);
                verifySignInCode(codereceived);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

    };

    @Override
    public void onStop() {
        super.onStop();
        //finish();
    }

}
