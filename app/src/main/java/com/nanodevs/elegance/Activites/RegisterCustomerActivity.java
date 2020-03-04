package com.nanodevs.elegance.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nanodevs.elegance.Pojo.Customer;
import com.nanodevs.elegance.R;

import java.util.HashMap;
import java.util.Map;

public class RegisterCustomerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private DatabaseReference cusRef = FirebaseDatabase.getInstance().getReference("Customer");
    private TextView customerName, customerContact, customerSerialNo;

    private Spinner spinner;

    private Button btnSaveCustomer;
    private long count;
    String SelectedCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_customer);

        initComponents();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }

    @Override
    protected void onStart() {
        super.onStart();

        cusRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                count = dataSnapshot.getChildrenCount();

                if (count < 1) {
                    count = 1;
                    customerSerialNo.setText(String.valueOf(count));
                } else {
                    count = count + 1;
                    customerSerialNo.setText(String.valueOf(count));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    private void initComponents() {

        customerName = findViewById(R.id.customerName);
        customerContact =findViewById(R.id.customerNumber);
        customerSerialNo =findViewById(R.id.customerSerialNo);

        btnSaveCustomer =findViewById(R.id.btnSaveCustomer);

        spinner= findViewById(R.id.spinnerCategory);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(RegisterCustomerActivity.this,
                R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        btnSaveCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




            }
        });

    }


  /*  private void saveCustomerData(String customerSerialNo, String customerName, String customerContact,
                                  String customerDescription, String customerMeasurement) {


        Customer customer = new Customer(Long.parseLong(customerSerialNo), customerName, customerContact,
                customerMeasurement, customerDescription);

        Map<String, Object> postValues = customer.toCustomerMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(cusRef.push().getKey(), postValues);

        cusRef.updateChildren(childUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    clearFields();
                    Toast.makeText(RegisterCustomerActivity.this, "Customer Added SuccessFully ", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(RegisterCustomerActivity.this, "Task Failed", Toast.LENGTH_SHORT).show();
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterCustomerActivity.this, "Task Failed : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

*/

   /* private void clearFields() {
        customerName.setText("");
        customerContact.setText("");

    }*/


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        SelectedCategory=parent.getItemAtPosition(position).toString();
        Toast.makeText(this, ""+SelectedCategory, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
