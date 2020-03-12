package com.nanodevs.elegance.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nanodevs.elegance.Pojo.Cart;
import com.nanodevs.elegance.Pojo.CustomerBill;
import com.nanodevs.elegance.R;

import java.util.List;

public class ViewOrdersDetailsAdapter extends  RecyclerView.Adapter<ViewOrdersDetailsAdapter.viewHolderOrderDetails>{

    private Context mContext;
    private List<Cart> cartList;
    private List<CustomerBill> customerBillList;

    public ViewOrdersDetailsAdapter(Context mContext, List<Cart> cartList, List<CustomerBill> customerBillList) {
        this.mContext = mContext;
        this.cartList = cartList;
        this.customerBillList = customerBillList;
    }



    @NonNull
    @Override
    public viewHolderOrderDetails onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.disp_vieworders_layout, parent, false);

        return new viewHolderOrderDetails(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolderOrderDetails holder, int position) {





    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class viewHolderOrderDetails extends RecyclerView.ViewHolder{




        private TextView ViewOrder_totalBillTextView,ViewOrder_discountBillTextView,ViewOrder_advanceBillTextView,
                ViewOrder_balanceTextView,ViewOrder_remainingBalanceTextView,ViewOrder_orderDateTextView, ViewOrder_expectedDateTextView;

        private Switch ViewOrder_suitStatusSwitch;
        public viewHolderOrderDetails(@NonNull View itemView) {
            super(itemView);



            ViewOrder_totalBillTextView = itemView.findViewById(R.id.totalBillTextView);
            ViewOrder_discountBillTextView = itemView.findViewById(R.id.discountBillTextView);
            ViewOrder_advanceBillTextView = itemView.findViewById(R.id.advancePaymentTextView);
            ViewOrder_balanceTextView = itemView.findViewById(R.id.balancePaymentTextView);
            ViewOrder_remainingBalanceTextView = itemView.findViewById(R.id.remainingBalanceTextView);
            ViewOrder_orderDateTextView = itemView.findViewById(R.id.orderSubmittedDateTextView);
            ViewOrder_expectedDateTextView = itemView.findViewById(R.id.expectedDateTextView);

            ViewOrder_suitStatusSwitch = itemView.findViewById(R.id.viewOrder_SuitStatusSwitch);






        }
    }
}
