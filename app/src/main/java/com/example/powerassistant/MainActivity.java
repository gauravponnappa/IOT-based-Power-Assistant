package com.example.powerassistant;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.URI;

public class MainActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private Button signInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private  String TAG = "MainActivity";
    private Button Signinemail;
    private TextView signup;
    private int RC_SIGN_IN = 1;
    private FirebaseAuth firebaseAuth;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    String UID= mAuth.getUid();
    FirebaseDatabase database = FirebaseDatabase.getInstance();






    int logval=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        email = (EditText) findViewById(R.id.mEmail);
        password = (EditText) findViewById(R.id.mPassword);
        Signinemail = findViewById(R.id.SigninEmail);
        signInButton = findViewById(R.id.sign_in_button);
        signup = findViewById(R.id.signup);






        getSupportActionBar().hide();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("327064572947-bo5j7jntmn4aftofb12j1an357ldg327.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, register.class);
                startActivity(myIntent);

            }
        });

        //email auth starts here:

        Signinemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    String usermail = email.getText().toString().trim();
                    String userpassword = password.getText().toString().trim();
                    mAuth.signInWithEmailAndPassword(usermail, userpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            int logval = 1;
                            if (task.isSuccessful()) {
                                if (mAuth.getCurrentUser().isEmailVerified()) {


                                    Toast.makeText(MainActivity.this, "login SUCCESSFUL", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(MainActivity.this, Dashboard.class);
                                    intent.putExtra("key", logval);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(MainActivity.this, "please check your email & Verify your account", Toast.LENGTH_LONG).show();
                                    mAuth.signOut();

                                }

                            } else {
                                Toast.makeText(MainActivity.this, "Please enter valid details", Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                }
                else{
                    Toast.makeText(MainActivity.this, "Please enter all details", Toast.LENGTH_LONG).show();

                }



            }



        });


    }

    private void signIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        try{

            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
            Toast.makeText(MainActivity.this,"Signed In Successfully",Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(acc);
        }
        catch (ApiException e){
            Toast.makeText(MainActivity.this,"Sign In Failed",Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(null);
        }
    }

    private void FirebaseGoogleAuth(GoogleSignInAccount acct) {
        if (acct != null) {
            AuthCredential authCredential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
            mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        int logval=2;



                        Intent myIntent = new Intent(MainActivity.this, Dashboard.class);
                        myIntent.putExtra("key", logval);
                        startActivity(myIntent);
                        updateUI(user);
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                }
            });
        }
        else{
            Toast.makeText(MainActivity.this, "acc failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUI(FirebaseUser fUser){
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if(account !=  null){
            String personName = account.getDisplayName();

            Toast.makeText(MainActivity.this,"welcome "+personName  ,Toast.LENGTH_SHORT).show();
        }

    }

    private Boolean validate() {

        String username = email.getText().toString();
        String userpassword = password.getText().toString();

        if (username.isEmpty() || userpassword.isEmpty())
            return false;

        return true;
    }


}













