package com.oman.shoppingcart.customview;

import android.content.Context;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oman.shoppingcart.R;
import com.oman.shoppingcart.ShoppingCartListAdapter;
import com.oman.shoppingcart.model.CartCommodityModel;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by 15936 on 2017/3/2.
 */

public class ShoppingCartBottomView extends LinearLayout {

    protected AppCompatCheckBox mCheckBox;
    protected TextView mSelectedNumberView;
    protected TextView mSumPriceView;
    protected TextView mDiscountPriceView;

    protected List<CartCommodityModel> mData;
    protected ShoppingCartListAdapter mBinderAdapter;

    protected OnClickListener mListener;

    private DecimalFormat mDecimalFormat = new DecimalFormat("##0.00");

    public ShoppingCartBottomView(Context context) {
        super(context);
        initView();
    }

    public ShoppingCartBottomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ShoppingCartBottomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void setData(List<CartCommodityModel> data) {
        mData = data;
    }

    public void setBinderAdapter(ShoppingCartListAdapter adapter) {
        mBinderAdapter = adapter;
    }

    protected void initView() {
        View view = View.inflate(getContext(), R.layout.view_shoppingcart_bottom, null);
        mCheckBox = (AppCompatCheckBox) view.findViewById(R.id.shoppingcart_bottom_checkbox);
        mSelectedNumberView = (TextView) view.findViewById(R.id.shoppingcart_bottom_selected_number);
        mSumPriceView = (TextView) view.findViewById(R.id.shoppingcart_bottom_sum_price);
        mDiscountPriceView = (TextView) view.findViewById(R.id.shoppingcart_bottom_discount_price);
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                for (CartCommodityModel model : mData) {
                    model.setSelected(isChecked);
                }
                if (mBinderAdapter != null) {
                    mBinderAdapter.notifyDataSetChanged();
                }
            }
        });
        view.findViewById(R.id.shoppingcart_bottom_confirm).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onClick(v);
                }
            }
        });
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        addView(view);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }


    private void updateNumberView(int number) {
        mSelectedNumberView.setText("(" + number + ")");
    }

    private void updateSumPriceView(float sumPrice) {
        mSumPriceView.setText("合计: " + mDecimalFormat.format(sumPrice) + "元");
    }

    private void updateDiscountView(float discount) {
        mDiscountPriceView.setText("已优惠: " + mDecimalFormat.format(discount) + "元");
    }


    public void setOnConfirmListener(OnClickListener listener) {
        mListener = listener;
    }


    public void notifyDataSetChanged() {
        if (mData != null) {
            float sumPrice = 0;
            float originalPrice = 0;
            int sumCount = 0;
            for (CartCommodityModel model : mData) {
                if (model.isSelected()) {
                    sumPrice += Float.valueOf(model.getPrice()) * Integer.valueOf(model.getBuyNum());
                    originalPrice += Float.valueOf(model.getOriginalPrice()) * Integer.valueOf(model.getBuyNum());
                    sumCount += Integer.valueOf(model.getBuyNum());
                }
            }
            updateNumberView(sumCount);
            updateSumPriceView(sumPrice);
            updateDiscountView(originalPrice - sumPrice);
        }
    }

}
