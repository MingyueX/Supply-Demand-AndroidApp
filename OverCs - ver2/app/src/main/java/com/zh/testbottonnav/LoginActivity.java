package com.zh.testbottonnav;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.transition.Explode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;

import com.alibaba.fastjson.JSON;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;
import com.zh.testbottonnav.net.User;
import com.zh.testbottonnav.ui.AppTest;
import com.zh.testbottonnav.ui.HttpCallback;
import com.zh.testbottonnav.ui.UserInfoModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import io.rong.imkit.RongIM;
import io.rong.imkit.userinfo.UserDataProvider;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

public class LoginActivity extends AppCompatActivity implements UserDataProvider.UserInfoProvider {

    EditText etUsername;
    EditText etPassword;
    Button btGo;
    CardView cv;
    FloatingActionButton fab;

    private static final String TAG = "LoginActivity";

    private Button btnOne;
    private Button btnTwo;
    private List<Friend> userList;
    private String mUserid;
    private String avatar;
    private String name;
    private EditText et_userName;
    private EditText et_passWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);
        //etUsername = (EditText) findViewById(R.id.et_username);
        //etPassword = (EditText) findViewById(R.id.et_password);
        btGo = (Button) findViewById(R.id.bt_go);
        cv = (CardView) findViewById(R.id.cv);
        fab  = (FloatingActionButton) findViewById(R.id.fab);
        initViewAndData();
        RongIM.setUserInfoProvider(this, true);

    }

    public void initViewAndData(){
        userList = new ArrayList<>();
        btnOne = (Button) findViewById(R.id.btn_connect_server_one);
        btnTwo = (Button) findViewById(R.id.btn_connect_server_two);
        et_userName = (EditText) findViewById(R.id.et_username);
        et_passWord = (EditText) findViewById(R.id.et_password);
        userList.add(new Friend("10086","Miranda",Constant.imageUrl1));
        userList.add(new Friend("10010","littleMi",Constant.imageUrl2));
        userList.add(new Friend("10001","Hello",Constant.imageUrl3));
    }


    public void click(View view) {
        switch (view.getId()) {
            case R.id.btn_connect_server_one:
                verify(Constant.token1);
                break;
            case R.id.btn_connect_server_two:
                verify(Constant.token2);
                break;
            case R.id.fab:
                getWindow().setExitTransition(null);
                getWindow().setEnterTransition(null);

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, fab, fab.getTransitionName());
                startActivity(new Intent(this, RegisterActivity.class), options.toBundle());

                break;
            case R.id.bt_go:
                Explode explode = new Explode();
                explode.setDuration(500);

                /*
                ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
                Intent i2 = new Intent(this,LoginSuccessActivity.class);
                startActivity(i2, oc2.toBundle());

                 */

                verify(Constant.token2);

                getWindow().setExitTransition(explode);
                getWindow().setEnterTransition(explode);

                break;

        }
    }

    public void verify(String token){
        final Context context = this;
        final Activity activity = this;
        UserInfoModel userInfoModel = new UserInfoModel();

        final String username = et_userName.getText().toString();
        final String password = et_passWord.getText().toString();
        if(TextUtils.isEmpty(username)){
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                return;
        } else {
            userInfoModel.getUserInfo(username, password, new HttpCallback() {
                @Override
                public void onSuccess(String result) {
                    final User user = JSON.parseObject(result, User.class);
                    /*
                    try {
                        org.json.JSONObject jsonObject = new JSONObject(result);
                        int id = jsonObject.getInt("id");
                        user.setUserID(id);
                    } catch (JSONException e) {   e.printStackTrace(); }

                     */
                    if (user == null) {
                        Toast.makeText(context, "用户名不存在", Toast.LENGTH_SHORT).show();
                    } else if (!user.getPassword().equals(password)) {
                        Toast.makeText(context, "密码错误", Toast.LENGTH_SHORT).show();
                    } else {
                        avatar = Constant.address + "upload/" + user.getAvatar();
                        name = user.getUserName();
                        SPUtil.saveAvatar("avatar", avatar);
                        SPUtil.setState("isLogin", true);
                        SPUtil.saveUsername("username", user.getUserName());
                        SPUtil.saveId("id", user.getId());
                        Toast.makeText(context, "登陆成功！", Toast.LENGTH_SHORT).show();
                        connectServer(token);
                    }
                }

                @Override
                public void onError(String s) {
                    Log.e("TAG", "请求失败" + s);
                }
            });
        }
    }

    public void connectServer(String token){
            //利用token连服务器
            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                @Override
                public void onError(RongIMClient.ConnectionErrorCode connectionErrorCode) {
                    Toast.makeText(LoginActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onDatabaseOpened(RongIMClient.DatabaseOpenStatus databaseOpenStatus) {
                }

                @Override
                public void onSuccess(String userid) {
                    //userid，是我们在申请token时填入的userid
                    mUserid = userid;
                    Log.d(TAG, "onSuccess: "+userid);
                    System.out.println("success");

                    SPUtil.saveUserId("userId",userid);
                    RongIM.getInstance().refreshUserInfoCache(new UserInfo("10010", "Miranda",Uri.parse(Constant.imageUrl2)));
                    RongIM.getInstance().refreshUserInfoCache(new UserInfo("10086", "Mingyue",Uri.parse(Constant.imageUrl1)));
                    RongIM.getInstance().refreshUserInfoCache(new UserInfo("10001", "Little_Mi",Uri.parse(Constant.imageUrl3)));
                    if(userid.equals("10086")){
                        btnOne.setText("连接融云服务器成功(用户一)");
                        Toast.makeText(LoginActivity.this, "连接成功", Toast.LENGTH_SHORT).show();
                        btnTwo.setEnabled(false);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent();
                                intent.putExtra("data", "Connected");
                                intent.putExtra("avatar", avatar);
                                intent.putExtra("name", name);
                                intent.putExtra("userid", userid);
                                setResult(2, intent);

                                //startActivity(new Intent(LoginActivity.this, MessageFragment.class));
                                finish();
                            }
                        },1000);
                    }else{
                        btnTwo.setText("连接融云服务器成功(用户二)");
                        Toast.makeText(LoginActivity.this, "连接成功", Toast.LENGTH_SHORT).show();
                        btnOne.setEnabled(false);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent();
                                intent.putExtra("data", "Connected");
                                intent.putExtra("avatar", avatar);
                                intent.putExtra("name", name);
                                intent.putExtra("userid", userid);
                                setResult(2, intent);


                                /*
                                LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                View view;
                                view = inflater.inflate(R.layout.profilepage, null);
                                ImageView imageView = (ImageView) view.findViewById(R.id.avatar);
                                Picasso.get().load("http://imgsrc.baidu.com/forum/w%3D580/sign=3d2c2974d0160924dc25a213e406359b/381d9b504fc2d5620ee49ecfe71190ef77c66ccd.jpg").placeholder(R.drawable.avatardefault).centerCrop().fit().into(imageView);

                                 */
                                //startActivity(new Intent(LoginActivity.this,MessageFragment.class));
                                finish();
                            }
                        },1000);
                    }

                }
            });
    }

    @Override
    public UserInfo getUserInfo(String s) {
        for (Friend i:userList){
            if(i.userid.equals(s)){
                //从缓存或者自己服务端获取到数据后返回给融云SDK
                return new UserInfo(i.userid,i.name, Uri.parse(i.imageUrl));
            }
        }
        return null;
    }

}
