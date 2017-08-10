package com.le.mychat;

import android.os.Bundle;

import com.le.mychat.core.BaseActivity;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction().add(R.id.content, new LoginFragment()).commit();
    }

//    @Override
//    protected void onDestroy() {
//        //防止InputMethodManager导致的内存泄漏
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        try {
//            InputMethodManager.class.getDeclaredMethod("windowDismissed", IBinder.class).invoke(imm,
//                    getWindow().getDecorView().getWindowToken());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        super.onDestroy();
//    }
}

