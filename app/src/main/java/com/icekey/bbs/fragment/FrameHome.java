package com.icekey.bbs.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.icekey.bbs.R;
import com.icekey.bbs.adapter.ImageAdapter;
import com.icekey.bbs.adapter.RecyclerViewAdapter;
import com.icekey.bbs.bean.DataBean;
import com.icekey.bbs.bean.RecyclerData;
import com.icekey.bbs.network.UserApi;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.util.LogUtils;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FrameHome extends Fragment {
    private Banner banner;
    private RecyclerView recyclerView;
    private ArrayList<RecyclerData> datas;
    private RecyclerViewAdapter recyclerViewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frame_home, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initBanner();
        initData();
        //设置recyclerView适配器
        RecyclerViewAdapter.SpacesItemDecoration spacesItemDecoration = new RecyclerViewAdapter.SpacesItemDecoration(10);
        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), datas);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.addItemDecoration(spacesItemDecoration);
        //设置LayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

    }

    private void initData() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(UserApi.BaseUrl).build();
        Call<ResponseBody> call = retrofit.create(UserApi.class).getPost();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    Log.d("TAG", "onResponse: " + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("TAG", "onResponse Error: " + t.getMessage());
            }
        });
        datas = new ArrayList<>();
        RecyclerData.Builder builder = new RecyclerData.Builder();
        for (int i = 0; i < 20; i++) {
            RecyclerData module = builder.setUserName("用户" + i).setTitle("标题" + i).setContent("test").crate();

            datas.add(module);
        }
    }

    private void initBanner() {
        ImageAdapter adapter = new ImageAdapter(DataBean.getTestData2());
        banner.setAdapter(adapter)
//              .setCurrentItem(0,false)
                .addBannerLifecycleObserver(this)//添加生命周期观察者
                .setIndicator(new CircleIndicator(getActivity()))//设置指示器
                .setOnBannerListener((data, position) -> {
                    Snackbar.make(banner, ((DataBean) data).title, Snackbar.LENGTH_SHORT).show();
                    LogUtils.d("position：" + position);
                });
    }

    private void initView() {
        banner = getActivity().findViewById(R.id.frame_home_banner);
        recyclerView = getActivity().findViewById(R.id.fame_home_recyclerView);
    }
}
