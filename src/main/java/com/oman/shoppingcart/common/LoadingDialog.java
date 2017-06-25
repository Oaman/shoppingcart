package com.oman.shoppingcart.common;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by 15936 on 2017/3/3.
 */

public class LoadingDialog extends ProgressDialog {
    public LoadingDialog(Context context) {
        super(context);
    }

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
    }

}
