package com.le.mychat.module;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.le.mychat.R;
import com.le.mychat.adapter.CommonAdapter;
import com.le.mychat.adapter.ViewHolder;
import com.le.mychat.core.BaseFragment;
import com.le.mychat.module.chat.ChatActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import pulltorefresh.PullToRefreshBase;
import pulltorefresh.PullToRefreshListView;

/**
 * Created by chenle on 2017/8/7.
 */

public class ChatListFragment extends BaseFragment{
    @ViewInject(R.id.list)
    private PullToRefreshListView list;
    @ViewInject(R.id.emptyView)
    private View emptyView;
    private List<Chatlist> datas;
    private CommonAdapter<Chatlist> adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.f_chat_list,container,false);
        ViewUtils.inject(this,view);
        return view;
    }

    @Override
    public void initData() {
        list.setMode(PullToRefreshBase.Mode.BOTH);
        list.setEmptyView(emptyView);
        datas=new ArrayList<>();
        for(int i=0;i<20;i++){
            Chatlist c1=new Chatlist();
            c1.setId(i+"");
            c1.setTitle("聊天"+i);
            c1.setImageUrl("http://tupian.enterdesk.com/2014/mxy/11/2/1/12.jpg");
            c1.setMessage("你好啊，我是牛皮");
            c1.setTime("时间"+i);
            datas.add(c1);
        }
        adapter=new CommonAdapter<Chatlist>(getContext(),R.layout.item_chat,datas) {
            @Override
            protected void convert(ViewHolder holder, Chatlist item, int position) {
                holder.displayImage(R.id.image,item.getImageUrl());
                holder.setText(R.id.tv_message,item.getMessage());
                holder.setText(R.id.tv_title,item.getTitle());
                holder.setText(R.id.tv_time,item.getTime());
            }
        };
        list.setAdapter(adapter);
    }

    @Override
    public void initListener() {
        list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                list.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                list.onRefreshComplete();
            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("title",adapter.getItem(i-1).getTitle());
                startActivity(intent);
            }
        });
    }

    @Override
    public void processClick(View v) {

    }




}
class Chatlist{
    private String id;
    private String title;
    private String message;
    private String imageUrl;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}