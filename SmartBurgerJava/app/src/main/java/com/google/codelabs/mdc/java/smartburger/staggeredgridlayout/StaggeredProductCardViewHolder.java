package com.google.codelabs.mdc.java.smartburger.staggeredgridlayout;

import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.google.codelabs.mdc.java.smartburger.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StaggeredProductCardViewHolder extends RecyclerView.ViewHolder {

    public NetworkImageView productImage;
    public TextView productTitle;
    public TextView productPrice;

    StaggeredProductCardViewHolder(@NonNull View itemView) {
        super(itemView);
        productImage = itemView.findViewById(R.id.product_image);
        productTitle = itemView.findViewById(R.id.product_title);
        productPrice = itemView.findViewById(R.id.product_price);
    }
}
