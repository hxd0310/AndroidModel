package com.bry.ankle.androidmodel.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * 测试导航栏Fragment
 * Created by ankle on 16/3/23.
 */
public class DemoNavigationFragment extends BaseNavigationFragment {

    public static final String INTENT_TITLE = "intent_title";

    public static DemoNavigationFragment newInstance(String title) {
        DemoNavigationFragment fragment = new DemoNavigationFragment();
        Bundle args = new Bundle();
        args.putString(INTENT_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected Fragment createdFirstFragment() {
        return DemoFragment.newInstance(getArguments().getString(INTENT_TITLE) + "-0");
    }

    @Override
    protected Fragment createdSecondFragment() {
        return DemoFragment.newInstance(getArguments().getString(INTENT_TITLE) + "-1");
    }

    @Override
    protected Fragment createdThirdFragment() {
        return DemoFragment.newInstance(getArguments().getString(INTENT_TITLE) + "-2");
    }

    @Override
    protected Fragment createdFourthFragment() {
        return DemoFragment.newInstance(getArguments().getString(INTENT_TITLE) + "-3");
    }

}
