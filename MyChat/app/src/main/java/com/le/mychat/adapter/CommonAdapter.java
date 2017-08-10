package com.le.mychat.adapter;

import android.content.Context;

import com.le.mychat.adapter.base.ItemViewDelegate;

import java.util.List;

public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T> {

    public CommonAdapter(Context context, final int layoutId, List<T> datas) {
        super(context, datas);

        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, T t, int position) {
                CommonAdapter.this.convert(holder, t, position);
            }
        });
    }

    /**
     * 设置显示内容
     * eg:holder.setText(R.id.xxx, "text");
     *
     * @param holder
     * @param item
     * @param position
     */
    protected abstract void convert(ViewHolder holder, T item, int position);

}
