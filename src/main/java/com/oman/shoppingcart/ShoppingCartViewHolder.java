package com.oman.shoppingcart;

import android.graphics.Paint;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.oman.shoppingcart.common.TemplateViewHolder;

/**
 * Created by 15936 on 2017/3/2.
 */

public class ShoppingCartViewHolder extends TemplateViewHolder {
    public ImageView imageView;
    public TextView nameView;
    public TextView priceView;
    public TextView originPriceView;
    public TextView vipPriceView;
    public AppCompatCheckBox checkBox;
    public TextView sumPriceView;
    public TextView amountView;
    public View addView;
    public View minusView;
    public View rootView;

    public ShoppingCartViewHolder(View view, final OnViewClickListener listener) {
        super(view);

        rootView = view.findViewById(R.id.shoppingcart_item_root_view);
        imageView = (ImageView) view.findViewById(R.id.shoppingcart_commodity_image);
        nameView = (TextView) view.findViewById(R.id.shoppingcart_commodity_name);
        priceView = (TextView) view.findViewById(R.id.shoppingcart_commodity_price);
        originPriceView = (TextView) view.findViewById(R.id.shoppingcart_commodity_origin_price);
        vipPriceView = (TextView) view.findViewById(R.id.shoppingcart_commodity_vip_price);
        checkBox = (AppCompatCheckBox) view.findViewById(R.id.shoppingcart_commodity_checkbox);
        sumPriceView = (TextView) view.findViewById(R.id.shoppingcart_commodity_sum_price);
        amountView = (TextView) view.findViewById(R.id.shoppingcart_commodity_amount);
        addView = view.findViewById(R.id.shoppingcart_commodity_amount_calculator_add);
        minusView = view.findViewById(R.id.shoppingcart_commodity_amount_calculator_minus);

        // 添加删除线效果
        Paint paint = originPriceView.getPaint();
        paint.setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        paint.setAntiAlias(true);

        addView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onAmountAddClick(getAdapterPosition());
                }
            }
        });
        minusView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onAmountMinusClick(getAdapterPosition());
                }
            }
        });

    }

    public interface OnViewClickListener {

        void onAmountAddClick(int position);

        void onAmountMinusClick(int position);
    }
}
