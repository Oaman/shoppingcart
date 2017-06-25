package com.oman.shoppingcart.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


/**
 *  on 16/3/18.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public static final String TAG = "BaseActivity";

    protected LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        preInitView();
        initView();
        afterInitView();
    }

    protected abstract int getLayoutId();

    protected void preInitView() {

    }

    protected abstract void initView();

    protected void afterInitView() {

    }

    protected <T> T findView(int id) {
        return (T) findViewById(id);
    }


    public void showLoadingDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
        }
        mLoadingDialog.show();
    }

    public void showLoadingDialog(String message) {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
        }
        mLoadingDialog.setMessage(message);
        mLoadingDialog.show();
    }

    public void dismissLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }
}
