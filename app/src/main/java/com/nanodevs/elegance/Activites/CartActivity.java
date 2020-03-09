package com.nanodevs.elegance.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nanodevs.elegance.Adapters.CartAdapter;
import com.nanodevs.elegance.Pojo.Cart;
import com.nanodevs.elegance.R;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

   private DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("Cart");
    private String serialNo,customerName,contactNo;
    private List<Cart> cCartList;
    private RecyclerView cartItemRecyclerView;
    private TextView nameTextView,serialTextView,contactTextView;


    @Override
    protected void onStart() {
        super.onStart();

        new Thread(new Runnable() {
            @Override
            public void run() {

                cartRef.child(String.valueOf(serialNo)).addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        cCartList.clear();
                        for(DataSnapshot ds:dataSnapshot.getChildren()){
                            Cart cart=ds.getValue(Cart.class);
                            cCartList.add(cart);
                        }
                        cartItemRecyclerView.setLayoutManager(new LinearLayoutManager(CartActivity.this));
                        CartAdapter cartAdapter = new CartAdapter(cCartList,CartActivity.this,serialNo);
                        cartItemRecyclerView.setAdapter(cartAdapter);
                        cartAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }).start();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        if (getIntent() != null) {

            serialNo= getIntent().getStringExtra("cusId");
            customerName= getIntent().getStringExtra("cusName");
            contactNo =getIntent().getStringExtra("cusPhone");

        }

        cCartList =new ArrayList<>();
        serialTextView = findViewById(R.id.cartCustomerSerialNo);
        nameTextView = findViewById(R.id.cartCustomerName);
        contactTextView = findViewById(R.id.cartCustomerContact);
        cartItemRecyclerView = findViewById(R.id.cartListRecyclerView);


        nameTextView.setText(customerName);
        serialTextView.setText(serialNo);
        contactTextView.setText(contactNo);


    }

}


