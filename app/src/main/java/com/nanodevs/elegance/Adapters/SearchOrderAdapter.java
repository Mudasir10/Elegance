package com.nanodevs.elegance.Adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nanodevs.elegance.Activites.RegisterCustomerActivity;
import com.nanodevs.elegance.Pojo.Cart;
import com.nanodevs.elegance.Pojo.Customer;
import com.nanodevs.elegance.Pojo.ShowOrder;
import com.nanodevs.elegance.R;

import java.util.ArrayList;
import java.util.List;

public class SearchOrderAdapter extends RecyclerView.Adapter<SearchOrderAdapter.SearchOrderViewHolder> {


    private Context mContext;
    private List<String > CustomerIds;
    private List<Long> CustomerOrderCount;





    public SearchOrderAdapter(Context mContext ,List<String> ids,List<Long> OrderCount) {
        this.mContext = mContext;
        CustomerIds=ids;
        CustomerOrderCount=OrderCount;

        }

    @NonNull
    @Override
    public SearchOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(mContext).inflate(R.layout.single_item_search_orders,parent,false);
        return new SearchOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchOrderViewHolder holder, int position) {


        holder.tvCusId.setText(CustomerIds.get(position));

       /* ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mContext,
                CustomerOrderCount.indexOf(position), android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.slectOrderSpinner.setAdapter(adapter);
*/

    }

    @Override
    public int getItemCount() {
        return CustomerIds.size();
    }

    public class SearchOrderViewHolder extends RecyclerView.ViewHolder{

        TextView tvCusId;
        Spinner slectOrderSpinner;

        public SearchOrderViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCusId=itemView.findViewById(R.id.customer_id);
            slectOrderSpinner=itemView.findViewById(R.id.spinner_select_order);




        }
    }


}
