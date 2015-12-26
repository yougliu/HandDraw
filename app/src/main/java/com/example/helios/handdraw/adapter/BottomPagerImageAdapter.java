package com.example.helios.handdraw.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.helios.handdraw.R;
import com.example.helios.handdraw.contants.Contants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by helios on 12/26/15.
 */
public class BottomPagerImageAdapter extends PagerAdapter{

    private static final String TAG = BottomPagerImageAdapter.class.getSimpleName();
    private Context mContext;
    private int[] mResId;
    private List<Map<Integer,View>> mViewMapList = new ArrayList<>();
    private List<Map<Integer,ImageView>> mImageViewList = new ArrayList<>();

    public BottomPagerImageAdapter(Context context, int[] resId) {
        this.mContext = context;
        this.mResId = mResId;

    }

    @Override
    public int getCount() {
        return mResId.length == 0 ? 0 : mResId.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.bottom_pager_item_layout,null);
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.linear);
        for (int i = 0 ; i < Contants.BOTTOM_PAGER_SPAN ; i++){
            Map<Integer,ImageView> imageMap = new HashMap<>();
            ImageView imageView = new ImageView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setPadding(2,2,2,2);
            imageView.setImageAlpha(200);
            imageView.setLayoutParams(params);
            if(position*Contants.BOTTOM_PAGER_SPAN + i >= mResId.length){
                break;
            }
            imageView.setImageResource(mResId[position*Contants.BOTTOM_PAGER_SPAN + i]);
            imageMap.put(position*Contants.BOTTOM_PAGER_SPAN + i,imageView);
            mImageViewList.add(imageMap);
            layout.addView(imageView);

        }
        container.addView(view);
        Map<Integer,View> viewMap = new HashMap<>();
        viewMap.put(position,view);
        mViewMapList.add(viewMap);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewMapList.get(position).get(position));
    }
}
