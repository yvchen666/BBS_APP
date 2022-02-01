package com.icekey.bbs.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.icekey.bbs.R;

public class GridViewAdapter extends BaseAdapter {

    private int size;
    private String json;
    private final Context context;
    private LayoutInflater layoutInflater;
    private GridView gridView;
    public GridViewAdapter(Context context, String json,GridView gridView) {
        this.json = json;
        this.gridView = gridView;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        String[] arrays = new Gson().fromJson(json,String[].class);
        size = arrays.length;
    }

    @Override
    public int getCount() {
        return size;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            //填写ListView的图标和标题等控件的来源，来自于layout_list_item这个布局文件
            //把控件所在的布局文件加载到当前类中
            convertView = layoutInflater.inflate(R.layout.item_grid, null);
            //生成一个ViewHolder的对象
            holder = new ViewHolder();
            //获取控件对象
            holder.imageView = convertView.findViewById(R.id.item_grid_image);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String[] arrays = new Gson().fromJson(json, String[].class);
        //加载第三方网络图片
        for (String img_url : arrays) {
            Log.d("img_url", "getView: "+img_url);
            Glide.with(context).load(img_url).override(WindowManager.LayoutParams.WRAP_CONTENT).into(holder.imageView);
        }
        return convertView;

    }

    static class ViewHolder {
        private ImageView imageView;
    }
}
