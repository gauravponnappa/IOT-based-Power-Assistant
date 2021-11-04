package com.example.powerassistant;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Dashboard extends AppCompatActivity {

    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth firebaseAuth;
    private ImageView meter;
    private TextView pulse;
    private TextView hour;
    private TextView killowatts;
    private TextView watts;
    private TextView amps;
    private TextView volts;
    private TextView bill2;
    private TextView bill1;
    private TextView prevv;
    private TextView estm;

    double usage_charge;
    double usage_chargeday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        firebaseAuth = FirebaseAuth.getInstance();
        getSupportActionBar().hide();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        int logvalue = extras.getInt("logval");
        ImageView logout = findViewById(R.id.sign_out_button);
        ImageView settings = findViewById(R.id.settings);

        meter = findViewById(R.id.imageView9);
        pulse = findViewById(R.id.Pulse);
        hour = findViewById(R.id.hours);
        killowatts = findViewById(R.id.KilloWatts);
        watts = findViewById(R.id.Watts);
        amps = findViewById(R.id.Amps);
        volts = findViewById(R.id.Volts);
        bill2 = findViewById(R.id.bill2);
        bill1 = findViewById(R.id.bill1);
        prevv=findViewById(R.id.prevv);
        estm = findViewById(R.id.estmbill);



        meter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this, MeterSettings.class));
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this, Settings.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(Dashboard.this, profile.class);
                intent.putExtra("key", logvalue);
                finish();
                startActivity(intent);



               /* Intent intent = getIntent();
                if (logval==1) {
                    firebaseAuth.signOut();
                }
                if (logval==2){
                    mGoogleSignInClient.signOut();
                }
                Toast.makeText(Dashboard.this,"You are Logged Out",Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(Dashboard.this, MainActivity.class));*/

            }
        });



        String UID= mAuth.getUid();
        String datapath = "users/"+UID+"/data";


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(datapath);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                if (datasnapshot.exists()) {

                    String hours = datasnapshot.child("hours").getValue().toString();
                    hour.setText(hours);
                    float hrs = Float.parseFloat(hour.getText().toString());

                    String pulsee = datasnapshot.child("pulse").getValue().toString();
                    pulse.setText(pulsee);
                    float pulses = Float.parseFloat(pulse.getText().toString());


                    double kwatts = (pulses * 0.625) / 1000;      //calculates units
                    String kwattss = String.valueOf(kwatts).format("%.02f", kwatts);  //convert double to string
                    killowatts.setText(kwattss + "Kwh");      //display string value obtained

                    double wattage = (kwatts / hrs) * 1000;//calculates live consumption watts
                    String wattages = String.valueOf(wattage).format("%.02f", wattage);
                    watts.setText(wattages + "w");

                    double current = (wattage / 240);//calculates current consumption
                    String currents = String.valueOf(current).format("%.02f", current);
                    amps.setText(currents + " Amps");

                    // bill estimation with reference to current pricing in mysore
                    if (kwatts <= 30) {
                        usage_charge = kwatts * 3.25;
                    }
                    if (kwatts > 30 && kwatts <= 100) {
                        usage_charge = 97.5 + ((kwatts - 50) * 4.70);
                    }
                    if (kwatts > 100 && kwatts <= 200) {
                        usage_charge = 97.5 + 235 + ((kwatts - 100) * 6.25);
                    }
                    if (kwatts > 200) {
                        usage_charge = 97.5 + 235 + 625 + ((kwatts - 200) * 7.30);
                    }

                    String usagecharge = String.valueOf(usage_charge).format("%.02f", usage_charge);
                    bill2.setText(usagecharge + "Rs.");


                    String prevpulse = datasnapshot.child("prevday").getValue().toString();
                    prevv.setText(prevpulse);
                    float prevpulsee = Float.parseFloat(prevv.getText().toString());

                    double kwattsprev = (((prevpulsee * 30) * 0.625) / 1000);


                    if (kwattsprev <= 30) {
                        usage_chargeday = kwattsprev * 3.25;
                    }
                    if (kwattsprev > 30 && kwattsprev <= 100) {
                        usage_chargeday = 97.5 + ((kwattsprev - 50) * 4.70);
                    }
                    if (kwattsprev > 100 && kwattsprev <= 200) {
                        usage_chargeday = 97.5 + 235 + ((kwattsprev - 100) * 6.25);
                    }
                    if (kwattsprev > 200) {
                        usage_chargeday = 97.5 + 235 + 625 + ((kwattsprev - 200) * 7.30);
                    }

                    String usagechargeday = String.valueOf(usage_chargeday).format("%.02f", usage_chargeday);
                    estm.setText(usagechargeday + "Rs.");

                }
                else
                {
                    Toast.makeText(Dashboard.this, "User Device not registered", Toast.LENGTH_SHORT).show();
                    Toast.makeText(Dashboard.this, "Please Register your device in meter settings", Toast.LENGTH_LONG).show();


                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(Dashboard.this, "data fetch unsuccessful", Toast.LENGTH_LONG).show();


            }
        });



    }
}
