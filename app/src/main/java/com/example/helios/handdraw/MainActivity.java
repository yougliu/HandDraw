package com.example.helios.handdraw;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends BaseMainActivity implements View.OnClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_CODE_TAKE_PHOTO = 0x01;
    private static final int REQUEST_CODE_GET_PHOTOT = 0x02;
    private PopupWindow mPopup;
    private RelativeLayout mFabLayout;
    private LinearLayout mToolLayout;
    private ViewPager mViewpager;
    private LinearLayout mViewpagerLayout;
    private boolean mPopStatus,mfabStatus,mToolStatus,mViewpagerStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        getToolbarHelper().showAsUpEnable(false);
        getToolbarHelper().getTitle().setText("HandDraw");
        initView();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Snackbar.make(v,"choose watemaker!",Snackbar.LENGTH_SHORT).setAction("Action",null).show();
//                mDialog.show(getFragmentManager(),"PICK_DIALOG");
                showPopupWindow();
            }
        });
    }

    private void initView() {
        mFabLayout = (RelativeLayout) findViewById(R.id.fab_layout);
        mToolLayout = (LinearLayout) findViewById(R.id.id_tools);
        mViewpagerLayout = (LinearLayout) findViewById(R.id.viewPager_layout);
        mViewpager = (ViewPager) findViewById(R.id.id_viewpager);
    }

    private void updateStataus(boolean popStatus, boolean fabStatus, boolean toolStatus, boolean viewpagerStatus){
        if(popStatus){
            showPopupWindow();
            mFabLayout.setVisibility(View.GONE);
            mToolLayout.setVisibility(View.GONE);
            mViewpagerLayout.setVisibility(View.GONE);
        }else if(fabStatus){
            hidePopupWindow();
            mFabLayout.setVisibility(View.VISIBLE);
            mToolLayout.setVisibility(View.GONE);
            mViewpagerLayout.setVisibility(View.GONE);
        }else if(toolStatus){
            hidePopupWindow();
            mFabLayout.setVisibility(View.GONE);
            mToolLayout.setVisibility(View.VISIBLE);
            mViewpagerLayout.setVisibility(View.GONE);
        }else if(viewpagerStatus){
            hidePopupWindow();
            mFabLayout.setVisibility(View.GONE);
            mToolLayout.setVisibility(View.GONE);
            mViewpagerLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * choose popupwindow
     */
    private void showPopupWindow() {
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popup_dialog_layout,null);
        TextView takePhoto = (TextView) view.findViewById(R.id.tv_take);
        TextView choosePhoto = (TextView) view.findViewById(R.id.tv_choice);
        TextView cancel = (TextView) view.findViewById(R.id.tv_cancel);
        takePhoto.setOnClickListener(this);
        choosePhoto.setOnClickListener(this);
        cancel.setOnClickListener(this);
        //you must use next order to create a popupwindow
        mPopup = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        mPopup.setBackgroundDrawable(dw);
        mPopup.setOutsideTouchable(true);
        mPopup.setFocusable(true);
        mPopup.showAtLocation(MainActivity.this.findViewById(R.id.main), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,0);
    }

    private void hidePopupWindow(){
        if(this != null && mPopup != null && mPopup.isShowing()){
            mPopup.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_OK){
            return ;
        }
        if(requestCode == REQUEST_CODE_TAKE_PHOTO){
            Toast.makeText(this,data.getData().toString(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.tv_take:
                Intent camIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                break;
            case R.id.tv_choice:
                Intent takePhotoIntent = new Intent(Intent.ACTION_PICK);
                takePhotoIntent.setType("image/*");
                startActivityForResult(takePhotoIntent,REQUEST_CODE_TAKE_PHOTO);
                break;
            case R.id.tv_cancel:
                updateStataus(false,true,false,false);
                break;
        }
    }
}
