package com.example.powerassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class profile extends AppCompatActivity {

    private GoogleSignInClient mGoogleSignInClient;

    private FirebaseAuth firebaseAuth;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    String UID= mAuth.getUid();
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        int logval = extras.getInt("logval");

        firebaseAuth = FirebaseAuth.getInstance();

        getSupportActionBar().hide();
        TextView history = findViewById(R.id.textViewx);
        TextView userid = findViewById(R.id.textView10);
        TextView reset = findViewById(R.id.textView11);
        TextView delete = findViewById(R.id.textView12);
        TextView logout = findViewById(R.id.textView14);
        TextView back = findViewById(R.id.textView15);
        
        userid.setText("User ID : "+UID);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();
                startActivity(new Intent(profile.this, MainActivity.class));
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(profile.this, reset.class));

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(profile.this, deleteuser.class));

            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(profile.this,history.class));

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = getIntent();
                if (logval==1) {
                    firebaseAuth.signOut();
                }
                if (logval==2){
                    mGoogleSignInClient.signOut();
                }
                Toast.makeText(profile.this,"You are Logged Out",Toast.LENGTH_SHORT).show();

                finish();

                startActivity(new Intent(profile.this, MainActivity.class));


            }
        });



    }
}