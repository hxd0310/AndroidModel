package com.bry.ankle.androidmodel.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.bry.ankle.androidmodel.Fragment.DemoNavigationFragment;
import com.bry.ankle.androidmodel.R;
import com.bry.ankle.androidmodel.Utils.Constant;
import com.bry.ankle.androidmodel.Widget.NavigationViews;

import java.util.ArrayList;
import java.util.List;

/**
 * 带有顶部和底部导航栏的Activity
 * Created by ankle on 16/3/16.
 */
public class NavigationBarActivity extends AppCompatActivity implements NavigationViews.OnNavigationClickListener {

    private static final String FRAGMENT_TAG_01 = "fragment_tag_01";
    private static final  String FRAGMENT_TAG_02 = "fragment_tag_02";
    private static final String FRAGMENT_TAG_03 = "fragment_tag_03";
    private static final String FRAGMENT_TAG_04 = "fragment_tag_04";
    private static final String[] FRAGMENT_TAGS = {FRAGMENT_TAG_01, FRAGMENT_TAG_02, FRAGMENT_TAG_03, FRAGMENT_TAG_04};

    private NavigationViews mTopView;
    private NavigationViews mBottomView;

    private Fragment mCurrentFragment;

    private List<NavigationViews.OnNavigationClickListener> navigationClickListeners = new ArrayList<>();

    private String mCurrentTopTag;
    private String mCurrentBottomTag;

    public static void startActivity(Activity activity, String fragment) {
        Bundle args = new Bundle();
        args.putString(Constant.INTENT_FRAGMENT, fragment);
        Intent intent = new Intent(activity, NavigationBarActivity.class);
        intent.putExtras(args);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_bar);
        initView();
    }

    private void initView() {
        Bundle args = getIntent().getExtras();
        showFragment(args.getString(Constant.INTENT_FRAGMENT), true);
        mTopView = (NavigationViews) findViewById(R.id.top_navigation);
        mBottomView = (NavigationViews) findViewById(R.id.bottom_navigation);
        addNavigationViews(mTopView, 1);
        addNavigationViews(mBottomView, 2);
        mTopView.setOnNavigationClickListener(this);
        mBottomView.setOnNavigationClickListener(new NavigationViews.OnNavigationClickListener() {
            @Override
            public void onNavigationClick(View v, int position) {
                showFragment(FRAGMENT_TAGS[position], false);
            }
        });
    }

    private void showFragment(String tag, boolean fromSave) {
        if(TextUtils.isEmpty(tag)) {
            tag = FRAGMENT_TAG_01;
        }
        if(mCurrentBottomTag == tag) {
            return;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);

        if(null == fragment) {
            switch (tag) {
                case FRAGMENT_TAG_01:
                    fragment = DemoNavigationFragment.newInstance("fragment-0");
                    break;
                case FRAGMENT_TAG_02:
                    fragment = DemoNavigationFragment.newInstance("fragment-1");
                    break;
                case FRAGMENT_TAG_03:
                    fragment = DemoNavigationFragment.newInstance("fragment-2");
                    break;
                case FRAGMENT_TAG_04:
                    fragment = DemoNavigationFragment.newInstance("fragment-3");
                    break;
                default:
                    break;
            }
            transaction.add(R.id.container, fragment, tag);
        } else {
            if(fragment.isHidden()) {
                transaction.show(fragment);
            }
            if(fragment.isDetached()) {
                transaction.attach(fragment);
            }
        }

        if(!fromSave) {
            if(null != mCurrentFragment && fragment != mCurrentFragment) {
                transaction.hide(mCurrentFragment);
            }
        }
        if(fromSave) {
            for(String t : FRAGMENT_TAGS) {
                Fragment f = fragmentManager.findFragmentByTag(t);
                if(null != f && f != fragment) {
                    transaction.hide(f);
                }
            }
        }

        mCurrentFragment = fragment;
        mCurrentBottomTag = tag;

        transaction.commitAllowingStateLoss();
    }

    private void addNavigationViews(NavigationViews navigationViews, int flag) {
        List<View> textViews = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            TextView textView = new TextView(this);
            textView.setText("tab" + flag + "-" + i);
            textViews.add(textView);
            textView.setGravity(Gravity.CENTER);
            if(i == 0 || i == 2) {
                textView.setBackgroundResource(R.color.colorAccent);
            }
        }
        navigationViews.addViews(textViews);
    }

    public void addNavigationClickListener(NavigationViews.OnNavigationClickListener listener) {
        navigationClickListeners.add(listener);
    }

    public String getCurrentTopTag() {
        if(null == mCurrentTopTag) {
            mCurrentTopTag = FRAGMENT_TAG_01;
        }
        return mCurrentTopTag;
    }

    @Override
    public void onNavigationClick(View v, int position) {
        mCurrentTopTag = FRAGMENT_TAGS[position];
        for(NavigationViews.OnNavigationClickListener listener : navigationClickListeners) {
            listener.onNavigationClick(v, position);
        }
    }

}
