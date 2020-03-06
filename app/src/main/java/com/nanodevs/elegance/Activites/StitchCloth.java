package com.nanodevs.elegance.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nanodevs.elegance.R;

public class StitchCloth extends AppCompatActivity {

    private Button boskiPlusBtn, boksiMinusBtn;
    private Button cottonPlusBtn, cottonMinusBtn;
    private Button karandiPlusBtn, karandiMinusBtn;
    private Button khaadiPlusBtn, khaadiMinusBtn;
    private Button lilanPlusBtn, lilanMinusBtn;
    private Button wWearPlusBtn, wWearMinusBtn;
    private EditText boskiEditText, cottonEditText, karandiEditText, khaadiEditText, lilanEditText, wWearEditText;
    private Button addToCartBtn;
    private TextView textCartItemCount;
    private int mCartItemCount =0;
    private long boskiQty = 0, cottonQty = 0, karandiQty = 0, khaadiQty = 0, lilanQty = 0, wWearQty = 0;

    private Spinner suitSpinner;
    private TextView clothOrderCustomerSerialNo, clothOrderCustomerName, clothOrderCustomerContact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stitch_cloth);
        initComponents();
        handleButtonListener();


        suitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        clearFields();
                    case 1:
                        clearFields();
                    case 2:
                        clearFields();
                    case 3:
                        clearFields();
                    case 4:
                        clearFields();
                    case 5:
                        clearFields();


                }
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
                mCartItemCount++;
                Toast.makeText(StitchCloth.this, ""+mCartItemCount, Toast.LENGTH_SHORT).show();
                setupBadge(mCartItemCount);

            }
        });


    }

    private void initComponents() {

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
        final MenuItem menuItem = menu.findItem(R.id.cartMenuActionBar);

       /* View actionView = menuItem.getActionView();
        textCartItemCount =actionView.findViewById(R.id.cart_badge);

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });*/

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

      /*  if (item.getItemId() == R.id.cartMenuActionBar) {
            Toast.makeText(this, "Cart Clicked", Toast.LENGTH_SHORT).show();
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }


    private void setupBadge(int count) {

        if (textCartItemCount != null) {
            if (count == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(count,0)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }


}
