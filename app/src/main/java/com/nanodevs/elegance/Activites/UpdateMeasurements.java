package com.nanodevs.elegance.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.provider.SelfDestructiveThread;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nanodevs.elegance.Pojo.Measurements;
import com.nanodevs.elegance.R;
import com.nanodevs.elegance.classes.ESC_POS_EPSON_ANDROID;
import com.nanodevs.elegance.classes.InternetConnection;
import com.nanodevs.elegance.classes.USBPort;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.view.View.GONE;

public class UpdateMeasurements extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText customerName, customerContact, customerSerialNo;
    private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Measurements");
    private Spinner spinner;

    private CheckBox checkBoxPocket_bothSides,checkBoxPocket_front,checkBoxcolr_simple,checkBoxcolr_sherwani,checkBoxcolr_halfSherwani;

    String bS="no",Fp="no";
    String colerStyle="not defined";
    private TextView textViewpocketStyle,textViewColerInfo;
    private String SelectedCategory;
    private TextInputLayout etLenght, etShoulder, etSleeves, etcolr, etchest, etstomachSize, ethipSize, etarms, etwrist, etloosingchest, etloosingstomach,
            etloosinghip, etpentlength, etpentbottom, etwaist, etthigh, etDescription;


    Toolbar mToolbar;

    Button btnPrintMeasurement;


    private static final String TAG = "UpdateMeasurements";

    // libs and perms for printing
    private static final int RESULT_SETTINGS = 1;

    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";

    // Intent
    private PendingIntent mPermissionIntent;
    // USB
    private UsbManager mUsbManager;
    private USBPort mUsbPort;

    private ESC_POS_EPSON_ANDROID mEscPos;
    private String globalCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_measurements);

        mToolbar=findViewById(R.id.app_bar_update);
        mToolbar.setTitle("Update Activity");
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        init();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_update,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==R.id.UpdatebtndoneSaveCustomer){

            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("Confirmation Message");
            builder.setMessage("Are You Sure You Want to Save This Measurements");
            builder.setCancelable(false);
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (InternetConnection.checkConnection(UpdateMeasurements.this)){
                        if (SelectedCategory.equals("Kurta")){
                                UpdateDataForKurtaCategory();
                                dialog.dismiss();
                        }
                        else if (SelectedCategory.equals("Shirt")){
                                UpdateDataForShirtCategory();
                                dialog.dismiss();
                        }
                        else if (SelectedCategory.equals("Suit")){
                                UpdateDataForSuitCategory();
                                dialog.dismiss();
                        }
                        else if (SelectedCategory.equals("Saffari Coat")){
                                UpdateDataForSaffariCoatCategory();
                                dialog.dismiss();
                        }
                        else if (SelectedCategory.equals("Three Piece")){
                                UpdateDataForThreePieceCategory();
                                dialog.dismiss();
                        }
                        else if (SelectedCategory.equals("Pant")){
                                UpdateDataForPantCategory();
                                dialog.dismiss();
                        }
                        else if (SelectedCategory.equals("Waist Coat")){
                                UpdateDataForWaistCoatCategory();
                                dialog.dismiss();
                        }
                    }
                    else{
                        Toast.makeText(UpdateMeasurements.this, "Can not Save Customer Without Internet", Toast.LENGTH_SHORT).show();
                    }


                }
            });
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.cancel();
                }
            });

            builder.create();
            builder.show();
        }
        else if(item.getItemId()==R.id.UpdatebtnPrinterSettings){

            Intent settingActivity = new Intent(UpdateMeasurements.this, SettingsActivity.class);
            startActivity(settingActivity);

        }else if (item.getItemId()==R.id.UpdatebtnConnectPrinter){

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

    private void UpdateDataForWaistCoatCategory() {

        String len= etLenght.getEditText().getText().toString();
        String shoulder=  etShoulder.getEditText().getText().toString();
        String colr=  etcolr.getEditText().getText().toString();
        String chest=  etchest.getEditText().getText().toString();
        String stomachSize= etstomachSize.getEditText().getText().toString();
        String loosinChest= etloosingchest.getEditText().getText().toString();
        String loosinstomach= etloosingstomach.getEditText().getText().toString();
        String des=  etDescription.getEditText().getText().toString();

        Measurements measurements = new Measurements();
        measurements.SetMeasurementsForWaistCoat(len,shoulder,colr,chest,stomachSize,loosinChest,loosinstomach,des);

        Map<String, Object> data = measurements.WaistCoattToMap();
        UpdateCustomerMeasurement(customerSerialNo.getText().toString(),data,SelectedCategory);
    }

    private void UpdateDataForPantCategory() {

        String waist= etwaist.getEditText().getText().toString();
        String thigh= etthigh.getEditText().getText().toString();
        String hipSize= ethipSize.getEditText().getText().toString();
        String des= etDescription.getEditText().getText().toString();
        String pentLength = etpentlength.getEditText().getText().toString();
        String pentBottom= etpentbottom.getEditText().getText().toString();

        Measurements measurements = new Measurements();
        measurements.SetMeasurementsForPant(thigh,waist,hipSize,des,pentLength,pentBottom);
        Map<String, Object> data = measurements.PanttToMap();

        UpdateCustomerMeasurement(customerSerialNo.getText().toString(),data,SelectedCategory);
    }

    private void UpdateDataForThreePieceCategory() {

        String len= etLenght.getEditText().getText().toString();
        String sel=  etSleeves.getEditText().getText().toString();
        String shoulder=  etShoulder.getEditText().getText().toString();
        String colr= etcolr.getEditText().getText().toString();
        String chest=  etchest.getEditText().getText().toString();
        String stomach= etstomachSize.getEditText().getText().toString();
        String hipSize= ethipSize.getEditText().getText().toString();
        String wristSize= etwrist.getEditText().getText().toString();
        String pentLength= etpentlength.getEditText().getText().toString();
        String pentBottom= etpentbottom.getEditText().getText().toString();
        String thigh = etthigh.getEditText().getText().toString();
        String waist= etwaist.getEditText().getText().toString();
        String des= etDescription.getEditText().getText().toString();
        String arms =etarms.getEditText().getText().toString();


        Measurements measurements = new Measurements();
        measurements.SetMeasurementsForThreePiece(len,sel,shoulder,colr,chest,stomach,hipSize,wristSize,pentLength,pentBottom,thigh,waist,des,arms);

        Map<String, Object> data = measurements.ThreePiecetToMap();

        UpdateCustomerMeasurement(customerSerialNo.getText().toString(),data,SelectedCategory);

    }

    private void UpdateDataForSaffariCoatCategory() {

        String len = etLenght.getEditText().getText().toString();
        String slev = etSleeves.getEditText().getText().toString();
        String shoulder = etShoulder.getEditText().getText().toString();
        String colr = etcolr.getEditText().getText().toString();
        String chest = etchest.getEditText().getText().toString();
        String stomachSize = etstomachSize.getEditText().getText().toString();
        String hipSize = ethipSize.getEditText().getText().toString();
        String wrist = etwrist.getEditText().getText().toString();
        String pentLength = etpentlength.getEditText().getText().toString();
        String pentBottom = etpentbottom.getEditText().getText().toString();
        String thigh = etthigh.getEditText().getText().toString();
        String waist = etwaist.getEditText().getText().toString();
        String des = etDescription.getEditText().getText().toString();
        String arms =etarms.getEditText().getText().toString();


        Measurements measurements = new Measurements();
        measurements.SetMeasurementsForSaffariCoat(len, slev, shoulder, colr, chest, stomachSize, hipSize, wrist, pentLength, pentBottom, thigh, waist, des,arms);

        Map<String, Object> data = measurements.SaffariCoatToMap();
        UpdateCustomerMeasurement(customerSerialNo.getText().toString(),data,SelectedCategory);
    }

    private void UpdateDataForSuitCategory() {

        String length=etLenght.getEditText().getText().toString();
        String Sleeves=etSleeves.getEditText().getText().toString();
        String shoulder= etShoulder.getEditText().getText().toString();
        String coler= etcolr.getEditText().getText().toString();
        String chest = etchest.getEditText().getText().toString();
        String stomachSize= etstomachSize.getEditText().getText().toString();
        String HipSize= ethipSize.getEditText().getText().toString();
        String armsSize= etarms.getEditText().getText().toString();
        String wristSize= etwrist.getEditText().getText().toString();
        String loosinHip= etloosinghip.getEditText().getText().toString();
        String loosingChest= etloosingchest.getEditText().getText().toString();
        String loosingStomach= etloosingstomach.getEditText().getText().toString();
        String des= etDescription.getEditText().getText().toString();
        String pentLength= etpentlength.getEditText().getText().toString();
        String pentBottom= etpentbottom.getEditText().getText().toString();

        if (checkBoxPocket_front.isChecked() && checkBoxPocket_bothSides.isChecked()){
            Fp="yes";
            bS="yes";
        }
        if (checkBoxPocket_front.isChecked()){
            Fp="yes";
        }
        if (checkBoxPocket_bothSides.isChecked()){
            bS="yes";
        }
        if (checkBoxcolr_simple.isChecked()){
            colerStyle="simple";
        }
        if (checkBoxcolr_sherwani.isChecked()){
            colerStyle="Sherwani";
        }
        if (checkBoxcolr_halfSherwani.isChecked()){
            colerStyle="half Sherwani";
        }


        Measurements measurements=new Measurements();
        measurements.SetMeasurementsForSuit(length,Sleeves,shoulder,coler,chest,stomachSize,armsSize,wristSize,loosingChest,
                loosingStomach,des,HipSize,loosinHip,pentLength,pentBottom,Fp,bS,colerStyle);

        Map<String,Object> data= measurements.SuitToMap();

        UpdateCustomerMeasurement(customerSerialNo.getText().toString(),data,SelectedCategory);

    }


    private boolean isOnline() {
        try {
            return Runtime.getRuntime().exec("/system/bin/ping -c 1 8.8.8.8").waitFor() == 0; //  "8.8.8.8" is the server to ping
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void UpdateDataForShirtCategory() {

        String len= etLenght.getEditText().getText().toString();
        String Sleeves= etSleeves.getEditText().getText().toString();
        String shoulder= etShoulder.getEditText().getText().toString();
        String colr= etcolr.getEditText().getText().toString();
        String chest= etchest.getEditText().getText().toString();
        String stomachSize= etstomachSize.getEditText().getText().toString();
        String arms= etarms.getEditText().getText().toString();
        String wrist= etwrist.getEditText().getText().toString();
        String loosingChest= etloosingchest.getEditText().getText().toString();
        String loosingstomach= etloosingstomach.getEditText().getText().toString();
        String des= etDescription.getEditText().getText().toString();

        Measurements measurements=new Measurements();
        measurements.SetMeasurementsForShirt(len,Sleeves,shoulder,colr,chest,stomachSize,arms,wrist,loosingChest,loosingstomach,des,Fp,colerStyle);

        Map<String,Object> data= measurements.ShirttoMap();
        UpdateCustomerMeasurement(customerSerialNo.getText().toString(),data,SelectedCategory);

    }


    private void UpdateDataForKurtaCategory() {

        String length= etLenght.getEditText().getText().toString();
        String sleeve=  etSleeves.getEditText().getText().toString();
        String shoulder=  etShoulder.getEditText().getText().toString();
        String colr=  etcolr.getEditText().getText().toString();
        String chest= etchest.getEditText().getText().toString();
        String stomachsize=  etstomachSize.getEditText().getText().toString();
        String hipsize=  ethipSize.getEditText().getText().toString();
        String arms=  etarms.getEditText().getText().toString();
        String wrist= etwrist.getEditText().getText().toString();
        String loosingchest= etloosingchest.getEditText().getText().toString();
        String loosingstomach= etloosingstomach.getEditText().getText().toString();
        String loosinghip= etloosinghip.getEditText().getText().toString();
        String pentLenght= etpentlength.getEditText().getText().toString();
        String pentbottom= etpentbottom.getEditText().getText().toString();
        String desciption= etDescription.getEditText().getText().toString();

        if (checkBoxPocket_front.isChecked() && checkBoxPocket_bothSides.isChecked()){
            Fp="yes";
            bS="yes";
        }
        if (checkBoxPocket_front.isChecked()){
            Fp="yes";
        }
        if (checkBoxPocket_bothSides.isChecked()){
            bS="yes";
        }
        if (checkBoxcolr_simple.isChecked()){
            colerStyle="simple";
        }
        if (checkBoxcolr_sherwani.isChecked()){
            colerStyle="Sherwani";
        }
        if (checkBoxcolr_halfSherwani.isChecked()){
            colerStyle="half Sherwani";
        }


        Measurements measurements=new Measurements();
        measurements.SetMeasurementsForKurta(length,sleeve,shoulder,colr,chest,stomachsize,hipsize,
                arms,wrist,loosingchest,loosingstomach,loosinghip,pentLenght,pentbottom,desciption,Fp,bS,colerStyle);

        Map<String,Object> data= measurements.KurtatoMap();
        UpdateCustomerMeasurement(customerSerialNo.getText().toString(),data,SelectedCategory);
    }

    private String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }


    private void UpdateCustomerMeasurement(String customerSerialNo,
                                  Map<String, Object> measurements, String SelectedCategory) {

        Map<String,Object> data=new HashMap<>();
        data.put(customerSerialNo+"/"+SelectedCategory,measurements);

        databaseReference.updateChildren(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){

                    Toast.makeText(UpdateMeasurements.this, "Measurement Updated .", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(UpdateMeasurements.this, "Could not Uploaded", Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(UpdateMeasurements.this,"Failed to "+e.getMessage(),Toast.LENGTH_SHORT);
            }
        });


    }


    private void init() {
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


        btnPrintMeasurement=findViewById(R.id.printMeasurement);

        customerSerialNo=findViewById(R.id.customerSerialNo);
        customerName=findViewById(R.id.customerName);
        customerContact=findViewById(R.id.customerNumber);


        //init check boxes
        checkBoxPocket_bothSides = findViewById(R.id.UpdatecheckboxPocket_both_sides);
        checkBoxPocket_front = findViewById(R.id.UpdatecheckboxPocket_front_procket);
        checkBoxcolr_simple = findViewById(R.id.UpdatecheckboxColer_simple);
        checkBoxcolr_sherwani = findViewById(R.id.UpdatecheckboxColer_sherwani);
        checkBoxcolr_halfSherwani = findViewById(R.id.UpdatecheckboxColer_half_sherwani);


        textViewColerInfo=findViewById(R.id.textColerStyleUpdate);
        textViewpocketStyle=findViewById(R.id.textPocketInfoUpdate);


        if (getIntent()!=null){
            customerSerialNo.setText(String.valueOf(getIntent().getLongExtra("customerId", 0)));
            customerName.setText(String.valueOf(getIntent().getStringExtra("cus_name")));
            customerContact.setText(String.valueOf(getIntent().getStringExtra("cus_phone")));

        }

        spinner = findViewById(R.id.spinnerCategory);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(UpdateMeasurements.this,
                R.array.suitCategories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);



        checkBoxPocket_bothSides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBoxPocket_bothSides.isChecked()){
                    bS="yes";
                }
                if (!checkBoxPocket_bothSides.isChecked()){
                    bS="no";
                }
            }
        });
        checkBoxPocket_front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBoxPocket_front.isChecked()){
                    Fp="yes";
                }
                if (!checkBoxPocket_front.isChecked()){
                  Fp="no";
                }


            }
        });
        checkBoxcolr_simple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBoxcolr_simple.isChecked()){
                    colerStyle="simple";
                }

            }
        });

        checkBoxcolr_sherwani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBoxcolr_sherwani.isChecked()){
                    colerStyle="Sherwani";
                }

            }
        });
        checkBoxcolr_halfSherwani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBoxcolr_halfSherwani.isChecked()){
                    colerStyle="half Sherwani";
                }

            }
        });


        btnPrintMeasurement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (InternetConnection.checkConnection(UpdateMeasurements.this)){

                    try{
                        print();
                    }catch (Exception ex){
                        Toast.makeText(UpdateMeasurements.this, ""+ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(UpdateMeasurements.this, "Cannot Print Withour Internet!", Toast.LENGTH_SHORT).show();
                }



            }
        });


    }

    private void print() {

         String len=etLenght.getEditText().getText().toString();
         String Shoulder=etShoulder.getEditText().getText().toString();
         String coler=etcolr.getEditText().getText().toString();
         String chest=etchest.getEditText().getText().toString();
         String stomach= etstomachSize.getEditText().getText().toString();
         String loosingchest= etloosingchest.getEditText().getText().toString();
         String loosingStomach=  etloosingstomach.getEditText().getText().toString();
         String des=  etDescription.getEditText().getText().toString();
         String wrist= etwrist.getEditText().getText().toString();
         String thigh= etthigh.getEditText().getText().toString();
         String waist = etwaist.getEditText().getText().toString();
         String hipSize= ethipSize.getEditText().getText().toString();
         String pantLenght= etpentlength.getEditText().getText().toString();
         String pantBottom= etpentbottom.getEditText().getText().toString();
         String loosingHip= etloosinghip.getEditText().getText().toString();
         String arms= etarms.getEditText().getText().toString();
        String slev=  etSleeves.getEditText().getText().toString();

          boolean pocketBSides= checkBoxPocket_bothSides.isChecked();
          boolean pocketFront= checkBoxPocket_front.isChecked();

          boolean colerSimple= checkBoxcolr_simple.isChecked();
        boolean colerSherwani= checkBoxcolr_sherwani.isChecked();
        boolean colerHalfSherwani= checkBoxcolr_halfSherwani.isChecked();




        if(globalCategory.equals("Kurta")){

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

            mEscPos.justification_left();
            test="Customer Name : "+customerName.getText().toString();
            mEscPos.print_line(test);

            test="Customer Id : "+customerSerialNo.getText().toString();
            mEscPos.print_line(test);
            mEscPos.print_linefeed();

            test="Current Date : "+getDate();
            mEscPos.print_line(test);
            mEscPos.print_linefeed();

            test="Suit Type =  Kurta";
            mEscPos.print_line(test);
            mEscPos.print_linefeed();

            mEscPos.print_linefeed();

            mEscPos.justification_center();
            test=len +"  -  "+slev+ "  -  "+Shoulder+"  -  "+coler+"  -  "+chest+"  -  "+stomach;
            mEscPos.print_line(test);

            mEscPos.print_linefeed();
            mEscPos.justification_center();
            test=pantLenght+"-"+pantBottom+"  -  "+loosingHip+"  -  "+loosingStomach +"  -  "+loosingchest;
            mEscPos.print_line(test);

            mEscPos.print_linefeed();

            mEscPos.justification_center();


            if (pocketFront){
                test="Front Pocket : Yes";
                mEscPos.print_line(test);
                mEscPos.print_linefeed();
            }
            if (pocketBSides){
                test="2 Sides Pocket : Yes ";
                mEscPos.print_line(test);
                mEscPos.print_linefeed();
            }

            if (colerSimple){
                test="Coler : Simple";
                mEscPos.print_line(test);
                mEscPos.print_linefeed();
            }
            if (colerSherwani){
                test="Coler : Sherwani";
                mEscPos.print_line(test);
                mEscPos.print_linefeed();
            }
            if (colerHalfSherwani){
                test="Coler :  Half-Sherwani";
                mEscPos.print_line(test);
                mEscPos.print_linefeed();
            }


            mEscPos.justification_right();
            test=""+arms+"  -  "+wrist;
            mEscPos.print_line(test);

            mEscPos.print_linefeed();
            mEscPos.justification_left();
            test= "Description : "+des;
            mEscPos.print_line(test);
            mEscPos.print_linefeed();
            mEscPos.print_linefeed();

            mEscPos.feedpapercut();


        }else if(globalCategory.equals("Shirt")){

            Toast.makeText(this, "Print For Shirt", Toast.LENGTH_SHORT).show();
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

            mEscPos.justification_left();
            test="Customer Name : "+customerName.getText().toString();
            mEscPos.print_line(test);

            test="Customer Id : "+customerSerialNo.getText().toString();
            mEscPos.print_line(test);
            mEscPos.print_linefeed();

            test="Current Date : "+getDate();
            mEscPos.print_line(test);
            mEscPos.print_linefeed();

            test="Suit Type =  Shirt";
            mEscPos.print_line(test);
            mEscPos.print_linefeed();

            mEscPos.print_linefeed();

            mEscPos.justification_center();
            test=len +"  -  "+slev+ "  -  "+Shoulder+"  -  "+coler+"  -  "+chest+"  -  "+stomach;
            mEscPos.print_line(test);

            mEscPos.print_linefeed();
            mEscPos.justification_center();
            test="\t"+"-"+"\t"+"  -  "+"\t"+"  -  "+loosingStomach +"  -  "+loosingchest;
            mEscPos.print_line(test);

            mEscPos.print_linefeed();

            mEscPos.justification_center();


            if (pocketFront){
                test="Front Pocket : Yes";
                mEscPos.print_line(test);
                mEscPos.print_linefeed();
            }

            if (colerSimple){
                test="Coler : Simple";
                mEscPos.print_line(test);
                mEscPos.print_linefeed();
            }
            if (colerSherwani){
                test="Coler : Sherwani";
                mEscPos.print_line(test);
                mEscPos.print_linefeed();
            }
            if (colerHalfSherwani){
                test="Coler :  Half-Sherwani";
                mEscPos.print_line(test);
                mEscPos.print_linefeed();
            }


            mEscPos.justification_right();
            test=""+arms+"  -  "+wrist;
            mEscPos.print_line(test);

            mEscPos.print_linefeed();
            mEscPos.justification_left();
            test= "Description : "+des;
            mEscPos.print_line(test);
            mEscPos.print_linefeed();
            mEscPos.print_linefeed();

            mEscPos.feedpapercut();






        }else if(globalCategory.equals("Suit")){

           Toast.makeText(this, "Print For Suit", Toast.LENGTH_SHORT).show();

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

            mEscPos.justification_left();
            test="Customer Name : "+customerName.getText().toString();
            mEscPos.print_line(test);

            test="Customer Id : "+customerSerialNo.getText().toString();
            mEscPos.print_line(test);
            mEscPos.print_linefeed();

            test="Current Date : "+getDate();
            mEscPos.print_line(test);
            mEscPos.print_linefeed();

            test="Suit Type =  Suit";
            mEscPos.print_line(test);
            mEscPos.print_linefeed();

            mEscPos.print_linefeed();

            mEscPos.justification_center();
            test=len +"  -  "+slev+ "  -  "+Shoulder+"  -  "+coler+"  -  "+chest+"  -  "+stomach;
            mEscPos.print_line(test);

            mEscPos.print_linefeed();
            mEscPos.justification_center();
            test=pantLenght+"-"+pantBottom+"  -  "+loosingHip+"  -  "+loosingStomach +"  -  "+loosingchest;
            mEscPos.print_line(test);

            mEscPos.print_linefeed();

            mEscPos.justification_center();


            if (pocketFront){
                test="Front Pocket : Yes";
                mEscPos.print_line(test);
                mEscPos.print_linefeed();
            }
            if (pocketBSides){
                test="2 Sides Pocket : Yes ";
                mEscPos.print_line(test);
                mEscPos.print_linefeed();
            }

            if (colerSimple){
                test="Coler : Simple";
                mEscPos.print_line(test);
                mEscPos.print_linefeed();
            }
            if (colerSherwani){
                test="Coler : Sherwani";
                mEscPos.print_line(test);
                mEscPos.print_linefeed();
            }
            if (colerHalfSherwani){
                test="Coler :  Half-Sherwani";
                mEscPos.print_line(test);
                mEscPos.print_linefeed();
            }


            mEscPos.justification_right();
            test=""+arms+"  -  "+wrist;
            mEscPos.print_line(test);

            mEscPos.print_linefeed();
            mEscPos.justification_left();
            test= "Description : "+des;
            mEscPos.print_line(test);
            mEscPos.print_linefeed();
            mEscPos.print_linefeed();

            mEscPos.feedpapercut();







        }else if(globalCategory.equals("Saffari Coat")){
            Toast.makeText(this, "Print For Saffari Coat ", Toast.LENGTH_SHORT).show();

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

            mEscPos.justification_left();
            test="Customer Name : "+customerName.getText().toString();
            mEscPos.print_line(test);

            test="Customer Id : "+customerSerialNo.getText().toString();
            mEscPos.print_line(test);
            mEscPos.print_linefeed();


            test="Current Date : "+getDate();
            mEscPos.print_line(test);
            mEscPos.print_linefeed();

            test="Suit Type =  Saffari Coat";
            mEscPos.print_line(test);
            mEscPos.print_linefeed();

            test="Saffari Coat Length : "+len;
            mEscPos.print_line(test);

            test="Sleeves : "+slev;
            mEscPos.print_line(test);

            test="Shoulder : "+Shoulder;
            mEscPos.print_line(test);

            test="Coler : "+coler;
            mEscPos.print_line(test);

            test="Chest : "+chest;
            mEscPos.print_line(test);

            test="Stomach : "+stomach;
            mEscPos.print_line(test);

            test="hip Size : "+hipSize;
            mEscPos.print_line(test);

            test="Arms : "+arms;
            mEscPos.print_line(test);

            test="Wrsit : "+wrist;
            mEscPos.print_line(test);

            test="pant Lenght  : "+pantLenght;
            mEscPos.print_line(test);

            test="pant Bottom  : "+pantBottom;
            mEscPos.print_line(test);

            test="Waist  : "+waist;
            mEscPos.print_line(test);

            test="Thigh  : "+thigh;
            mEscPos.print_line(test);

            test= "Description : "+des;
            mEscPos.print_line(test);
            mEscPos.print_linefeed();
            mEscPos.print_linefeed();
            mEscPos.feedpapercut();




        }else if(globalCategory.equals("Three Piece")){
            Toast.makeText(this, "Print For Three-Piece ", Toast.LENGTH_SHORT).show();


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

            mEscPos.justification_left();
            test="Customer Name : "+customerName.getText().toString();
            mEscPos.print_line(test);

            test="Customer Id : "+customerSerialNo.getText().toString();
            mEscPos.print_line(test);
            mEscPos.print_linefeed();

            test="Current Date : "+getDate();
            mEscPos.print_line(test);
            mEscPos.print_linefeed();


            test="Suit Type =  Three Piece";
            mEscPos.print_line(test);
            mEscPos.print_linefeed();

            test="Coat Length : "+len;
            mEscPos.print_line(test);

            test="Sleeves : "+slev;
            mEscPos.print_line(test);

            test="Shoulder : "+Shoulder;
            mEscPos.print_line(test);

            test="Coler : "+coler;
            mEscPos.print_line(test);

            test="Chest : "+chest;
            mEscPos.print_line(test);

            test="Stomach : "+stomach;
            mEscPos.print_line(test);

            test="hip Size : "+hipSize;
            mEscPos.print_line(test);

            test="Arms : "+arms;
            mEscPos.print_line(test);

            test="Wrsit : "+wrist;
            mEscPos.print_line(test);

            test="pant Lenght  : "+pantLenght;
            mEscPos.print_line(test);

            test="pant Bottom  : "+pantBottom;
            mEscPos.print_line(test);

            test="Waist  : "+waist;
            mEscPos.print_line(test);

            test="Thigh  : "+thigh;
            mEscPos.print_line(test);

            test= "Description : "+des;
            mEscPos.print_line(test);
            mEscPos.print_linefeed();
            mEscPos.print_linefeed();
            mEscPos.feedpapercut();



        }else if(globalCategory.equals("Pant")){

            Toast.makeText(this, "Print For Pant ", Toast.LENGTH_SHORT).show();

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

            mEscPos.justification_left();
            test="Customer Name : "+customerName.getText().toString();
            mEscPos.print_line(test);

            test="Customer Id : "+customerSerialNo.getText().toString();
            mEscPos.print_line(test);
            mEscPos.print_linefeed();

            test="Current Date : "+getDate();
            mEscPos.print_line(test);
            mEscPos.print_linefeed();

            test="Suit Type =  Pant";
            mEscPos.print_line(test);
            mEscPos.print_linefeed();

            test="Hip Size : "+hipSize;
            mEscPos.print_line(test);

            test="Pant Length : "+pantLenght;
            mEscPos.print_line(test);

            test="Pant Bottom : "+pantBottom;
            mEscPos.print_line(test);

            test="Wais : "+waist;
            mEscPos.print_line(test);

            test="Thigh : "+thigh;
            mEscPos.print_line(test);

            test= "Description : "+des;
            mEscPos.print_line(test);
            mEscPos.print_linefeed();
            mEscPos.print_linefeed();
            mEscPos.feedpapercut();






        }else if(globalCategory.equals("Waist Coat")){
            Toast.makeText(this, "Print For  Waist Coat ", Toast.LENGTH_SHORT).show();

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

            mEscPos.justification_left();
            test="Customer Name : "+customerName.getText().toString();
            mEscPos.print_line(test);

            test="Customer Id : "+customerSerialNo.getText().toString();
            mEscPos.print_line(test);
            mEscPos.print_linefeed();

            test="Current Date : "+getDate();
            mEscPos.print_line(test);
            mEscPos.print_linefeed();

            test="Suit Type =  Waist Coat";
            mEscPos.print_line(test);
            mEscPos.print_linefeed();

            test="Waist Coat Length : "+len;
            mEscPos.print_line(test);

            test="Shoulder : "+Shoulder;
            mEscPos.print_line(test);

            test="Coler : "+coler;
            mEscPos.print_line(test);

            test="Chest : "+chest;
            mEscPos.print_line(test);

            test="Stomach : "+stomach;
            mEscPos.print_line(test);

            test="Loosing Chest: "+loosingchest;
            mEscPos.print_line(test);

            test="Loosing Stomach: "+loosingStomach;
            mEscPos.print_line(test);

            test= "Description : "+des;
            mEscPos.print_line(test);
            mEscPos.print_linefeed();
            mEscPos.print_linefeed();
            mEscPos.feedpapercut();


        }





    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        SelectedCategory = parent.getItemAtPosition(position).toString();

        globalCategory=SelectedCategory;

        if (SelectedCategory.equals("Kurta")){

            checkBoxcolr_halfSherwani.setChecked(false);
            checkBoxcolr_sherwani.setChecked(false);
            checkBoxcolr_simple.setChecked(false);
            checkBoxPocket_bothSides.setChecked(false);
            checkBoxPocket_front.setChecked(false);


            // Show these Text View
            etLenght.setHint("Kameez Length     قمیض کی لمبائی");
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

            checkBoxPocket_bothSides.setVisibility(View.VISIBLE);
            checkBoxcolr_sherwani.setVisibility(View.VISIBLE);
            checkBoxcolr_halfSherwani.setVisibility(View.VISIBLE);
            checkBoxcolr_simple.setVisibility(View.VISIBLE);
            checkBoxPocket_front.setVisibility(View.VISIBLE);


            textViewpocketStyle.setVisibility(View.VISIBLE);
            textViewColerInfo.setVisibility(View.VISIBLE);





            etpentlength.setHint("Shalwar Length        شلوار کی لمبائی");
            etpentlength.setVisibility(View.VISIBLE);

            etpentbottom.setHint("Shalwar Bottom        شلوار نیچے");
            etpentbottom.setVisibility(View.VISIBLE);

            etDescription.setVisibility(View.VISIBLE);

            // hide these TextBoxes
            etthigh.setVisibility(GONE);
            etwaist.setVisibility(GONE);

            databaseReference.child(customerSerialNo.getText().toString()).child("Kurta").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    if (dataSnapshot.exists()){

                        String length= dataSnapshot.child("length").getValue().toString();
                        String sleeve=  dataSnapshot.child("Sleeves").getValue().toString();
                        String shoulder= dataSnapshot.child("Shoulder").getValue().toString();
                        String colr= dataSnapshot.child("coler").getValue().toString();
                        String chest= dataSnapshot.child("chest").getValue().toString();
                        String stomachsize=  dataSnapshot.child("stomachSize").getValue().toString();
                        String hipsize=dataSnapshot.child("hipSize").getValue().toString();
                        String arms= dataSnapshot.child("armsSize").getValue().toString();
                        String wristSize= dataSnapshot.child("wristSize").getValue().toString();
                        String loosingchest=dataSnapshot.child("loosingChest").getValue().toString();
                        String loosingstomach= dataSnapshot.child("loosingStomach").getValue().toString();
                        String loosinghip=dataSnapshot.child("loosingHip").getValue().toString();
                        String pentLenght=dataSnapshot.child("pantLength").getValue().toString();
                        String pentbottom=dataSnapshot.child("pantBottom").getValue().toString();
                        String desciption=dataSnapshot.child("des").getValue().toString();

                        String colerStyle=dataSnapshot.child("colerStyle").getValue().toString();
                        String frontPocket=dataSnapshot.child("frontPocket").getValue().toString();
                        String bothSidesPocket=dataSnapshot.child("pocketBothSides").getValue().toString();

                        // Show these Text View
                        etLenght.getEditText().setText(length);
                        etSleeves.getEditText().setText(sleeve);
                        etShoulder.getEditText().setText(shoulder);
                        etcolr.getEditText().setText(colr);
                        etchest.getEditText().setText(chest);
                        etstomachSize.getEditText().setText(stomachsize);
                        ethipSize.getEditText().setText(hipsize);
                        etarms.getEditText().setText(arms);
                        etwrist.getEditText().setText(wristSize);
                        etloosingchest.getEditText().setText(loosingchest);
                        etloosingstomach.getEditText().setText(loosingstomach);
                        etloosinghip.getEditText().setText(loosinghip);
                        etpentlength.getEditText().setText(pentLenght);
                        etpentbottom.getEditText().setText(pentbottom);
                        etDescription.getEditText().setText(desciption);

                        // set check if contain value

                        if (colerStyle.equals("half Sherwani")){
                            checkBoxcolr_halfSherwani.setChecked(true);
                        }
                         if (colerStyle.equals("simple")){
                            checkBoxcolr_simple.setChecked(true);
                        }
                         if (colerStyle.equals("Sherwani")){
                            checkBoxcolr_sherwani.setChecked(true);
                        }

                        if (frontPocket.equals("yes")){
                            checkBoxPocket_front.setChecked(true);
                        }
                         if (bothSidesPocket.equals("yes")){
                            checkBoxPocket_bothSides.setChecked(true);
                        }
                         if (frontPocket.equals("yes") && bothSidesPocket.equals("yes")){
                            checkBoxPocket_front.setChecked(true);
                            checkBoxPocket_bothSides.setChecked(true);
                        }



                    }
                    else{

                        etLenght.getEditText().setText("");
                        etSleeves.getEditText().setText("");
                        etShoulder.getEditText().setText("");
                        etcolr.getEditText().setText("");
                        etchest.getEditText().setText("");
                        etstomachSize.getEditText().setText("");
                        ethipSize.getEditText().setText("");
                        etarms.getEditText().setText("");
                        etwrist.getEditText().setText("");
                        etloosingchest.getEditText().setText("");
                        etloosingstomach.getEditText().setText("");
                        etloosinghip.getEditText().setText("");
                        etpentlength.getEditText().setText("");
                        etpentbottom.getEditText().setText("");
                        etDescription.getEditText().setText("");

                        checkBoxcolr_halfSherwani.setChecked(false);
                        checkBoxcolr_sherwani.setChecked(false);
                        checkBoxcolr_simple.setChecked(false);
                        checkBoxPocket_bothSides.setChecked(false);
                        checkBoxPocket_front.setChecked(false);

                    }



                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



        }
        else if(SelectedCategory.equals(("Shirt"))){

            checkBoxcolr_halfSherwani.setChecked(false);
            checkBoxcolr_sherwani.setChecked(false);
            checkBoxcolr_simple.setChecked(false);
            checkBoxPocket_front.setChecked(false);

            // showText Views

            etLenght.setHint("Shirt Length      شرٹ کی لمبائی");
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

            checkBoxcolr_sherwani.setVisibility(View.VISIBLE);
            checkBoxcolr_halfSherwani.setVisibility(View.VISIBLE);
            checkBoxcolr_simple.setVisibility(View.VISIBLE);
            checkBoxPocket_front.setVisibility(View.VISIBLE);


            textViewpocketStyle.setVisibility(View.VISIBLE);
            textViewColerInfo.setVisibility(View.VISIBLE);




            //hide EditText
            ethipSize.setVisibility(GONE);
            etloosinghip.setVisibility(GONE);
            etpentbottom.setVisibility(GONE);
            etpentlength.setVisibility(GONE);
            etwaist.setVisibility(GONE);
            etthigh.setVisibility(GONE);

            checkBoxPocket_bothSides.setVisibility(GONE);

            databaseReference.child(customerSerialNo.getText().toString()).child("Shirt").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()){

                        String length=dataSnapshot.child("length").getValue().toString();
                        String shoulder= dataSnapshot.child("Shoulder").getValue().toString();
                        String Sleeves=dataSnapshot.child("Sleeves").getValue().toString();
                        String colr=dataSnapshot.child("coler").getValue().toString();
                        String chest=dataSnapshot.child("chest").getValue().toString();
                        String stomachSize=dataSnapshot.child("stomachSize").getValue().toString();
                        String armsSize=dataSnapshot.child("armsSize").getValue().toString();
                        String wrist=dataSnapshot.child("wristSize").getValue().toString();
                        String loosingChest=dataSnapshot.child("loosingChest").getValue().toString();
                        String loosingStomach=dataSnapshot.child("loosingStomach").getValue().toString();
                        String description=dataSnapshot.child("des").getValue().toString();

                        String colerStyle=dataSnapshot.child("colerStyle").getValue().toString();
                        String frontPocket=dataSnapshot.child("frontPocket").getValue().toString();

                        etLenght.getEditText().setText(length);
                        etSleeves.getEditText().setText(Sleeves);
                        etShoulder.getEditText().setText(shoulder);
                        etcolr.getEditText().setText(colr);
                        etchest.getEditText().setText(chest);
                        etstomachSize.getEditText().setText(stomachSize);
                        etarms.getEditText().setText(armsSize);
                        etwrist.getEditText().setText(wrist);
                        etloosingchest.getEditText().setText(loosingChest);
                        etloosingstomach.getEditText().setText(loosingStomach);
                        etDescription.getEditText().setText(description);

                        // set check if contain value

                        if (colerStyle.equals("half Sherwani")){
                            checkBoxcolr_halfSherwani.setChecked(true);
                        }
                        if (colerStyle.equals("simple")){
                            checkBoxcolr_simple.setChecked(true);
                        }
                        if (colerStyle.equals("Sherwani")){
                            checkBoxcolr_sherwani.setChecked(true);
                        }

                        if (frontPocket.equals("yes")){
                            checkBoxPocket_front.setChecked(true);
                        }


                    }
                    else{
                        etLenght.getEditText().setText("");
                        etSleeves.getEditText().setText("");
                        etShoulder.getEditText().setText("");
                        etcolr.getEditText().setText("");
                        etchest.getEditText().setText("");
                        etstomachSize.getEditText().setText("");
                        etarms.getEditText().setText("");
                        etwrist.getEditText().setText("");
                        etloosingchest.getEditText().setText("");
                        etloosingstomach.getEditText().setText("");
                        etDescription.getEditText().setText("");

                        checkBoxcolr_halfSherwani.setChecked(false);
                        checkBoxcolr_sherwani.setChecked(false);
                        checkBoxcolr_simple.setChecked(false);
                        checkBoxPocket_front.setChecked(false);
                    }



                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });




        }
        else if(SelectedCategory.equals(("Three Piece"))){

            //showing EditText

            etLenght.setHint("Coat Length       کوٹ کی لمبائی");
            etLenght.setVisibility(View.VISIBLE);
            etSleeves.setVisibility(View.VISIBLE);
            etShoulder.setVisibility(View.VISIBLE);
            etcolr.setVisibility(View.VISIBLE);
            etchest.setVisibility(View.VISIBLE);
            etstomachSize.setVisibility(View.VISIBLE);
            ethipSize.setVisibility(View.VISIBLE);
            etwrist.setVisibility(View.VISIBLE);

            etpentlength.setHint("pant Length       پینٹ لمبائی");
            etpentlength.setVisibility(View.VISIBLE);

            etpentbottom.setHint("pant Bottom       پینٹ نیچے");
            etpentbottom.setVisibility(View.VISIBLE);
            etthigh.setVisibility(View.VISIBLE);
            etarms.setVisibility(View.VISIBLE);
            etwaist.setVisibility(View.VISIBLE);
            etDescription.setVisibility(View.VISIBLE);



            //hiding EditText
            etloosingchest.setVisibility(GONE);
            etloosingstomach.setVisibility(GONE);
            etloosinghip.setVisibility(GONE);

            checkBoxPocket_bothSides.setVisibility(GONE);
            checkBoxcolr_sherwani.setVisibility(GONE);
            checkBoxcolr_halfSherwani.setVisibility(GONE);
            checkBoxcolr_simple.setVisibility(GONE);
            checkBoxPocket_front.setVisibility(GONE);


            textViewpocketStyle.setVisibility(GONE);
            textViewColerInfo.setVisibility(GONE);



            databaseReference.child(customerSerialNo.getText().toString()).child("Three Piece").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()){


                        String shoulder = dataSnapshot.child("Shoulder").getValue().toString();
                        String Slev = dataSnapshot.child("Sleeves").getValue().toString();
                        String arms = dataSnapshot.child("arms").getValue().toString();
                        String chest = dataSnapshot.child("chest").getValue().toString();
                        String colr = dataSnapshot.child("coler").getValue().toString();
                        String des = dataSnapshot.child("des").getValue().toString();
                        String hipSize = dataSnapshot.child("hipSize").getValue().toString();
                        String length = dataSnapshot.child("length").getValue().toString();
                        String pantBottom = dataSnapshot.child("pantBottom").getValue().toString();
                        String pantLength = dataSnapshot.child("pantLength").getValue().toString();
                        String stomachSize = dataSnapshot.child("stomachSize").getValue().toString();
                        String thigh = dataSnapshot.child("thigh").getValue().toString();
                        String waist = dataSnapshot.child("waist").getValue().toString();
                        String wristSize = dataSnapshot.child("wristSize").getValue().toString();


                        etLenght.getEditText().setText(length);
                        etSleeves.getEditText().setText(Slev);
                        etShoulder.getEditText().setText(shoulder);
                        etcolr.getEditText().setText(colr);
                        etchest.getEditText().setText(chest);
                        etstomachSize.getEditText().setText(stomachSize);
                        ethipSize.getEditText().setText(hipSize);
                        etwrist.getEditText().setText(wristSize);
                        etpentlength.getEditText().setText(pantLength);
                        etpentbottom.getEditText().setText(pantBottom);
                        etarms.getEditText().setText(arms);
                        etthigh.getEditText().setText(thigh);
                        etwaist.getEditText().setText(waist);
                        etDescription.getEditText().setText(des);
                    }
                    else{

                        etLenght.getEditText().setText("");
                        etSleeves.getEditText().setText("");
                        etShoulder.getEditText().setText("");
                        etcolr.getEditText().setText("");
                        etchest.getEditText().setText("");
                        etstomachSize.getEditText().setText("");
                        ethipSize.getEditText().setText("");
                        etarms.getEditText().setText("");
                        etwrist.getEditText().setText("");
                        etpentlength.getEditText().setText("");
                        etpentbottom.getEditText().setText("");
                        etthigh.getEditText().setText("");
                        etwaist.getEditText().setText("");
                        etDescription.getEditText().setText("");

                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });






        }
        else if(SelectedCategory.equals(("Waist Coat"))){

            //showing editText
            etLenght.setHint("Waist Coat Length     کمر کوٹ کی لمبائی");
            etLenght.setVisibility(View.VISIBLE);
            etShoulder.setVisibility(View.VISIBLE);
            etcolr.setVisibility(View.VISIBLE);
            etchest.setVisibility(View.VISIBLE);
            etstomachSize.setVisibility(View.VISIBLE);
            etloosingchest.setVisibility(View.VISIBLE);
            etloosingstomach.setVisibility(View.VISIBLE);
            etDescription.setVisibility(View.VISIBLE);


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

            checkBoxPocket_bothSides.setVisibility(GONE);
            checkBoxcolr_sherwani.setVisibility(GONE);
            checkBoxcolr_halfSherwani.setVisibility(GONE);
            checkBoxcolr_simple.setVisibility(GONE);
            checkBoxPocket_front.setVisibility(GONE);


            textViewpocketStyle.setVisibility(GONE);
            textViewColerInfo.setVisibility(GONE);


            databaseReference.child(customerSerialNo.getText().toString()).child("Waist Coat").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()){


                        String chest=dataSnapshot.child("chest").getValue().toString();
                        String coler=dataSnapshot.child("coler").getValue().toString();
                        String des=dataSnapshot.child("des").getValue().toString();
                        String length=dataSnapshot.child("length").getValue().toString();
                        String loosingChest=dataSnapshot.child("loosingChest").getValue().toString();
                        String loosingStomach=dataSnapshot.child("loosingStomach").getValue().toString();
                        String shoulder=dataSnapshot.child("shoulder").getValue().toString();
                        String stomachSize=dataSnapshot.child("stomachSize").getValue().toString();


                        etLenght.getEditText().setText(length);
                        etShoulder.getEditText().setText(shoulder);
                        etcolr.getEditText().setText(coler);
                        etchest.getEditText().setText(chest);
                        etstomachSize.getEditText().setText(stomachSize);
                        etloosingchest.getEditText().setText(loosingChest);
                        etloosingstomach.getEditText().setText(loosingStomach);
                        etDescription.getEditText().setText(des);


                    }
                    else{

                        etLenght.getEditText().setText("");
                        etShoulder.getEditText().setText("");
                        etcolr.getEditText().setText("");
                        etchest.getEditText().setText("");
                        etstomachSize.getEditText().setText("");
                        etloosingchest.getEditText().setText("");
                        etloosingstomach.getEditText().setText("");
                        etDescription.getEditText().setText("");


                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });







        }
        else if(SelectedCategory.equals(("Pant"))){

            // showing textViews
            etwaist.setVisibility(View.VISIBLE);
            etthigh.setVisibility(View.VISIBLE);
            ethipSize.setVisibility(View.VISIBLE);
            etDescription.setVisibility(View.VISIBLE);

            etpentlength.setHint("Pant Length      پینٹ لمبائی");
            etpentlength.setVisibility(View.VISIBLE);

            etpentbottom.setHint("Pant Bottom       پینٹ نیچے");
            etpentbottom.setVisibility(View.VISIBLE);


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


            checkBoxPocket_bothSides.setVisibility(GONE);
            checkBoxcolr_sherwani.setVisibility(GONE);
            checkBoxcolr_halfSherwani.setVisibility(GONE);
            checkBoxcolr_simple.setVisibility(GONE);
            checkBoxPocket_front.setVisibility(GONE);


            textViewpocketStyle.setVisibility(GONE);
            textViewColerInfo.setVisibility(GONE);

            databaseReference.child(customerSerialNo.getText().toString()).child("Pant").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()){

                        String des=dataSnapshot.child("des").getValue().toString();
                        String hipSize=dataSnapshot.child("hipSize").getValue().toString();
                        String pantBottom=dataSnapshot.child("pantBottom").getValue().toString();
                        String pantLenght=dataSnapshot.child("pantLength").getValue().toString();
                        String thigh=dataSnapshot.child("thigh").getValue().toString();
                        String waist=dataSnapshot.child("waist").getValue().toString();


                        etwaist.getEditText().setText(waist);
                        etthigh.getEditText().setText(thigh);
                        ethipSize.getEditText().setText(hipSize);
                        etDescription.getEditText().setText(des);
                        etpentlength.getEditText().setText(pantLenght);
                        etpentbottom.getEditText().setText(pantBottom);


                    }
                    else{
                        etwaist.getEditText().setText("");
                        etthigh.getEditText().setText("");
                        ethipSize.getEditText().setText("");
                        etDescription.getEditText().setText("");
                        etpentlength.getEditText().setText("");
                        etpentbottom.getEditText().setText("");

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });





        }
        else if(SelectedCategory.equals(("Saffari Coat"))){

            //showing EditText

            etLenght.setHint("Saffari Coat Length       سفاری کوٹ کی لمبائی");
            etLenght.setVisibility(View.VISIBLE);
            etSleeves.setVisibility(View.VISIBLE);
            etShoulder.setVisibility(View.VISIBLE);
            etcolr.setVisibility(View.VISIBLE);
            etchest.setVisibility(View.VISIBLE);
            etstomachSize.setVisibility(View.VISIBLE);
            ethipSize.setVisibility(View.VISIBLE);
            etwrist.setVisibility(View.VISIBLE);

            etpentlength.setHint("pant Length       پینٹ لمبائی");
            etpentlength.setVisibility(View.VISIBLE);

            etpentbottom.setHint("pant Bottom       پینٹ نیچے");
            etpentbottom.setVisibility(View.VISIBLE);
            etthigh.setVisibility(View.VISIBLE);

            etwaist.setVisibility(View.VISIBLE);
            etDescription.setVisibility(View.VISIBLE);
            etarms.setVisibility(View.VISIBLE);


            //hiding EditText
            etloosingchest.setVisibility(GONE);
            etloosingstomach.setVisibility(GONE);
            etloosinghip.setVisibility(GONE);


            checkBoxPocket_bothSides.setVisibility(GONE);
            checkBoxcolr_sherwani.setVisibility(GONE);
            checkBoxcolr_halfSherwani.setVisibility(GONE);
            checkBoxcolr_simple.setVisibility(GONE);
            checkBoxPocket_front.setVisibility(GONE);


            textViewpocketStyle.setVisibility(GONE);
            textViewColerInfo.setVisibility(GONE);


            databaseReference.child(customerSerialNo.getText().toString()).child("Saffari Coat").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()){

                       String shoulder= dataSnapshot.child("Shoulder").getValue().toString();
                       String Slev=dataSnapshot.child("Sleeves").getValue().toString();
                       String arms=dataSnapshot.child("arms").getValue().toString();
                       String chest=dataSnapshot.child("chest").getValue().toString();
                       String coler=dataSnapshot.child("coler").getValue().toString();
                       String des=dataSnapshot.child("des").getValue().toString();
                       String hipSize=dataSnapshot.child("hipSize").getValue().toString();
                       String length=dataSnapshot.child("length").getValue().toString();
                       String pantBottom=dataSnapshot.child("pantBottom").getValue().toString();
                       String pantLength=dataSnapshot.child("pantLength").getValue().toString();
                       String stomachSize=dataSnapshot.child("stomachSize").getValue().toString();
                       String thigh =dataSnapshot.child("thigh").getValue().toString();
                       String waist=dataSnapshot.child("waist").getValue().toString();
                       String wristSize=dataSnapshot.child("wristSize").getValue().toString();


                        etLenght.getEditText().setText(length);
                        etSleeves.getEditText().setText(Slev);
                        etShoulder.getEditText().setText(shoulder);
                        etcolr.getEditText().setText(coler);
                        etchest.getEditText().setText(chest);
                        etstomachSize.getEditText().setText(stomachSize);
                        ethipSize.getEditText().setText(hipSize);
                        etwrist.getEditText().setText(wristSize);
                        etpentlength.getEditText().setText(pantLength);
                        etpentbottom.getEditText().setText(pantBottom);
                        etthigh.getEditText().setText(thigh);
                        etarms.getEditText().setText(arms);
                        etwaist.getEditText().setText(waist);
                        etDescription.getEditText().setText(des);


                    }
                    else{

                        etLenght.getEditText().setText("");
                        etarms.getEditText().setText("");
                        etSleeves.getEditText().setText("");
                        etShoulder.getEditText().setText("");
                        etcolr.getEditText().setText("");
                        etchest.getEditText().setText("");
                        etstomachSize.getEditText().setText("");
                        ethipSize.getEditText().setText("");
                        etwrist.getEditText().setText("");
                        etpentlength.getEditText().setText("");
                        etpentbottom.getEditText().setText("");
                        etthigh.getEditText().setText("");
                        ethipSize.getEditText().setText("");
                        etwaist.getEditText().setText("");
                        etDescription.getEditText().setText("");

                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });






        }
        else if (SelectedCategory.equals("Suit")){



            checkBoxcolr_halfSherwani.setChecked(false);
            checkBoxcolr_sherwani.setChecked(false);
            checkBoxcolr_simple.setChecked(false);
            checkBoxPocket_bothSides.setChecked(false);
            checkBoxPocket_front.setChecked(false);


            //showing EditText

            etLenght.setHint("Kameez Length     قمیض کی لمبائی");
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
            etDescription.setVisibility(View.VISIBLE);

            etpentlength.setHint("Shalwar Length        سلور لمبائی");
            etpentlength.setVisibility(View.VISIBLE);

            etpentbottom.setHint("Shalwar Bottom        شلوار نیچے");
            etpentbottom.setVisibility(View.VISIBLE);


            checkBoxPocket_bothSides.setVisibility(View.VISIBLE);
            checkBoxcolr_sherwani.setVisibility(View.VISIBLE);
            checkBoxcolr_halfSherwani.setVisibility(View.VISIBLE);
            checkBoxcolr_simple.setVisibility(View.VISIBLE);
            checkBoxPocket_front.setVisibility(View.VISIBLE);


            textViewpocketStyle.setVisibility(View.VISIBLE);
            textViewColerInfo.setVisibility(View.VISIBLE);




            // hiding the Edit Text
            etwaist.setVisibility(GONE);
            etthigh.setVisibility(GONE);


            databaseReference.child(customerSerialNo.getText().toString()).child("Suit").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()){


                     String shoulder=dataSnapshot.child("Shoulder").getValue().toString();
                     String Sleeves=dataSnapshot.child("Sleeves").getValue().toString();
                     String armSize=dataSnapshot.child("armsSize").getValue().toString();
                     String chest=dataSnapshot.child("chest").getValue().toString();
                     String coler=dataSnapshot.child("coler").getValue().toString();
                     String des=dataSnapshot.child("des").getValue().toString();
                     String hipSize=dataSnapshot.child("hipSize").getValue().toString();
                     String length=dataSnapshot.child("length").getValue().toString();
                     String loosingChest=dataSnapshot.child("loosingChest").getValue().toString();
                     String loosingHip=dataSnapshot.child("loosingHip").getValue().toString();
                     String loosingStomach=dataSnapshot.child("loosingStomach").getValue().toString();
                     String pantBottom=dataSnapshot.child("pantBottom").getValue().toString();
                     String pantLength=dataSnapshot.child("pantLength").getValue().toString();
                     String stomachSize=dataSnapshot.child("stomachSize").getValue().toString();
                     String wrsitSize=dataSnapshot.child("wristSize").getValue().toString();

                        String colerStyle=dataSnapshot.child("colerStyle").getValue().toString();
                        String frontPocket=dataSnapshot.child("frontPocket").getValue().toString();
                        String bothSidesPocket=dataSnapshot.child("pocketBothSides").getValue().toString();


                        etLenght.getEditText().setText(length);
                        etSleeves.getEditText().setText(Sleeves);
                        etShoulder.getEditText().setText(shoulder);
                        etcolr.getEditText().setText(coler);
                        etchest.getEditText().setText(chest);
                        etstomachSize.getEditText().setText(stomachSize);
                        ethipSize.getEditText().setText(hipSize);
                        etarms.getEditText().setText(armSize);
                        etwrist.getEditText().setText(wrsitSize);
                        etloosinghip.getEditText().setText(loosingHip);
                        etloosingchest.getEditText().setText(loosingChest);
                        etloosingstomach.getEditText().setText(loosingStomach);
                        etDescription.getEditText().setText(des);
                        etpentlength.getEditText().setText(pantLength);
                        etpentbottom.getEditText().setText(pantBottom);


                        // set check if contain value

                        if (colerStyle.equals("half Sherwani")) {
                            checkBoxcolr_halfSherwani.setChecked(true);
                        }
                        if (colerStyle.equals("simple")) {
                            checkBoxcolr_simple.setChecked(true);
                        }
                        if (colerStyle.equals("Sherwani")) {
                            checkBoxcolr_sherwani.setChecked(true);
                        }
                        if (frontPocket.equals("yes")) {
                            checkBoxPocket_front.setChecked(true);
                        }
                        if (bothSidesPocket.equals("yes")) {
                            checkBoxPocket_bothSides.setChecked(true);
                        }
                        if (frontPocket.equals("yes") && bothSidesPocket.equals("yes")) {
                            checkBoxPocket_front.setChecked(true);
                            checkBoxPocket_bothSides.setChecked(true);
                        }





                    }
                    else{

                        etLenght.getEditText().setText("");
                        etSleeves.getEditText().setText("");
                        etShoulder.getEditText().setText("");
                        etcolr.getEditText().setText("");
                        etchest.getEditText().setText("");
                        etstomachSize.getEditText().setText("");
                        ethipSize.getEditText().setText("");
                        etarms.getEditText().setText("");
                        etwrist.getEditText().setText("");
                        etloosinghip.getEditText().setText("");
                        etloosingchest.getEditText().setText("");
                        etloosingstomach.getEditText().setText("");
                        etDescription.getEditText().setText("");
                        etpentlength.getEditText().setText("");
                        etpentbottom.getEditText().setText("");

                        checkBoxcolr_halfSherwani.setChecked(false);
                        checkBoxcolr_sherwani.setChecked(false);
                        checkBoxcolr_simple.setChecked(false);
                        checkBoxPocket_bothSides.setChecked(false);
                        checkBoxPocket_front.setChecked(false);

                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



}
