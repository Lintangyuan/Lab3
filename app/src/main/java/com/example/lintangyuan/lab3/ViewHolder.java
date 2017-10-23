package com.example.lintangyuan.lab3;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lintangyuan on 2017/10/19.
 */


public class ViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews; //存储list_Item的子View;
    private View mConvertView; //存储list_Item;

    public ViewHolder(Context context, View itemView, ViewGroup parent) {
        super(itemView);
        mConvertView = itemView;
        mViews = new SparseArray<View>();
    }
    //获取viewHolder实例;
    public static ViewHolder get(Context context, ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        ViewHolder holder = new ViewHolder(context, itemView, parent);
        return holder;
    }
    public <T extends View> T getView (int viewId) {
        View view = mViews.get(viewId);
        if(view == null) {
            view = mConvertView.findViewById(viewId); //创建view;
            mViews.put(viewId, view); //将view存入mViews;
        }
        return (T) view;
    }
}

