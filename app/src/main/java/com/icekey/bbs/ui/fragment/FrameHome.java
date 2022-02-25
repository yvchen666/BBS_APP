package com.icekey.bbs.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.icekey.bbs.R;
import com.icekey.bbs.adapter.ImageAdapter;
import com.icekey.bbs.adapter.RecyclerViewAdapter;
import com.icekey.bbs.bean.DataBean;
import com.icekey.bbs.bean.PostDataBean;
import com.icekey.bbs.bean.RecyclerData;
import com.icekey.bbs.network.UserApi;
import com.icekey.bbs.ui.richtext.EditPostActivity;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.util.LogUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FrameHome extends Fragment {
    private Banner banner;
    private RecyclerView recyclerView;
    private ArrayList<RecyclerData> datas = new ArrayList<>();
    private RecyclerViewAdapter recyclerViewAdapter;
    private FloatingActionButton floatingActionButton;

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

        floatingActionButton.setOnClickListener((v) -> {
            Intent intent = new Intent();
            intent.setClass(getContext(),EditPostActivity.class);
            startActivity(intent);
        });

    }

    private void initData() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(UserApi.BaseUrl).build();
        Call<ResponseBody> call = retrofit.create(UserApi.class).getPost();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String json = response.body().string();
                    List<PostDataBean> postDataBeanList = new Gson().fromJson(json, new TypeToken<List<PostDataBean>>() {
                    }.getType());
                    RecyclerData.Builder builder = new RecyclerData.Builder();
                    for (PostDataBean postDataBean : postDataBeanList) {
                        RecyclerData module = builder
                                .setUserName(postDataBean.getPostUser())
                                .setTitle(postDataBean.getPostHeader())
                                .setContent(postDataBean.getPostContent())
                                .setImg_json(postDataBean.getPostPic())
                                .setDate(postDataBean.getPostTime())
                                .setText_read(postDataBean.getPostRead())
                                .setText_commit(postDataBean.getPostCommit())
                                .setText_favorite(postDataBean.getPostFavorite())
                                .crate();
                        datas.add(module);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @SuppressLint("CheckResult")
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toasty.warning(requireActivity(), Objects.requireNonNull(t.getMessage()));
            }
        });
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
        floatingActionButton = getActivity().findViewById(R.id.fab);
    }
}
