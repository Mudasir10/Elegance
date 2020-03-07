package com.nanodevs.elegance.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.HashMap;
import java.util.Map;

import static android.view.View.GONE;

public class UpdateMeasurements extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText customerName, customerContact, customerSerialNo;
    private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Measurements");
    private Spinner spinner;

    private String SelectedCategory;
    private TextInputLayout etLenght, etShoulder, etSleeves, etcolr, etchest, etstomachSize, ethipSize, etarms, etwrist, etloosingchest, etloosingstomach,
            etloosinghip, etpentlength, etpentbottom, etwaist, etthigh, etDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_measurements);

        getSupportActionBar().setTitle("Update Activity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.reg_cus_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==R.id.btndoneSaveCustomer){

            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("Confirmation Message");
            builder.setMessage("Are You Sure You Want to Save This Measurements");
            builder.setCancelable(false);
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (isOnline()){

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
                        else{
                            Toast.makeText(UpdateMeasurements.this, "", Toast.LENGTH_SHORT).show();
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

        Measurements measurements=new Measurements();
        measurements.SetMeasurementsForSuit(length,Sleeves,shoulder,coler,chest,stomachSize,armsSize,wristSize,loosingChest,
                loosingStomach,des,HipSize,loosinHip,pentLength,pentBottom);

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
        measurements.SetMeasurementsForShirt(len,Sleeves,shoulder,colr,chest,stomachSize,arms,wrist,loosingChest,loosingstomach,des);

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

        Measurements measurements=new Measurements();
        measurements.SetMeasurementsForKurta(length,sleeve,shoulder,colr,chest,stomachsize,hipsize,
                arms,wrist,loosingchest,loosingstomach,loosinghip,pentLenght,pentbottom,desciption);

        Map<String,Object> data= measurements.KurtatoMap();
        UpdateCustomerMeasurement(customerSerialNo.getText().toString(),data,SelectedCategory);
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

        customerSerialNo=findViewById(R.id.customerSerialNo);
        customerName=findViewById(R.id.customerName);
        customerContact=findViewById(R.id.customerNumber);

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



    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        SelectedCategory = parent.getItemAtPosition(position).toString();

        if (SelectedCategory.equals("Kurta")){

            // Show these Text View
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


            etpentlength.setVisibility(View.VISIBLE);

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

                    }



                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



        }
        else if(SelectedCategory.equals(("Shirt"))){

            // showText Views

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

            //hide EditText
            ethipSize.setVisibility(GONE);
            etloosinghip.setVisibility(GONE);
            etpentbottom.setVisibility(GONE);
            etpentlength.setVisibility(GONE);
            etwaist.setVisibility(GONE);
            etthigh.setVisibility(GONE);


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
                    }



                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });




        }
        else if(SelectedCategory.equals(("Three Piece"))){

            //showing EditText

            etLenght.setVisibility(View.VISIBLE);
            etSleeves.setVisibility(View.VISIBLE);
            etShoulder.setVisibility(View.VISIBLE);
            etcolr.setVisibility(View.VISIBLE);
            etchest.setVisibility(View.VISIBLE);
            etstomachSize.setVisibility(View.VISIBLE);
            ethipSize.setVisibility(View.VISIBLE);
            etwrist.setVisibility(View.VISIBLE);
            etpentlength.setVisibility(View.VISIBLE);
            etpentbottom.setVisibility(View.VISIBLE);
            etthigh.setVisibility(View.VISIBLE);
            etarms.setVisibility(View.VISIBLE);
            etwaist.setVisibility(View.VISIBLE);
            etDescription.setVisibility(View.VISIBLE);



            //hiding EditText
            etloosingchest.setVisibility(GONE);
            etloosingstomach.setVisibility(GONE);
            etloosinghip.setVisibility(GONE);



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
            etpentlength.setVisibility(View.VISIBLE);
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

            etLenght.setVisibility(View.VISIBLE);
            etSleeves.setVisibility(View.VISIBLE);
            etShoulder.setVisibility(View.VISIBLE);
            etcolr.setVisibility(View.VISIBLE);
            etchest.setVisibility(View.VISIBLE);
            etstomachSize.setVisibility(View.VISIBLE);
            ethipSize.setVisibility(View.VISIBLE);
            etwrist.setVisibility(View.VISIBLE);
            etpentlength.setVisibility(View.VISIBLE);
            etpentbottom.setVisibility(View.VISIBLE);
            etthigh.setVisibility(View.VISIBLE);

            etwaist.setVisibility(View.VISIBLE);
            etDescription.setVisibility(View.VISIBLE);
            etarms.setVisibility(View.VISIBLE);


            //hiding EditText
            etloosingchest.setVisibility(GONE);
            etloosingstomach.setVisibility(GONE);
            etloosinghip.setVisibility(GONE);


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

            //showing EditText

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
            etpentlength.setVisibility(View.VISIBLE);
            etpentbottom.setVisibility(View.VISIBLE);

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
