package com.nanodevs.elegance.Adapters;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
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


        holder.boskiItemDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String childName="boskiQty";
            deleteCartItem(userID,childName, String.valueOf(cartList.get(position).getBoskiQty()));

            }
        });


        holder.cottonIItemDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.khaadiItemDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        holder.karandiItemDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.lilanItemDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.wWearitemDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });






    }

    private void deleteCartItem(String id, final String child,String childValue) {

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Cart");
       // reference.child(id).orderByChild(child).equalTo(childValue);
        reference.child(id).getRef().child(child).removeValue();

    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        private Button boskiIncrementButton, boskiDecrementButton, boskiItemDeleteBtn;
        private EditText boskiQtyEditText;
        private Button cottonIncrementButton, cottonIDecrementButton, cottonIItemDeleteBtn;
        private EditText cottonIQtyEditText;
        private Button khaadiIncrementButton, khaadiDecrementButton, khaadiItemDeleteBtn;
        private EditText khaadiQtyEditText;
        private Button karandiIncrementButton, karandiDecrementButton, karandiItemDeleteBtn;
        private EditText karandiQtyEditText;
        private Button lilanIncrementButton, lilanDecrementButton, lilanItemDeleteBtn;
        private EditText lilanQtyEditText;
        private Button wWearIncrementButton, wWearDecrementButton, wWearitemDeleteBtn;
        private EditText wWearQtyEditText;

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


            suitTypeTextView = itemView.findViewById(R.id.suitTypeCartItemList);
            // boski
            boskiIncrementButton = itemView.findViewById(R.id.boskiIncrementButton);
            boskiDecrementButton = itemView.findViewById(R.id.boskiDecrementButton);
            boskiItemDeleteBtn = itemView.findViewById(R.id.boskiItemDeleteBtn);
            boskiQtyEditText = itemView.findViewById(R.id.boskiQtyEditText);
            // Cotton
            cottonIncrementButton = itemView.findViewById(R.id.cottonIncrementButton);
            cottonIDecrementButton = itemView.findViewById(R.id.cottonDecrementButton);
            cottonIItemDeleteBtn = itemView.findViewById(R.id.cottonItemDeleteBtn);
            cottonIQtyEditText = itemView.findViewById(R.id.cottonQtyEditText);
            //Khadi
            khaadiIncrementButton = itemView.findViewById(R.id.khaadiIncrementButton);
            khaadiDecrementButton = itemView.findViewById(R.id.khaadiDecrementButton);
            khaadiItemDeleteBtn = itemView.findViewById(R.id.khaadiItemDeleteBtn);
            khaadiQtyEditText = itemView.findViewById(R.id.khaadiQtyEditText);
            // karandi
            karandiIncrementButton = itemView.findViewById(R.id.karandiIncrementButton);
            karandiDecrementButton = itemView.findViewById(R.id.karandiDecrementButton);
            karandiItemDeleteBtn = itemView.findViewById(R.id.karandiItemDeleteBtn);
            karandiQtyEditText = itemView.findViewById(R.id.karandiQtyEditText);
            // lillan
            lilanIncrementButton = itemView.findViewById(R.id.lilanIncrementButton);
            lilanDecrementButton = itemView.findViewById(R.id.lilanDecrementButton);
            lilanItemDeleteBtn = itemView.findViewById(R.id.lilanItemDeleteBtn);
            lilanQtyEditText = itemView.findViewById(R.id.lilanQtyEditText);
            // Washing-wear
            wWearIncrementButton = itemView.findViewById(R.id.wWearIncrementButton);
            wWearDecrementButton = itemView.findViewById(R.id.wWearDecrementButton);
            wWearitemDeleteBtn = itemView.findViewById(R.id.wWearItemDeleteBtn);
            wWearQtyEditText = itemView.findViewById(R.id.wWearQtyEditText);


        }
    }
}
