package com.le.mychat.core;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.le.mychat.R;
import com.le.mychat.widget.ActionBarCompat;

public class BaseActivity extends AppCompatActivity {

    protected Toolbar toolbar;
    protected ActionBarCompat mActionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        StatusBarCompat.compat(this, getResources().getColor(R.color.colorPrimaryDark));
        initActionBarCompat();
    }

    private void initActionBarCompat() {
        if (mActionBar == null) {
            // 初始化自定义的actionbar
            mActionBar = (ActionBarCompat) findViewById(R.id.actionBar);

            mActionBar.setLeftViewResourceId(R.drawable.selector_back);
            // 如果左方只是返回键，则在其他地方不需要管点击后做什么，也可以根据需要重新设置该监听，这里设置的功能是
            // 如果Activity中含有多个Fragment则Pop操作，否则finish Activity
            mActionBar.setLeftViewAction(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    finish();
                }
            });

        }
    }

    // 在fragment中需要用到此方法
    public ActionBarCompat getActionBarCompat() {
        return mActionBar;
    }

    public Toolbar getToolbar(){
        return toolbar;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

//        if (requestCode == ImagePicker.PICK_IMAGE && resultCode == RESULT_OK) {
//            Uri uri = ImagePicker.getInstance().onResult(data);
//            File f = new File(FileUtil.getImageAbsolutePath(this, uri));
//            if (f.exists()) {
//                ImagePicker.getInstance().performResult(uri);
//            } else {
//                Toast.makeText(this, "图像文件不存在或已被删除", Toast.LENGTH_SHORT).show();
//            }
//            return;
//        }
//        if (requestCode == ImagePicker.TAKE_PICTURE && resultCode == RESULT_OK) {
//            ImagePicker.getInstance().performResult();
//            return;
//        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
