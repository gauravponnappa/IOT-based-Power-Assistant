package com.example.powerassistant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.ContentValues.TAG;

public class deleteuser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deleteuser);

        Button deleteuser = findViewById(R.id.deleteuser);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        deleteuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user.delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    finish();
                                    Toast.makeText(deleteuser.this, "User Account Deleted successfully", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }
        });

    }
}