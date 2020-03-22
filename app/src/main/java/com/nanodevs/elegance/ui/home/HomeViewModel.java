package com.nanodevs.elegance.ui.home;

import android.os.AsyncTask;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nanodevs.elegance.Pojo.Customer;


import java.util.ArrayList;
import java.util.List;
public class HomeViewModel extends ViewModel {

    private DatabaseReference databaseReference;
    private MutableLiveData<List<Customer>> customersList;

    public LiveData<List<Customer>> getAllCustomers() {
        if (customersList == null ) {
            customersList = new MutableLiveData<List<Customer>>();
            loadAllCustomers();
        }
        return customersList;
    }



    private void loadAllCustomers() {

        new FetchAllCustomerAsyncTask().execute();
        // do async operation to fetch articles
    }

    // Fetch Few Customers

    class FetchAllCustomerAsyncTask extends AsyncTask<Void, Void, Void> {

        List<Customer> list;


        @Override
        protected Void doInBackground(Void... voids) {

            list = new ArrayList<>();


            databaseReference = FirebaseDatabase.getInstance().getReference("Customer");

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    list.clear();


                    if(dataSnapshot.exists()){
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            Customer customer = snapshot.getValue(Customer.class);
                            list.add(customer);
                        }

                        customersList.setValue(list);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            return null;
        }
    }


}