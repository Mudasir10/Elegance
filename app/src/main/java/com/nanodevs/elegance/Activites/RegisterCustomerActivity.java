package com.nanodevs.elegance.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.os.Bundle;
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
import com.google.firebase.storage.FirebaseStorage;
import com.nanodevs.elegance.Pojo.Customer;
import com.nanodevs.elegance.Pojo.Measurements;
import com.nanodevs.elegance.R;

import java.util.HashMap;
import java.util.Map;

import static android.view.View.GONE;

public class RegisterCustomerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private DatabaseReference cusRef = FirebaseDatabase.getInstance().getReference("Customer");
    private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Measurements");
    private EditText customerName, customerContact, customerSerialNo;

    String Bs="no",Fp="no";
    String colerStyle="not defined";

    private TextView textViewpocketStyle,textViewColerInfo;


    private  CheckBox checkBoxPocket_bothSides,checkBoxPocket_front,checkBoxcolr_simple,checkBoxcolr_sherwani,checkBoxcolr_halfSherwani;


    private Spinner spinner;

    private long count;
    String SelectedCategory;

    TextInputLayout etLenght, etShoulder, etSleeves, etcolr, etchest, etstomachSize, ethipSize, etarms, etwrist, etloosingchest, etloosingstomach,
            etloosinghip, etpentlength, etpentbottom, etwaist, etthigh, etDescription;

    Toolbar mToolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_customer);

        mToolbar=findViewById(R.id.app_bar_register);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("Register Customer Activity");
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        initComponents();






    }

    @Override
    protected void onStart() {
        super.onStart();

        cusRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                count = dataSnapshot.getChildrenCount();

                if (count < 1) {
                    count = 1;
                    customerSerialNo.setText(String.valueOf(count));
                }
                else{
                    count = count + 1;
                    customerSerialNo.setText(String.valueOf(count));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void initComponents() {

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

        customerName = findViewById(R.id.customerName);
        customerContact = findViewById(R.id.customerNumber);
        customerSerialNo = findViewById(R.id.customerSerialNo);

        //init check boxes
        checkBoxPocket_bothSides = findViewById(R.id.checkboxPocket_both_sides);
        checkBoxPocket_front = findViewById(R.id.checkboxPocket_front_procket);
        checkBoxcolr_simple = findViewById(R.id.checkboxColer_simple);
        checkBoxcolr_sherwani = findViewById(R.id.checkboxColer_sherwani);
        checkBoxcolr_halfSherwani = findViewById(R.id.checkboxColer_half_sherwani);

        textViewColerInfo=findViewById(R.id.textColerStyle);
        textViewpocketStyle=findViewById(R.id.textPocketInfo);



        spinner = findViewById(R.id.spinnerCategory);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(RegisterCustomerActivity.this,
                R.array.suitCategories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);




        checkBoxPocket_bothSides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBoxPocket_bothSides.isChecked()){
                    Bs="yes";
                }
                if (!checkBoxPocket_bothSides.isChecked()){
                    Bs="no";
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


    }



    private boolean isOnline() {
        try {
            return Runtime.getRuntime().exec("/system/bin/ping -c 1 8.8.8.8").waitFor() == 0; //  "8.8.8.8" is the server to ping
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }




    private void UploadDataForSuitCategory(){

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

       if (!length.isEmpty() && !Sleeves.isEmpty() && !shoulder.isEmpty() && !coler.isEmpty() && !chest.isEmpty() && !stomachSize.isEmpty()
         && !HipSize.isEmpty() && !armsSize.isEmpty() && !wristSize.isEmpty() && !loosinHip.isEmpty() && !loosingChest.isEmpty()
         && !loosingStomach.isEmpty() && !des.isEmpty() && !pentLength.isEmpty() && !pentBottom.isEmpty()){

           Measurements measurements=new Measurements();
           measurements.SetMeasurementsForSuit(length,Sleeves,shoulder,coler,chest,stomachSize,armsSize,wristSize,loosingChest,loosingStomach
                   ,des,HipSize,loosinHip,pentLength,pentBottom,Fp,Bs,colerStyle);

           Map<String,Object> data= measurements.SuitToMap();
           saveCustomerData(customerSerialNo.getText().toString(),customerName.getText().toString(),customerContact.getText().toString(),data,SelectedCategory);


       }
       else{
           Toast.makeText(this, "Please Fill Measurements Properly", Toast.LENGTH_SHORT).show();
       }


    }




    private void uploadDataForKurtaCategory() {

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

        if (!length.isEmpty() && !sleeve.isEmpty() && !shoulder.isEmpty() && !colr.isEmpty() && !chest.isEmpty() && !stomachsize.isEmpty()
        && !hipsize.isEmpty() && !arms.isEmpty() && !wrist.isEmpty() && !loosingchest.isEmpty() && !loosinghip.isEmpty() && !pentLenght.isEmpty()
        && !pentbottom.isEmpty() && !desciption.isEmpty()){


            Measurements measurements=new Measurements();
            measurements.SetMeasurementsForKurta(length,sleeve,shoulder,colr,chest,stomachsize,hipsize,
                    arms,wrist,loosingchest,loosingstomach,loosinghip,pentLenght,pentbottom,desciption,Fp,Bs,colerStyle);

            Map<String,Object> data= measurements.KurtatoMap();
            saveCustomerData(customerSerialNo.getText().toString(),customerName.getText().toString(),customerContact.getText().toString(),data,SelectedCategory);


        }
        else{
            Toast.makeText(this, "Please Fill Measurements Properly", Toast.LENGTH_SHORT).show();
        }



    }


    private void saveCustomerData(String customerSerialNo, String customerName, String customerContact,
                               Map<String, Object> measurements, String SelectedCategory) {


        Customer customer = new Customer(Long.parseLong(customerSerialNo), customerName, customerContact);

        Map<String, Object> postValues = customer.toCustomerMap();
        final Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(cusRef.push().getKey(), postValues);

        Map<String,Object> data=new HashMap<>();
        data.put(customerSerialNo+"/"+SelectedCategory,measurements);

        databaseReference.updateChildren(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){
                    cusRef.updateChildren(childUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                finish();
                                Toast.makeText(RegisterCustomerActivity.this, "Customer Added SuccessFully ", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(RegisterCustomerActivity.this, "Task Failed", Toast.LENGTH_SHORT).show();
                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterCustomerActivity.this, "Task Failed : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                else{
                    Toast.makeText(RegisterCustomerActivity.this, "Failed To Upload", Toast.LENGTH_SHORT).show();
                }

            }
        });




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

                        if (SelectedCategory.equals("Kurta")) {
                            uploadDataForKurtaCategory();
                            dialog.dismiss();
                        } else if (SelectedCategory.equals("Shirt")) {
                            uploadDataForShirtCategory();
                            dialog.dismiss();
                        } else if (SelectedCategory.equals("Suit")) {
                            UploadDataForSuitCategory();
                            dialog.dismiss();
                        } else if (SelectedCategory.equals("Saffari Coat")) {
                            UploadDataForSaffariCoat();
                            dialog.dismiss();
                        } else if (SelectedCategory.equals("Three Piece")) {
                            UploadDataForThreePiece();
                            dialog.dismiss();
                        } else if (SelectedCategory.equals("Pant")) {
                            UploadDataForPant();
                            dialog.dismiss();
                        } else if (SelectedCategory.equals("Waist Coat")) {
                            UPloadDataForWaistCoat();
                            dialog.dismiss();
                        }

                    }
                    else{
                        Toast.makeText(RegisterCustomerActivity.this, "Can not Save Customer Without Internet", Toast.LENGTH_SHORT).show();
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

    private void UPloadDataForWaistCoat() {

       String len= etLenght.getEditText().getText().toString();
       String shoulder=  etShoulder.getEditText().getText().toString();
       String colr=  etcolr.getEditText().getText().toString();
       String chest=  etchest.getEditText().getText().toString();
       String stomachSize= etstomachSize.getEditText().getText().toString();
       String loosinChest= etloosingchest.getEditText().getText().toString();
       String loosinstomach= etloosingstomach.getEditText().getText().toString();
       String des=  etDescription.getEditText().getText().toString();

       if (!len.isEmpty() && !shoulder.isEmpty() && !colr.isEmpty() && !chest.isEmpty() && !stomachSize.isEmpty() && !loosinChest.isEmpty()
          && !loosinstomach.isEmpty() && !des.isEmpty()){

           Measurements measurements = new Measurements();
           measurements.SetMeasurementsForWaistCoat(len,shoulder,colr,chest,stomachSize,loosinChest,loosinstomach,des);

           Map<String, Object> data = measurements.WaistCoattToMap();
           saveCustomerData(customerSerialNo.getText().toString(), customerName.getText().toString(), customerContact.getText().toString(), data, SelectedCategory);

       }
       else{
           Toast.makeText(this, "Please Fill All the Information Properly.", Toast.LENGTH_SHORT).show();
       }



    }

    private void UploadDataForPant() {


      String waist= etwaist.getEditText().getText().toString();
      String thigh= etthigh.getEditText().getText().toString();
      String hipSize= ethipSize.getEditText().getText().toString();
      String des= etDescription.getEditText().getText().toString();
      String pentLength = etpentlength.getEditText().getText().toString();
      String pentBottom= etpentbottom.getEditText().getText().toString();



      if (!waist.isEmpty() && !thigh.isEmpty() && !hipSize.isEmpty() && !des.isEmpty() && !pentLength.isEmpty() && !pentBottom.isEmpty()){

          Measurements measurements = new Measurements();
          measurements.SetMeasurementsForPant(thigh,waist,hipSize,des,pentLength,pentBottom);
          Map<String, Object> data = measurements.PanttToMap();

          saveCustomerData(customerSerialNo.getText().toString(), customerName.getText().toString(), customerContact.getText().toString(), data, SelectedCategory);


      }
      else{
          Toast.makeText(this, "Please Fill All The Information Peroperly.", Toast.LENGTH_SHORT).show();
      }


    }

    private void UploadDataForThreePiece() {


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


       if (!len.isEmpty() && !sel.isEmpty() && !shoulder.isEmpty() && !colr.isEmpty() && !chest.isEmpty() && !stomach.isEmpty() && !hipSize.isEmpty()
        && !wristSize.isEmpty() && !pentLength.isEmpty() && !pentBottom.isEmpty() && !thigh.isEmpty() && !waist.isEmpty() && !des.isEmpty()
        && !arms.isEmpty()){

           Measurements measurements = new Measurements();
           measurements.SetMeasurementsForThreePiece(len,sel,shoulder,colr,chest,stomach,hipSize,wristSize,pentLength,pentBottom,thigh,waist,des,arms);

           Map<String, Object> data = measurements.ThreePiecetToMap();
           saveCustomerData(customerSerialNo.getText().toString(), customerName.getText().toString(), customerContact.getText().toString(), data, SelectedCategory);


       }
       else{
           Toast.makeText(this, "Please Fill All The Information Properly.", Toast.LENGTH_SHORT).show();
       }



    }

    private void UploadDataForSaffariCoat() {

        //showing EditText

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
        String arms=etarms.getEditText().getText().toString();


        if (!len.isEmpty() && !slev.isEmpty() && !shoulder.isEmpty() && !colr.isEmpty() && !chest.isEmpty() && !stomachSize.isEmpty() && !hipSize.isEmpty()
         && !wrist.isEmpty() && !pentLength.isEmpty() && !pentBottom.isEmpty() && !thigh.isEmpty() && !waist.isEmpty() && !des.isEmpty()
         && !arms.isEmpty()){

            Measurements measurements = new Measurements();
            measurements.SetMeasurementsForSaffariCoat(len, slev, shoulder, colr, chest, stomachSize, hipSize, wrist, pentLength, pentBottom, thigh, waist, des,arms);

            Map<String, Object> data = measurements.SaffariCoatToMap();
            saveCustomerData(customerSerialNo.getText().toString(), customerName.getText().toString(), customerContact.getText().toString(), data, SelectedCategory);

        }
        else{

            Toast.makeText(this, "Please Fill All The Information Properly.", Toast.LENGTH_SHORT).show();

        }




    }

    private void uploadDataForShirtCategory() {

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

       if (!len.isEmpty() && !Sleeves.isEmpty() && !shoulder.isEmpty() && !colr.isEmpty() && !chest.isEmpty() && !stomachSize.isEmpty()
        && !arms.isEmpty() && !wrist.isEmpty() && !loosingChest.isEmpty() && !loosingstomach.isEmpty() && !des.isEmpty()){

           Measurements measurements=new Measurements();
           measurements.SetMeasurementsForShirt(len,Sleeves,shoulder,colr,chest,stomachSize,arms,wrist,loosingChest,loosingstomach,des,Fp,colerStyle);

           Map<String,Object> data= measurements.ShirttoMap();
           saveCustomerData(customerSerialNo.getText().toString(),customerName.getText().toString(),customerContact.getText().toString(),data,SelectedCategory);


       }
       else{
           Toast.makeText(this, "Please Fill Measurements Properly", Toast.LENGTH_SHORT).show();
       }


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        SelectedCategory = parent.getItemAtPosition(position).toString();

        if (SelectedCategory.equals("Kurta")){

            // Show these Text View
            etLenght.setHint(" Kameez lenght        قمیض کی لمبائی");
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

            etpentbottom.setHint("Shalwar bottom        شلوار نیچے");
            etpentbottom.setVisibility(View.VISIBLE);

            etDescription.setVisibility(View.VISIBLE);

            // hide these TextBoxes
            etthigh.setVisibility(GONE);
            etwaist.setVisibility(GONE);
        }
        else if(SelectedCategory.equals(("Shirt"))){

            // showText Views
            etLenght.setHint("Shirt Lenght      شرٹ کی لمبائی");
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

        }
        else if(SelectedCategory.equals(("Three Piece"))){

            //showing EditText
            etLenght.setHint("Coat Lenght       کوٹ کی لمبائی");
            etLenght.setVisibility(View.VISIBLE);

            etSleeves.setVisibility(View.VISIBLE);
            etShoulder.setVisibility(View.VISIBLE);
            etcolr.setVisibility(View.VISIBLE);
            etchest.setVisibility(View.VISIBLE);
            etstomachSize.setVisibility(View.VISIBLE);
            ethipSize.setVisibility(View.VISIBLE);
            etwrist.setVisibility(View.VISIBLE);
            etarms.setVisibility(View.VISIBLE);
            etpentlength.setHint("pant Length       پینٹ لمبائی");
            etpentlength.setVisibility(View.VISIBLE);

            etpentbottom.setHint("pant Bottom       پینٹ نیچے");
            etpentbottom.setVisibility(View.VISIBLE);
            etthigh.setVisibility(View.VISIBLE);
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

        }
        else if(SelectedCategory.equals(("Pant"))){

            // showing textViews
            etwaist.setVisibility(View.VISIBLE);
            etthigh.setVisibility(View.VISIBLE);
            ethipSize.setVisibility(View.VISIBLE);
            etDescription.setVisibility(View.VISIBLE);
            etpentlength.setVisibility(View.VISIBLE);
            etpentlength.setHint("Pant Lenght        پینٹ لمبائی");
            etpentbottom.setVisibility(View.VISIBLE);
            etpentbottom.setHint("pant bottom       پینٹ نیچے");


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
        }
        else if(SelectedCategory.equals(("Saffari Coat"))){

            //showing EditText
            etLenght.setHint("Saffari Coat Lenght       سفاری کوٹ کی لمبائی");
            etLenght.setVisibility(View.VISIBLE);

            etSleeves.setVisibility(View.VISIBLE);
            etShoulder.setVisibility(View.VISIBLE);
            etcolr.setVisibility(View.VISIBLE);
            etchest.setVisibility(View.VISIBLE);
            etstomachSize.setVisibility(View.VISIBLE);
            etwrist.setVisibility(View.VISIBLE);

            etpentlength.setHint("pant Length       پینٹ لمبائی");
            etpentlength.setVisibility(View.VISIBLE);

            etpentbottom.setHint("pant Bottom       پینٹ نیچے");
            etpentbottom.setVisibility(View.VISIBLE);
            etthigh.setVisibility(View.VISIBLE);
            ethipSize.setVisibility(View.VISIBLE);
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
        }
        else if (SelectedCategory.equals("Suit")){

            //showing EditText
            etLenght.setHint("Kameez Lenght     قمیض کی لمبائی");
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

            checkBoxPocket_bothSides.setVisibility(View.VISIBLE);
            checkBoxcolr_sherwani.setVisibility(View.VISIBLE);
            checkBoxcolr_halfSherwani.setVisibility(View.VISIBLE);
            checkBoxcolr_simple.setVisibility(View.VISIBLE);
            checkBoxPocket_front.setVisibility(View.VISIBLE);

            textViewpocketStyle.setVisibility(View.VISIBLE);
            textViewColerInfo.setVisibility(View.VISIBLE);






            etpentlength.setHint("Shalwar Length        سلور لمبائی");
            etpentlength.setVisibility(View.VISIBLE);

            etpentbottom.setHint("Shalwar Bottom Length     شلوار نیچے");
            etpentbottom.setVisibility(View.VISIBLE);

            // hiding the Edit Text
            etwaist.setVisibility(GONE);
            etthigh.setVisibility(GONE);

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
