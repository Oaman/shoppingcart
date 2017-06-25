package com.oman.shoppingcart;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;

import com.oman.shoppingcart.common.BaseActivity;
import com.oman.shoppingcart.customview.ShoppingCartBottomView;
import com.oman.shoppingcart.model.CartCommodityModel;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    protected ShoppingCartBottomView mBottomView;

    protected ListView mListView;
    protected ShoppingCartListAdapter mAdapter;
    protected ArrayList<CartCommodityModel> mData = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        CartCommodityModel model = new CartCommodityModel();
        model.setId(1);
        model.setName("测试商品1");
        model.setPrice("100");
        model.setOriginalPrice("120");
        model.setVipPrice("80");
        model.setBuyNum("2");
        mData.add(model);

        CartCommodityModel model2 = new CartCommodityModel();
        model2.setId(1);
        model2.setName("测试商品2");
        model2.setPrice("200");
        model2.setOriginalPrice("220");
        model2.setVipPrice("160");
        model2.setBuyNum("3");
        mData.add(model2);

        CartCommodityModel model3 = new CartCommodityModel();
        model3.setId(1);
        model3.setName("测试商品3");
        model3.setPrice("300");
        model3.setOriginalPrice("340");
        model3.setVipPrice("280");
        model3.setBuyNum("4");
        mData.add(model3);
        mBottomView = (ShoppingCartBottomView) findViewById(R.id.shoppingcart_bottom);
        mAdapter = new ShoppingCartListAdapter(this, mData, mBottomView);

        mBottomView.setData(mData);
        mBottomView.setBinderAdapter(mAdapter);
        mListView= (ListView) findViewById(R.id.listview);
        mListView.setAdapter(mAdapter);

        mBottomView.setOnConfirmListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,PaymentActivity.class));
            }
        });
    }
}
