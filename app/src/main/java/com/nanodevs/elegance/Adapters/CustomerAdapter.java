package com.nanodevs.elegance.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nanodevs.elegance.Pojo.Customer;
import com.nanodevs.elegance.R;

import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.viewHolder> {

    private List<Customer> customerList;
    private Context context;

    public CustomerAdapter() {
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.customeritem, parent,false);
        return new viewHolder(v);
    }

    public CustomerAdapter(List<Customer> customerList, Context context) {
        this.customerList = customerList;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

       Customer customer=customerList.get(position);
       holder.CusId.setText("Customer Id : "+customer.getCustomerSerial());
       holder.CusName.setText("Customer Name : "+customer.getCustomerName());
       holder.CusPhone.setText("Customer Phone : "+customer.getCustomerContact());
    }


    @Override
    public int getItemCount() {
        return customerList.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {

        TextView CusId,CusName,CusPhone;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            CusId=itemView.findViewById(R.id.customerDispId);
            CusName=itemView.findViewById(R.id.customerDispName);
            CusPhone=itemView.findViewById(R.id.customerDispContact);

        }
    }


}
