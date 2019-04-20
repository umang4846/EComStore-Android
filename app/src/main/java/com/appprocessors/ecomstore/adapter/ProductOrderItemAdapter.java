package com.appprocessors.ecomstore.adapter;

import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.activities.ActivityPayment;
import com.appprocessors.ecomstore.model.customer.ShoppingCartItems;
import com.appprocessors.ecomstore.model.order.OrderItem;
import com.appprocessors.ecomstore.utils.Common;
import com.squareup.picasso.Picasso;

import org.fabiomsr.moneytextview.MoneyTextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductOrderItemAdapter extends RecyclerView.Adapter<ProductOrderItemAdapter.CartViewHolder> {
    ActivityPayment activityPayment;
    List<ShoppingCartItems> shoppingCartItems;
    private int mSelectedIndex = 0;


    public ProductOrderItemAdapter(ActivityPayment activityPayment, List<ShoppingCartItems> ShoppingCartItems) {
        this.activityPayment = activityPayment;
        this.shoppingCartItems = ShoppingCartItems;

    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(activityPayment).inflate(R.layout.cart_item_layout, viewGroup, false);


        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, final int position) {

        String imageID = shoppingCartItems.get(position).getPictureDetails().get(0).get_id();
        String imageNmae = shoppingCartItems.get(position).getPictureDetails().get(0).getSeoFilename();
        String imageMimeType = shoppingCartItems.get(position).getPictureDetails().get(0).getMimeType().replace("image/", "").trim();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Common.IMAGE_BASE_URL).append(imageID).append("_").append(imageNmae).append("_415.").append(imageMimeType);
        //Load Image with Picasso
        Picasso.get().load(stringBuilder.toString()).placeholder(R.color.md_grey_300).into(cartViewHolder.ivCartImage);

        cartViewHolder.tvCartProductName.setText(shoppingCartItems.get(position).getProductDetails().getName());
        cartViewHolder.tvCartProductMrp.setText(String.valueOf(shoppingCartItems.get(position).getProductDetails().getOldPrice()));
        cartViewHolder.tvCartProductMrp.setPaintFlags(cartViewHolder.tvCartProductMrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        cartViewHolder.tvCartProductPrice.setAmount(Float.parseFloat(String.valueOf(shoppingCartItems.get(position).getProductDetails().getPrice())));

        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        //  ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item, list);

        ArrayAdapter dataAdapter = new ArrayAdapter<String>(activityPayment, android.R.layout.simple_spinner_dropdown_item, list) {
            public View getView(int position, View convertView, ViewGroup parent) {
                // Cast the spinner collapsed item (non-popup item) as a text view
                TextView tv = (TextView) super.getView(position, convertView, parent);

                // Set the text color of spinner item
                tv.setTextColor(Color.BLUE);

                // Return the view
                return tv;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                // Cast the drop down items (popup items) as text view
                TextView tv = (TextView) super.getDropDownView(position, convertView, parent);

                // Set the text color of drop down items
                tv.setTextColor(Color.BLACK);

                // If this item is selected item
                if (position == mSelectedIndex) {
                    // Set spinner selected popup item's text color
                    tv.setTextColor(Color.BLUE);
                }

                // Return the modified view
                return tv;
            }

        };
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cartViewHolder.spQuantity.setAdapter(dataAdapter);
        if (list.contains(String.valueOf(shoppingCartItems.get(position).getQuantity())))
            cartViewHolder.spQuantity.setSelection(shoppingCartItems.get(position).getQuantity() - 1);
        else
            Toast.makeText(activityPayment, "Quantity Error !!!", Toast.LENGTH_SHORT).show();
        cartViewHolder.spQuantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                mSelectedIndex = i;

                activityPayment.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        cartViewHolder.spQuantity.setSelection(cartViewHolder.spQuantity.getSelectedItemPosition());
                        activityPayment.currentOrderItems.get(position).setQuantity(Long.parseLong(cartViewHolder.spQuantity.getSelectedItem().toString()));
                        activityPayment.tvPriceRs.setAmount(cartViewHolder.totalOrderPrice());
                        activityPayment.tvTotalAmountPrice.setAmount(cartViewHolder.totalOrderPrice());
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                shoppingCartItems.get(position).setQuantity(1);

            }
        });

        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        cartViewHolder.spQuantity.setAdapter(dataAdapter);
        cartViewHolder.spQuantity.setSelected(true);
        cartViewHolder.ivCartRemove.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return shoppingCartItems.size();
    }


    class CartViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_cart_image)
        ImageView ivCartImage;
        @BindView(R.id.tv_quantity)
        TextView tvQuantity;
        @BindView(R.id.sp_quantity)
        Spinner spQuantity;
        @BindView(R.id.LL_Quantity)
        LinearLayout LLQuantity;
        @BindView(R.id.tv_cart_product_name)
        TextView tvCartProductName;
        @BindView(R.id.tv_cart_product_price)
        MoneyTextView tvCartProductPrice;
        @BindView(R.id.tv_cart_product_mrp)
        TextView tvCartProductMrp;
        @BindView(R.id.iv_cart_remove)
        ImageView ivCartRemove;


        public CartViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public long totalOrderPrice() {

            double totalPrice = 0;
            for (int i = 0; i < activityPayment.currentOrderItems.size(); i++) {
                totalPrice += activityPayment.currentOrderItems.get(i).getUnitPriceExclTax() * activityPayment.currentOrderItems.get(i).getQuantity();
            }
            return Math.round(totalPrice);
        }

    }


}

