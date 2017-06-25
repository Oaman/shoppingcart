# shoppingcart

直接上代码。

1 首先需要一个显示的界面MainActivity;xml文件如下,其中ShoppingCartBottomView是简单自定义的一个底部控件，下面会讲到

<com.oman.shoppingcart.customview.ShoppingCartBottomView
    android:id="@+id/shoppingcart_bottom"
    android:layout_width="match_parent"
    android:layout_height="60dp"
    android:layout_alignParentBottom="true"/>

<ListView
    android:id="@+id/listview"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:layout_above="@id/shoppingcart_bottom"
    android:layout_marginTop="10dp"
    android:dividerHeight="10dp"/>

然后MainActivity.ava的代码如下，不要着急有些类没有讲到，后面会详细讲解，淡定。。。

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

2 这里面用到了几个类 ShoppingCartBottomView（底部的一些操作按钮） ShoppingCartListAdapter（填充商品的适配器） CartCommodityModel（商品的model）

ShoppingCartViewHolder（这个就不用说了吧。。。你懂的）

在hoppingCartViewHolder中我写了一个接口，用来监听商品的数量变化的接口，当点击加减号的时候就会回调；

addView.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View view) { if (listener != null) { listener.onAmountAddClick(getAdapterPosition()); } } }); minusView.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View view) { if (listener != null) { listener.onAmountMinusClick(getAdapterPosition()); } } });

}

public interface OnViewClickListener {

    void onAmountAddClick(int position);

    void onAmountMinusClick(int position);
}

3 当然了，在ShoppingCartListAdapter中需要实现这个接口，那么就要实现他的方法，点击加号或者减号的时候要将商品的数量相应的加减1（当商品的数量是大于等于1的时候 才能减），当点击小计前面的CheckBox按钮时候需要监听中实现该model是否选中该商品，然后更新一下适配器（不过这里我自己重写了一下notifyDataSetChanged()方法，见 下面代码）；我还封装了两个抽象类，一个是适配器继承的类TemplateListAdapter，还有一个是Activity继承的类BaseActivity，在项目中挺实用的；

public class ShoppingCartListAdapter extends TemplateListAdapter<CartCommodityModel, ShoppingCartViewHolder> implements ShoppingCartViewHolder.OnViewClickListener {

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

4 我认为这里面最主要的就属于ShoppingCartBottomView这个类了，下面详细讲解一下

当点击全选按钮时候，需要将每一个model的选中状态和全选按钮一致，然后更新一下各个控件的显示（需要写几个方法用来在notifyDataSetChanged()中调用即可）

当点击去结算按钮时候就会跳转到付款页面

有没有被我的详细讲解惊艳到，哈哈，是不是很简单

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

这样的话这个完整的小购物车项目基本上就结束了
