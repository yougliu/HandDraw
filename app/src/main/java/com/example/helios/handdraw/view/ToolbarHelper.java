package com.example.helios.handdraw.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.helios.handdraw.R;

/**
 * Created by helios on 12/21/15.
 */
public class ToolbarHelper {

    private static final String TAG = ToolbarHelper.class.getSimpleName();
    private Context mContext;
    private LayoutInflater mInflater;
    private FrameLayout mContentView;//for content view
    private View mUserView;//add to content view
    private Toolbar mToolbar;
    private TextView mTitle;
    private TextView mBackTextView;
    private Drawable mDrawable;

    private static int[] attrs = {R.attr.windowActionBarOverlay,R.attr.actionBarSize};

    public ToolbarHelper(Context mContext, int layoutId) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        initContentView();//init content view
        initUserView(layoutId);//init user define layout
        initToolbar();
    }

    private void initToolbar() {
        View toolbarView = mInflater.inflate(R.layout.toolbar, mContentView);
        mToolbar = (Toolbar) toolbarView.findViewById(R.id.id_tool_bar);
        mBackTextView = (TextView) toolbarView.findViewById(R.id.toolbar_back_title);
        mTitle = (TextView) toolbarView.findViewById(R.id.toolbar_center_title);
        mTitle.setText("Title");
    }

    /**
     * for add the user define layout to contentview
     * consider topMargin for actionbar overlay
     * @param layoutId
     */
    private void initUserView(int layoutId) {
        mUserView = mInflater.inflate(layoutId,null);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        TypedArray array = mContext.getTheme().obtainStyledAttributes(attrs);
        boolean overlay = array.getBoolean(0,false);
        int actionbarSize = array.getDimensionPixelSize(1, (int) mContext.getResources().getDimension(R.dimen.abc_action_bar_default_height_material));
        array.recycle();
        params.topMargin = overlay ? 0 : actionbarSize;
        mUserView.setLayoutParams(params);
        mContentView.addView(mUserView);
    }

    /**
     * for init a framelayout content view
     */
    private void initContentView() {
        mContentView = new FrameLayout(mContext);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mContentView.setLayoutParams(params);
    }

    public Toolbar getToolbar(){
        return mToolbar;
    }

    public FrameLayout getContentView(){
        return mContentView;
    }

    public TextView getBackTextView(){
        return mBackTextView;
    }

    public TextView getTitle(){
        return mTitle;
    }

    public void SetDrawable(int resId){
        mDrawable = mContext.getResources().getDrawable(resId);
        if(mDrawable !=null){
            showAsUpEnable(true);
        }
    }

    public void SetDrawable(Bitmap bitmap){
        mDrawable = new BitmapDrawable(bitmap);
        if(mDrawable !=null){
            showAsUpEnable(true);
        }
    }

    public void setDrawable(Drawable drawable){
        this.mDrawable = drawable;
        if(mDrawable !=null){
            showAsUpEnable(true);
        }
    }

    public void showAsUpEnable(boolean flag){
        if(flag){
            mDrawable = mContext.getResources().getDrawable(R.drawable.head_arrow_back);
            mDrawable.setBounds(0,0,mDrawable.getMinimumWidth(),mDrawable.getMinimumHeight());
            mBackTextView.setCompoundDrawables(mDrawable,null,null,null);
        }else{
            mBackTextView.setCompoundDrawables(null,null,null,null);
        }
    }
}
