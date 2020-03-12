package com.nanodevs.elegance.Adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nanodevs.elegance.Pojo.Cart;
import com.nanodevs.elegance.R;

import java.util.List;

public class ViewOrdersListAdapter extends RecyclerView.Adapter<ViewOrdersListAdapter.viewHolder_ViewOrdersListAdapter> {


    private Context mContext;
    private List<Cart> mCartList;

    public ViewOrdersListAdapter(Context mContext, List<Cart> mCartList) {
        this.mContext = mContext;
        this.mCartList = mCartList;
    }

    @NonNull
    @Override
    public viewHolder_ViewOrdersListAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.disp_vieworders_layout, parent, false);
        return new viewHolder_ViewOrdersListAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder_ViewOrdersListAdapter holder, int position) {


        holder.ViewOrder_suitTypeTextView.setText(mCartList.get(position).getSuitType());

        holder.ViewOrder_boskiQtyEditText.setText(String.valueOf(mCartList.get(position).getBoskiQty()));
        holder.ViewOrder_cottonIQtyEditText.setText(String.valueOf(mCartList.get(position).getCottonQty()));
        holder.ViewOrder_khaadiQtyEditText.setText(String.valueOf(mCartList.get(position).getKhaadiQty()));
        holder.ViewOrder_karandiQtyEditText.setText(String.valueOf(mCartList.get(position).getKarandiQty()));
        holder.ViewOrder_lilanQtyEditText.setText(String.valueOf(mCartList.get(position).getLilanQty()));
        holder.ViewOrder_wWearQtyEditText.setText(String.valueOf(mCartList.get(position).getwWearQty()));


        if(holder.ViewOrder_boskiQtyEditText.getText().toString().equals("0")||holder.ViewOrder_cottonIQtyEditText.getText().toString().equals("0")
                || holder.ViewOrder_khaadiQtyEditText.getText().toString().equals("0")||holder.ViewOrder_karandiQtyEditText.getText().toString().equals("0")||
                holder.ViewOrder_lilanQtyEditText.getText().toString().equals("0")|| holder.ViewOrder_wWearQtyEditText.getText().toString().equals("0")){

            if(holder.ViewOrder_boskiQtyEditText.getText().toString().equals("0")){
                holder.ViewOrder_boski.setVisibility(View.GONE);

            }
            if(holder.ViewOrder_cottonIQtyEditText.getText().toString().equals("0")){
                holder.ViewOrder_cotton.setVisibility(View.GONE);

            }
            if(holder.ViewOrder_khaadiQtyEditText.getText().toString().equals("0")){
                holder.ViewOrder_khaadi.setVisibility(View.GONE);

            }
            if(holder.ViewOrder_karandiQtyEditText.getText().toString().equals("0")){
                holder.ViewOrder_karandi.setVisibility(View.GONE);

            }
            if(holder.ViewOrder_lilanQtyEditText.getText().toString().equals("0")){
                holder.ViewOrder_lilan.setVisibility(View.GONE);

            }
            if(holder.ViewOrder_wWearQtyEditText.getText().toString().equals("0")){

                holder.ViewOrder_wWear.setVisibility(View.GONE);
            }

        }




    }

    @Override
    public int getItemCount() {
        return mCartList.size();
    }

    public static class viewHolder_ViewOrdersListAdapter extends RecyclerView.ViewHolder{

        private EditText ViewOrder_boskiQtyEditText;
        private EditText ViewOrder_cottonIQtyEditText;
        private EditText ViewOrder_khaadiQtyEditText;
        private EditText ViewOrder_karandiQtyEditText;
        private EditText ViewOrder_lilanQtyEditText;
        private EditText ViewOrder_wWearQtyEditText;
        private TextView ViewOrder_suitTypeTextView;

        private LinearLayout ViewOrder_boski,ViewOrder_cotton,ViewOrder_khaadi,ViewOrder_karandi,ViewOrder_lilan,ViewOrder_wWear;

        public viewHolder_ViewOrdersListAdapter(@NonNull View itemView) {
            super(itemView);

            ViewOrder_boskiQtyEditText = itemView.findViewById(R.id.ViewOrder_boskiQtyEditText);
            ViewOrder_cottonIQtyEditText = itemView.findViewById(R.id.ViewOrder_cottonQtyEditText);
            ViewOrder_khaadiQtyEditText = itemView.findViewById(R.id.ViewOrder_khaadiQtyEditText);
            ViewOrder_karandiQtyEditText = itemView.findViewById(R.id.ViewOrder_karandiQtyEditText);
            ViewOrder_lilanQtyEditText = itemView.findViewById(R.id.ViewOrder_lilanQtyEditText);
            ViewOrder_wWearQtyEditText = itemView.findViewById(R.id.ViewOrder_wWearQtyEditText);

            ViewOrder_suitTypeTextView = itemView.findViewById(R.id.suitTypeViewOrderItemList);

            ViewOrder_boski = itemView.findViewById(R.id.boskiViewOrderLayout);
            ViewOrder_cotton = itemView.findViewById(R.id.cottonViewOrderLayout);
            ViewOrder_khaadi = itemView.findViewById(R.id.khaadiViewOrderLayout);
            ViewOrder_karandi = itemView.findViewById(R.id.karandiViewOrderLayout);
            ViewOrder_lilan = itemView.findViewById(R.id.lilanViewOrderLayout);
            ViewOrder_wWear = itemView.findViewById(R.id.wWearViewOrderLayout);


        }
    }

}
