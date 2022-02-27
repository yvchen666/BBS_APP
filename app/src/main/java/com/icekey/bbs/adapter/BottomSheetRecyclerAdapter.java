package com.icekey.bbs.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.icekey.bbs.R;
import com.icekey.bbs.bean.RecyclerUserData;

import java.util.List;

public class BottomSheetRecyclerAdapter extends RecyclerView.Adapter<BottomSheetRecyclerAdapter.ViewHolder> {
    private List<RecyclerUserData> list;

    public BottomSheetRecyclerAdapter(List<RecyclerUserData> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bottom_dialog_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
