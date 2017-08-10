package com.le.mychat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.le.mychat.context.Constant;
import com.le.mychat.context.ShowPrompt;
import com.le.mychat.core.BaseFragment;
import com.le.mychat.utils.ValidateUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginFragment extends BaseFragment {

    @ViewInject(R.id.et_loginName)
    private EditText et_loginName;
    @ViewInject(R.id.et_password)
    private EditText et_password;
    @ViewInject(R.id.cb_savePass)
    private CheckBox cb_savePass;

    private ProgressDialog pd;

    private String imsi;

    private String imei;

    private void init() {
        SharedPreferences sp = getActivity().getSharedPreferences(Constant.SP_FILE_1, Context.MODE_PRIVATE);
        et_loginName.setText(sp.getString(Constant.LOGIN_NAME, ""));
        et_password.setText(sp.getString(Constant.LOGIN_PASS, ""));
        cb_savePass.setChecked(sp.getBoolean(Constant.SAVE_PASS, false));
        cb_savePass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                savePass();
            }
        });
    }

    @OnClick({R.id.bt_login})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login: {
                if (ValidateUtil.isNotEmpty(et_loginName, et_password)) {
                    if (canReadPhoneState()) {
                    }
                }
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            }
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 112 && resultCode == Activity.RESULT_OK) {
            et_loginName.setText(data.getStringExtra("name"));
            et_password.setText(data.getStringExtra("pwd"));
        }
    }






    /**
     * 登录
     *
     * @param url
     */
    private void login(String url) {

//                savePass();
//                // 登录总中心成功
//                LoginCache cache = App.getLoginCache();
//                cache.setToken(response.optString("D1"));
//                cache.setRights(new HashSet<>(parseRights(response.optJSONArray("D2"))));
//                UserInfo userInfo = UserInfo.parse(response.optJSONObject("D3"));
//                userInfo.setLoginName(et_loginName.getText().toString());
//                cache.setHasNewVersion(response.optInt("D4"));
////					cache.setUnitID(response.optString("D5"));
////					cache.setSiteID(response.optString("D6"));
//                // List<Node> wangge =
//                // Node.prase(response.optJSONArray("D5"), null);
//                // List<ID_Name> unitTypes =
//                // ID_Name.parse(response.optJSONArray("D6"));
//                // List<Status> xunjian =
//                // Status.prase(response.optJSONArray("D7"));
//                // List<Status> choujian =
//                // Status.prase(response.optJSONArray("D8"));
//                // cache.setUnitTypes(unitTypes);
//                cache.setUserInfo(userInfo);
//                // cache.setAreaTree(wangge);
//                // cache.setXjStatus(xunjian);
//                // cache.setCjStatus(choujian);
//                App.saveState();
//                ConfigCacheUtil.clearCache(null);
//                Intent intent = new Intent(getActivity(), MainActivity.class);
//                startActivity(intent);
//                getActivity().finish();


    }

    private List<String> parseRights(JSONArray array) {
        List<String> rights = new ArrayList<String>();
        if (array != null) {
            for (int i = 0; i < array.length(); i++) {
                rights.add(array.optString(i));
            }
        }

        return rights;
    }


    private String getCpuInfo() {
        try {
            FileReader fr = new FileReader("/proc/cpuinfo");
            BufferedReader br = new BufferedReader(fr, 8192);
            String line = br.readLine();
            if (line != null){
                line = line.substring(line.indexOf(":")).trim();
            }
            br.close();
            return line;
        } catch (IOException e) {
            Log.e(LoginFragment.class.getName(), "", e);
        }

        return "";
    }

    private String getVersionName() {
        // 获取packagemanager的实例
        PackageManager packageManager = getActivity().getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo;
        try {
            packInfo = packageManager.getPackageInfo(getActivity().getPackageName(), 0);
            return packInfo.versionName;
        } catch (NameNotFoundException e) {
            Log.e(LoginFragment.class.getName(), "", e);
        }
        return "";
    }

    private void savePass() {
        SharedPreferences sp = getActivity().getSharedPreferences(Constant.SP_FILE_1, Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putString(Constant.LOGIN_NAME, et_loginName.getText().toString());
        if (cb_savePass.isChecked()) {
            editor.putString(Constant.LOGIN_PASS, et_password.getText().toString());
        } else {
            editor.putString(Constant.LOGIN_PASS, "");
        }
        editor.putBoolean(Constant.SAVE_PASS, cb_savePass.isChecked());
        editor.apply();
    }

    @Override
    public View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_login, container, false);
        ViewUtils.inject(this, view);
        toolbar.setVisibility(View.GONE);
        init();
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // READ_PHONE_STATE permission has not been granted.
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 2);
        }
        return view;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void processClick(View v) {

    }


    // @Override
    // public boolean onKeyDown(int keyCode, KeyEvent event) {
    // if (keyCode == KeyEvent.KEYCODE_BACK){
    // if ("".equals(et_loginName.getText().toString())){
    // et_password.setText("");
    // savePass();
    // }
    // }
    // return super.onKeyDown(keyCode, event);
    // }

    public boolean canReadPhoneState() {
        // Check if the READ_PHONE_STATE permission is already available.
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // READ_PHONE_STATE permission has not been granted.
            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        } else {
            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 1) {
            // Received permission result for READ_PHONE_STATE permission.est.");
            // Check if the only required permission has been granted
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // READ_PHONE_STATE permission has been granted, proceed with displaying IMEI Number
            } else {
                ShowPrompt.showShort(getActivity(), "未授权");
            }
        }
    }
}
