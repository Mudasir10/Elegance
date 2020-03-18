package com.nanodevs.elegance.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
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
import com.nanodevs.elegance.classes.ESC_POS_EPSON_ANDROID;
import com.nanodevs.elegance.classes.InternetConnection;
import com.nanodevs.elegance.classes.USBPort;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import es.rcti.printerplus.printcom.models.PrintTool;
import es.rcti.printerplus.printcom.models.StructReport;

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


    // libs and perms for printing
    private static final int RESULT_SETTINGS = 1;

    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";

    // Intent
    private PendingIntent mPermissionIntent;
    // USB
    private UsbManager mUsbManager;
    private USBPort mUsbPort;

    private ESC_POS_EPSON_ANDROID mEscPos;

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
        });



        // Mudasir
        mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);

        // TODO
        // Regist BroadCast Receiver. (To acquire Permission.)
        mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        registerReceiver(mUsbReceiver, filter);
        filter = new IntentFilter(mUsbManager.ACTION_USB_DEVICE_DETACHED);
        registerReceiver(mUsbReceiver, filter);
        filter = new IntentFilter(mUsbManager.ACTION_USB_DEVICE_ATTACHED);
        registerReceiver(mUsbReceiver, filter);

        mUsbPort = new USBPort(mUsbManager);
        mEscPos  = new ESC_POS_EPSON_ANDROID(mUsbPort);

        // Mudasir



    }


    @Override
    public void onDestroy()
    {
        unregisterReceiver(mUsbReceiver);
        try {
            mUsbPort.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver()
    {
        public void onReceive(Context context, Intent intent)
        {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action))
            {
                synchronized (this)
                {
                    UsbDevice device = (UsbDevice) intent
                            .getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false))
                    {
                        if (device != null)
                        {
                            // call method to set up device communication
                        }
                    }
                    else
                    {
                        Log.d(TAG, "permission denied for device " + device);
                    }
                }
            }
            if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action))
            {
                UsbDevice device = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                if (device != null)
                {
                    // call your method that cleans up and closes communication with the device
                    mUsbPort.SetUSBConnectionFlag(false);

                    Toast.makeText(context, "Usb Attached", Toast.LENGTH_SHORT).show();
                   /* list.clear();
                    list.add("USB Device ATTACHED");
                    mAdapter.notifyDataSetChanged();*/
                }
            }

            if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action))
            {
                UsbDevice device = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                if (device != null)
                {
                    // call your method that cleans up and closes communication with the device
                    mUsbPort.SetUSBConnectionFlag(false);

                    Toast.makeText(context, "Usb Detached", Toast.LENGTH_SHORT).show();
                    /*list.clear();
                    list.add("USB Device detached");
                    mAdapter.notifyDataSetChanged();*/
                }
            }
        }
    };







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
        final String key= ordersReference.push().getKey();

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

                        // Make A Reciept here
                        // for Every Confirmed Order



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
                        if(task.isSuccessful()){
                            Toast.makeText(CartActivity.this, "Order uploaded", Toast.LENGTH_SHORT).show();
                            cartData.child(serialNo).removeValue();
                        }
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
                        if(task.isSuccessful()){

                            Toast.makeText(CartActivity.this, "Order uploaded", Toast.LENGTH_SHORT).show();
                            cartData.child(serialNo).removeValue();
                        }

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
                        {

                            Toast.makeText(CartActivity.this, "Order uploaded", Toast.LENGTH_SHORT).show();
                            cartData.child(serialNo).removeValue();
                        }
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

                      if (InternetConnection.checkConnection(CartActivity.this)){

                         new AlertDialog.Builder(CartActivity.this)
                                  .setTitle("Order Confirmation")
                                  .setMessage("Are You Sure You Want to Upload This Order.First Make Sure You Have Printed Reciept For Customer.")
                                  .setIcon(R.drawable.ic_save)
                                  .setCancelable(false)
                                  .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                      @Override
                                      public void onClick(DialogInterface dialog, int which) {
                                          dialog.dismiss();

                                          uploadOrder();
                                      }
                                  })
                                  .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                      @Override
                                      public void onClick(DialogInterface dialog, int which) {

                                          dialog.cancel();

                                      }
                                  }).create().show();

                      }else {
                          Toast.makeText(CartActivity.this, "Can not Upload Without Internet!", Toast.LENGTH_SHORT).show();
                      }

            }else
                Toast.makeText(this, "First complete Billing", Toast.LENGTH_SHORT).show();


        }
        else if (item.getItemId()==R.id.btnPrinterSettings)
        {
            Intent settingActivity = new Intent(CartActivity.this, SettingsActivity.class);
            startActivity(settingActivity);

        } else if (item.getItemId()==R.id.btnConnectPrinter)
        {
           /* list.clear();*/

            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
            String vendorIDstr = sharedPrefs.getString("printer_vendorId", "0");
            int vendorID = 0;
            try {
                vendorID = Integer.parseInt(vendorIDstr);
            } catch(NumberFormatException nfe) {
                vendorID = 0;
            }
            String productIDstr = sharedPrefs.getString("printer_productId","0");
            int productID = 0;
            try {
                productID = Integer.parseInt(productIDstr);
            } catch(NumberFormatException nfe) {
                productID = 0;
            }

            UsbDevice foundDevice = mUsbPort.search_device(vendorID,productID);
            if ( foundDevice == null) {

                Toast.makeText(this, " Printer Not Found", Toast.LENGTH_SHORT).show();
                /*list.add("USB Device vendorId=" + vendorIDstr + " productID=" + productIDstr + " not found");
                list.add("Try to search devices");
                list.add("Don't forget to enter the VendorID and ProductID into the settings dialog");
                mAdapter.notifyDataSetChanged();
*/
            }
            else {
                Toast.makeText(this, " PrinterFound", Toast.LENGTH_SHORT).show();
         /*       list.add("Device found...");*/

            }
            try {
                if ( !this.mUsbManager.hasPermission(foundDevice) )
                    Toast.makeText(this, "Need Authification, please repeat...", Toast.LENGTH_SHORT).show();
                    /*list.add("Need Authification, please repeat...");*/
                this.mUsbManager.requestPermission(foundDevice, mPermissionIntent);


                if ( mUsbPort.connect_device(foundDevice)) {
                    Toast.makeText(this, "Device connected...", Toast.LENGTH_SHORT).show();
                  /*  list.add("Device connected...");*/

                }
                else
                    Toast.makeText(this, "Device not connected...", Toast.LENGTH_SHORT).show();
                  /*  list.add("Device not connected...");*/

            }
            catch ( Exception  e )
            {
                Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
               /* list.clear();
                //list.add(e.getLocalizedMessage());
                list.add(e.getMessage());
                mAdapter.notifyDataSetChanged();*/
            }

           /* mAdapter.notifyDataSetChanged();*/

        }

        return super.onOptionsItemSelected(item);

    }

    private String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }


    public void print(View v){

        if (InternetConnection.checkConnection(this)){
            printRecipt();
        }
        else{
            Toast.makeText(this, "Cannot Print Without Internet", Toast.LENGTH_SHORT).show();
        }

    }



    private void printRecipt() {

        try{

            if (!totalSumEditText.getEditText().getText().toString().isEmpty() &&
                    !discountEditTextBox.getEditText().getText().toString().isEmpty() &&
                    !advancePaymentEditText.getEditText().getText().toString().isEmpty() &&
                    !balancePaymentEditText.getEditText().getText().toString().isEmpty() &&
                    !remainingBalanceEditText.getEditText().getText().toString().isEmpty())
            {

               // String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

                String test = null;
                mEscPos.init_printer();
                mEscPos.select_fontA();
                mEscPos.select_code_tab(ESC_POS_EPSON_ANDROID.CodePage.PC858);
                mEscPos.underline_2dot_on();
                mEscPos.justification_center();
                test = "Elegance Collection";
                mEscPos.print_line(test);
                mEscPos.underline_off();
                mEscPos.print_linefeed();

                mEscPos.justification_center();
                test="Mohni Bazar Near National Bank Nawabshah";
                mEscPos.print_line(test);

                test="phone: 0333-3552445 - 03003552428";
                mEscPos.print_line(test);
                mEscPos.print_linefeed();
                mEscPos.justification_center();
                mEscPos.underline_2dot_on();
                test="Bill Reciept";
                mEscPos.print_line(test);
                mEscPos.underline_off();
                mEscPos.print_linefeed();

                mEscPos.justification_left();
                mEscPos.print_line("Current : "+getDate()+"  Expected Date : "+expectedDate.getText().toString());
                mEscPos.print_linefeed();

                mEscPos.justification_left();
                test="Customer Name: "+customerName;
                mEscPos.print_line(test);
                mEscPos.justification_left();
                test="Customer Id:"+serialNo;
                mEscPos.print_line(test);
                mEscPos.print_linefeed();



                mEscPos.justification_center();
                test="Ct    Kh    kr    L    W/w    B";
                mEscPos.print_line(test);

                for (int i = 0; i < cCartList.size(); i++) {


                   mEscPos.justification_center() ;
                   test=cCartList.get(i).getCottonQty()+"      "+cCartList.get(i).getKhaadiQty()+"     "+cCartList.get(i).getKarandiQty()+"     "+
                           cCartList.get(i).getLilanQty()+"     "+cCartList.get(i).getwWearQty()+"     "+cCartList.get(i).getBoskiQty();
                    mEscPos.print_line(test);

                    test=""+cCartList.get(i).getSuitType();
                    mEscPos.print_line(test);
                    mEscPos.print_linefeed();
                }

                mEscPos.print_linefeed();
                mEscPos.justification_center();
                test="\tTotal Bill : "+totalSumEditText.getEditText().getText().toString();
                mEscPos.print_line(test);

                if (Long.valueOf(discountEditTextBox.getEditText().getText().toString()) >0){
                    test="\tDiscount : "+  discountEditTextBox.getEditText().getText().toString();
                    mEscPos.print_line(test);
                }

                if (Long.valueOf(advancePaymentEditText.getEditText().getText().toString())>0){
                    test="\tAdvance : "+advancePaymentEditText.getEditText().getText().toString();
                    mEscPos.print_line(test);
                }


          /*  mEscPos.justification_center();
            test="\tBalance : "+  balancePaymentEditText.getEditText().getText().toString();
            mEscPos.print_line(test);*/

                if (Long.valueOf(discountEditTextBox.getEditText().getText().toString()) >0
                        || Long.valueOf(advancePaymentEditText.getEditText().getText().toString())>0){

                    test="\tRemaining : "+ remainingBalanceEditText.getEditText().getText().toString();
                    mEscPos.print_line(test);
                }
                mEscPos.print_linefeed();

                if(cCartList.size()<5){
                    mEscPos.justification_center();
                    mEscPos.underline_2dot_on();
                    test="Powered By NanoDevLab "+"03002012619 - 03063246643";
                    mEscPos.print_line(test);
                    mEscPos.underline_off();
                    mEscPos.print_linefeed();
                }

                mEscPos.feedpapercut();

            }
            else{
                Toast.makeText(this, "Please Give Payment Details Before printing Reciept", Toast.LENGTH_SHORT).show();
            }



        }
        catch (Exception ex){

            Toast.makeText(this, ""+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }




    }



}




