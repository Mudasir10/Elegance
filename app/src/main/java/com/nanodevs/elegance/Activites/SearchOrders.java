package com.nanodevs.elegance.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nanodevs.elegance.Adapters.SearchOrderAdapter;
import com.nanodevs.elegance.R;

import java.util.ArrayList;
import java.util.List;


public class SearchOrders extends AppCompatActivity {

    RecyclerView recyclerView;

    List<String> CustomerId;
    List<Long> CustomerOrdersCount;
    private SearchOrderAdapter searchOrderAdapter;


    @Override
    protected void onStart() {
        super.onStart();

        new Thread(new Runnable() {
            @Override
            public void run() {
                new LoadCustomerORderIds().execute();
                new LoadCustomerORderOrderCounts().execute();
            }
        }).start();




    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_orders);


        CustomerId=new ArrayList<String>();
        CustomerOrdersCount=new ArrayList<Long>();


        Log.d("MyTAG", "onCreate: "+CustomerId);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView = findViewById(R.id.recyclerViewSearchOrders);
                recyclerView.setLayoutManager(new LinearLayoutManager(SearchOrders.this));
                searchOrderAdapter = new SearchOrderAdapter(SearchOrders.this, CustomerId, CustomerOrdersCount);
                recyclerView.setAdapter(searchOrderAdapter);

            }
        },1000);




    }


    private class LoadCustomerORderIds extends AsyncTask<Void,Void,Void> {

        private DatabaseReference ref= FirebaseDatabase.getInstance().getReference("CustomerOrders");

        @Override
        protected Void doInBackground(Void... voids) {
            CustomerId.clear();
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds:dataSnapshot.getChildren()){
                        String id =ds.getKey();
                        CustomerId.add(id);
                    }
                    Log.d("MyTAG", "onDataChange: "+CustomerId);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            return null;
        }
    }

    private class LoadCustomerORderOrderCounts extends AsyncTask<Void,Void,Void> {

        private DatabaseReference ref=FirebaseDatabase.getInstance().getReference("CustomerOrders");
        @Override
        protected Void doInBackground(Void... voids) {


            for (int i = 0; i < CustomerId.size(); i++) {

                ref.child(CustomerId.get(i)).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    /*    for (int j = 0; j < CustomerId.size(); j++) {*/
                            CustomerOrdersCount.add(dataSnapshot.getChildrenCount());
                        /*}*/


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }




            return null;
        }
    }



}
