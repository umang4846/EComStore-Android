package com.appprocessors.ecomstore.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.activities.AddAddressActivity;
import com.appprocessors.ecomstore.interfaces.MyAddressItemClickListner;
import com.appprocessors.ecomstore.model.Address;
import com.appprocessors.ecomstore.utils.Common;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAddressesAdapter extends RecyclerView.Adapter<MyAddressesAdapter.MyAddressesViewHolder> {


    Activity activity;
    List<Address> addressList;

    private int lastSelectedPosition = 0;

    public MyAddressesAdapter(Activity activity, List<Address> addressList) {
        this.activity = activity;
        this.addressList = addressList;
    }

    @NonNull
    @Override
    public MyAddressesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.address_list_item, viewGroup, false);


        return new MyAddressesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAddressesViewHolder holder, int position) {

        if (addressList.get(position).getIschecked()) {
            holder.rbSelectAddress.setChecked(true);
            holder.ibEditAddress.setVisibility(View.VISIBLE);

        } else {
            holder.rbSelectAddress.setChecked(false);
            holder.ibEditAddress.setVisibility(View.GONE);

        }

        holder.rbSelectAddress.setChecked(lastSelectedPosition == position);
        holder.tvAddressFullName.setText(addressList.get(position).getFullName());
        holder.tvAddressType.setText(addressList.get(position).getAddressType());
        StringBuilder addressFullDetails = new StringBuilder();
        addressFullDetails.append(addressList.get(position).getHomeNoBuildingName())
                .append(" ,")
                .append(addressList.get(position).getLocalityAreaStreet())
                .append(" ,")
                .append(addressList.get(position).getCityTown())
                .append(" ,")
                .append(addressList.get(position).getSubDistrict());
        holder.ibEditAddress.setTag(position);
        holder.tvAddressFullDetails.setText(addressFullDetails);
        holder.tvAddressPhoneNumber.setText(addressList.get(position).getMobileNo());
        holder.ibEditAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editAddressIntent = new Intent(activity, AddAddressActivity.class);
                editAddressIntent.putExtra("editAddress", addressList.get(position));
                activity.startActivityForResult(editAddressIntent, Common.tagUpdateAddress);
            }
        });
        holder.setMyAddressItemClickListner(new MyAddressItemClickListner() {
            @Override
            public void onClick(View view, int position) {
                lastSelectedPosition = position;
                notifyDataSetChanged();
            }
        });
        if (lastSelectedPosition == position) {
            holder.itemView.setBackgroundColor(Color.parseColor("#F8F8FA"));
            holder.rbSelectAddress.setChecked(true);
            holder.ibEditAddress.setVisibility(View.VISIBLE);
        } else {
            holder.rbSelectAddress.setChecked(false);
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.ibEditAddress.setVisibility(View.GONE);

        }
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }


    public class MyAddressesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.rb_select_address)
        RadioButton rbSelectAddress;
        @BindView(R.id.tv_address_full_name)
        TextView tvAddressFullName;
        @BindView(R.id.tv_address_type)
        TextView tvAddressType;
        @BindView(R.id.tv_address_full_details)
        TextView tvAddressFullDetails;
        @BindView(R.id.tv_address_phone_number)
        TextView tvAddressPhoneNumber;
        @BindView(R.id.ib_edit_address)
        ImageButton ibEditAddress;

        MyAddressItemClickListner myAddressItemClickListner;

        public void setMyAddressItemClickListner(MyAddressItemClickListner myAddressItemClickListner) {
            this.myAddressItemClickListner = myAddressItemClickListner;
        }

        public MyAddressesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
            rbSelectAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelectedPosition = getAdapterPosition();
                    notifyDataSetChanged();
                }
            });
        }


        @Override
        public void onClick(View v) {
            myAddressItemClickListner.onClick(v, getAdapterPosition());
        }
    }

}
