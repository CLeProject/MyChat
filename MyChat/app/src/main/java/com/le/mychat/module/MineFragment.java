package com.le.mychat.module;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.le.mychat.R;
import com.le.mychat.core.BaseFragment;
import com.le.mychat.utils.BitmapHelp;
import com.le.mychat.widget.CircleImageView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by chenle on 217/8/7.
 */

public class MineFragment extends BaseFragment{
    @ViewInject(R.id.tv_name)
    private TextView tv_name;
    @ViewInject(R.id.tv_number)
    private TextView tv_number;
    @ViewInject(R.id.iv_head)
    private CircleImageView iv_head;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.f_mine,container,false);
        ViewUtils.inject(this,view);
        return view;
    }

    @Override
    public void initData() {
        BitmapHelp.getBitmapUtils(getContext()).display(iv_head,"http://tupian.enterdesk.com/2014/mxy/11/2/1/12.jpg");
    }

    @Override
    public void initListener() {

    }

    @Override
    public void processClick(View v) {

    }


}
