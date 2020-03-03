package com.nanodevs.elegance.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.nanodevs.elegance.R;

public class CustomerDisplay extends AppCompatActivity {

    TextView tvid,tvname,tvphone,tvmeasurements,tvdes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_display);

        init();




    }

    private void init() {

        tvid=findViewById(R.id.customerDisplaySerialNo);
        tvname=findViewById(R.id.customerDisplayName);
        tvphone=findViewById(R.id.customerDisplayContactNo);
        tvmeasurements=findViewById(R.id.customerDisplayMeasurement);
        tvdes=findViewById(R.id.customerDisplayDescription);

        if (getIntent()!=null){

            String id = String.valueOf(getIntent().getStringExtra("cus_id"));
            String name = String.valueOf(getIntent().getStringExtra("cus_name"));
            String measurement = String.valueOf(getIntent().getStringExtra("cus_measurements"));
            String contact = String.valueOf(getIntent().getStringExtra("cus_phone"));
            String des = String.valueOf(getIntent().getStringExtra("suit_des"));

            tvid.setText(id);
            tvname.setText(name);
            tvphone.setText(contact);
            tvmeasurements.setText(measurement);
            tvdes.setText(des);

        }




    }


}
