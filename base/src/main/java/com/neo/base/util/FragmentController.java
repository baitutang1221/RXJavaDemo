package com.neo.base.util;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.neo.base.ui.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class FragmentController {


    private static FragmentController fm;
    private BaseFragment mFragment;

    private FragmentController() {
    }

    public static FragmentController build() {
        if (fm == null)
            fm = new FragmentController();
        return fm;
    }

    public BaseFragment addFragments(BaseFragment fragment) {
        List<BaseFragment> fragments  =new ArrayList();
        fragments.add(fragment);
        List<BaseFragment> fs = addFragments(fragments);
        mFragment = fs.get(0);
        return mFragment;
    }

    public BaseFragment get(){
        return mFragment;
    }






    public List<BaseFragment> addFragments(List<BaseFragment> fragments) {
        List<BaseFragment> fragments2 = new ArrayList<BaseFragment>(fragments.size());
        FragmentActivity context = (FragmentActivity)YHContext.getInstance().getContext();
        FragmentManager fm = context.getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        boolean commit = false;
        for (int i = 0; i < fragments.size(); i++) {
            // install
            BaseFragment fragment = fragments.get(i);
            int id = fragment.getContainerId();

            BaseFragment fragment2 = (BaseFragment) fm.findFragmentById(id);
            if (fragment2 == null) {
                fragment2 = fragment;
                transaction.replace(id, fragment);
                commit = true;
            }
            fragments2.add(i, fragment2);
        }
        if (commit) {
            try {
                transaction.commitAllowingStateLoss();
            } catch (Exception e) {

            }
        }
        return fragments2;
    }
}
