package com.nanodevs.elegance.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nanodevs.elegance.Activites.ViewCustomerOrdersActivity;
import com.nanodevs.elegance.Activites.StitchCloth;
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
        holder.CusName.setText("Name : " + customerList.get(position).getCustomerName());
        holder.CusPhone.setText("Phone : " + customerList.get(position).getCustomerContact());


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

        holder.placeStitchOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, StitchCloth.class);
                intent.putExtra("customerId",String.valueOf(customerList.get(position).getCustomerSerial()) );
                intent.putExtra("cus_name",customerList.get(position).getCustomerName());
                intent.putExtra("cus_phone",customerList.get(position).getCustomerContact());
                context.startActivity(intent);

            }
        });

        holder.viewOrdersCustomerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ViewCustomerOrdersActivity.class);
                intent.putExtra("order_cusId",String.valueOf(customerList.get(position).getCustomerSerial()) );
                intent.putExtra("order_cusName",String.valueOf(customerList.get(position).getCustomerName()) );
                intent.putExtra("order_cusContact",String.valueOf(customerList.get(position).getCustomerContact()) );
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
                    if (item.getCustomerName().toLowerCase().contains(filterPattern) ||
                            item.getCustomerContact().toLowerCase().contains(filterPattern)) {
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
       Button imageButtonUpdate,placeStitchOrderBtn,viewOrdersCustomerBtn;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            CusId = itemView.findViewById(R.id.customerDispId);
            CusName = itemView.findViewById(R.id.customerDispName);
            CusPhone = itemView.findViewById(R.id.customerDispContact);
            imageButtonUpdate=itemView.findViewById(R.id.updateMeasurement);
            placeStitchOrderBtn=itemView.findViewById(R.id.callStitchOrder);
            viewOrdersCustomerBtn=itemView.findViewById(R.id.viewOrderOfCustomer);

        }
    }


}
