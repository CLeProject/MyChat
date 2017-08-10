package com.le.mychat.module;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.le.mychat.R;
import com.le.mychat.core.BaseFragment;
import com.le.mychat.module.contacts.ContactAdapter;
import com.le.mychat.module.contacts.HanziToPinyin;
import com.le.mychat.module.contacts.bean.Contact;
import com.le.mychat.module.contacts.widget.SideBar;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpConfig;
import org.kymjs.kjframe.utils.KJLoger;

import java.util.ArrayList;

/**
 * Created by chenle on 2017/8/7.
 */

public class ContactsFragment extends BaseFragment implements SideBar
        .OnTouchingLetterChangedListener, TextWatcher {
    @ViewInject(R.id.school_friend_sidrbar)
    private SideBar mSideBar;
    @ViewInject(R.id.school_friend_dialog)
    private TextView mDialog;
    @ViewInject(R.id.school_friend_member_search_input)
    private EditText mSearchInput;

    @ViewInject(R.id.school_friend_member)
    private ListView mListView;

    private TextView mFooterView;

    private KJHttp kjh = null;
    private ArrayList<Contact> datas = new ArrayList<>();
    private ContactAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.f_contact,container,false);
        ViewUtils.inject(this,view);
        initWidget();
        return view;
    }

    @Override
    public void initData() {
        HttpConfig config = new HttpConfig();
        HttpConfig.sCookie = "oscid=V" +
                "%2BbmxZFR8UfmpvrBHAcVRKALrd72iPWknXaWDa5Is3veK7WsxTSWY80kRXB1LoH%2F4VJ" +
                "%2F9s7K3Kd9CwCC1CAV%2BnJ7T3ka0blF8vZojozhUdOYkq6D6Laldg%3D%3D; Domain=.oschina" +
                ".net; Path=/; ";
        config.cacheTime = 0;
        kjh = new KJHttp();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void processClick(View v) {

    }





    public void initWidget() {
        mSideBar.setTextView(mDialog);
        mSideBar.setOnTouchingLetterChangedListener(this);
        mSearchInput.addTextChangedListener(this);

        // 给listView设置底部view
        mFooterView = (TextView) View.inflate(getContext(), R.layout.item_list_contact_count, null);
        mListView.addFooterView(mFooterView);

        doHttp();
    }

    private void doHttp() {
        parserFake();
//        kjh.get("http://zb.oschina.net/action/zbApi/contacts_list?uid=863548", new HttpCallBack() {
//            @Override
//            public void onSuccess(String t) {
//                super.onSuccess(t);
//                parser(t);
//            }
//        });
    }
    private void parserFake() {

            for (int i = 0; i < 100; i++) {
                Contact data = new Contact();
                data.setName("name"+i);
                data.setUrl("url"+i);
                data.setId(i);
                data.setPinyin(HanziToPinyin.getPinYin(data.getName()));
                datas.add(data);
            }
            mFooterView.setText(datas.size() + "位联系人");
            mAdapter = new ContactAdapter(mListView, datas);
            mListView.setAdapter(mAdapter);

    }
    private void parser(String json) {
        try {
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.optJSONObject(i);
                Contact data = new Contact();
                data.setName(object.optString("name"));
                data.setUrl(object.optString("portrait"));
                data.setId(object.optInt("id"));
                data.setPinyin(HanziToPinyin.getPinYin(data.getName()));
                datas.add(data);
            }
            mFooterView.setText(datas.size() + "位联系人");
            mAdapter = new ContactAdapter(mListView, datas);
            mListView.setAdapter(mAdapter);
        } catch (JSONException e) {
            KJLoger.debug("解析异常" + e.getMessage());
        }
    }

    @Override
    public void onTouchingLetterChanged(String s) {
        int position = 0;
        // 该字母首次出现的位置
        if (mAdapter != null) {
            position = mAdapter.getPositionForSection(s.charAt(0));
        }
        if (position != -1) {
            mListView.setSelection(position);
        } else if (s.contains("#")) {
            mListView.setSelection(0);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        ArrayList<Contact> temp = new ArrayList<>(datas);
        for (Contact data : datas) {
            if (data.getName().contains(s) || data.getPinyin().contains(s)) {
            } else {
                temp.remove(data);
            }
        }
        if (mAdapter != null) {
            mAdapter.refresh(temp);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }


}
