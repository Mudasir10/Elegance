package com.nanodevs.elegance.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nanodevs.elegance.Pojo.Cart;
import com.nanodevs.elegance.R;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.HashMap;
import java.util.Map;

public class AddStitchOrderActivity extends AppCompatActivity {
    private DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("Cart");

    private Button boskiPlusBtn, boksiMinusBtn;
    private Button cottonPlusBtn, cottonMinusBtn;
    private Button karandiPlusBtn, karandiMinusBtn;
    private Button khaadiPlusBtn, khaadiMinusBtn;
    private Button lilanPlusBtn, lilanMinusBtn;
    private Button wWearPlusBtn, wWearMinusBtn;
    private EditText boskiEditText, cottonEditText, karandiEditText, khaadiEditText, lilanEditText, wWearEditText;
    private Button addToCartBtn,deleteCartOrderBtn;

    private long mCartItemCount = 0;
    private long boskiQty = 0, cottonQty = 0, karandiQty = 0, khaadiQty = 0, lilanQty = 0, wWearQty = 0;
    private NotificationBadge badge;
    private Spinner suitSpinner;
    private TextView clothOrderCustomerSerialNo, clothOrderCustomerName, clothOrderCustomerContact;

    private LinearLayout bLayout, cLayout, kaLayout, khLayout, lLayout, wlayout;
    private int globalSpinnerPosition;
    private String globalItemName;
    private Toolbar mToolbar;

    @Override
    protected void onResume() {
        super.onResume();
        loadDataForCustomerTextView();
        updateCartCount();
        if(globalItemName !=null){
            clearFields();
            loadDataForAllCategories(globalItemName);
            loadDataForThreeCategories(globalItemName);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        updateCartCount();
        loadDataForCustomerTextView();
        updateCartCount();
        if(globalItemName !=null){
            loadDataForAllCategories(globalItemName);
            loadDataForThreeCategories(globalItemName);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addstitchorder);
        initComponents();
        handleButtonListener();
        updateCartCount();

        mToolbar=findViewById(R.id.app_bar_stitch_cloth);
        mToolbar.setTitle("Cloth Stitch");
        setSupportActionBar(mToolbar);

        suitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                globalSpinnerPosition = position;
                globalItemName= parent.getItemAtPosition(position).toString();
                String itemName = parent.getItemAtPosition(position).toString();
                if (itemName.equals("Kurta")){
                    clearFields();
                    loadDataForAllCategories(itemName);
                    cLayout.setVisibility(View.VISIBLE);
                    bLayout.setVisibility(View.VISIBLE);
                    kaLayout.setVisibility(View.VISIBLE);
                    khLayout.setVisibility(View.VISIBLE);
                    lLayout.setVisibility(View.VISIBLE);
                    wlayout.setVisibility(View.VISIBLE);
                }else if (itemName.equals("Shirt")) {
                    clearFields();
                    loadDataForAllCategories(itemName);
                    cLayout.setVisibility(View.VISIBLE);
                    bLayout.setVisibility(View.VISIBLE);
                    kaLayout.setVisibility(View.VISIBLE);
                    khLayout.setVisibility(View.VISIBLE);
                    lLayout.setVisibility(View.VISIBLE);
                    wlayout.setVisibility(View.VISIBLE);
                } else if (itemName.equals("Suit")) {
                    clearFields();
                    loadDataForAllCategories(itemName);
                    cLayout.setVisibility(View.VISIBLE);
                    cLayout.setVisibility(View.VISIBLE);
                    bLayout.setVisibility(View.VISIBLE);
                    kaLayout.setVisibility(View.VISIBLE);
                    lLayout.setVisibility(View.VISIBLE);
                    wlayout.setVisibility(View.VISIBLE);
                } else if (itemName.equals("Saffari Coat")) {
                    clearFields();
                    loadDataForThreeCategories(itemName);
                    bLayout.setVisibility(View.GONE);
                    kaLayout.setVisibility(View.GONE);
                    lLayout.setVisibility(View.GONE);
                } else if (itemName.equals("Three Piece")) {
                    clearFields();
                    loadDataForThreeCategories(itemName);
                    bLayout.setVisibility(View.GONE);
                    kaLayout.setVisibility(View.GONE);
                    lLayout.setVisibility(View.GONE);
                    cLayout.setVisibility(View.VISIBLE);
                    khLayout.setVisibility(View.VISIBLE);
                    wlayout.setVisibility(View.VISIBLE);
                } else if (itemName.equals("Pant")) {
                    clearFields();
                    loadDataForThreeCategories(itemName);
                    bLayout.setVisibility(View.GONE);
                    kaLayout.setVisibility(View.GONE);
                    lLayout.setVisibility(View.GONE);
                    cLayout.setVisibility(View.VISIBLE);
                    khLayout.setVisibility(View.VISIBLE);
                    wlayout.setVisibility(View.VISIBLE);
                } else if (itemName.equals("Waist Coat")) {
                    clearFields();
                    loadDataForThreeCategories(itemName);
                    bLayout.setVisibility(View.GONE);
                    kaLayout.setVisibility(View.GONE);
                    lLayout.setVisibility(View.GONE);
                    cLayout.setVisibility(View.VISIBLE);
                    khLayout.setVisibility(View.VISIBLE);
                    wlayout.setVisibility(View.VISIBLE);
                } else
                    Toast.makeText(AddStitchOrderActivity.this, "Invalid Category !", Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (getIntent() != null) {

           clothOrderCustomerSerialNo.setText(String.valueOf(getIntent().getStringExtra("customerId")));
            clothOrderCustomerName.setText(getIntent().getStringExtra("cus_name"));
            clothOrderCustomerContact.setText(String.valueOf(getIntent().getStringExtra("cus_phone")));

        }


    }

    private void loadDataForCustomerTextView(){

        if (getIntent() != null) {

            clothOrderCustomerSerialNo.setText(String.valueOf(getIntent().getStringExtra("customerId")));
            clothOrderCustomerName.setText(getIntent().getStringExtra("cus_name"));
            clothOrderCustomerContact.setText(String.valueOf(getIntent().getStringExtra("cus_phone")));

        }
    }

    private void loadDataForAllCategories(final String itemName) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                cartRef.child(String.valueOf(clothOrderCustomerSerialNo.getText())).child(itemName).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){
                            boskiQty = Long.parseLong(dataSnapshot.child("boskiQty").getValue().toString());
                            cottonQty= Long.parseLong(dataSnapshot.child("cottonQty").getValue().toString());
                            khaadiQty= Long.parseLong(dataSnapshot.child("khaadiQty").getValue().toString());
                            karandiQty= Long.parseLong(dataSnapshot.child("karandiQty").getValue().toString());
                            lilanQty= Long.parseLong(dataSnapshot.child("lilanQty").getValue().toString());
                            wWearQty= Long.parseLong(dataSnapshot.child("wWearQty").getValue().toString());
                            boskiEditText.setText(String.valueOf(boskiQty));
                            cottonEditText.setText(String.valueOf(cottonQty));
                            khaadiEditText.setText(String.valueOf(khaadiQty));
                            karandiEditText.setText(String.valueOf(karandiQty));
                            lilanEditText.setText(String.valueOf(lilanQty));
                            wWearEditText.setText(String.valueOf(wWearQty));
                        }else{
                            boskiEditText.setHint("Qty");
                            cottonEditText.setHint("Qty");;
                            khaadiEditText.setHint("Qty");;
                            karandiEditText.setHint("Qty");;
                            lilanEditText.setHint("Qty");;
                            wWearEditText.setHint("Qty");;
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }).start();

    }

    private void loadDataForThreeCategories(final String itemName) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                cartRef.child(String.valueOf(clothOrderCustomerSerialNo.getText())).child(itemName).
                        addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            cottonQty= Long.parseLong(dataSnapshot.child("cottonQty").getValue().toString());
                            khaadiQty= Long.parseLong(dataSnapshot.child("khaadiQty").getValue().toString());
                            wWearQty= Long.parseLong(dataSnapshot.child("wWearQty").getValue().toString());
                            boskiEditText.setText(String.valueOf(boskiQty));
                            cottonEditText.setText(String.valueOf(cottonQty));
                            khaadiEditText.setText(String.valueOf(khaadiQty));
                            karandiEditText.setText(String.valueOf(karandiQty));
                            lilanEditText.setText(String.valueOf(lilanQty));
                            wWearEditText.setText(String.valueOf(wWearQty));
                        }else{
                            boskiEditText.setHint("Qty");
                            cottonEditText.setHint("Qty");;
                            khaadiEditText.setHint("Qty");;
                            karandiEditText.setHint("Qty");;
                            lilanEditText.setHint("Qty");;
                            wWearEditText.setHint("Qty");;
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }).start();

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

               new Thread(new Runnable() {
                   @Override
                   public void run() {
                       updateCartCount();
                       saveCartItemsData();
                   }
               }).start();

            }
        });


        deleteCartOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(mCartItemCount>0){
                  deleteCartOrder();
              }
              else
                  Toast.makeText(AddStitchOrderActivity.this, "first add items to cart then you can delete !", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void deleteCartOrder() {

        AlertDialog.Builder builder=new AlertDialog.Builder(AddStitchOrderActivity.this).setTitle("Confirmation Dialog ")
                .setMessage("Are you sure you want to delete "+globalItemName+" suit order ? ")
                .setIcon(R.drawable.ic_delete)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Todo
                        final String suitTypeName = suitSpinner.getItemAtPosition(globalSpinnerPosition).toString();
                        cartRef.child(clothOrderCustomerSerialNo.getText().toString()).child(suitTypeName).getRef().removeValue()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            updateCartCount();
                                            if(globalItemName !=null){
                                                clearFields();
                                                loadDataForAllCategories(globalItemName);
                                                loadDataForThreeCategories(globalItemName);
                                                Toast.makeText(AddStitchOrderActivity.this, "Order deleted !", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    }

                                });
                        dialog.dismiss();


                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //Todo
                         dialog.cancel();
                    }
                });
        builder.create();
                builder.show();

    }

    private void saveCartItemsData() {
        updateCartCount();
        DatabaseReference cartRefSaveData = FirebaseDatabase.getInstance().getReference("Cart");
        final String suitTypeName = suitSpinner.getItemAtPosition(globalSpinnerPosition).toString();
            if (boskiQty == 0 && cottonQty == 0 &&
                    khaadiQty == 0 && karandiQty == 0 && lilanQty == 0
                    && wWearQty == 0)
                Toast.makeText(AddStitchOrderActivity.this, "Please first add items to cart !", Toast.LENGTH_SHORT).show();
            else {
                Cart itemCart = new Cart(boskiQty, cottonQty, khaadiQty, karandiQty, lilanQty, wWearQty, suitTypeName);
                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put(clothOrderCustomerSerialNo.getText() + "/" + suitTypeName, itemCart.toCartMap());
                cartRefSaveData.updateChildren(childUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(AddStitchOrderActivity.this, "Items added to cart !", Toast.LENGTH_SHORT).show();
                        updateCartCount();
                    }
                });
            }
        }

    private void initComponents() {

        deleteCartOrderBtn= findViewById(R.id.deleteItemsFromCartBtn);
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

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCartItemCount == 0)
                    Toast.makeText(AddStitchOrderActivity.this, "Cart is Empty", Toast.LENGTH_SHORT).show();
                else {
                    Intent intent=new Intent(AddStitchOrderActivity.this, CartActivity.class);
                    intent.putExtra("cusId",clothOrderCustomerSerialNo.getText());
                    intent.putExtra("cusName",clothOrderCustomerName.getText());
                    intent.putExtra("cusPhone",clothOrderCustomerContact.getText());
                    startActivity(intent);
                }
            }
        });

        badge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCartItemCount == 0)
                    Toast.makeText(AddStitchOrderActivity.this, "Cart is Empty", Toast.LENGTH_SHORT).show();
                else {
                    Intent intent=new Intent(AddStitchOrderActivity.this, CartActivity.class);
                    intent.putExtra("cusId",clothOrderCustomerSerialNo.getText());
                    intent.putExtra("cusName",clothOrderCustomerName.getText());
                    intent.putExtra("cusPhone",clothOrderCustomerContact.getText());
                    startActivity(intent);
                }

            }

        });


        return true;

    }

    private void updateCartCount() {

        cartRef.child(String.valueOf(clothOrderCustomerSerialNo.getText())).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mCartItemCount = dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if (badge == null) return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mCartItemCount == 0)
                    badge.setVisibility(View.INVISIBLE);
                else {
                    badge.setVisibility(View.VISIBLE);
                    badge.setText(String.valueOf(mCartItemCount));
                    Log.d("MYTag", "run: " + mCartItemCount);
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.badge) {
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {

        if(mCartItemCount>0)
        {
            AlertDialog.Builder builder=new AlertDialog.Builder(AddStitchOrderActivity.this).setTitle("Confirmation Dialog ")
                    .setMessage("Are you sure you want to delete "+globalItemName+" suit order ? ")
                    .setIcon(R.drawable.ic_delete)
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            final String suitTypeName = suitSpinner.getItemAtPosition(globalSpinnerPosition).toString();
                            cartRef.child(clothOrderCustomerSerialNo.getText().toString()).child(suitTypeName).getRef().removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                updateCartCount();
                                                if(globalItemName !=null){
                                                    clearFields();
                                                    loadDataForAllCategories(globalItemName);
                                                    loadDataForThreeCategories(globalItemName);
                                                   AddStitchOrderActivity.super.onBackPressed();
                                                }

                                            }
                                        }

                                    });
                            dialog.dismiss();


                        }
                    })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.cancel();
                        }
                    });
            builder.create();
            builder.show();


        }else
        super.onBackPressed();


    }
}
