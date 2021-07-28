package com.zh.testbottonnav;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSON;
import com.squareup.picasso.Picasso;
import com.zh.testbottonnav.adapter.HomePostAdapter;
import com.zh.testbottonnav.adapter.PostImageAdapter;
import com.zh.testbottonnav.net.Post;
import com.zh.testbottonnav.net.User;
import com.zh.testbottonnav.ui.HttpCallback;
import com.zh.testbottonnav.ui.PostModel;
import com.zh.testbottonnav.ui.UserInfoModel;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class PostDetail extends Activity {

    private ImageView avatarImg;
    private TextView backBtn;
    private TextView name;
    private TextView createTime;
    private TextView title;
    private TextView description;
    private TextView length;
    private TextView width;
    private TextView height;
    private TextView weight;
    private TextView price;
    private LinearLayout btn1;
    private LinearLayout btn2;
    private LinearLayout btn3;
    private ImageView btn1Img;
    private ImageView btn2Img;
    private ImageView btn3Img;
    private TextView btn1Text;
    private TextView btn2Text;
    private TextView btn3Text;
    private TextView applyTv;
    private boolean is_stared;

    private ViewPager viewPager;

    private ImageView[] tips;

    private int currentPage = 0; //当前展示的页码

    private List<String> imgList = new ArrayList<>();

    private String username;
    private String avatar;
    private String userId;

    private Handler uiHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Log.d("PostDetail", username);
                    name.setText(username);
                    Picasso.get().load(avatar).placeholder(R.drawable.avatardefault).centerCrop().transform(new CropCircleTransformation()).fit().into(avatarImg);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_detail);
        init();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        Post post = (Post) intent.getSerializableExtra("post");

        UserInfoModel userInfoModel= new UserInfoModel();
        userInfoModel.getUserById(post.getUid(), new HttpCallback() {
            @Override
            public void onSuccess(String s) {
                User user = JSON.parseObject(s, User.class);
                username = user.getUserName();
                userId = user.getUserId();
                avatar = user.getAvatar() == null ? null : (Constant.address + "upload/" + user.getAvatar());
                Message msg = new Message();
                msg.what = 1;
                uiHandler.sendMessage(msg);
            }

            @Override
            public void onError(String s) {

            }
        });

        PostModel postModel = new PostModel();
        if (SPUtil.getId("id") == post.getUid()) {
            applyTv.setText("申请列表");
            btn1Img.setImageDrawable(getDrawable(R.drawable.ic_outline_delete_24));
            btn1Text.setText("删除");
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    postModel.deletePost(post.getId(), new HttpCallback() {
                        @Override
                        public void onSuccess(String s) {
                            if (s.equals("succeed")) {
                                finish();
                            }
                        }

                        @Override
                        public void onError(String s) {
                        }
                    });
                }
            });
            btn3Img.setImageDrawable(getDrawable(R.drawable.ic_outline_edit_24));
            btn3Text.setText("修改更新");
            btn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        } else {
            Activity activity = this;
            btn1Img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!is_stared) {
                        btn1Img.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_star_24));
                        Toast.makeText(activity, "已收藏", Toast.LENGTH_SHORT).show();
                        is_stared = true;
                    } else {
                        btn1Img.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_star_border_24));
                        Toast.makeText(activity, "已取消收藏", Toast.LENGTH_SHORT).show();
                        is_stared = false;
                    }
                }
            });
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            btn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(RongIM.getInstance()!=null){
                        /*
                        String userId = SPUtil.getUserId("userId");
                        if("10086".equals(userId)){
                            userId = "10010";
                        }else{
                            userId = "10086";
                        }

                         */
                        //开启单聊界面
                        RongIM.getInstance().startPrivateChat(activity,userId,"单聊");
                    }
                }
            });
        }

        createTime.setText("发布于" + post.getCreateTime().toString());
        title.setText(post.getTitle());
        description.setText(post.getDescription());
        length.setText(post.getLengthMin().toString()  + "cm ~ " + post.getLengthMax().toString() + "cm");
        width.setText(post.getWidthMin().toString()  + "cm ~ " + post.getWidthMax().toString() + "cm");
        height.setText(post.getHeightMin().toString()  + "cm ~ " + post.getHeightMax().toString() + "cm");
        weight.setText(post.getWeightMin().toString()  + "kg ~ " + post.getWeightMax().toString() + "kg");
        price.setText("¥" + post.getPrice().toString());


        /*
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.post_image_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        PostImageAdapter adapter = new PostImageAdapter(imgList);
        recyclerView.setAdapter(adapter);

        imgList.add(post.getImageurl());
        imgList.add(post.getImageurl());
        adapter.addList(imgList);

         */

        if (post.getImage1() != null) {
            imgList.add(post.getImage1());
        }
        if (post.getImage2() != null) {
            imgList.add(post.getImage2());
        }
        if (post.getImage3() != null) {
            imgList.add(post.getImage3());
        }
        if (post.getImage4() != null) {
            imgList.add(post.getImage4());
        }
        if (post.getImage5() != null) {
            imgList.add(post.getImage5());
        }
        if (post.getImage6() != null) {
            imgList.add(post.getImage6());
        }
        if (post.getImage7() != null) {
            imgList.add(post.getImage7());
        }
        if (post.getImage8() != null) {
            imgList.add(post.getImage8());
        }
        if (post.getImage9() != null) {
            imgList.add(post.getImage9());
        }

        LinearLayout tipsBox=(LinearLayout)findViewById(R.id.tipsBox);

        tips=new ImageView[imgList.size()];

        for(int i=0;i<tips.length;i++){
            ImageView img=new ImageView(this); //实例化一个点点

            img.setLayoutParams(new LinearLayout.LayoutParams(10,10));

            tips[i]=img;

            if(i==0)

            {
                img.setBackgroundResource(R.drawable.ic_baseline_radio_button_checked_24);//蓝色背景

            }

            else{
                img.setBackgroundResource(R.drawable.ic_baseline_radio_button_unchecked_24);//黑色背景

            }



            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            params.leftMargin=5;

            params.rightMargin=5;

            tipsBox.addView(img, params); //把点点添加到容器中

        }

        PagerAdapter adapter=new PagerAdapter(){


            @Override
            public int getCount() {
                return imgList.size();
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public void destroyItem(ViewGroup container,int position,Object o){
            }

            @Override
            public Object instantiateItem(ViewGroup container,int position){
                ImageView im = new ImageView(PostDetail.this);

                Picasso.get().load(imgList.get(position)).placeholder(R.drawable.failtoload).centerCrop().fit().into(im);

                container.addView(im);

                return im;

            }

        };

        viewPager.setAdapter(adapter);

        //更改当前tip
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){


            @Override

            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
            }



            @Override

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // TODO Auto-generated method stub
            }



            @Override

            public void onPageSelected(int position) {
                // TODO Auto-generated method stub

                Log.e("rf",String.valueOf(position));

                tips[currentPage].setBackgroundResource(R.drawable.ic_baseline_radio_button_unchecked_24);

                currentPage=position;

                tips[position].setBackgroundResource(R.drawable.ic_baseline_radio_button_checked_24);
            }
        });

        applyTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (applyTv.getText().toString().equals("申请交易")) {
                    Intent i = new Intent();
                    i.setComponent(new ComponentName("com.zh.testbottonnav", "com.zh.testbottonnav.OrderApplyActivity"));
                    startActivity(i);
                }
            }
        });
    }

    public void init() {
        avatarImg = (ImageView)findViewById(R.id.avatar);
        backBtn = (TextView)findViewById(R.id.back);
        name = (TextView)findViewById(R.id.username);
        createTime= (TextView)findViewById(R.id.create_time);
        title = (TextView)findViewById(R.id.titleTv);
        description = (TextView)findViewById(R.id.descriptionTv);
        length = (TextView)findViewById(R.id.length);
        width = (TextView)findViewById(R.id.width);
        height = (TextView)findViewById(R.id.height);
        weight = (TextView)findViewById(R.id.weight);
        price = (TextView)findViewById(R.id.price);
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        btn1 = (LinearLayout) findViewById(R.id.btn1);
        btn2 = (LinearLayout) findViewById(R.id.btn2);
        btn3 = (LinearLayout) findViewById(R.id.btn3);
        btn1Img = (ImageView) findViewById(R.id.btn1_img);
        btn2Img = (ImageView) findViewById(R.id.btn2_img);
        btn3Img = (ImageView) findViewById(R.id.btn3_img);
        btn1Text = (TextView) findViewById(R.id.btn1_text);
        btn2Text = (TextView) findViewById(R.id.btn2_text);
        btn3Text = (TextView) findViewById(R.id.btn3_text);
        applyTv = (TextView) findViewById(R.id.apply);
    }
}
