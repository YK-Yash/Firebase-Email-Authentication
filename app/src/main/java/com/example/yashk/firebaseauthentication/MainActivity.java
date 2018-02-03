package com.example.yashk.firebaseauthentication;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private EditText edtName,edtEmail,edtPassword,edtotp;
    private Button btnSignUp,btnSignIn;

    private FirebaseAuth firebaseAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private String mVerificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtName = (EditText)findViewById(R.id.edtname);
        edtEmail = (EditText)findViewById(R.id.edtEmail);
        edtPassword = (EditText)findViewById(R.id.edtPassword);

        btnSignIn = (Button)findViewById(R.id.btnsignin);
        btnSignUp = (Button)findViewById(R.id.btnSignUp);

        firebaseAuth = FirebaseAuth.getInstance();


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signUpUsingEmailPassword(edtEmail.getText().toString(),edtPassword.getText().toString());

            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signInTheUserWithEmailPassword(edtEmail.getText().toString(),edtPassword.getText().toString());
                 }
        });

    }

    private void signUpUsingEmailPassword(String userEmail,String userPassword)
    {

        firebaseAuth.createUserWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(!task.isSuccessful()){

                    Toast.makeText(getBaseContext(),"Login Unsuccessful",Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(getBaseContext(),"Signed up..",Toast.LENGTH_SHORT).show();
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            try{
                                if(user!=null)
                            user.sendEmailVerification();
                            }catch(Exception e){
                                Toast.makeText(getBaseContext(),"E-mail not sent..",Toast.LENGTH_LONG).show();
                            }
                        }
                    },3000);
                    specifyUserProfile();
                }

            }
        });

    }

    private void specifyUserProfile()
    {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null){

            UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(edtName.getText().toString()).build();
            firebaseUser.updateProfile(userProfileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful())
                    {

                    }else{

                    }

                }
            });

        }
    }

    private void signInTheUserWithEmailPassword(String userEmail,String userPassword)
    {
        firebaseAuth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(!task.isSuccessful())
                {
                    Toast.makeText(getBaseContext(),"Sign in not successful",Toast.LENGTH_SHORT).show();
                }

                else{
                    Toast.makeText(getBaseContext(),"Welcome..",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this,Welcome.class);
                    startActivity(intent);
                }

            }
        });
    }
/*
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            Toast.makeText(getBaseContext(),"OTP verified",Toast.LENGTH_LONG).show();
                            // ...
                        } else {
                            Toast.makeText(getBaseContext(),"OTP not verified",Toast.LENGTH_LONG).show();
                            // Sign in failed, display a message and update the UI
                            //Log.w(TAG, "signInWithCredential:failure", task.getException());
                            //if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            //}
                        }
                    }
                });
    }*/

}
