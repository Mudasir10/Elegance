package com.nanodevs.elegance.Fragments;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class AddCustomerFragment extends Fragment {
    DatabaseReference cusRef = FirebaseDatabase.getInstance().getReference();
    private AddCustomerViewModel mViewModel;
    private TextView customerName,customerContact,customerSerialNo,customerDescription,measurementDescription;

    public static AddCustomerFragment newInstance() {
        return new AddCustomerFragment();
    }


    @Override
    public void onStart() {
        super.onStart();

        cusRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }





    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.add_customer_fragment, container, false);
        initComponents(view);
        
        view.findViewById(R.id.btnSaveCustomer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(customerName.getText().toString().isEmpty()||customerContact.getText().toString().isEmpty()||
                customerDescription.getText().toString().isEmpty()||measurementDescription.getText().toString().isEmpty()||
                customerSerialNo.getText().toString().isEmpty()){

                    if(customerName.getText().toString().isEmpty())
                      customerName.setError("field is empty");
                    else if (customerContact.getText().toString().isEmpty())
                        customerContact.setError("field is empty");
                    else if (customerDescription.getText().toString().isEmpty())
                        customerDescription.setError("field is empty");
                    else if (measurementDescription.getText().toString().isEmpty())
                        measurementDescription.setError("field is empty");
                    else if (customerSerialNo.getText().toString().isEmpty())
                        customerSerialNo.setError("field is empty");
                    else
                        Toast.makeText(getContext(), "All fields are empty !", Toast.LENGTH_SHORT).show();
                }
                else{
                    saveCustomerData(customerSerialNo.getText().toString(),customerName.getText().toString(),customerContact.getText().toString(),
                            customerDescription.getText().toString(),
                            measurementDescription.getText().toString());
                }


                
            }
        });
        

        return view;

    }


    private void initComponents(View view) {

        customerName=view.findViewById(R.id.customerName);
        customerContact=view.findViewById(R.id.customerNumber);
        customerSerialNo=view.findViewById(R.id.customerSerialNo);
        customerDescription=view.findViewById(R.id.customerDescription);
        measurementDescription=view.findViewById(R.id.customerMeasurement);

    }

    private void saveCustomerData(String customerSerialNo,String customerName, String customerContact,
                                  String customerDescription, String customerMeasurement) {



        Customer customer = new Customer(Long.parseLong(customerSerialNo), customerName, customerContact,
                customerMeasurement, customerDescription);

        Map<String,Object> postValues=customer.toCustomerMap();
        Map<String,Object> childUpdates=new HashMap<>();
        childUpdates.put("Customer/"+cusRef.push().getKey(),postValues);

        cusRef.updateChildren(childUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful())
                    Toast.makeText(getContext(), "Customer Registered ", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getContext(), "Task Failed", Toast.LENGTH_SHORT).show(); }

            }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Task Failed : "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });








    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AddCustomerViewModel.class);
        // TODO: Use the ViewModel
    }



}
