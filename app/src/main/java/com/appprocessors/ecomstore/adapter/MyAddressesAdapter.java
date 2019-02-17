package com.appprocessors.ecomstore.adapter;

import android.app.Activity;
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
import com.appprocessors.ecomstore.interfaces.OnItemSelectedListener;
import com.appprocessors.ecomstore.model.Address;
import com.appprocessors.ecomstore.model.SelectableAddress;
import com.appprocessors.ecomstore.utils.Common;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAddressesAdapter extends RecyclerView.Adapter implements OnItemSelectedListener {

    private static final String TAG = "MyAddressesAdapter";
    OnItemSelectedListener listener;
    MyAddressItemClickListner myAddressItemClickListner;
    Activity activity;
    List<SelectableAddress> addressList = new ArrayList<>();
    private int lastSelectedPosition = 0;

    public MyAddressesAdapter(Activity activity, List<Address> address, OnItemSelectedListener listener, MyAddressItemClickListner myAddressItemClickListner) {
        this.listener = listener;
        this.activity = activity;
        this.myAddressItemClickListner = myAddressItemClickListner;
        addressList = new ArrayList<>();
        for (Address item : address) {
            addressList.add(new SelectableAddress(item, false));
        }
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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {


        MyAddressesViewHolder holder = (MyAddressesViewHolder) viewHolder;
        SelectableAddress selectableItem = addressList.get(position);

        if (selectableItem.isSelected()) {
            holder.rbSelectAddress.setChecked(true);
            holder.ibEditAddress.setVisibility(View.VISIBLE);
            listener.onItemSelected(selectableItem);
            myAddressItemClickListner.onClick(viewHolder.itemView, position, addressList.get(position));

        } else {
            holder.rbSelectAddress.setChecked(false);
            holder.ibEditAddress.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastSelectedPosition = position;
                notifyDataSetChanged();
                holder.rbSelectAddress.setChecked(true);
                holder.ibEditAddress.setVisibility(View.VISIBLE);
                holder.itemView.setBackgroundColor(Color.parseColor("#E0E0E0"));
                listener.onItemSelected(selectableItem);
                myAddressItemClickListner.onClick(v, position, addressList.get(position));
            }
        });

        holder.rbSelectAddress.setChecked(lastSelectedPosition == position);
        holder.rbSelectAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastSelectedPosition = position;
                notifyDataSetChanged();
                holder.rbSelectAddress.setChecked(true);
                holder.ibEditAddress.setVisibility(View.VISIBLE);
                holder.itemView.setBackgroundColor(Color.parseColor("#E0E0E0"));
                listener.onItemSelected(selectableItem);
                myAddressItemClickListner.onClick(v, position, addressList.get(position));
            }
        });
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
                myAddressItemClickListner.onClick(v, position, addressList.get(position));
            }
        });
        if (lastSelectedPosition == position) {
            holder.itemView.setBackgroundColor(Color.parseColor("#E0E0E0"));
            holder.rbSelectAddress.setChecked(true);
            holder.ibEditAddress.setVisibility(View.VISIBLE);
        } else {
            holder.rbSelectAddress.setChecked(false);
            holder.itemView.setBackground(null);
            holder.ibEditAddress.setVisibility(View.GONE);

        }
        holder.mItem = selectableItem;

    }

    @Override
    public int getItemCount() {
        return addressList.size()==0 ? 1 :addressList.size();
    }

    public Address getSelectedAddress() {
        for (int i = 0; i < addressList.size(); i++) {
            if (addressList.get(i).isSelected()) {
                break;
            }
            return addressList.get(i);
        }
        return null;
    }


    @Override
    public void onItemSelected(SelectableAddress item) {


        for (SelectableAddress selectableItem : addressList) {
            if (!selectableItem.equals(item)
                    && selectableItem.isSelected()) {
                selectableItem.setSelected(true);
            } else if (selectableItem.equals(item)
                    && item.isSelected()) {
                selectableItem.setSelected(true);
            }
        }
        notifyDataSetChanged();

        listener.onItemSelected(item);

    }


    public void UpdateData(int position, Address address) {

        for (int i = 0;i<addressList.size();i++){
            addressList.get(i).setSelected(false);
        }
        addressList.set(position, new SelectableAddress(address, true));
        notifyItemChanged(position);
    }


    public class MyAddressesViewHolder extends RecyclerView.ViewHolder {

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

        OnItemSelectedListener itemSelectedListener;
        SelectableAddress mItem;


        public MyAddressesViewHolder(@NonNull View itemView, OnItemSelectedListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemSelectedListener = listener;
            rbSelectAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelectedPosition = getAdapterPosition();
                    notifyDataSetChanged();
                    rbSelectAddress.setChecked(true);
                    ibEditAddress.setVisibility(View.VISIBLE);
                    itemView.setBackgroundColor(Color.parseColor("#E0E0E0"));
                    itemSelectedListener.onItemSelected(mItem);
                    myAddressItemClickListner.onClick(itemView, getAdapterPosition(), mItem);

                }
            });


        }

    }



}



