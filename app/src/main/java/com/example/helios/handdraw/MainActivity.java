package com.example.helios.handdraw;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helios.handdraw.adapter.BottomPagerAdapter;
import com.example.helios.handdraw.utils.FileUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.io.File;

public class MainActivity extends BaseMainActivity implements View.OnClickListener{

    private static final String TAG = "HandDraw";
    private static final int REQUEST_CODE_TAKE_PHOTO = 0x01;
    private static final int REQUEST_CODE_GET_PHOTO = 0x02;
    private static final int REQUEST_CODE_CLIP_PHOTO = 0x03;
    private PopupWindow mPopup;
    private RelativeLayout mFabLayout;
    private LinearLayout mToolLayout;
    private ViewPager mViewpager;
    private LinearLayout mViewpagerLayout;
    private boolean mPopStatus,mfabStatus,mToolStatus,mViewpagerStatus;
    private String mPicPath;
    private ImageView mImageView;
    @ViewInject(R.id.id_watermaker)
    private TextView waterMaker;
    @ViewInject(R.id.id_blackwhite)
    private TextView blackWhite;
    @ViewInject(R.id.id_clipper)
    private TextView clip;
    @ViewInject(R.id.id_more)
    private TextView more;
    @ViewInject(R.id.viewPager_layout1)
    private LinearLayout mViewpagerLayout1;
    @ViewInject(R.id.id_viewpager1)
    private ViewPager mViewpager1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //add inject
        ViewUtils.inject(this);
//        new BaseApplication().addActivity(this);
        mPicPath = FileUtils.getPath(this,"cam_pic")+"HandDraw.jpg";
        getToolbarHelper().showAsUpEnable(false);
        getToolbarHelper().getTitle().setText("HandDraw");
        initView();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStataus(false,false,true,false);
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
        mImageView = (ImageView) findViewById(R.id.id_image);
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
        Log.d(TAG,"resultcode = "+resultCode + ", "+Activity.RESULT_OK + ", "+RESULT_CANCELED);
        if(resultCode != Activity.RESULT_OK){
            return ;
        }
        if(requestCode == REQUEST_CODE_TAKE_PHOTO){
            if(mPicPath != null){
                clipPhoto(mPicPath);
            }
        }else if(requestCode == REQUEST_CODE_CLIP_PHOTO){
            Log.d(TAG,"request cod clip photo");
            //获得剪切后的图片
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(mPicPath,options);
            if(bitmap == null){
                Log.d(TAG,"get clip photo is null");
            }else{
                mImageView.setImageBitmap(bitmap);
            }
        }else if(REQUEST_CODE_GET_PHOTO == requestCode){
            if(data == null){
                return ;
            }
            Uri selectedImage = data.getData();
            String selectPath = null;
            if(selectedImage == null){
                return ;
            }
//            try{
                Log.d(TAG,"select path uri = "+selectedImage.toString());
                if (selectedImage.toString().startsWith("file://")) {
                    selectPath = selectedImage.toString().replace("file://", "");
                } else {
                    selectPath = FileUtils.getPath(this, selectedImage);
                }
                if(selectPath != null){
                    clipPhoto(selectPath);
                }
//            }catch(Exception e){
//                throw new RuntimeException("query select photo failed !");
//            }
        }
    }

    /**
     * 图片剪切
     * @param path
     */
    private void clipPhoto(String path) {
        String newPath = null;

        if (path.contains("%")) {
            try {
                newPath = FileUtils.DecodeURL(path);
            } catch (Exception e) {
                newPath = null;
            }
            if (newPath != null) {
                path = newPath;
            }
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(Uri.fromFile(new File(path)),"image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 1000);
        intent.putExtra("outputY", 1000);
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("return-data", false);
        intent.putExtra("output", Uri.fromFile(new File(mPicPath)));
        startActivityForResult(intent,REQUEST_CODE_CLIP_PHOTO);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.tv_take:
                Intent camIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                mPicPath = FileUtils.getPath(MainActivity.this,"cam_pic") +
                        System.currentTimeMillis() +".jpg";
                File photo = new File(mPicPath);
                camIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
                startActivityForResult(camIntent,REQUEST_CODE_TAKE_PHOTO);
                updateStataus(false,true,false,false);
                break;
            case R.id.tv_choice:
                Intent choiceIntent = new Intent(Intent.ACTION_GET_CONTENT,null);
                choiceIntent.setType("image/*");
                startActivityForResult(Intent.createChooser(choiceIntent,"select photo"),REQUEST_CODE_GET_PHOTO);
                updateStataus(false,true,false,false);
                break;
            case R.id.tv_cancel:
                updateStataus(false,true,false,false);
                break;

        }
    }
    @OnClick({R.id.id_more,R.id.id_clipper,R.id.id_watermaker,R.id.id_blackwhite})
    public void testButtonClick(View view){
        int id = view.getId();
        switch (id){
            case R.id.id_watermaker:
                Toast.makeText(MainActivity.this,"watermark",Toast.LENGTH_SHORT).show();
                updateStataus(false,false,false,true);
                BottomPagerAdapter adapter = new BottomPagerAdapter(MainActivity.this);
                mViewpager.setAdapter(adapter);
                break;
            case R.id.id_blackwhite:
                break;
            case R.id.id_clipper:
                break;
            case R.id.id_more:
                break;
        }

    }
}
