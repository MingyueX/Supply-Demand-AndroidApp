package com.zh.testbottonnav;

import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class OrderApplyActivity extends AppCompatActivity {

    private EditText length;
    private EditText width;
    private EditText height;
    private EditText weight;
    private EditText extraMsg;
    private EditText address;
    private EditText phone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_apply);
    }

    public void initView(){
        length = (EditText) findViewById(R.id.length);
        width = (EditText) findViewById(R.id.width);
        height = (EditText) findViewById(R.id.height);
        weight = (EditText) findViewById(R.id.weight);
        extraMsg = (EditText) findViewById(R.id.extraMessage);
        address = (EditText) findViewById(R.id.address);
        phone = (EditText) findViewById(R.id.phone);
    }
}
