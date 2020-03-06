package com.nanodevs.elegance.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nanodevs.elegance.Activites.CustomerDisplay;
import com.nanodevs.elegance.Activites.UpdateMeasurements;
import com.nanodevs.elegance.Pojo.Customer;
import com.nanodevs.elegance.R;

import java.util.ArrayList;
import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.viewHolder> implements Filterable {

    private List<Customer> customerList;
    private List<Customer> customerListFull;
    private Context context;

    public CustomerAdapter(List<Customer> customerList, Context context) {
        this.customerList = customerList;
        this.customerListFull = new ArrayList<>(customerList);
        this.context = context;
    }



    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.customeritem, parent, false);
        return new viewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, final int position) {

        holder.CusId.setText("Customer ID : " + customerList.get(position).getCustomerSerial());
        holder.CusName.setText("Customer Name : " + customerList.get(position).getCustomerName());
        holder.CusPhone.setText("Customer Phone : " + customerList.get(position).getCustomerContact());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, CustomerDisplay.class);
                intent.putExtra("customerId", customerList.get(position).getCustomerSerial());
                intent.putExtra("cus_name",customerList.get(position).getCustomerName());
                intent.putExtra("cus_phone",customerList.get(position).getCustomerContact());
                context.startActivity(intent);

            }
        });

        holder.imageButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, UpdateMeasurements.class);
                intent.putExtra("customerId", customerList.get(position).getCustomerSerial());
                intent.putExtra("cus_name",customerList.get(position).getCustomerName());
                intent.putExtra("cus_phone",customerList.get(position).getCustomerContact());
                context.startActivity(intent);


            }
        });


    }


    @Override
    public int getItemCount() {
        return customerList.size();
    }

    @Override
    public Filter getFilter() {
        return customerListFilter;
    }

    private Filter customerListFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Customer> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(customerListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Customer item : customerListFull) {
                    if (item.getCustomerName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            customerList.clear();
            customerList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    class viewHolder extends RecyclerView.ViewHolder {

       private TextView CusId, CusName, CusPhone;
       ImageButton imageButtonUpdate;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            CusId = itemView.findViewById(R.id.customerDispId);
            CusName = itemView.findViewById(R.id.customerDispName);
            CusPhone = itemView.findViewById(R.id.customerDispContact);
            imageButtonUpdate=itemView.findViewById(R.id.updateMeasurement);

        }
    }


}
