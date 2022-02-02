package com.icekey.bbs;

import org.junit.Test;

import static org.junit.Assert.*;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    String img_json = "[]";

    @Test
    public void addition_isCorrect() {
        Gson gson = new Gson();
        String[] arrays = new String[0];
        try {
            arrays = gson.fromJson(img_json,String[].class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        System.out.println(arrays.length);
    }
}