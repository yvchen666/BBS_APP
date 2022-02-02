package com.icekey.bbs.ui;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.icekey.bbs.R;
import com.icekey.bbs.network.UserApi;
import com.icekey.bbs.utils.StaticFinalString;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        String TAG = new StaticFinalString().StaticFinalString(LoginActivity.this);
        initView();
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
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                Log.d(TAG, "onResponse: " + response.body().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                } else {
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
    }
}
