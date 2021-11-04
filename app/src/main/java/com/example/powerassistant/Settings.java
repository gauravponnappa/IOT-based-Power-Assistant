package com.example.powerassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        TextView price = findViewById(R.id.textView5);
        TextView meter = findViewById(R.id.textView6);
        TextView feedback = findViewById(R.id.feedback);
        TextView about = findViewById(R.id.textView8);
        TextView back = findViewById(R.id.textView13);

        price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this, tarrif.class));

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Settings.this,"Made in Schneider electric india",Toast.LENGTH_SHORT).show();

            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Settings.this, "Please write to us at %feedback@email.com%", Toast.LENGTH_SHORT).show();
            }
        });
        meter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this, MeterSettings.class));

            }
        });


    }
}