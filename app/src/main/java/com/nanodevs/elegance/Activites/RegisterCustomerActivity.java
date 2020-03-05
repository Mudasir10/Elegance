package com.nanodevs.elegance.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

import static android.view.View.GONE;

public class RegisterCustomerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private DatabaseReference cusRef = FirebaseDatabase.getInstance().getReference("Customer");
    private TextView customerName, customerContact, customerSerialNo;

    private Spinner spinner;

    private Button btnSaveCustomer;
    private long count;
    String SelectedCategory;

    EditText etLenght, etShoulder, etSleeves, etcolr, etchest, etstomachSize, ethipSize, etarms, etwrist, etloosingchest, etloosingstomach,
            etloosinghip, etpentlength, etpentbottom, etwaist, etthigh, etDescription;


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

        // edit Text
        etLenght = findViewById(R.id.act_reg_etlength);
        etShoulder = findViewById(R.id.act_reg_etshoulder);
        etSleeves = findViewById(R.id.act_reg_etsleeves);
        etcolr = findViewById(R.id.act_reg_etcoler);
        etchest = findViewById(R.id.act_reg_etchest);
        etstomachSize = findViewById(R.id.act_reg_etstomach);
        ethipSize = findViewById(R.id.act_reg_ethipsize);
        etarms = findViewById(R.id.act_reg_etarms);
        etwrist = findViewById(R.id.act_reg_etwrist);
        etloosingchest = findViewById(R.id.act_reg_etloosingchest);
        etloosingstomach = findViewById(R.id.act_reg_etloosingstomach);
        etloosinghip = findViewById(R.id.act_reg_etloosinghip);
        etpentlength = findViewById(R.id.act_reg_etpentlength);
        etpentbottom = findViewById(R.id.act_reg_etpentbottom);
        etDescription = findViewById(R.id.act_reg_etdescription);

        etwaist = findViewById(R.id.act_reg_etwaist);
        etthigh = findViewById(R.id.act_reg_etthigh);

        customerName = findViewById(R.id.customerName);
        customerContact = findViewById(R.id.customerNumber);
        customerSerialNo = findViewById(R.id.customerSerialNo);

        btnSaveCustomer = findViewById(R.id.btnSaveCustomer);

        spinner = findViewById(R.id.spinnerCategory);

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

        SelectedCategory = parent.getItemAtPosition(position).toString();

        if (SelectedCategory.equals("Kurta")){

            // Show these Text View
            etLenght.setVisibility(View.VISIBLE);
            etSleeves.setVisibility(View.VISIBLE);
            etShoulder.setVisibility(View.VISIBLE);
            etcolr.setVisibility(View.VISIBLE);
            etchest.setVisibility(View.VISIBLE);
            etstomachSize.setVisibility(View.VISIBLE);
            ethipSize.setVisibility(View.VISIBLE);
            etarms.setVisibility(View.VISIBLE);
            etwrist.setVisibility(View.VISIBLE);
            etloosingchest.setVisibility(View.VISIBLE);
            etloosingstomach.setVisibility(View.VISIBLE);
            etloosinghip.setVisibility(View.VISIBLE);
            etpentlength.setVisibility(View.VISIBLE);
            etpentbottom.setVisibility(View.VISIBLE);

            // hide these TextBoxes
            etthigh.setVisibility(GONE);
            etwaist.setVisibility(GONE);
        }
        else if(SelectedCategory.equals(("Shirt"))){

            // showText Views
            etLenght.setVisibility(View.VISIBLE);
            etSleeves.setVisibility(View.VISIBLE);
            etShoulder.setVisibility(View.VISIBLE);
            etcolr.setVisibility(View.VISIBLE);
            etchest.setVisibility(View.VISIBLE);
            etstomachSize.setVisibility(View.VISIBLE);
            etarms.setVisibility(View.VISIBLE);
            etwrist.setVisibility(View.VISIBLE);
            etloosingchest.setVisibility(View.VISIBLE);
            etloosingstomach.setVisibility(View.VISIBLE);
            etDescription.setVisibility(View.VISIBLE);

            //hide EditText
            ethipSize.setVisibility(GONE);
            etloosinghip.setVisibility(GONE);
            etpentbottom.setVisibility(GONE);
            etpentlength.setVisibility(GONE);



        }
        else if(SelectedCategory.equals(("3_piece"))){



        }
        else if(SelectedCategory.equals(("West_Coat"))){

            //showing editText
            etLenght.setVisibility(View.VISIBLE);
            etShoulder.setVisibility(View.VISIBLE);
            etcolr.setVisibility(View.VISIBLE);
            etchest.setVisibility(View.VISIBLE);
            etstomachSize.setVisibility(View.VISIBLE);
            ethipSize.setVisibility(View.VISIBLE);
            etloosingchest.setVisibility(View.VISIBLE);
            etloosingstomach.setVisibility(View.VISIBLE);


            //hiding EditText
            etSleeves.setVisibility(GONE);
            ethipSize.setVisibility(GONE);
            etarms.setVisibility(GONE);
            etwrist.setVisibility(GONE);
            etloosinghip.setVisibility(GONE);
            etpentlength.setVisibility(GONE);
            etpentbottom.setVisibility(GONE);
            etwaist.setVisibility(GONE);
            etthigh.setVisibility(GONE);

        }
        else if(SelectedCategory.equals(("Pent"))){

            // showing textViews
            etwaist.setVisibility(View.VISIBLE);
            etthigh.setVisibility(View.VISIBLE);
            ethipSize.setVisibility(View.VISIBLE);
            etDescription.setVisibility(View.VISIBLE);


            // hiding the Text Views
            etLenght.setVisibility(GONE);
            etSleeves.setVisibility(GONE);
            etShoulder.setVisibility(GONE);
            etcolr.setVisibility(GONE);
            etchest.setVisibility(GONE);
            etstomachSize.setVisibility(GONE);
            etarms.setVisibility(GONE);
            etwrist.setVisibility(GONE);
            etloosingchest.setVisibility(GONE);
            etloosingstomach.setVisibility(GONE);
            etloosinghip.setVisibility(GONE);

        }
        else if(SelectedCategory.equals(("Saffari"))){

            etloosingchest.setVisibility(GONE);
            etloosingstomach.setVisibility(GONE);
            etloosinghip.setVisibility(GONE);
            etpentlength.setVisibility(GONE);
            etpentbottom.setVisibility(GONE);
            etwaist.setVisibility(GONE);
            etthigh.setVisibility(GONE);
        }
        else if (SelectedCategory.equals("suit")){

            //showing EditText
            etLenght.setVisibility(View.VISIBLE);
            etSleeves.setVisibility(View.VISIBLE);
            etShoulder.setVisibility(View.VISIBLE);
            etcolr.setVisibility(View.VISIBLE);
            etchest.setVisibility(View.VISIBLE);
            etstomachSize.setVisibility(View.VISIBLE);
            ethipSize.setVisibility(View.VISIBLE);
            etarms.setVisibility(View.VISIBLE);
            etwrist.setVisibility(View.VISIBLE);

            etloosinghip.setVisibility(View.VISIBLE);
            etloosingchest.setVisibility(View.VISIBLE);
            etloosingstomach.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
