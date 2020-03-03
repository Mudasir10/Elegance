package com.nanodevs.elegance.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nanodevs.elegance.Pojo.Customer;
import com.nanodevs.elegance.R;

import java.util.HashMap;
import java.util.Map;

public class RegisterCustomerSheet extends BottomSheetDialogFragment {

    private DatabaseReference cusRef = FirebaseDatabase.getInstance().getReference("Customer");
    private TextView customerName,customerContact,customerSerialNo;
    private TextInputLayout customerDescription,measurementDescription;
    private Button btnSaveCustomer;

    private long count;

    @Override
    public void onStart() {
        super.onStart();

        cusRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                count=dataSnapshot.getChildrenCount();

                if (count<1){
                    count=1;
                    customerSerialNo.setText(String.valueOf(count));
                }
                else{
                    count=count+1;
                    customerSerialNo.setText(String.valueOf(count));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.register_customersheet, container, false);


        initComponents(view);

        return view;

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);


    }


    private void initComponents(View view) {

        customerName=view.findViewById(R.id.customerName);
        customerContact=view.findViewById(R.id.customerNumber);
        customerSerialNo=view.findViewById(R.id.customerSerialNo);
        customerDescription=view.findViewById(R.id.customerDescription);
        measurementDescription=view.findViewById(R.id.customerMeasurement);
        btnSaveCustomer=view.findViewById(R.id.btnSaveCustomer);

        btnSaveCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(customerName.getText().toString().isEmpty()||customerContact.getText().toString().isEmpty()||
                        customerDescription.getEditText().toString().isEmpty()||measurementDescription.getEditText().toString().isEmpty()||
                        customerSerialNo.getText().toString().isEmpty()){

                    if(customerName.getText().toString().isEmpty())
                        customerName.setError("field is empty");
                    else if (customerContact.getText().toString().isEmpty())
                        customerContact.setError("field is empty");
                    else if (customerDescription.getEditText().toString().isEmpty())
                        customerDescription.setError("field is empty");
                    else if (measurementDescription.getEditText().toString().isEmpty())
                        measurementDescription.setError("field is empty");
                    else if (customerSerialNo.getText().toString().isEmpty())
                        customerSerialNo.setError("field is empty");
                    else
                        Toast.makeText(getContext(), "All fields are empty !", Toast.LENGTH_SHORT).show();
                }
                else{
                    saveCustomerData(customerSerialNo.getText().toString(),customerName.getText().toString(),customerContact.getText().toString(),
                            customerDescription.getEditText().toString(),
                            measurementDescription.getEditText().toString());
                }

            }
        });

    }

    private void saveCustomerData(String customerSerialNo,String customerName, String customerContact,
                                  String customerDescription, String customerMeasurement) {



        Customer customer = new Customer(Long.parseLong(customerSerialNo), customerName, customerContact,
                customerMeasurement, customerDescription);

        Map<String,Object> postValues=customer.toCustomerMap();
        Map<String,Object> childUpdates=new HashMap<>();
        childUpdates.put(cusRef.push().getKey(),postValues);

        cusRef.updateChildren(childUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    clearFields();
                    Toast.makeText(getContext(), "Customer Added SuccessFully ", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getContext(), "Task Failed", Toast.LENGTH_SHORT).show(); }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Task Failed : "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void clearFields() {
        customerName.setText("");
         customerContact.setText("");
         customerDescription.getEditText().setText("");
        measurementDescription.getEditText().setText("");
    }


}