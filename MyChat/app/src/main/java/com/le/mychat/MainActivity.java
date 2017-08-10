package com.le.mychat;


import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.le.mychat.core.FragmentInfo;
import com.le.mychat.core.FragmentInfoActivity;
import com.le.mychat.module.ChatListFragment;
import com.le.mychat.module.ContactsFragment;
import com.le.mychat.module.MineFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.tv_scan)
    private TextView tv_scan;
//    @ViewInject(R.id.rg)
//    private RadioGroup rg;
    @ViewInject(R.id.ib_contact_list)
    private ImageView ib_contact_list;
    @ViewInject(R.id.ib_weixin)
    private ImageView ib_weixin;
    @ViewInject(R.id.ib_profile)
    private ImageView ib_profile;

    @ViewInject(R.id.tv_weixin)
    private TextView tv_weixin;
    @ViewInject(R.id.tv_contact_list)
    private TextView tv_contact_list;
    @ViewInject(R.id.tv_profile)
    private TextView tv_profile;

    private FragmentManager fragmentManager;
    private long time;
    private Fragment currentShow;
    int num = 0;
    private int curIndex=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        EventBus.getDefault().register(this);
        SharedPreferences setedPreferences;
        setedPreferences = getSharedPreferences("systemset",
                Context.MODE_PRIVATE);


        if (setedPreferences.getBoolean("set1", true)) {

        } else {
        }
//        JPushInterface.resumePush(MainActivity.this);
        init();
        bindService();
    }

    private void bindService() {
    }

    private ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }
    };
    public void onTabClicked(View view) {
        switch (view.getId()) {
            case R.id.re_weixin:
                tv_weixin.setTextColor(0xFF45C01A);
                tv_contact_list.setTextColor(0xFF999999);
                tv_profile.setTextColor(0xFF999999);
                ib_weixin.setSelected(true);
                ib_contact_list.setSelected(false);
                ib_profile.setSelected(false);
                select(1);
                break;
            case R.id.re_contact_list:
                tv_weixin.setTextColor(0xFF999999);
                tv_contact_list.setTextColor(0xFF45C01A);
                tv_profile.setTextColor(0xFF999999);
                ib_weixin.setSelected(false);
                ib_contact_list.setSelected(true);
                ib_profile.setSelected(false);
                select(2);
                break;
//            case R.id.re_find:
//
//                break;
            case R.id.re_profile:
                tv_weixin.setTextColor(0xFF999999);
                tv_contact_list.setTextColor(0xFF999999);
                tv_profile.setTextColor(0xFF45C01A);
                ib_weixin.setSelected(false);
                ib_contact_list.setSelected(false);
                ib_profile.setSelected(true);
                select(3);
                break;
        }

    }
    private void init() {
        fragmentManager = getSupportFragmentManager();
        ib_weixin.setSelected(true);
        tv_weixin.setTextColor(0xFF45C01A);
//        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId) {
//                    case R.id.rb1: {
//                        select(1);
//                        break;
//                    }
//                    case R.id.rb2: {
//                        select(2);
//                        break;
//                    }
//                    case R.id.rb3: {
//                        select(3);
//                        break;
//                    }
//                    default:
//                        break;
//                }
//            }
//        });
//        ((RadioButton) rg.findViewById(R.id.rb1)).setChecked(true);
        select(1);
    }

    private void select(int index) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        if (currentShow != null&&index!=curIndex) {
            transaction.hide(currentShow);
        }

        switch (index) {
            case 1: {
                ChatListFragment f = (ChatListFragment) fragmentManager.findFragmentByTag(ChatListFragment.class.getName());
                if (f == null) {
                    f = new ChatListFragment();
                    f.setArguments(new Bundle());
                    transaction.add(R.id.content, f, ChatListFragment.class.getName());
                } else {
                    transaction.show(f);
                }
                currentShow = f;
                tv_title.setText("消息");
                tv_scan.setVisibility(View.VISIBLE);
                tv_scan.setText("扫一扫");

                break;
            }
            case 2: {

                ContactsFragment f = (ContactsFragment) fragmentManager.findFragmentByTag(ContactsFragment.class.getName());
                if (f == null) {
                    f = new ContactsFragment();
                    transaction.add(R.id.content, f, ContactsFragment.class.getName());
                } else {
                    transaction.show(f);
                }
                currentShow = f;
                tv_title.setText("联系人");
                tv_scan.setVisibility(View.VISIBLE);
                tv_scan.setText("添加");
                break;
            }
            case 3: {
                MineFragment f = (MineFragment) fragmentManager.findFragmentByTag(MineFragment.class.getName());
                if (f == null) {
                    f = new MineFragment();
                    transaction.add(R.id.content, f, MineFragment.class.getName());
                } else {
                    transaction.show(f);
                }
                currentShow = f;
                tv_title.setText("我的");
                tv_scan.setVisibility(View.GONE);
                break;
            }
            default:
                break;
        }
        transaction.commit();
        curIndex=index;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - time > 2000) {
                time = System.currentTimeMillis();
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                return true;
            }
//            JPushInterface.stopPush(getApplicationContext());
//            android.os.Process.killProcess(android.os.Process.myPid());
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
//        if (versionManager != null) {
//            versionManager.cancel();
//        }
    }

    @Subscriber(mode = ThreadMode.MAIN, tag = "logout")
    public void logout(String msg) {
        // logout
        FragmentInfo info = new FragmentInfo(LoginFragment.class, new Bundle());
        FragmentInfoActivity.startActivity(this, info);
        finish();
    }



    @Subscriber(mode = ThreadMode.MAIN, tag = "cmd_exit")
    public void exit(String msg) {
        finish();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}

