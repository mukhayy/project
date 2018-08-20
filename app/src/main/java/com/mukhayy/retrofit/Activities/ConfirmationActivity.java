package com.mukhayy.retrofit.Activities;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.util.SharedPreferencesUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.mukhayy.retrofit.R;
import com.mukhayy.retrofit.utils.Session;

import java.util.concurrent.TimeUnit;

public class ConfirmationActivity extends AppCompatActivity {

    private Button next_;
    private EditText code;
    private String verificationId;
    FirebaseAuth auth;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmtion);

        session = new Session(ConfirmationActivity.this);

        if(session.loggedin()){
            Intent intent = new Intent(ConfirmationActivity.this, ProfileHomeActivity.class);
            startActivity(intent);
        }

        code = findViewById(R.id.code);
        next_ = findViewById(R.id.next_);
        auth = FirebaseAuth.getInstance();

        String phoneNumber = getIntent().getStringExtra("phoneNumber").trim();
        sendVerificationCode(phoneNumber);
        session.setLoggedin(true);

        next_.setOnClickListener(new View.OnClickListener() {
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
    }

    protected void verifySignInCode(String codeEntered){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, codeEntered);
        signInWithCredential(credential);
    }

    protected void signInWithCredential(PhoneAuthCredential credential){
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //FirebaseUser user = task.getResult().getUser();
                            String phoneNumber = getIntent().getStringExtra("phoneNumber").trim();

                            Intent intent = new Intent(ConfirmationActivity.this, ProfileHomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.putExtra("phoneNumber", phoneNumber);
                            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(ConfirmationActivity.this).toBundle());

                        }else {
                            Toast.makeText(ConfirmationActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
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
                code.setText(codereceived);
                verifySignInCode(codereceived);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(ConfirmationActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
