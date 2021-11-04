package com.example.powerassistant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class register extends AppCompatActivity {

    private EditText userName, userPassword,userPassword2, userEmail;
    private Button regButton;
    private TextView userLogin;
    private FirebaseAuth firebaseAuth;
    private TextView error2;
    private ConstraintLayout main2;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();


        userEmail = (EditText) findViewById(R.id.UserEmail);
        userPassword = (EditText) findViewById(R.id.UserPassword);
        userPassword2 = (EditText) findViewById(R.id.UserPassword2);

        regButton = (Button) findViewById(R.id.Register);
        userLogin = (TextView) findViewById(R.id.logintext);
        firebaseAuth = FirebaseAuth.getInstance();





        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    //upload to firebase part...
                    String user_email = userEmail.getText().toString().trim();
                    String user_password = userPassword.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(register.this, "REGISTRATION SUCCESSFUL", Toast.LENGTH_SHORT).show();
                                firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            new CountDownTimer(2000, 1000) {
                                                public void onFinish() {
                                                    Toast.makeText(register.this, "Please Verify your E-mail", Toast.LENGTH_LONG).show();
                                                }

                                                public void onTick(long millisUntilFinished) {
                                                    // millisUntilFinished    The amount of time until finished.
                                                }
                                            }.start();                                        }
                                    }
                                });

                                onBackPressed();

                            }else {
                                Toast.makeText(register.this, "REGISTRATION failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }else{
                    Toast.makeText(register.this, "Fill all fields", Toast.LENGTH_LONG).show();


                }
            }
        });


        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

    }

    private Boolean validate() {

        String password = userPassword.getText().toString();
        String password2 = userPassword2.getText().toString();

        String email = userEmail.getText().toString();
        if(password.isEmpty()&&email.isEmpty()&&(!password.equals(password2))) {
            return false;
        }
        else {
            return true;
        }
    }

}