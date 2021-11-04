package com.example.powerassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class reset extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        Button resetall = findViewById(R.id.deleteuser);

        resetall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String UID= mAuth.getUid();
                String datapath = "users/"+UID+"/data";


                DatabaseReference reference = FirebaseDatabase.getInstance().getReference(datapath);
                reference.child("hours").setValue(0);
                reference.child("prevday").setValue(0);
                reference.child("pulse").setValue(0);
                Toast.makeText(reset.this, "data reset successful", Toast.LENGTH_LONG).show();


            }
        });


    }
}