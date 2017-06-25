package com.oman.shoppingcart.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 *  on 16/3/27.  List适配器模板  有三个方法
 */
public abstract class TemplateListAdapter<T, E extends TemplateViewHolder> extends BaseAdapter {

    protected List<T> mData;

    protected Context mContext;

    protected LayoutInflater mInflater;

    public TemplateListAdapter(Context context, List<T> data) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        E holder;
        if (convertView == null) {
            convertView = mInflater.inflate(getLayoutId(), null);
            holder = getViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (E) convertView.getTag();
        }

        bindData(position, holder);

        return convertView;
    }

    public abstract int getLayoutId();

    public abstract E getViewHolder(View convertView);

    public abstract void bindData(int position, E holder);

}
