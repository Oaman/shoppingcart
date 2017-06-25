package com.oman.shoppingcart;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.oman.shoppingcart.common.TemplateListAdapter;
import com.oman.shoppingcart.customview.ShoppingCartBottomView;
import com.oman.shoppingcart.model.CartCommodityModel;

import java.text.DecimalFormat;
import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by 15936 on 2017/3/2.
 */

public class ShoppingCartListAdapter extends TemplateListAdapter<CartCommodityModel, ShoppingCartViewHolder>
        implements ShoppingCartViewHolder.OnViewClickListener {

    private DecimalFormat mDecimalFormat = new DecimalFormat("##0.00");

    private ShoppingCartBottomView mBottomView;

    public ShoppingCartListAdapter(Context context, List<CartCommodityModel> data, ShoppingCartBottomView mBottomView) {
        super(context, data);
        this.mBottomView = mBottomView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        mBottomView.notifyDataSetChanged();
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_shopping_cart;
    }

    @Override
    public ShoppingCartViewHolder getViewHolder(View convertView) {
        return new ShoppingCartViewHolder(convertView, this);
    }

    @Override
    public void bindData(int position, ShoppingCartViewHolder holder) {
        final CartCommodityModel model = getItem(position);
        holder.sumPriceView.setText(getSumPrice(model));
        holder.checkBox.setChecked(model.isSelected());
        holder.amountView.setText(model.getBuyNum());
        Picasso.with(mContext).load(R.mipmap.ic_launcher).into(holder.imageView);
        holder.nameView.setText(model.getName());
        holder.priceView.setText(model.getPrice());
        holder.originPriceView.setText(model.getOriginalPrice());
        holder.vipPriceView.setText(model.getVipPrice());
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "点击了" + model.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.setSelected(!model.isSelected());
                mBottomView.notifyDataSetChanged();
            }
        });
    }

    private String getSumPrice(CartCommodityModel model) {
        if (Integer.valueOf(model.getBuyNum()) != 0) {
            return "小计:" + (Integer.valueOf(model.getBuyNum())) * (Integer.valueOf(model.getPrice()));
        } else {
            return "小计：¥0.00";
        }

    }

    @Override
    public void onAmountAddClick(int position) {
        CartCommodityModel model = getItem(position);
        model.setBuyNum(String.valueOf(Integer.valueOf(model.getBuyNum()) + 1));
        notifyDataSetChanged();
    }

    @Override
    public void onAmountMinusClick(int position) {
        CartCommodityModel model = getItem(position);
        if (Integer.valueOf(model.getBuyNum()) > 1) {
            model.setBuyNum(String.valueOf(Integer.valueOf(model.getBuyNum()) - 1));
            notifyDataSetChanged();
        }
    }

}
