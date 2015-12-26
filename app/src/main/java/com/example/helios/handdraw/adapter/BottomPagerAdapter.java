package com.example.helios.handdraw.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.helios.handdraw.R;
import com.example.helios.handdraw.contants.Contants;
import com.example.helios.handdraw.utils.ConfigUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by helios on 12/25/15.
 */
public class BottomPagerAdapter extends PagerAdapter{

    private static final String TAG = BottomPagerAdapter.class.getSimpleName();
    private List<Map<Integer , View>> mViewMap = new ArrayList<>();
    private Context mContext;
    @ViewInject(R.id.watermaker1)
    private TextView watermark1;
    @ViewInject(R.id.watermaker2)
    private TextView watermark2;
    @ViewInject(R.id.watermaker3)
    private TextView watermark3;
    @ViewInject(R.id.watermaker4)
    private TextView watermark4;
    private int mPosition;

    public BottomPagerAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return Contants.watermark_desc.length%4 == 0 ? Contants.watermark_desc.length/4 : Contants.watermark_desc.length/4+1;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        mPosition = position;
        View view = LayoutInflater.from(mContext).inflate(R.layout.bottom_pager_layout,null);
        ViewUtils.inject(this,view);
        Drawable drawable1 = null,drawable2 = null,drawable3 = null ,drawable4 = null;
        if(position*Contants.BOTTOM_PAGER_SPAN >= Contants.watermark_desc.length){
            drawable1 = null;
        }else{
            drawable1 = mContext.getResources().getDrawable(ConfigUtils.getWatermarkList().get(position*Contants.BOTTOM_PAGER_SPAN)[0]);
            //you must invoke setBounds,the drawable will show
            drawable1.setBounds(0,0,drawable1.getMinimumWidth(),drawable1.getMinimumHeight());
        }
        Log.d(TAG,"position = "+position+", "+(drawable1 == null));

        if(position*Contants.BOTTOM_PAGER_SPAN +1 >= Contants.watermark_desc.length){
            drawable2 = null;
        }else{
            drawable2 = mContext.getResources().getDrawable(ConfigUtils.getWatermarkList().get(position*Contants.BOTTOM_PAGER_SPAN+1)[0]);
            drawable2.setBounds(0,0,drawable2.getMinimumWidth(),drawable2.getMinimumHeight());
        }
        if(position*Contants.BOTTOM_PAGER_SPAN + 2 >= Contants.watermark_desc.length){
            drawable3 = null;
        }else{
            drawable3 = mContext.getResources().getDrawable(ConfigUtils.getWatermarkList().get(position*Contants.BOTTOM_PAGER_SPAN+2)[0]);
            drawable3.setBounds(0,0,drawable3.getMinimumWidth(),drawable3.getMinimumHeight());
        }
        if(position*Contants.BOTTOM_PAGER_SPAN + 3>= Contants.watermark_desc.length){
            drawable4 = null;
        }else{
            drawable4 = mContext.getResources().getDrawable(ConfigUtils.getWatermarkList().get(position*Contants.BOTTOM_PAGER_SPAN+3)[0]);
            drawable4.setBounds(0,0,drawable4.getMinimumWidth(),drawable4.getMinimumHeight());
        }

        watermark1.setCompoundDrawables(null,drawable1,null,null);
        watermark1.setText(Contants.watermark_desc[position*Contants.BOTTOM_PAGER_SPAN ]);
        watermark2.setCompoundDrawables(null,drawable2,null,null);
        watermark2.setText(Contants.watermark_desc[position*Contants.BOTTOM_PAGER_SPAN +1]);
        watermark3.setCompoundDrawables(null,drawable3,null,null);
        watermark3.setText(Contants.watermark_desc[position*Contants.BOTTOM_PAGER_SPAN +2]);
        watermark4.setCompoundDrawables(null,drawable4,null,null);
        watermark4.setText(Contants.watermark_desc[position*Contants.BOTTOM_PAGER_SPAN +3]);
        container.addView(view);
        Map<Integer,View> viewMap = new HashMap<>();
        viewMap.put(position,view);
        mViewMap.add(viewMap);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Map<Integer,View> viewMap = mViewMap.get(position);
        if(viewMap.containsKey(position)){
            View view = viewMap.get(position);
            if(view != null){
                container.removeView(view);
            }
        }
    }

    @OnClick({R.id.watermaker1,R.id.watermaker2,R.id.watermaker3,R.id.watermaker4})
    public void testButtonClick(View view){
        int id = view.getId();
        switch (id){
            case R.id.watermaker1:

                break;
            case R.id.watermaker2:
                break;
            case R.id.watermaker3:
                break;
            case R.id.watermaker4:
                break;
        }
    }

}
