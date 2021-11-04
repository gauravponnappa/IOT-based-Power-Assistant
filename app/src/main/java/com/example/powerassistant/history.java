package com.example.powerassistant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class history extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        String UID = mAuth.getUid();
        String datapath = "users/" + UID + "/data/logs";

        getSupportActionBar().hide();


        TextView january = findViewById(R.id.january);
        TextView february = findViewById(R.id.february);
        TextView march = findViewById(R.id.march);
        TextView april = findViewById(R.id.april);
        TextView may = findViewById(R.id.may);
        TextView june = findViewById(R.id.june);
        TextView july = findViewById(R.id.july);
        TextView august = findViewById(R.id.august);
        TextView september = findViewById(R.id.september);
        TextView october = findViewById(R.id.october);
        TextView november = findViewById(R.id.november);
        TextView december = findViewById(R.id.december);



        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(datapath);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                if (datasnapshot.exists()) {
                    String January = datasnapshot.child("January").getValue().toString();
                    float jan = Float.parseFloat(January);
                    double Jan=(jan*0.625)/1000;
                    january.setText("January : "+Jan+" Units.");

                    String February = datasnapshot.child("February").getValue().toString();
                    float feb = Float.parseFloat(February);
                    double Feb=(feb*0.625)/1000;
                    february.setText("February : "+Feb+" Units.");

                    String March = datasnapshot.child("March").getValue().toString();
                    float mar = Float.parseFloat(March);
                    double Mar=(mar*0.625)/1000;
                    march.setText("March : "+Mar+" Units.");

                    String April = datasnapshot.child("April").getValue().toString();
                    float apr = Float.parseFloat(April);
                    double Apr=(apr*0.625)/1000;
                    april.setText("April : "+Apr+" Units.");

                    String May = datasnapshot.child("May").getValue().toString();
                    float mayy = Float.parseFloat(May);
                    double Mayy=(mayy*0.625)/1000;
                    may.setText("May : "+Mayy+" Units.");

                    String June = datasnapshot.child("June").getValue().toString();
                    float jun = Float.parseFloat(June);
                    double Jun=(jun*0.625)/1000;
                    june.setText("June : "+Jun+" Units.");

                    String July  = datasnapshot.child("July").getValue().toString();
                    float jul = Float.parseFloat(July);
                    double Jul=(jul*0.625)/1000;
                    july.setText("July : "+Jul+" Units.");

                    String August = datasnapshot.child("August").getValue().toString();
                    float aug = Float.parseFloat(August);
                    double Aug=(aug*0.625)/1000;
                    august.setText("August : "+Aug+" Units.");

                    String September = datasnapshot.child("September").getValue().toString();
                    float sept = Float.parseFloat(September);
                    double Sept=(sept*0.625)/1000;
                    september.setText("September : "+Sept+" Units.");

                    String October = datasnapshot.child("October").getValue().toString();
                    float oct = Float.parseFloat(October);
                    double Oct=(oct*0.625)/1000;
                    october.setText("October : "+Oct+" Units.");

                    String November = datasnapshot.child("November").getValue().toString();
                    float nov = Float.parseFloat(November);
                    double Nov=(nov*0.625)/1000;
                    november.setText("November : "+Nov+" Units.");


                    String December = datasnapshot.child("December").getValue().toString();
                    float dec = Float.parseFloat(December);
                    double Dec=(dec*0.625)/1000;
                    december.setText("December : "+Dec+" Units.");



                }
                else {
                    Toast.makeText(history.this, "User Data Fetch Failed !", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}