package com.nanodevs.elegance.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.internal.service.Common;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nanodevs.elegance.R;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

import javax.sql.CommonDataSource;

public class StitchCloth extends AppCompatActivity {

    private Button boskiPlusBtn, boksiMinusBtn;
    private Button cottonPlusBtn, cottonMinusBtn;
    private Button karandiPlusBtn, karandiMinusBtn;
    private Button khaadiPlusBtn, khaadiMinusBtn;
    private Button lilanPlusBtn, lilanMinusBtn;
    private Button wWearPlusBtn, wWearMinusBtn;
    private EditText boskiEditText, cottonEditText, karandiEditText, khaadiEditText, lilanEditText, wWearEditText;
    private Button addToCartBtn;

    private long mCartItemCount =0;
    private long boskiQty = 0, cottonQty = 0, karandiQty = 0, khaadiQty = 0, lilanQty = 0, wWearQty = 0;
    private NotificationBadge badge;
    private Spinner suitSpinner;
    private TextView clothOrderCustomerSerialNo, clothOrderCustomerName, clothOrderCustomerContact;

    private LinearLayout bLayout,cLayout,kaLayout,khLayout,lLayout,wlayout;


    private  DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("ClothOrderDetails");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stitch_cloth);
        initComponents();
        handleButtonListener();

        suitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

              String itemName= parent.getItemAtPosition(position).toString();
              if(itemName.equals("Kurta")) {
                  clearFields();
                  cLayout.setVisibility(View.VISIBLE);
                  bLayout.setVisibility(View.VISIBLE);
                  kaLayout.setVisibility(View.VISIBLE);
                  khLayout.setVisibility(View.VISIBLE);
                  lLayout.setVisibility(View.VISIBLE);
                  wlayout.setVisibility(View.VISIBLE);
              }else if (itemName.equals("Shirt")){
                  cLayout.setVisibility(View.VISIBLE);
                  bLayout.setVisibility(View.VISIBLE);
                  kaLayout.setVisibility(View.VISIBLE);
                  khLayout.setVisibility(View.VISIBLE);
                  lLayout.setVisibility(View.VISIBLE);
                  wlayout.setVisibility(View.VISIBLE);
                  clearFields();
              }else if (itemName.equals("Suit")){
                  cLayout.setVisibility(View.VISIBLE);
                  cLayout.setVisibility(View.VISIBLE);
                  bLayout.setVisibility(View.VISIBLE);
                  kaLayout.setVisibility(View.VISIBLE);
                  lLayout.setVisibility(View.VISIBLE);
                  wlayout.setVisibility(View.VISIBLE);
                  clearFields();
              }else if (itemName.equals("Saffari Coat")){
                  cLayout.setVisibility(View.GONE);
                  bLayout.setVisibility(View.GONE);
                  kaLayout.setVisibility(View.GONE);
                  khLayout.setVisibility(View.GONE);
                  lLayout.setVisibility(View.GONE);
                  clearFields();
              }else if (itemName.equals("Three Piece")){
                  bLayout.setVisibility(View.GONE);
                  kaLayout.setVisibility(View.GONE);
                  lLayout.setVisibility(View.GONE);
                  cLayout.setVisibility(View.VISIBLE);
                  khLayout.setVisibility(View.VISIBLE);
                  wlayout.setVisibility(View.VISIBLE);
                  clearFields();
              }else if (itemName.equals("Pant")){
                  bLayout.setVisibility(View.GONE);
                  kaLayout.setVisibility(View.GONE);
                  lLayout.setVisibility(View.GONE);
                  cLayout.setVisibility(View.VISIBLE);
                  khLayout.setVisibility(View.VISIBLE);
                  wlayout.setVisibility(View.VISIBLE);
                  clearFields();
              }else if (itemName.equals("Waist Coat")){
                  bLayout.setVisibility(View.GONE);
                  kaLayout.setVisibility(View.GONE);
                  lLayout.setVisibility(View.GONE);
                  cLayout.setVisibility(View.VISIBLE);
                  khLayout.setVisibility(View.VISIBLE);
                  wlayout.setVisibility(View.VISIBLE);
                  clearFields();
              }else
                  Toast.makeText(StitchCloth.this, "InValid Input !", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (getIntent() != null) {

            clothOrderCustomerSerialNo.setText(String.valueOf(getIntent().getStringExtra("customerUid")));
            clothOrderCustomerName.setText(String.valueOf(getIntent().getStringExtra("customerName")));
            clothOrderCustomerContact.setText(String.valueOf(getIntent().getStringExtra("customerContactNumber")));

        }


    }

    private void clearFields() {

        boskiEditText.setText("");
        boskiQty = 0;
        cottonQty = 0;
        karandiQty = 0;
        khaadiQty = 0;
        lilanQty = 0;
        wWearQty = 0;
        cottonEditText.setText("");
        karandiEditText.setText("");
        khaadiEditText.setText("");
        lilanEditText.setText("");
        wWearEditText.setText("");

    }

    private void handleButtonListener() {


        boksiMinusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (boskiQty < 1) {
                    boskiQty = 0;
                } else
                    boskiQty--;
                boskiEditText.setText(String.valueOf(boskiQty));
            }
        });


        boskiPlusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boskiQty++;
                boskiEditText.setText(String.valueOf(boskiQty));
            }
        });


        cottonPlusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cottonQty++;
                cottonEditText.setText(String.valueOf(cottonQty));

            }
        });


        cottonMinusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cottonQty < 1) {
                    cottonQty = 0;

                } else
                    cottonQty--;
                cottonEditText.setText(String.valueOf(cottonQty));
            }
        });


        karandiPlusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                karandiQty++;
                karandiEditText.setText(String.valueOf(karandiQty));

            }
        });


        karandiMinusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (karandiQty < 1)
                    karandiQty = 0;
                else
                    karandiQty--;
                karandiEditText.setText(String.valueOf(karandiQty));

            }
        });


        khaadiPlusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                khaadiQty++;
                khaadiEditText.setText(String.valueOf(khaadiQty));

            }
        });


        khaadiMinusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (khaadiQty < 1)
                    khaadiQty = 0;
                else
                    khaadiQty--;
                khaadiEditText.setText(String.valueOf(khaadiQty));
            }
        });


        lilanPlusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lilanQty++;
                lilanEditText.setText(String.valueOf(lilanQty));

            }
        });

        lilanMinusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lilanQty < 1)
                    lilanQty = 0;
                else
                    lilanQty--;
                lilanEditText.setText(String.valueOf(lilanQty));
            }
        });


        wWearPlusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wWearQty++;
                wWearEditText.setText(String.valueOf(wWearQty));

            }
        });

        wWearMinusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wWearQty < 1)
                    wWearQty = 0;
                else
                    wWearQty--;
                wWearEditText.setText(String.valueOf(wWearQty));
            }
        });


        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCartItemCount=boskiQty+ cottonQty + karandiQty + khaadiQty + lilanQty + wWearQty;

               if(mCartItemCount==0)
                   Toast.makeText(StitchCloth.this, "Please select items to add in cart", Toast.LENGTH_SHORT).show();
               else{
                   saveCartItemsData();
                   updateCartCount();
               }

            }
        });


    }

    private void saveCartItemsData() {


    }

    private void initComponents() {

        cLayout = findViewById(R.id.cottonLayout);
        bLayout = findViewById(R.id.boskiLayout);
        kaLayout = findViewById(R.id.karandiLayout);
        khLayout = findViewById(R.id.khaadiLayout);
        lLayout = findViewById(R.id.lilanLayout);
        wlayout = findViewById(R.id.wWearLayout);

        suitSpinner = findViewById(R.id.suitSpinner);
        clothOrderCustomerSerialNo = findViewById(R.id.customerClothSerialNo);
        clothOrderCustomerName = findViewById(R.id.customerClothDisplayName);
        clothOrderCustomerContact = findViewById(R.id.customerClothDisplayContactNo);

        addToCartBtn = findViewById(R.id.addItemToCartBtn);

        boksiMinusBtn = findViewById(R.id.boskiDecrementButton);
        boskiPlusBtn = findViewById(R.id.boskiIncrementButton);
        boskiEditText = findViewById(R.id.boskiQtyEditText);


        cottonMinusBtn = findViewById(R.id.cottonDecrementButton);
        cottonPlusBtn = findViewById(R.id.cottonIncrementButton);
        cottonEditText = findViewById(R.id.cottonQtyEditText);

        khaadiMinusBtn = findViewById(R.id.khaadiDecrementButton);
        khaadiPlusBtn = findViewById(R.id.khaadiIncrementButton);
        khaadiEditText = findViewById(R.id.khaadiQtyEditText);

        karandiMinusBtn = findViewById(R.id.karandiDecrementButton);
        karandiPlusBtn = findViewById(R.id.karandiIncrementButton);
        karandiEditText = findViewById(R.id.karandiQtyEditText);

        lilanMinusBtn = findViewById(R.id.lilanDecrementButton);
        lilanPlusBtn = findViewById(R.id.lilanIncrementButton);
        lilanEditText = findViewById(R.id.lilanQtyEditText);


        wWearMinusBtn = findViewById(R.id.wWearDecrementButton);
        wWearPlusBtn = findViewById(R.id.wWearIncrementButton);
        wWearEditText = findViewById(R.id.wWearQtyEditText);


        ArrayAdapter<CharSequence> suitAdapter = ArrayAdapter.createFromResource(this,
                R.array.suitCategories, android.R.layout.simple_spinner_item);
        suitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        suitSpinner.setAdapter(suitAdapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

       getMenuInflater().inflate(R.menu.cart_menu, menu);

        View actionView = menu.findItem(R.id.cartMenuActionBar).getActionView();
        badge = actionView.findViewById(R.id.badge);



        return true;

    }

    private void updateCartCount() {

        if(badge==null) return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mCartItemCount==0)
                    badge.setVisibility(View.INVISIBLE);
                else{
                    badge.setVisibility(View.VISIBLE);
                    cartRef.child(String.valueOf(clothOrderCustomerSerialNo)).child(getDateTime()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                           mCartItemCount=dataSnapshot.getChildrenCount();
                            badge.setText(String.valueOf(mCartItemCount));

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.cartMenuActionBar) {
            Toast.makeText(this, "Cart Clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }


}
