package com.zh.testbottonnav;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class DetailContent extends Activity {

    private TextView textView;

    private Button CloseActBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailcontent);

        //textView = (TextView) findViewById(R.id.tv1);

        CloseActBtn = (Button) findViewById(R.id.CloseAct1Btn);
        CloseActBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                finish();
            }
        });
    }
}
