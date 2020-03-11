package com.nanodevs.elegance.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nanodevs.elegance.Adapters.CartAdapter;
import com.nanodevs.elegance.MainActivity;
import com.nanodevs.elegance.Pojo.Cart;
import com.nanodevs.elegance.Pojo.CustomerBill;
import com.nanodevs.elegance.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class CartActivity extends AppCompatActivity {
    private static final String TAG = "MyTag";

    private DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("Cart");
    private String serialNo, customerName, contactNo;
    private List<Cart> cCartList;
    private RecyclerView cartItemRecyclerView;
    private TextView nameTextView, serialTextView, contactTextView;
    private TextInputLayout calculationEditText, totalSumEditText, discountEditTextBox,
            advancePaymentEditText, balancePaymentEditText, remainingBalanceEditText;
    private EditText expectedDate;
    private Toolbar mToolbar;
    private Button getBillbtn;
    private long global_TotalSum=0,global_CalculationsSum;

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
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Cart cart = ds.getValue(Cart.class);
                            cCartList.add(cart);
                        }
                        cartItemRecyclerView.setLayoutManager(new LinearLayoutManager(CartActivity.this));
                        CartAdapter cartAdapter = new CartAdapter(cCartList, CartActivity.this, serialNo);
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

        mToolbar = findViewById(R.id.app_bar_cart_activity);
        mToolbar.setTitle("View Cart");
        setSupportActionBar(mToolbar);


        if (getIntent() != null) {
            serialNo = getIntent().getStringExtra("cusId");
            customerName = getIntent().getStringExtra("cusName");
            contactNo =getIntent().getStringExtra("cusPhone");
        }
        cCartList = new ArrayList<>();
        serialTextView = findViewById(R.id.cartCustomerSerialNo);
        nameTextView = findViewById(R.id.cartCustomerName);
        contactTextView = findViewById(R.id.cartCustomerContact);
        cartItemRecyclerView = findViewById(R.id.cartListRecyclerView);
        nameTextView.setText(customerName);
        serialTextView.setText(serialNo);
        contactTextView.setText(contactNo);
        getBillbtn = findViewById(R.id.getBillsBtn);
        expectedDate = findViewById(R.id.expectedDate);
        calculationEditText = findViewById(R.id.totalCalculationsEditText);
        totalSumEditText = findViewById(R.id.totalCalculationsSumEditText);
        discountEditTextBox = findViewById(R.id.discountEditText);
        advancePaymentEditText = findViewById(R.id.advancePayment);
        balancePaymentEditText = findViewById(R.id.balancePayment);
        remainingBalanceEditText = findViewById(R.id.remainingBalance);

        TextWatcher textWatcher = new TextWatcher() {
            public void afterTextChanged(Editable s) {
                calculateResult();

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        };
        calculationEditText.getEditText().addTextChangedListener(textWatcher);

        getBillbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(global_TotalSum!=0&&global_TotalSum>0){
                    getBillCalculations();
                }else
                    Toast.makeText(CartActivity.this, "Fill all required fields", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onGetBillClick: "+global_TotalSum);
            }
        }); }

    private void getBillCalculations() {

        long d = global_CalculationsSum;

        if (global_TotalSum > 0 && d >0) {
            Log.d(TAG, "getBillCalculations first if : " + d);
            balancePaymentEditText.getEditText().setText(String.valueOf(global_TotalSum));
            remainingBalanceEditText.getEditText().setText(String.valueOf(global_TotalSum));
            if( expectedDate.getText().toString().isEmpty()){
                expectedDate.setError("fill the date");
            }
            if(!discountEditTextBox.getEditText().getText().toString().isEmpty()&& !advancePaymentEditText.getEditText().getText().toString().isEmpty()){
                long finalValue = global_TotalSum - Long.parseLong(discountEditTextBox.getEditText().getText().toString());
                remainingBalanceEditText.getEditText().setText(String.valueOf(finalValue- Long.parseLong(advancePaymentEditText.getEditText().getText().toString())));
            }

            else if (!discountEditTextBox.getEditText().getText().toString().isEmpty()) {

                long var_Discount = Long.parseLong(discountEditTextBox.getEditText().getText().toString());
                balancePaymentEditText.getEditText().setText(String.valueOf(global_TotalSum - var_Discount));
                remainingBalanceEditText.getEditText().setText(String.valueOf(global_TotalSum - var_Discount));


            }
            else if (!advancePaymentEditText.getEditText().getText().toString().isEmpty()) {
                balancePaymentEditText.getEditText().setText(String.valueOf(global_TotalSum));
                remainingBalanceEditText.getEditText().setText(String.valueOf(global_TotalSum - Long.parseLong(advancePaymentEditText.getEditText().getText().toString())));
                remainingBalanceEditText.getEditText().setText(String.valueOf(global_TotalSum - Long.parseLong(advancePaymentEditText.getEditText().getText().toString())));
            }


        }

    }


    private void uploadOrder() {

        DatabaseReference ordersReference = FirebaseDatabase.getInstance().getReference("CustomerOrders");
        DatabaseReference billOrderReference = FirebaseDatabase.getInstance().getReference("CustomerBills");
        final DatabaseReference cartData = FirebaseDatabase.getInstance().getReference("Cart");
        String key= ordersReference.push().getKey();

            if(!discountEditTextBox.getEditText().getText().toString().isEmpty() &&
                    !advancePaymentEditText.getEditText().getText().toString().isEmpty()
                    && global_TotalSum > 0 && !expectedDate.getText().toString().isEmpty() &&
                    !remainingBalanceEditText.getEditText().getText().toString().isEmpty() &&
                    !balancePaymentEditText.getEditText().getText().toString().isEmpty())   {

                CustomerBill customerBill=new CustomerBill(Long.parseLong(serialNo),customerName,contactNo,getDate(),
                        expectedDate.getText().toString(),false,Long.parseLong(totalSumEditText.getEditText().getText().toString()),
                      Long.parseLong(discountEditTextBox.getEditText().getText().toString()),
                        Long.parseLong(advancePaymentEditText.getEditText().getText().toString()),
                        Long.parseLong(balancePaymentEditText.getEditText().getText().toString()),
                        Long.parseLong(remainingBalanceEditText.getEditText().getText().toString())
                        );

                Map<String,Object> data=new HashMap<>();
                data.put(serialNo+"/"+key,customerBill.completeCustomerBillWithAdvanceAndDiscountMap());
                billOrderReference.updateChildren(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                            Toast.makeText(CartActivity.this, "Order uploaded", Toast.LENGTH_SHORT).show();
                        cartData.child(serialNo).removeValue();
                        startActivity(new Intent(CartActivity.this, MainActivity.class));

                    }
                });

                for (int i = 0; i <cCartList.size(); i++) {
                        ordersReference.child(serialNo).child(key).child(cCartList.get(i).getSuitType()).setValue(cCartList.get(i));
                 }

            }

           else if (discountEditTextBox.getEditText().getText().toString().isEmpty() &&
                    advancePaymentEditText.getEditText().getText().toString().isEmpty() &&
                global_TotalSum > 0 && !expectedDate.getText().toString().isEmpty() &&
                !remainingBalanceEditText.getEditText().getText().toString().isEmpty() &&
                !balancePaymentEditText.getEditText().getText().toString().isEmpty()) {

                CustomerBill customerBillAWithoutAdvanceAndDiscount = new CustomerBill();
                customerBillAWithoutAdvanceAndDiscount.setMethodWithoutAdvanceAndDiscount(Long.parseLong(serialNo),customerName,contactNo,getDate(),
                        expectedDate.getText().toString(),false,Long.parseLong(totalSumEditText.getEditText().getText().toString()),
                        Long.parseLong(balancePaymentEditText.getEditText().getText().toString()),
                        Long.parseLong(remainingBalanceEditText.getEditText().getText().toString()));

                Map<String,Object> data=new HashMap<>();
                data.put(serialNo+"/"+key,customerBillAWithoutAdvanceAndDiscount.withoutAdvanceAndDiscountMapBill());
                billOrderReference.updateChildren(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                            Toast.makeText(CartActivity.this, "Order uploaded", Toast.LENGTH_SHORT).show();
                        cartData.child(serialNo).removeValue();
                        startActivity(new Intent(CartActivity.this, MainActivity.class));

                    }
                });

                for (int i = 0; i <cCartList.size(); i++) {
                    ordersReference.child(serialNo).child(key).child(cCartList.get(i).getSuitType()).setValue(cCartList.get(i));
                }


            }

           else if (!discountEditTextBox.getEditText().getText().toString().isEmpty() &&
                advancePaymentEditText.getEditText().getText().toString().isEmpty() &&
                global_TotalSum > 0 && !expectedDate.getText().toString().isEmpty() &&
                !remainingBalanceEditText.getEditText().getText().toString().isEmpty() &&
                !balancePaymentEditText.getEditText().getText().toString().isEmpty()) {

                CustomerBill customerBillAWithDiscount = new CustomerBill();
                customerBillAWithDiscount.setMethodWithDiscountOnly(Long.parseLong(serialNo),customerName,contactNo,getDate(),
                        expectedDate.getText().toString(),false,
                        Long.parseLong(totalSumEditText.getEditText().getText().toString()),
                        Long.parseLong(discountEditTextBox.getEditText().getText().toString()),
                        Long.parseLong(balancePaymentEditText.getEditText().getText().toString()),
                        Long.parseLong(remainingBalanceEditText.getEditText().getText().toString()));

                Map<String,Object> data=new HashMap<>();
                data.put(serialNo+"/"+key,customerBillAWithDiscount.withDiscountMapBill());
                billOrderReference.updateChildren(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                            Toast.makeText(CartActivity.this, "Order uploaded", Toast.LENGTH_SHORT).show();
                        cartData.child(serialNo).removeValue();
                        startActivity(new Intent(CartActivity.this, MainActivity.class));

                    }
                });

                for (int i = 0; i <cCartList.size(); i++) {
                    ordersReference.child(serialNo).child(key).child(cCartList.get(i).getSuitType()).setValue(cCartList.get(i));
                }

            }

           else if (!advancePaymentEditText.getEditText().getText().toString().isEmpty()&&
                  discountEditTextBox.getEditText().getText().toString().isEmpty() &&
                global_TotalSum > 0 && !expectedDate.getText().toString().isEmpty() &&
                !remainingBalanceEditText.getEditText().getText().toString().isEmpty() &&
                !balancePaymentEditText.getEditText().getText().toString().isEmpty()) {

                CustomerBill customerBillAWithOnlyAdvance = new CustomerBill();
                customerBillAWithOnlyAdvance.setMethodWithAdvanceOnly(Long.parseLong(serialNo),customerName,contactNo,getDate(),
                        expectedDate.getText().toString(),false,
                        Long.parseLong(totalSumEditText.getEditText().getText().toString()),
                        Long.parseLong(advancePaymentEditText.getEditText().getText().toString()),
                        Long.parseLong(balancePaymentEditText.getEditText().getText().toString()),
                        Long.parseLong(remainingBalanceEditText.getEditText().getText().toString()));

                Map<String,Object> data=new HashMap<>();
                data.put(serialNo+"/"+key,customerBillAWithOnlyAdvance.withAdvanceMapBill());
                billOrderReference.updateChildren(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                            Toast.makeText(CartActivity.this, "Order uploaded", Toast.LENGTH_SHORT).show();
                        cartData.child(serialNo).removeValue();
                        startActivity(new Intent(CartActivity.this, MainActivity.class));

                    }
                });

                for (int i = 0; i <cCartList.size(); i++) {
                    ordersReference.child(serialNo).child(key).child(cCartList.get(i).getSuitType()).setValue(cCartList.get(i));
                }

            }
           else
                Toast.makeText(this, "Error, Complete billing calculations!", Toast.LENGTH_SHORT).show();


    }


    private void calculateResult() throws NumberFormatException {
        Editable editableValue1 = calculationEditText.getEditText().getText();
        long sum = 0;
        String temp;
        try {
            if (editableValue1 != null) {
                Pattern regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]");
                final String value = editableValue1.toString();
                if (value.isEmpty() || value.matches(".*[a-zA-Z]+.*") || regex.matcher(value).find() || value.matches("\\s|\\n|\\t|\\t\\t")) {
                    calculationEditText.getEditText().setHint("Total Calculations");
                    totalSumEditText.getEditText().setText("");
                    balancePaymentEditText.getEditText().setText("");
                    remainingBalanceEditText.getEditText().setText("");
                    advancePaymentEditText.getEditText().setText("");
                    discountEditTextBox.getEditText().setText("");
                    expectedDate.setText("");
                    global_TotalSum=0;
                    advancePaymentEditText.getEditText().setEnabled(false);
                    discountEditTextBox.getEditText().setEnabled(false);
                } else {
                    String[] calue = editableValue1.toString().split("\\s|\\n|\t" + ".*[a-zA-Z]+.*");
                    for (String s : calue) {
                        temp = s;
                        sum += Long.parseLong(temp.replaceAll("[^0-9]+", ""));
                    }
                    global_TotalSum=sum;
                    global_CalculationsSum=sum;
                    totalSumEditText.getEditText().setText(String.valueOf((sum)));
                    advancePaymentEditText.getEditText().setEnabled(true);
                    discountEditTextBox.getEditText().setEnabled(true);
                }
            }
        } catch (Exception e) {
            Log.d(TAG, "calculateResult: " + e.getMessage());
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.viewcart_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.btnDonePlaceOrder)
        {

            if(global_TotalSum!=0&&global_TotalSum>0){
                uploadOrder();
            }else
                Toast.makeText(this, "First complete Billing", Toast.LENGTH_SHORT).show();


        }
        return super.onOptionsItemSelected(item);

    }

    private String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }


}




