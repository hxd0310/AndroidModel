package com.bry.ankle.androidmodel.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bry.ankle.androidmodel.R;

/**
 * 测试Fragment
 * Created by ankle on 16/3/23.
 */
public class DemoFragment extends Fragment {

    public static final String INTENT_TITLE = "intent_title";

    public static DemoFragment newInstance(String title) {
        DemoFragment fragment = new DemoFragment();
        Bundle args = new Bundle();
        args.putString(INTENT_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_demo, null, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(null == getActivity()) {
            return;
        }
        initView(view);
    }

    private void initView(View view) {
        TextView tv = (TextView) view.findViewById(R.id.tv_demo);
        tv.setText(getArguments().getString(INTENT_TITLE));
    }

}
