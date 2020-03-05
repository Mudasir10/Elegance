package com.nanodevs.elegance.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.nanodevs.elegance.R;

public class StitchCloth extends AppCompatActivity {


    private Spinner suitSpinner;

    private TextView clothOrderCustomerSerialNo, clothOrderCustomerName, clothOrderCustomerContact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stitch_cloth);

        initComponents();

        ArrayAdapter<CharSequence> suitAdapter = ArrayAdapter.createFromResource(this,
                R.array.suitCategories, android.R.layout.simple_spinner_item);
        suitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        suitSpinner.setAdapter(suitAdapter);
        suitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        if (getIntent() != null) {

            clothOrderCustomerSerialNo.setText(String.valueOf(getIntent().getStringExtra("customerUid")));
            clothOrderCustomerName.setText(String.valueOf(getIntent().getStringExtra("customerName")));
            clothOrderCustomerContact.setText(String.valueOf(getIntent().getStringExtra("customerContactNumber")));

        }


    }

    private void initComponents() {

        suitSpinner = findViewById(R.id.suitSpinner);
        clothOrderCustomerSerialNo = findViewById(R.id.customerClothSerialNo);
        clothOrderCustomerName = findViewById(R.id.customerClothDisplayName);
        clothOrderCustomerContact = findViewById(R.id.customerClothDisplayContactNo);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_menu, menu);
        RelativeLayout badgeLayout =(RelativeLayout)menu.findItem(R.id.cartMenuActionBar).getActionView();
      /*  TextView badgeTextViewCounter = badgeLayout.findViewById(R.id.notificationTextView);
        badgeTextViewCounter.setText("12");
*/

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.cartMenuActionBar) {
        }
        return true;
    }









}
