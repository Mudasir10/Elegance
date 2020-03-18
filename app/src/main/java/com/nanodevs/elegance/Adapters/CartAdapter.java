package com.nanodevs.elegance.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nanodevs.elegance.MainActivity;
import com.nanodevs.elegance.Pojo.Cart;
import com.nanodevs.elegance.R;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.viewHolder> {


    private Context context;
    private List<Cart> cartList;
    private String userID;

    public CartAdapter(List<Cart> cCartList, Context context,String userId) {
        this.cartList = cCartList;
        this.context = context;
        this.userID = userId;

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_list_items, parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, final int position) {

        holder.suitTypeTextView.setText(cartList.get(position).getSuitType());
        holder.boskiQtyEditText.setText(String.valueOf(cartList.get(position).getBoskiQty()));
        holder.cottonIQtyEditText.setText(String.valueOf(cartList.get(position).getCottonQty()));
        holder.khaadiQtyEditText.setText(String.valueOf(cartList.get(position).getKhaadiQty()) );
        holder.karandiQtyEditText.setText(String.valueOf(cartList.get(position).getKarandiQty()));
        holder.lilanQtyEditText.setText(String.valueOf(cartList.get(position).getLilanQty()));
        holder.wWearQtyEditText.setText(String.valueOf(cartList.get(position).getwWearQty()));

        if(holder.boskiQtyEditText.getText().toString().equals("0")||holder.cottonIQtyEditText.getText().toString().equals("0")
        || holder.khaadiQtyEditText.getText().toString().equals("0")||holder.karandiQtyEditText.getText().toString().equals("0")||
                holder.lilanQtyEditText.getText().toString().equals("0")|| holder.wWearQtyEditText.getText().toString().equals("0")){
            if(holder.boskiQtyEditText.getText().toString().equals("0")){
                holder.boski.setVisibility(View.GONE);
            }
            if(holder.cottonIQtyEditText.getText().toString().equals("0")){
                holder.cotton.setVisibility(View.GONE);

            }
            if(holder.khaadiQtyEditText.getText().toString().equals("0")){
                holder.khaadi.setVisibility(View.GONE);

            }
           if(holder.karandiQtyEditText.getText().toString().equals("0")){
               holder.karandi.setVisibility(View.GONE);

            }
           if(holder.lilanQtyEditText.getText().toString().equals("0")){
               holder.lilan.setVisibility(View.GONE);

            }
           if(holder.wWearQtyEditText.getText().toString().equals("0")){

                holder.wWear.setVisibility(View.GONE);
            }

        }


        holder.cartCategoryDeleteButtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 AlertDialog.Builder builder=new AlertDialog.Builder(context).setTitle("Confirmation Dialog ")
                        .setMessage("Are you sure you want to delete "+holder.suitTypeTextView.getText().toString()+" suit order ? ")
                        .setIcon(R.drawable.ic_delete)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                final DatabaseReference cartDataRef=FirebaseDatabase.getInstance().getReference("Cart");
                                cartDataRef.child(userID).child(holder.suitTypeTextView.getText().toString()).removeValue();
                                if(cartList.size()==1){

                                    Intent intent = new Intent(context,MainActivity.class);
                                    context.startActivity(intent);

                                }else
                                      Toast.makeText(context, "Suit Type : "+holder.suitTypeTextView.getText().toString()
                                              +" category is deleted !", Toast.LENGTH_SHORT).show();
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
        });

    }


    @Override
    public int getItemCount() {

        return cartList.size();

    }

    public class viewHolder extends RecyclerView.ViewHolder {


        private EditText boskiQtyEditText;
        private EditText cottonIQtyEditText;
        private EditText khaadiQtyEditText;
        private EditText karandiQtyEditText;
        private EditText lilanQtyEditText;
        private EditText wWearQtyEditText;
        private Button cartCategoryDeleteButtton;
        private TextView suitTypeTextView;

        private LinearLayout boski,cotton,khaadi,karandi,lilan,wWear;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            boski = itemView.findViewById(R.id.boskiCartLayout);
            cotton = itemView.findViewById(R.id.cottonCartLayout);
            khaadi = itemView.findViewById(R.id.khaadiCartLayout);
            karandi = itemView.findViewById(R.id.karandiCartLayout);
            lilan = itemView.findViewById(R.id.lilanCartLayout);
            wWear = itemView.findViewById(R.id.wWearCartLayout);
            cartCategoryDeleteButtton = itemView.findViewById(R.id.deleteItemsOfCurrentCartBtn);

            suitTypeTextView = itemView.findViewById(R.id.suitTypeCartItemList);
            // boski
            boskiQtyEditText = itemView.findViewById(R.id.boskiQtyEditText);
            // Cotton

            cottonIQtyEditText = itemView.findViewById(R.id.cottonQtyEditText);
            //Khadi

            khaadiQtyEditText = itemView.findViewById(R.id.khaadiQtyEditText);
            // karandi

            karandiQtyEditText = itemView.findViewById(R.id.karandiQtyEditText);
            // lillan

            lilanQtyEditText = itemView.findViewById(R.id.lilanQtyEditText);
            // Washing-wear
            wWearQtyEditText = itemView.findViewById(R.id.wWearQtyEditText);


        }
    }



}
