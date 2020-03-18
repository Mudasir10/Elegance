package com.nanodevs.elegance.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nanodevs.elegance.Pojo.CustomerBill;
import com.nanodevs.elegance.R;

import java.util.List;

public class ViewOrdersListBillAdapter extends RecyclerView.Adapter<ViewOrdersListBillAdapter.viewHolderViewOrdersListBillAdapter> {


    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("CustomerBills");

    private List<CustomerBill> mBillList;
    private Context mContext;
    private String key;

    public ViewOrdersListBillAdapter(List<CustomerBill> mBillList, Context mContext, String key) {
        this.mBillList = mBillList;
        this.mContext = mContext;
        this.key = key;
    }

    @NonNull
    @Override
    public viewHolderViewOrdersListBillAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.disp_orderbill_layout, parent, false);

        return new viewHolderViewOrdersListBillAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolderViewOrdersListBillAdapter holder, final int position) {


        holder.suitSwitchView.setChecked(mBillList.get(position).isSuitStatus());


        holder.suitSwitchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // set true
                    long id = mBillList.get(position).getCustomerSerial();
                    databaseReference.child(String.valueOf(id)).child(key).child("suitStatus").setValue(true);
                    mBillList.clear();
                }
                if (!isChecked) {
                    long id = mBillList.get(position).getCustomerSerial();
                    databaseReference.child(String.valueOf(id)).child(key).child("suitStatus").setValue(false);
                    mBillList.clear();
                }
            }
        });

        holder.bill_totalBillTextView.setText("Total Bill \t\t\t\t\t\t\t\t:  " + mBillList.get(position).getTotalBill());
        holder.bill_advanceBillPayementTextView.setText("Advance Payment \t\t\t:  " + mBillList.get(position).getSuitAdvancePayment());
        holder.bill_discountBillPaymentTextView.setText("Discount Payment \t\t:  " + mBillList.get(position).getDiscount());
        holder.bill_balanceBillTextView.setText("Total Balance \t\t\t\t\t:  " + mBillList.get(position).getBalance());
        holder.bill_remainingBillTextView.setText("Remaining Balance \t\t:  " + mBillList.get(position).getRemainingBalance());
        holder.bill_expectedDateTextView.setText("Expected Date \t\t\t\t\t:  " + mBillList.get(position).getExpectedDate());
        holder.bill_submissionDateTextView.setText("Order Date \t\t\t\t\t\t   :  " + mBillList.get(position).getOrderDate());


    }

    @Override
    public int getItemCount() {
        return mBillList.size();
    }

    public class viewHolderViewOrdersListBillAdapter extends RecyclerView.ViewHolder {

        private Switch suitSwitchView;
        private TextView bill_totalBillTextView, bill_advanceBillPayementTextView,
                bill_discountBillPaymentTextView, bill_balanceBillTextView, bill_remainingBillTextView, bill_expectedDateTextView, bill_submissionDateTextView;

        public viewHolderViewOrdersListBillAdapter(@NonNull View itemView) {
            super(itemView);

            suitSwitchView = itemView.findViewById(R.id.viewOrder_SuitStatusSwitch);


            bill_totalBillTextView = itemView.findViewById(R.id.totalBillTextView);
            bill_advanceBillPayementTextView = itemView.findViewById(R.id.advancePaymentTextView);
            bill_discountBillPaymentTextView = itemView.findViewById(R.id.discountBillTextView);
            bill_balanceBillTextView = itemView.findViewById(R.id.balancePaymentTextView);
            bill_remainingBillTextView = itemView.findViewById(R.id.remainingBalanceTextView);
            bill_expectedDateTextView = itemView.findViewById(R.id.expectedDateTextView);
            bill_submissionDateTextView = itemView.findViewById(R.id.orderSubmittedDateTextView);


        }
    }
}
