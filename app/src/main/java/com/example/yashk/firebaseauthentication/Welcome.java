package com.example.yashk.firebaseauthentication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Welcome extends AppCompatActivity {

    private Button logout;
    Boolean emailVerify = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        logout = (Button)findViewById(R.id.button3);
        FirebaseAuth firebaseAuth;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null)
        emailVerify = user.isEmailVerified();


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(emailVerify)
                Toast.makeText(getBaseContext(),"Email verified..",Toast.LENGTH_SHORT).show();

                else
                    Toast.makeText(getBaseContext(),"Please verify your email id..",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
