package com.zh.testbottonnav;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.zh.testbottonnav.net.QueryPost;
import com.zh.testbottonnav.ui.HttpCallback;
import com.zh.testbottonnav.ui.PostModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScreeningActivity extends AppCompatActivity {
    public static List<List<String> > mselected = new ArrayList<List<String> >();

    private DrawerLayout drawer;
    private LinearLayout navigationView;
    private RightSideslipLay menuHeaderView;
    private TextView mFrameTv;
    private TextView cancel;
    private TextView searchBtn;

    private RadioButton mRadioForward;
    private RadioButton mRadioBackward;

    private TextView category;
    private TextView delivery;
    private TextView location;
    private TextView quantity;

    private EditText lengthMin;
    private EditText lengthMax;
    private EditText widthMin;
    private EditText widthMax;
    private EditText heightMin;
    private EditText heightMax;
    private EditText priceMin;
    private EditText pricaMax;

    private Handler uiHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Log.d("Screening", "screening");
                    drawer.closeDrawer(GravityCompat.END);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screening);

        lengthMin = (EditText)findViewById(R.id.length_min);
        lengthMax = (EditText)findViewById(R.id.length_max);
        widthMin = (EditText)findViewById(R.id.width_min);
        widthMax = (EditText)findViewById(R.id.width_max);
        heightMin = (EditText)findViewById(R.id.height_min);
        heightMax = (EditText)findViewById(R.id.height_max);
        priceMin = (EditText) findViewById(R.id.price_min);
        pricaMax = (EditText) findViewById(R.id.price_max);


        category = (TextView)findViewById(R.id.cate);
        delivery = (TextView)findViewById(R.id.deli);
        location = (TextView)findViewById(R.id.locate);
        quantity = (TextView)findViewById(R.id.quantity);

        cancel = (TextView) findViewById(R.id.myCancel_screen);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (LinearLayout) findViewById(R.id.nav_view);
        mFrameTv = (TextView) findViewById(R.id.screenTV);
        searchBtn = (TextView)findViewById(R.id.goSearch);

        // drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN, Gravity.RIGHT);

        drawer.closeDrawer(GravityCompat.END);

        menuHeaderView = new RightSideslipLay(ScreeningActivity.this);
        navigationView.addView(menuHeaderView);
        mFrameTv.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                openMenu();
            }
        });


        menuHeaderView.setCloseMenuCallBack(new RightSideslipLay.CloseMenuCallBack() {
            @Override
            public void setupCloseMean() {
                closeMenu();
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QueryPost queryPost = new QueryPost();
                if (!category.getText().toString().equals("不限")) {
                    queryPost.setCategoryIds(mselected.get(0));
                } else {
                    queryPost.setCategoryIds(null);
                }
                if (mRadioForward.isChecked()) {
                    queryPost.setType("HELP");
                } else if (mRadioBackward.isChecked()) {
                    queryPost.setType("REQUEST");
                }
                String wayOfDelivery = delivery.getText().toString();
                if (wayOfDelivery.equals("快递")) {
                    queryPost.setDelivery("EXPRESS");
                } else if (wayOfDelivery.equals("面交")){
                    queryPost.setDelivery("FACETOFACE");
                }

                queryPost.setLengthMin(lengthMin.getText().toString());
                queryPost.setLengthMax(lengthMax.getText().toString());
                queryPost.setWidthMin(widthMin.getText().toString());
                queryPost.setWidthMax(widthMax.getText().toString());
                queryPost.setHeightMin(heightMin.getText().toString());
                queryPost.setHeightMax(heightMax.getText().toString());
                queryPost.setPriceMin(priceMin.getText().toString());
                queryPost.setPriceMax(pricaMax.getText().toString());

                PostModel postModel = new PostModel();
                postModel.queryPost(queryPost, new HttpCallback() {
                    @Override
                    public void onSuccess(String s) {
                        Intent i = new Intent();
                        i.setComponent(new ComponentName("com.zh.testbottonnav", "com.zh.testbottonnav.QuerySucceedActivity"));
                        i.putExtra("result", s);
                        startActivity(i);
                    }

                    @Override
                    public void onError(String s) {

                    }
                });


            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RadioGroup SendRG = (RadioGroup) findViewById(R.id.SendRG);
        initView();

        SendRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkId) { setClipState(); }
        });
        mRadioForward.setChecked(true);

    }

    public void closeMenu() {
        for (int i = 0; i < mselected.size(); i++) {
            String display = "";
            int wordcount = 0;
            if (i == 0) {
                for (int j = 0; j < mselected.get(i).size(); j++) {
                    if (wordcount != 0 && (wordcount % 2) == 0) {
                        display += "\n";
                    }
                    else if (wordcount > 0) {
                        display += " ";
                    }
                    display += mselected.get(i).get(j);
                    wordcount++;
                }
                category.setText(display);
            }
            if (i == 1) {
                for (int j = 0; j < mselected.get(i).size(); j++) {
                    if (wordcount != 0 && (wordcount % 2) == 0) {
                        display += "\n";
                    }
                    else if (wordcount > 0) {
                        display += " ";
                    }
                    display += mselected.get(i).get(j);
                    wordcount++;
                }
                delivery.setText(display);
            }
            if (i == 2) {
                for (int j = 0; j < mselected.get(i).size(); j++) {
                    if (wordcount != 0 && (wordcount % 2) == 0) {
                        display += "\n";
                    }
                    else if (wordcount > 0) {
                        display += " ";
                    }
                    display += mselected.get(i).get(j);
                    wordcount++;
                }
                location.setText(display);
            }
            if (i == 3) {
                for (int j = 0; j < mselected.get(i).size(); j++) {
                    if (wordcount != 0 && (wordcount % 2) == 0) {
                        display += "\n";
                    }
                    else if (wordcount > 0) {
                        display += " ";
                    }
                    display += mselected.get(i).get(j);
                    wordcount++;
                }
                quantity.setText(display);
            }
        }
        Message msg = new Message();
        msg.what = 1;
        uiHandler.sendMessage(msg);
    }

    public void openMenu() {
        drawer.openDrawer(GravityCompat.END);
    }

    private void initView() {
        cancel = (TextView) findViewById(R.id.myCancel);
        mRadioForward = (RadioButton) findViewById(R.id.ForwardRadioBtn);
        mRadioBackward = (RadioButton) findViewById(R.id.BackwardRadioBtn);
    }

    private void setClipState() {
        setForwardState();
        setBackwardState();
    }

    private void setForwardState() {
        if (mRadioForward.isChecked()) {
            mRadioForward.setTextColor(getResources().getColor(R.color.white));
            mRadioForward.setBackground(getDrawable(R.drawable.clip2));
        } else {
            mRadioForward.setTextColor(getResources().getColor(R.color.colorPrimary));
            mRadioForward.setBackground(getDrawable(R.drawable.clip1));
        }
    }

    private void setBackwardState() {
        if (mRadioBackward.isChecked()) {
            mRadioBackward.setTextColor(getResources().getColor(R.color.white));
            mRadioBackward.setBackground(getDrawable(R.drawable.clip2));
        } else {
            mRadioBackward.setTextColor(getResources().getColor(R.color.colorPrimary));
            mRadioBackward.setBackground(getDrawable(R.drawable.clip1));
        }
    }


}
