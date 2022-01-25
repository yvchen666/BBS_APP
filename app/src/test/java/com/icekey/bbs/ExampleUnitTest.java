package com.icekey.bbs;

import org.junit.Test;

import static org.junit.Assert.*;

import com.google.gson.Gson;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    String img_json = "['http://139.155.90.20:8888/down/kCqpkDuIm6Pq','http://139.155.90.20:8888/down/kCqpkDuIm6Pq','http://139.155.90.20:8888/down/kCqpkDuIm6Pq']";

    @Test
    public void addition_isCorrect() {
        Gson gson = new Gson();
        String[] arrays = gson.fromJson(img_json,String[].class);
        for (String a :arrays){
            System.out.println(a);
        }
    }
}