package com.icekey.bbs.ui;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.icekey.bbs.R;
import com.icekey.bbs.bean.LoginResponseBean;
import com.icekey.bbs.network.UserApi;
import com.icekey.bbs.utils.SharedPreferencesUtil;
import com.icekey.bbs.utils.StaticFinalString;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {
    private EditText userName;
    private EditText passWord;
    private CheckBox rememberPass;
    private TextView register;
    private Button login;
    private SharedPreferencesUtil preferencesUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        preferencesUtil = new SharedPreferencesUtil(LoginActivity.this);

        initView();
        boolean isCheck = false;
        try {
            isCheck = preferencesUtil.getBoolean("ISCHECK");
            if (isCheck){
                rememberPass.setChecked(true);
                userName.setText(preferencesUtil.getSP("userName"));
                passWord.setText(preferencesUtil.getSP("passWord"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = userName.getText().toString();
                String pass = passWord.getText().toString();
                if (user != null && pass != null) {
                    Retrofit retrofit = new Retrofit.Builder().baseUrl(UserApi.BaseUrl)
                            .build();
                    Call<ResponseBody> call = retrofit.create(UserApi.class).login(user, pass);
                    call.enqueue(new Callback<ResponseBody>() {
                        @SuppressLint("CheckResult")
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                LoginResponseBean responseBean = new Gson().fromJson(response.body().string(), LoginResponseBean.class);
                                if (responseBean.getSuccess()) {
                                    System.out.println("success");
                                    Toasty.normal(LoginActivity.this, "登录成功").show();
                                    preferencesUtil.putSP("userName", user);
                                    preferencesUtil.putSP("passWord", pass);
                                    finish();
                                } else {
                                    Toasty.warning(LoginActivity.this, "账号或密码错误！").show();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toasty.warning(LoginActivity.this, "登录失败！网络错误！").show();
                        }
                    });
                } else {
                }
            }
        });
        rememberPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (rememberPass.isChecked()) {
                    System.out.println("记住密码已选中");
                    preferencesUtil.putSP("ISCHECK", true);
                } else {

                    System.out.println("记住密码没有选中");
                    preferencesUtil.putSP("ISCHECK", false);
                }
            }
        });
    }

    private void initView() {
        userName = findViewById(R.id.login_edit_userName);
        passWord = findViewById(R.id.login_edit_passWord);
        rememberPass = findViewById(R.id.login_checkBox);
        register = findViewById(R.id.gotoRegister);
        login = findViewById(R.id.button);
        hideTitle();
    }
    private void hideTitle() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }
}
