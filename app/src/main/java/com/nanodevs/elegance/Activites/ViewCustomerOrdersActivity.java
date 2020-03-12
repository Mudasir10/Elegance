package com.nanodevs.elegance.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nanodevs.elegance.Adapters.ViewOrdersListAdapter;
import com.nanodevs.elegance.Adapters.ViewOrdersListBillAdapter;
import com.nanodevs.elegance.Pojo.Cart;
import com.nanodevs.elegance.Pojo.CustomerBill;
import com.nanodevs.elegance.R;

import java.util.ArrayList;
import java.util.List;


public class ViewCustomerOrdersActivity extends AppCompatActivity {


    private final DatabaseReference customerDeleteRef = FirebaseDatabase.getInstance().getReference();
    private List<String> CustomerKeys;
    private List<Cart> CustomerOrdersList;
    private List<CustomerBill> CustomerOrdersBillList;
    private String cusID,cusName,cusContact;
    private TextView tvCusId,tvCusName,tvCusContact, orderKey, orderBillKey;
    private Spinner tv_CustomerOrdersSpinner;
    private RecyclerView orderListRecyclerView, orderBillListRecyclerView;
    private Switch deliverSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcustomer_orders);
        deliverSwitch = findViewById(R.id.deliverOrdersSwitchBtn);
        CustomerOrdersList = new ArrayList<>();
        CustomerOrdersBillList = new ArrayList<>();
        tvCusId = findViewById(R.id.customer_id);
        tvCusName = findViewById(R.id.customer_name_viewOrder);
        tvCusContact = findViewById(R.id.customer_contact_viewOrder);
        orderKey = findViewById(R.id.orderSerialKeyTextView);
        orderBillKey = findViewById(R.id.orderBillSerialKeyTextView);
        tv_CustomerOrdersSpinner = findViewById(R.id.orderKeysSpinner);
        CustomerKeys = new ArrayList<String>();
        orderListRecyclerView = findViewById(R.id.recycler_ViewOrders);
        orderBillListRecyclerView = findViewById(R.id.recyclerView_OrdersBill);

        deliverSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(ViewCustomerOrdersActivity.this, ""+tvCusId.getText().toString(), Toast.LENGTH_SHORT).show();
       customerDeleteRef.child("CustomerBills").child(tvCusId.getText().toString()).child(orderKey.getText().toString()).removeValue();
      customerDeleteRef.child("CustomerOrders").child(tvCusId.getText().toString()).child(orderKey.getText().toString()).removeValue();
                }else{
                    Toast.makeText(ViewCustomerOrdersActivity.this, "not  checked", Toast.LENGTH_SHORT).show();
                }
            }
        });


        orderListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderBillListRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        if (getIntent() != null) {
            cusID = getIntent().getStringExtra("order_cusId");
            cusName = getIntent().getStringExtra("order_cusName");
            cusContact = getIntent().getStringExtra("order_cusContact");
            tvCusId.setText("Customer ID : " + cusID);
            tvCusName.setText("Customer Name : " + cusName);
            tvCusContact.setText("Customer Contact : " + cusContact);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    getKeysOfOrderAndBills(cusID);
                }
            }).start();

        }


    }

    private void getKeysOfOrderAndBills(final String cusID) {

        final DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference("CustomerOrders");
        if (cusID != null) {

            orderRef.child(cusID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    CustomerKeys.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String id = ds.getKey();
                        CustomerKeys.add(id);
                    }
                    ArrayAdapter<String> addressAdapter = new ArrayAdapter<String>(ViewCustomerOrdersActivity.this, android.R.layout.simple_spinner_item, CustomerKeys);
                    addressAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    tv_CustomerOrdersSpinner.setAdapter(addressAdapter);
                    tv_CustomerOrdersSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            final String itemName = parent.getItemAtPosition(position).toString();
                            orderKey.setText("Order Key :\t\t"+itemName);
                            orderBillKey.setText("Order Bill key :\t\t"+itemName);
                            getCustomerOrders(itemName);
                            getCustomerOrdersBill(itemName);


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

    private void getCustomerOrders(String itemName) {

        DatabaseReference mOrderRef = FirebaseDatabase.getInstance().getReference("CustomerOrders");
        mOrderRef.child(cusID).child(itemName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                CustomerOrdersList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Cart cart = ds.getValue(Cart.class);
                    CustomerOrdersList.add(cart);
                }

                ViewOrdersListAdapter viewOrdersListAdapter = new ViewOrdersListAdapter(ViewCustomerOrdersActivity.this, CustomerOrdersList);
                orderListRecyclerView.setAdapter(viewOrdersListAdapter);
                viewOrdersListAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void getCustomerOrdersBill(final String itemName) {
        CustomerOrdersBillList.clear();
        DatabaseReference mOrderBillRef = FirebaseDatabase.getInstance().getReference("CustomerBills");
        mOrderBillRef.child(cusID).child(itemName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                CustomerBill customerBill = dataSnapshot.getValue(CustomerBill.class);
                CustomerOrdersBillList.add(customerBill);
                ViewOrdersListBillAdapter viewOrdersListAdapter = new ViewOrdersListBillAdapter(CustomerOrdersBillList,ViewCustomerOrdersActivity.this,itemName);
                orderBillListRecyclerView.setAdapter(viewOrdersListAdapter);
                viewOrdersListAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}
