package com.oman.shoppingcart.common;

import android.view.View;
import android.widget.ListView;

/**
 *  on 16/3/27.
 */
public class TemplateViewHolder {

    protected View mConvertView;

    public TemplateViewHolder(View view) {
        mConvertView = view;
    }

    public int getAdapterPosition() {
        ListView parent = (ListView) mConvertView.getParent();
        return parent.getPositionForView(mConvertView);
    }

    protected <T> T findViewById(int id) {
        return (T) mConvertView.findViewById(id);
    }

}
