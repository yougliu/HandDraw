package com.example.helios.handdraw;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.helios.handdraw.view.ToolbarHelper;

/**
 * Created by helios on 12/21/15.
 */
public class BaseMainActivity extends AppCompatActivity{

    private static final String TAG = BaseMainActivity.class.getSimpleName();
    private ToolbarHelper mToolbarHelper;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        mToolbarHelper = new ToolbarHelper(this,layoutResID);
        mToolbar = mToolbarHelper.getToolbar();
        setContentView(mToolbarHelper.getContentView());
        setSupportActionBar(mToolbar);
        mToolbar.setContentInsetsRelative(0,0);
    }

    protected ToolbarHelper getToolbarHelper(){
        return mToolbarHelper;
    }
}
