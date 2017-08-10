package com.le.mychat.core;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.le.mychat.widget.ActionBarCompat;


/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    /**
     * 自定义actionBar
     */
    protected ActionBarCompat mActionBar;
    protected Toolbar toolbar;
    public BaseFragment() {
        // Required empty public constructor
    }

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        if (activity instanceof BaseActivity) {
//            mActionBar = ((BaseActivity) getActivity()).getActionBarCompat();
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity){
            mActionBar = ((BaseActivity) getActivity()).getActionBarCompat();
            toolbar = ((BaseActivity) getActivity()).getToolbar();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initViews(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initListener();
    }

    public View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        return null;
    }

    public abstract void initData();

    public abstract void initListener();

    public abstract void processClick(View v);

    @Override
    public void onClick(View v) {
        processClick(v);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
