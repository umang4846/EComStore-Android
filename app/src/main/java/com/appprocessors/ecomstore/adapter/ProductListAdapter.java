package com.appprocessors.ecomstore.adapter;

import androidx.paging.PagedListAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.appprocessors.ecomstore.activities.ProductDetailsActivity;
import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.interfaces.IItemClickListner;
import com.appprocessors.ecomstore.model.Content;
import com.appprocessors.ecomstore.paging.ProductListViewModel;
import com.appprocessors.ecomstore.paging.Resource;
import com.appprocessors.ecomstore.utils.Common;
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.squareup.picasso.Picasso;

import org.fabiomsr.moneytextview.MoneyTextView;

import java.math.BigDecimal;
import java.text.Format;
import java.text.NumberFormat;
import java.util.Locale;

public class ProductListAdapter extends PagedListAdapter<Content, RecyclerView.ViewHolder> {

    Context context;
    String TAG = "ProductListAdapter";

    private Resource resource = null;

    private ProductListViewModel mViewModel;

    private GridLayoutManager mLayoutManager;


    public static final int SPAN_COUNT_ONE = 1;
    public static final int SPAN_COUNT_TWO = 2;

    private static final int VIEW_TYPE_SMALL = 1;
    private static final int VIEW_TYPE_BIG = 2;

    Intent intent;

    public ProductListAdapter(Context context, GridLayoutManager layoutManager , ProductListViewModel viewModel) {
        super(DIFF_CALL_BACK);
        this.context = context;
        mLayoutManager = layoutManager;
        mViewModel = viewModel;

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        if (viewType == R.layout.item_progress) {
            view = LayoutInflater.from(context).inflate(R.layout.item_progress, parent, false);
            return new NetworkStateItemViewHolder(view, mViewModel );
        } else if (viewType == VIEW_TYPE_BIG) {
            view = LayoutInflater.from(context).inflate(R.layout.product_item_layout, parent, false);
            return new ProductListViewHolder(view, viewType);
        } else if (viewType == VIEW_TYPE_SMALL) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout_grid, parent, false);
            return new ProductListViewHolder(view, viewType);
        } else {
            throw new IllegalArgumentException("unknown view type");

        }


    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

        if (getItemViewType(position) == VIEW_TYPE_BIG) {

            ((ProductListViewHolder) holder).bindTo(getItem(position), position);

        } else if (getItemViewType(position) == VIEW_TYPE_SMALL) {

            ((ProductListViewHolder) holder).bindTo(getItem(position), position);

        } else if (getItemViewType(position) == R.layout.item_progress) {
            ((NetworkStateItemViewHolder) holder).bindView(resource);

        }

    }

    public static String getIndianRupee(String value) {
        Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
        return format.format(new BigDecimal(value));
    }


    private static DiffUtil.ItemCallback<Content> DIFF_CALL_BACK =
            new DiffUtil.ItemCallback<Content>() {
                @Override
                public boolean areItemsTheSame(@NonNull Content productDetails, @NonNull Content newproductDetails) {
                    return productDetails.getId().equals(newproductDetails.getId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Content productDetails, @NonNull Content newproductDetails) {
                    return productDetails.equals(newproductDetails);
                }
            };


    class ProductListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        ImageView img;
        TextView product_name, rating, total_rating, mrp, product_color;
        MoneyTextView price, discount;

        IItemClickListner itemClickListner;


        public void bindTo(Content content, int position) {
            if (content != null) {
                intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("productCode",content.getProductCode());

                product_name.setText(content.getProductName());
                price.setAmount(Float.parseFloat(content.getPrice()));
                mrp.setText(getIndianRupee(content.getMrp()));
                mrp.setPaintFlags(mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                discount.setAmount((float) Common.DiscountInPercentage(content.getMrp(), content.getPrice()));
                if (Common.DiscountInPercentage(content.getMrp(), content.getPrice()) <= 0) {
                    mrp.setVisibility(View.GONE);
                    discount.setVisibility(View.GONE);
                } else {
                    mrp.setVisibility(View.VISIBLE);
                    discount.setVisibility(View.VISIBLE);
                }
                if (content.getPrice().equalsIgnoreCase(content.getMrp())) {
                    mrp.setVisibility(View.GONE);
                    discount.setVisibility(View.GONE);
                } else {
                    mrp.setVisibility(View.VISIBLE);
                    discount.setVisibility(View.VISIBLE);
                }

                Picasso.get().load(content.getImageMain()).into(img);
                product_name.setText(content.getProductName());

                if (getItemViewType() == VIEW_TYPE_BIG) {

                    if (Common.DiscountInPercentage(content.getMrp(), content.getPrice()) <= 0) {
                        mrp.setVisibility(View.GONE);
                        discount.setVisibility(View.GONE);
                    } else {
                        mrp.setVisibility(View.VISIBLE);
                        discount.setVisibility(View.VISIBLE);
                    }
                    final ImagePopup imagePopup = new ImagePopup(context);
                    imagePopup.setBackgroundColor(Color.BLACK);
                    imagePopup.setHideCloseIcon(false);
                    imagePopup.setFullScreen(false);
                    imagePopup.setBackgroundColor(context.getResources().getColor(R.color.white_smoke));
                    imagePopup.setImageOnClickClose(true);
                    product_color.setText(content.getSoldBy());
                    rating.setText(content.getProductAverageRating());
                   /* imagePopup.initiatePopupWithPicasso(content.getImageMain());
                    img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            imagePopup.viewPopup();
                        }
                    });*/

                }
                else {
                    if (Common.DiscountInPercentage(content.getMrp(), content.getPrice()) <= 0) {
                        mrp.setVisibility(View.GONE);
                        discount.setVisibility(View.GONE);
                    } else {
                        mrp.setVisibility(View.VISIBLE);
                        discount.setVisibility(View.VISIBLE);
                    }
                }
            }
        }

        public ProductListViewHolder(View itemView, int viewType) {
            super(itemView);

            if (viewType == 2) {
                product_name = itemView.findViewById(R.id.tv_name_product_list);
                img = itemView.findViewById(R.id.iv_product_list);
                rating = itemView.findViewById(R.id.tv_rating_product_list);
                total_rating = itemView.findViewById(R.id.tv_total_rating_product_list);
                mrp = itemView.findViewById(R.id.tv_mrp_product_list);
                price = itemView.findViewById(R.id.tv_price_product_list);
                discount = itemView.findViewById(R.id.tv_discount_product_list);
                product_color = itemView.findViewById(R.id.tv_color_product_list);
            } else {
                product_name = itemView.findViewById(R.id.tv_name_product_list);
                img = itemView.findViewById(R.id.iv_product_list);
                mrp = itemView.findViewById(R.id.tv_mrp_product_list);
                price = itemView.findViewById(R.id.tv_price_product_list);
                discount = itemView.findViewById(R.id.tv_discount_product_list);
            }


            itemView.setOnClickListener(v -> {
                //Start Product Details Activity
                context.startActivity(intent);
            });


        }


        @Override
        public void onClick(View v) {
            itemClickListner.onClick(v);
        }


    }

    public class NetworkStateItemViewHolder extends RecyclerView.ViewHolder {

        private final ProgressBar progressBar;
        private final TextView errorMsg;
        private Button retryButton;

        public NetworkStateItemViewHolder(View itemView, final ProductListViewModel viewModel) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progress_bar);
            errorMsg = itemView.findViewById(R.id.error_msg);
            retryButton = itemView.findViewById(R.id.retry_button);
          // Trigger retry event on click
            retryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewModel.retry();
                }
            });
        }


        public void bindView(Resource networkState) {
            if (networkState != null && networkState.status == Resource.Status.LOADING) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }

            if (networkState != null && networkState.status == Resource.Status.ERROR) {
                errorMsg.setVisibility(View.VISIBLE);
                errorMsg.setText(networkState.message);
                retryButton.setVisibility(View.VISIBLE);
            } else {
                errorMsg.setVisibility(View.GONE);
                retryButton.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        int spanCount = mLayoutManager.getSpanCount();
        if (hasExtraRow() && position == getItemCount() - 1) {
            return R.layout.item_progress;
        } else if (spanCount == SPAN_COUNT_ONE) {
            return VIEW_TYPE_BIG;
        } else {
            return VIEW_TYPE_SMALL;
        }

    }


    public void setNetworkState(Resource resource) {
        Resource previousState = this.resource;
        boolean hadExtraRow = hasExtraRow();
        this.resource = resource;
        boolean hasExtraRow = hasExtraRow();
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount());
            } else {
                notifyItemInserted(super.getItemCount());
            }
        } else if (hasExtraRow && !previousState.equals(resource)) {
            notifyItemChanged(getItemCount() - 1);
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + (hasExtraRow() ? 1 : 0);
    }

    private boolean hasExtraRow() {
        return resource != null && resource.status != Resource.Status.SUCCESS;
    }
}