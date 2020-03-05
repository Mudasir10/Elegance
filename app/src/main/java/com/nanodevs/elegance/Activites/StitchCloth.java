package com.nanodevs.elegance.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.nanodevs.elegance.R;

public class StitchCloth extends AppCompatActivity {


    private TextView clothOrderCustomerSerialNo, clothOrderCustomerName, clothOrderCustomerContact;
    private TextInputLayout clothOrderCustomerMeasurements, clothOrderCustomerSuitDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stitch_cloth);

        initComponents();

        if (getIntent() != null) {

            clothOrderCustomerSerialNo.setText(String.valueOf(getIntent().getStringExtra("customerUid")));
            clothOrderCustomerName.setText(String.valueOf(getIntent().getStringExtra("customerName")));
            clothOrderCustomerContact.setText(String.valueOf(getIntent().getStringExtra("customerContactNumber")));
            clothOrderCustomerMeasurements.getEditText().setText(String.valueOf(getIntent().getStringExtra("customerMeasurementsDetails")));
            clothOrderCustomerSuitDescription.getEditText().setText(String.valueOf(getIntent().getStringExtra("customerSuitDetails")));

        }


    }

    private void initComponents() {

        clothOrderCustomerSerialNo = findViewById(R.id.customerClothSerialNo);
        clothOrderCustomerName = findViewById(R.id.customerClothDisplayName);
        clothOrderCustomerContact = findViewById(R.id.customerClothDisplayContactNo);
        clothOrderCustomerMeasurements = findViewById(R.id.customerMeasurementClothDisplay);
        clothOrderCustomerSuitDescription = findViewById(R.id.customerDescriptionClothOrder);


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
