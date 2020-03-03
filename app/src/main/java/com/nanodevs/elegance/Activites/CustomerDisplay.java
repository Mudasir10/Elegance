package com.nanodevs.elegance.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.nanodevs.elegance.R;

public class CustomerDisplay extends AppCompatActivity {

   private TextView tvid,tvname,tvphone,tvmeasurements,tvdes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_display);

        getSupportActionBar().setTitle("Customer Measurements");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();

    }

    private void init() {

        tvid=findViewById(R.id.customerDisplaySerialNo);
        tvname=findViewById(R.id.customerDisplayName);
        tvphone=findViewById(R.id.customerDisplayContactNo);
        tvmeasurements=findViewById(R.id.customerDisplayMeasurement);
        tvdes=findViewById(R.id.customerDisplayDescription);

        if (getIntent()!=null){

            tvid.setText(String.valueOf(getIntent().getLongExtra("customerId", 0)) );
            tvname.setText(String.valueOf(getIntent().getStringExtra("cus_name")));
            tvphone.setText(String.valueOf(getIntent().getStringExtra("cus_phone")));
            tvmeasurements.setText(String.valueOf(getIntent().getStringExtra("cus_measurements")));
            tvdes.setText(String.valueOf(getIntent().getStringExtra("suit_des")));

        }




    }


}
