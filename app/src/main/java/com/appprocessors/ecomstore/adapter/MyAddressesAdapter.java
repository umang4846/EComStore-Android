package com.appprocessors.ecomstore.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.activities.AddAddressActivity;
import com.appprocessors.ecomstore.model.customer.Addresses;
import com.appprocessors.ecomstore.utils.Common;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAddressesAdapter extends RecyclerView.Adapter<MyAddressesAdapter.MyAddressesViewHolder> {

    private static final String TAG = "MyAddressesAdapter";

    private int mSelectedItem = 0;
    private List<Addresses> mSingleCheckList;
    Activity activity;
    private AdapterView.OnItemClickListener onItemClickListener;

    public MyAddressesAdapter(Activity activity, List<Addresses> addressList) {
        this.activity = activity;
        mSingleCheckList = addressList;

    }

    public MyAddressesAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyAddressesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_address_list, viewGroup, false);

        return new MyAddressesViewHolder(view, this);
    }


    @Override
    public void onBindViewHolder(@NonNull MyAddressesViewHolder viewHolder, int position) {


        Addresses address = mSingleCheckList.get(position);

        try {
            viewHolder.setDateToView(address, position);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mSingleCheckList.size() == 0 ? 1 : mSingleCheckList.size();
    }


    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void onItemHolderClick(MyAddressesViewHolder holder) {
        if (onItemClickListener != null)
            onItemClickListener.onItemClick(null, holder.itemView, holder.getAdapterPosition(), holder.getItemId());

    }

    public void UpdateData(int position, Addresses address) {
        mSingleCheckList.set(position, address);
        notifyItemChanged(position);
    }


    public class MyAddressesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.rb_select_address)
        RadioButton rbSelectAddress;
        @BindView(R.id.tv_address_full_name)
        TextView tvAddressFullName;
        @BindView(R.id.tv_address_full_details)
        TextView tvAddressFullDetails;
        @BindView(R.id.tv_address_phone_number)
        TextView tvAddressPhoneNumber;
        @BindView(R.id.ib_edit_address)
        ImageButton ibEditAddress;


        private MyAddressesAdapter mAdapter;


        public MyAddressesViewHolder(@NonNull View itemView, final MyAddressesAdapter mAdapter) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.mAdapter = mAdapter;
            itemView.setOnClickListener(this);
            rbSelectAddress.setOnClickListener(this);
            ibEditAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent editAddressIntent = new Intent(activity, AddAddressActivity.class);
                    editAddressIntent.putExtra("editAddress", mSingleCheckList.get(getAdapterPosition()));
                    activity.startActivityForResult(editAddressIntent, Common.tagUpdateAddress);
                }
            });

        }

        public void setDateToView(Addresses item, int position) throws Exception {
            rbSelectAddress.setChecked(position == mSelectedItem);
            if (rbSelectAddress.isChecked()) {
                ibEditAddress.setVisibility(View.VISIBLE);
                itemView.setBackgroundColor(Color.parseColor("#E0E0E0"));
            } else {
                ibEditAddress.setVisibility(View.GONE);
                itemView.setBackgroundColor(Color.TRANSPARENT);
            }
            tvAddressFullName.setText(new StringBuilder().append(item.getFirstName()).append(" ").append(item.getLastName()).toString());
            StringBuilder addressFullDetails = new StringBuilder();
            addressFullDetails.append(item.getAddress1())
                    .append(" ,")
                    .append(item.getAddress2())
                    .append(" ,")
                    .append(item.getCity());
            tvAddressFullDetails.setText(addressFullDetails);
            tvAddressPhoneNumber.setText(item.getPhoneNumber());
        }

        @Override
        public void onClick(View v) {
            mSelectedItem = getAdapterPosition();
            ibEditAddress.setVisibility(View.VISIBLE);
            notifyItemRangeChanged(0, mSingleCheckList.size());
            mAdapter.onItemHolderClick(MyAddressesViewHolder.this);

        }
    }

}



