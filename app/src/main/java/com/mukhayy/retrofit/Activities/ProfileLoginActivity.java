package com.mukhayy.retrofit.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    EditText phoneNumber;
    Button next;
    dbManager db;
    Session session;
    private FirebaseAuth auth;
    ConfirmationActivity confirm;
    private String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_login);

        phoneNumber = findViewById(R.id.phone);
        next = findViewById(R.id.login);
        auth = FirebaseAuth.getInstance();
        session = new Session(this);
        confirm = new ConfirmationActivity();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = new dbManager(ProfileLoginActivity.this);
                db.open();
                String phone = phoneNumber.getText().toString();

                auth = FirebaseAuth.getInstance();
                confirm.sendVerificationCode(phone);


                if(db.getUser(phone)){
                    session.setLoggedin(true);
                    Intent intent = new Intent(ProfileLoginActivity.this, ProfileHomeActivity.class);
                    startActivity(intent);
                    finish();
                }else
                    Toast.makeText(ProfileLoginActivity.this, "Incorrect ", Toast.LENGTH_SHORT).show();
            }
        });

        if(session.loggedin()){
            Intent intent = new Intent(ProfileLoginActivity.this, ProfileHomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    protected void verifySignInCode(String codeEntered){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, codeEntered);
        signInWithCredential(credential);
    }

    protected void signInWithCredential(PhoneAuthCredential credential){
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = task.getResult().getUser();
                            Intent intent = new Intent(ProfileLoginActivity.this, ProfileHomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            intent.putExtra("user", user);

                            startActivity(intent);

                        }else {
                            Toast.makeText(ProfileLoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    protected void sendVerificationCode(String phone){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,
                60,
                TimeUnit.SECONDS,
                this,
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
                phoneNumber.setText(codereceived);
                verifySignInCode(codereceived);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(ProfileLoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };
}
