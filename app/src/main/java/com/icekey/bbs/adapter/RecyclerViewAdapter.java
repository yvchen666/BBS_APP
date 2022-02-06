package com.icekey.bbs.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.icekey.bbs.R;
import com.icekey.bbs.bean.RecyclerData;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private ArrayList<RecyclerData> datas;
    private static final int PIC_NONE = 0;
    private static final int PIC_ONE = 1;
    private static final int PIC_TWO = 2;
    private static final int PIC_THREE = 3;


    public RecyclerViewAdapter(Context context, ArrayList<RecyclerData> datas) {
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;
        if (viewType == PIC_NONE) {
            itemView = View.inflate(context, R.layout.item_none_pic, null);
            return new NonePicViewHolder(itemView);
        } else if (viewType == PIC_ONE) {
            itemView = View.inflate(context, R.layout.item_one_pic, null);
            return new OnePicViewHolder(itemView);
        } else if (viewType == PIC_TWO) {
            itemView = View.inflate(context, R.layout.item_two_pic, null);
            return null;
        } else if (viewType == PIC_THREE) {
            itemView = View.inflate(context, R.layout.item_three_pic, null);
            return null;
        } else {
            itemView = View.inflate(context, R.layout.item_recycler, null);
            return new ViewHolder(itemView);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

//        RecyclerData recyclerData = datas.get(position);
//        Glide.with(context).load(recyclerData.getIco_url()).into(holder.userIcon);
//        holder.userName.setText(recyclerData.getUserName());
//        DateFormat df3 = DateFormat.getDateTimeInstance();
//        holder.postDate.setText(df3.format(new Date()));
//        holder.title.setText(recyclerData.getTitle());
//        holder.content.setText(recyclerData.getContent());
//        String[] arrays = new Gson().fromJson(datas.get(position).getImg_json(), String[].class);
//        holder.gridView.setNumColumns(arrays.length);
//        holder.gridView.setAdapter(new GridViewAdapter(context, recyclerData.getImg_json(), holder.gridView));
        if (holder instanceof NonePicViewHolder){

        }
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

    @Override
    public int getItemViewType(int position) {
        String[] arrays = new String[0];
        try {
            arrays = new Gson().fromJson(datas.get(position).getImg_json(), String[].class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        if (arrays.length == 0) {
            return PIC_NONE;
        } else if (arrays.length == 1) {
            return PIC_ONE;
        } else if (arrays.length == 2) {
            return PIC_TWO;
        } else if (arrays.length >= 3) {
            return PIC_THREE;
        } else {
            return super.getItemViewType(position);
        }
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

    static class NonePicViewHolder extends RecyclerView.ViewHolder {
        private ImageView userIcon;
        private TextView userName;
        private TextView postDate;
        private TextView title;
        private TextView content;
        private TextView text_read;
        private TextView text_commit;
        private TextView text_favorite;

        public NonePicViewHolder(@NonNull View itemView) {
            super(itemView);
            userIcon = itemView.findViewById(R.id.item_none_userIcon);
            userName = itemView.findViewById(R.id.item_none_userName);
            postDate = itemView.findViewById(R.id.item_none_postDate);
            title = itemView.findViewById(R.id.item_none_title);
            content = itemView.findViewById(R.id.item_none_content);
            text_read = itemView.findViewById(R.id.item_none_text_read);
            text_commit = itemView.findViewById(R.id.item_none_text_commit);
            text_favorite = itemView.findViewById(R.id.item_none_text_favorite);
        }
    }
    static class OnePicViewHolder extends RecyclerView.ViewHolder {
        private ImageView userIcon;
        private TextView userName;
        private TextView postDate;
        private TextView title;
        private TextView content;
        private ImageView imageView;
        private TextView text_read;
        private TextView text_commit;
        private TextView text_favorite;

        public OnePicViewHolder(@NonNull View itemView) {
            super(itemView);
            userIcon = itemView.findViewById(R.id.item_one_userIcon);
            userName = itemView.findViewById(R.id.item_one_userName);
            postDate = itemView.findViewById(R.id.item_one_postDate);
            title = itemView.findViewById(R.id.item_one_title);
            content = itemView.findViewById(R.id.item_one_content);
            imageView = itemView.findViewById(R.id.item_one_pic);
            text_read = itemView.findViewById(R.id.item_one_text_read);
            text_commit = itemView.findViewById(R.id.item_one_text_commit);
            text_favorite = itemView.findViewById(R.id.item_one_text_favorite);
        }
    }

    /**
     * 设置recycler分割线
     */
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
