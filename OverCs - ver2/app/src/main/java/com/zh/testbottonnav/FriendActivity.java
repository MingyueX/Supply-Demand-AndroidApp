package com.zh.testbottonnav;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.zh.testbottonnav.ui.AppTest;

import io.rong.imkit.RongIM;

public class FriendActivity extends Activity {
    private Button btnFriend;
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friendpage);
        mContext = this;
        btnFriend = (Button) findViewById(R.id.btn_friend);
        String userId = SPUtil.getUserId("userId");
        if("10086".equals(userId)){
            btnFriend.setText("好友卡莎");
        }else{
            btnFriend.setText("好友akali");
        }
        btnFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(RongIM.getInstance()!=null){
                    String userId = SPUtil.getUserId("userId");
                    if("10086".equals(userId)){
                        userId = "10010";
                    }else{
                        userId = "10086";
                    }
                    //开启单聊界面
                    RongIM.getInstance().startPrivateChat(mContext,userId,"单聊");
                }
            }
        });

    }

}
