package com.zh.testbottonnav.ui;

import android.util.Log;

import com.zh.testbottonnav.Constant;
import com.zh.testbottonnav.net.User;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

public class UserInfoModel {
    public void getUserById(Integer id, final HttpCallback callback) {
        RequestParams params = new RequestParams(Constant.address + "hello/getUser");
        params.addQueryStringParameter("userId", id.toString());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess (String result) {
                callback.onSuccess(result);
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                callback.onError(ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public void getUserInfo(String username, String password, final HttpCallback callback) {

        RequestParams params = new RequestParams(Constant.address + "hello/login");
        params.addQueryStringParameter("username", username);
        params.addQueryStringParameter("password", password);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess (String result) {
                callback.onSuccess(result);
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                callback.onError(ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public void setUserInfo(String username, String password, final HttpCallback callback) {
        RequestParams params = new RequestParams(Constant.address + "hello/register");
        params.addQueryStringParameter("username", username);
        params.addQueryStringParameter("password", password);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess (String result) {
                callback.onSuccess(result);
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                callback.onError(ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public void setUsername(Integer id, String username) {
        RequestParams params = new RequestParams(Constant.address + "hello/saveUsername");
        params.addQueryStringParameter("id", id.toString());
        params.addQueryStringParameter("username", username);

        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("TAG", result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("TAG", "请求失败" + ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }

    public void setAvatar(Integer id, String avatarPath, final HttpCallback callback) {
        RequestParams params = new RequestParams(Constant.address + "hello/saveAvatar");
        params.setMultipart(true);
        File img = new File(avatarPath);
        params.addBodyParameter("img", img);
        params.addBodyParameter("id", id.toString());

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                callback.onSuccess(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                callback.onError(ex.toString());
                Log.e("TAG", "请求失败" + ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }
}
