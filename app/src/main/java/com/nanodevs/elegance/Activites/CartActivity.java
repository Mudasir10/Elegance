package com.nanodevs.elegance.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nanodevs.elegance.Adapters.CartAdapter;
import com.nanodevs.elegance.Pojo.Cart;
import com.nanodevs.elegance.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CartActivity extends AppCompatActivity {

   private DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("Cart");
    private String serialNo,customerName,contactNo;
    private List<Cart> cCartList;
    private RecyclerView cartItemRecyclerView;
    private TextView nameTextView,serialTextView,contactTextView;
    private TextInputLayout calculationEditText,totalSumEditText,discountEditText,
            advancePaymentEditText,balancePaymentEditText,remainingBalanceEditText;

    private long totalCalculations,totalSum,discountAmount,advanceAmount,balanceAmount,remainingAmount;
    Toolbar mToolbar;
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
                });}}).start();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        mToolbar=findViewById(R.id.app_bar_cart_activity);
        mToolbar.setTitle("View Cart");
        setSupportActionBar(mToolbar);


        if(getIntent() != null) {
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


        calculationEditText = findViewById(R.id.totalCalculationsEditText);
        totalSumEditText = findViewById(R.id.totalCalculationsSumEditText);
        discountEditText = findViewById(R.id.discountEditText);
        advancePaymentEditText = findViewById(R.id.advancePayment);
        balancePaymentEditText = findViewById(R.id.balancePayment);
        remainingBalanceEditText = findViewById(R.id.remainingBalance);

        TextWatcher textWatcher = new TextWatcher() {
            public void afterTextChanged(Editable s) {


                calculateResult();

            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        };

        calculationEditText.getEditText().addTextChangedListener(textWatcher);

    }

    private void calculateResult() throws NumberFormatException {
        Editable editableValue1 =calculationEditText.getEditText().getText();
        int sum=0;
        String temp;
        if (editableValue1 != null){
            String calue = editableValue1.toString().replaceAll("\\s","+");

            for (int i = 0; i < calue.length(); i++){
                temp=String.valueOf(calue.charAt(i));
                sum+=Integer.parseInt(temp);
            }
           totalSumEditText.getEditText().setText(String.valueOf((sum)));
        }

    }

    private String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }


}


