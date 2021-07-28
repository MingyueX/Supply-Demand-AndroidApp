package com.zh.testbottonnav.ui;

import android.app.Application;

import com.zh.testbottonnav.SPUtil;

import org.xutils.x;

import io.rong.imkit.RongIM;

public class AppTest extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SPUtil.init(this);
        String appKey = "82hegw5u81evx";
        RongIM.init(this, appKey);
        x.Ext.init(this);
        x.Ext.setDebug(true);
    }
}
