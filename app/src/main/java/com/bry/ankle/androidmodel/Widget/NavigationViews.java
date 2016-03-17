package com.bry.ankle.androidmodel.Widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Tab导航控件
 * Created by ankle on 16/1/29.
 */
@SuppressWarnings("unused")
public class NavigationViews extends LinearLayout {

    private Context mContext;

    private List<View> mViews;

    private OnTabClickListener onTabClickListener;

    public NavigationViews(Context context) {
        this(context, null);
    }

    public NavigationViews(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavigationViews(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
    }

    private void initViews(int size) {
        if(null == mViews) {
            mViews = new ArrayList<>(size);
        } else {
            mViews.clear();
        }
    }

    private void addViews() {
        LinearLayout.LayoutParams params;
        switch (getOrientation()) {
            case HORIZONTAL:
                params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
                break;
            case VERTICAL:
                params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 0);
                break;
            default:
                params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
                break;
        }
        params.weight = 1;
        for(int i = 0; i < mViews.size(); i++) {
            View view = mViews.get(i);
            view.setTag(i);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(null !=  onTabClickListener) {
                        onTabClickListener.onClickTab(v, (int) v.getTag());
                    }
                }
            });
            addView(view, params);
        }
    }

    public void addViews(int... resIds) {
        if(resIds.length <=0) {
            return;
        }
        initViews(resIds.length);
        for(int id : resIds) {
            View view = LayoutInflater.from(mContext).inflate(id, this, true);
            mViews.add(view);
        }
        addViews();
    }

    public void addViews(View... views) {
        if(views.length <=0) {
            return;
        }
        initViews(views.length);
        Collections.addAll(mViews, views);
        addViews();
    }

    public void addViews(List<View> views) {
        if(null == views || views.size() <=0) {
            return;
        }
        initViews(views.size());
        mViews.addAll(views);
        addViews();
    }

    public void setOnTabClickListener (OnTabClickListener onTabClickListener) {
        this.onTabClickListener = onTabClickListener;
    }

    public interface OnTabClickListener {
        void onClickTab(View v, int position);
    }

}
