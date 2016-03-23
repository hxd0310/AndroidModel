package com.bry.ankle.androidmodel.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bry.ankle.androidmodel.Activity.NavigationBarActivity;
import com.bry.ankle.androidmodel.R;
import com.bry.ankle.androidmodel.Widget.NavigationViews;

/**
 * 带有顶部和底部导航栏的基类Fragment
 * Created by ankle on 16/3/16.
 */
public abstract class BaseNavigationFragment extends Fragment implements NavigationViews.OnNavigationClickListener {

    private static final String FRAGMENT_TAG_01 = "fragment_tag_01";
    private static final  String FRAGMENT_TAG_02 = "fragment_tag_02";
    private static final String FRAGMENT_TAG_03 = "fragment_tag_03";
    private static final String FRAGMENT_TAG_04 = "fragment_tag_04";
    private static final String[] FRAGMENT_TAGS = {FRAGMENT_TAG_01, FRAGMENT_TAG_02, FRAGMENT_TAG_03, FRAGMENT_TAG_04};

    private Fragment mCurrentFragment;
    private String mCurrentTag;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_navigation_bar, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        if(null != getActivity() && getActivity() instanceof NavigationBarActivity) {
            showFragment(((NavigationBarActivity) getActivity()).getCurrentTopTag(), false);
            ((NavigationBarActivity) getActivity()).addNavigationClickListener(this);
        }
    }

    private void showFragment(String tag, boolean fromSave) {
        if (TextUtils.isEmpty(tag)) {
            tag = FRAGMENT_TAG_01;
        }
        if(mCurrentTag == tag) {
            return;
        }

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);

        if (null == fragment) {
            switch (tag) {
                case FRAGMENT_TAG_01:
                    fragment = createdFirstFragment();
                    break;
                case FRAGMENT_TAG_02:
                    fragment = createdSecondFragment();
                    break;
                case FRAGMENT_TAG_03:
                    fragment = createdThirdFragment();
                    break;
                case FRAGMENT_TAG_04:
                    fragment = createdFourthFragment();
                    break;
                default:
                    break;
            }
            transaction.add(R.id.container, fragment, tag);
        } else {
            if (fragment.isHidden()) {
                transaction.show(fragment);
            }
            if (fragment.isDetached()) {
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
        mCurrentTag = tag;

        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onNavigationClick(View v, int position) {
        if(position < 0 && position >= FRAGMENT_TAGS.length) {
            return;
        }
        showFragment(FRAGMENT_TAGS[position], false);
    }

    protected abstract Fragment createdFirstFragment();

    protected abstract Fragment createdSecondFragment();

    protected abstract Fragment createdThirdFragment();

    protected abstract Fragment createdFourthFragment();

}
