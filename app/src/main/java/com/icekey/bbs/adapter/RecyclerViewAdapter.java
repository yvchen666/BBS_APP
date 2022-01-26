package com.icekey.bbs.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.icekey.bbs.R;
import com.icekey.bbs.bean.RecyclerData;
import com.icekey.bbs.utils.CircleImageView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {


    private final Context context;
    private ArrayList<RecyclerData> datas;

    public RecyclerViewAdapter(Context context, ArrayList<RecyclerData> datas) {
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.item_recycler, null);
        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecyclerData recyclerData = datas.get(position);
        Glide.with(context).load(recyclerData.getIco_url()).into(holder.userIcon);
        holder.userName.setText(recyclerData.getUserName());
        DateFormat df3 = DateFormat.getDateTimeInstance();
        holder.postDate.setText(df3.format(new Date()));
        holder.title.setText(recyclerData.getTitle());
        holder.content.setText(recyclerData.getContent());
        holder.gridView.setAdapter(new  GridViewAdapter(context, recyclerData.getImg_json(),holder.gridView));
    }

    /**
     * 返回总条数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return datas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView userIcon;
        private TextView userName;
        private TextView postDate;
        private TextView title;
        private TextView content;
        private GridView gridView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userIcon = itemView.findViewById(R.id.item_recycler_userIcon);
            userName = itemView.findViewById(R.id.item_recycler_userName);
            postDate = itemView.findViewById(R.id.item_recycler_postDate);
            title = itemView.findViewById(R.id.item_recycler_Title);
            content = itemView.findViewById(R.id.item_recycler_content);
            gridView = itemView.findViewById(R.id.item_recycler_gridView);

        }
    }
    public static class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.top = space;
            outRect.bottom = space;
            outRect.left = space;
            outRect.right = space;
        }
    }
}
