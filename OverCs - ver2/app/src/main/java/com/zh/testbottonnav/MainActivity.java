package com.zh.testbottonnav;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private HomeFragment mHomeFragment;
    private StarFragment mStarFragment;
    private MessageFragment mMessageFragment;
    private ProfileFragment mProfileFragment;
    private RadioButton mRadioHome;
    private RadioButton mRadioStar;
    private RadioButton mRadioMessage;
    private RadioButton mRadioProfile;
    private FloatingActionButton mainFAB;
    private FloatingActionButton sendFAB;
    private FloatingActionButton searchFAB;
    private boolean isFABOpen = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RadioGroup mRadioGroup = (RadioGroup) findViewById(R.id.radio_group);

        initView();

        mainFAB = (FloatingActionButton) findViewById(R.id.mainFAB);
        sendFAB = (FloatingActionButton) findViewById(R.id.sendFAB);
        searchFAB = (FloatingActionButton) findViewById(R.id.searchFAB);
        mainFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isFABOpen){
                    showFABMenu();
                }else{
                    closeFABMenu();
                }
            }
        });

        sendFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SPUtil.getState("isLogin") == false) {
                    Toast.makeText(getParent(), "请先登陆！", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent i = new Intent();
                i.setComponent(new ComponentName("com.zh.testbottonnav", "com.zh.testbottonnav.Sendpage"));
                startActivity(i);
            }
        });

        searchFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setComponent(new ComponentName("com.zh.testbottonnav", "com.zh.testbottonnav.ScreeningActivity"));
                startActivity(i);
            }
        });

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkId) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch (checkId) {
                    case R.id.rb_home:
                        if (mHomeFragment == null) {
                            mHomeFragment = HomeFragment.newInstance(getString(R.string.item_home));
                        }
                        transaction.replace(R.id.sub_content, (Fragment) mHomeFragment);
                        break;
                    case R.id.rb_star:
                        if (mStarFragment == null) {
                            mStarFragment = StarFragment.newInstance(getString(R.string.item_star));
                        }
                        transaction.replace(R.id.sub_content, (Fragment) mStarFragment);
                        break;
                    case R.id.rb_message:
                        if (mMessageFragment == null) {
                            mMessageFragment = MessageFragment.newInstance(getString(R.string.item_message));
                        }
                        transaction.replace(R.id.sub_content, (Fragment) mMessageFragment);
                        break;
                    case R.id.rb_profile:
                        if (mProfileFragment == null) {
                            mProfileFragment = ProfileFragment.newInstance(getString(R.string.item_profile));
                        }
                        transaction.replace(R.id.sub_content, (Fragment) mProfileFragment);
                        break;
                }
                setTabState();//设置状态
                transaction.commit();
            }
        });
        setDefaultFragment();
}

    private void initView(){
        mRadioHome = (RadioButton) findViewById(R.id.rb_home);
        mRadioStar = (RadioButton) findViewById(R.id.rb_star);
        mRadioMessage= (RadioButton) findViewById(R.id.rb_message);
        mRadioProfile = (RadioButton) findViewById(R.id.rb_profile);
    }

    private void showFABMenu(){
        isFABOpen=true;
        mainFAB.animate().rotation(-45);
        sendFAB.animate().translationY(getResources().getDimension(R.dimen.standard_65));
        searchFAB.animate().translationY(getResources().getDimension(R.dimen.standard_125));
    }

    private void closeFABMenu(){
        isFABOpen=false;
        mainFAB.animate().rotation(0);
        sendFAB.animate().translationY(0);
        searchFAB.animate().translationY(0);
    }

    private void setDefaultFragment() {
        mRadioHome.setChecked(true);
        mRadioStar.setChecked(false);
        mRadioMessage.setChecked(false);
        mRadioProfile.setChecked(false);
        if (mRadioHome.isChecked()) {
            setTabState();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            mHomeFragment = mHomeFragment.newInstance(getString(R.string.item_home));
            transaction.replace(R.id.sub_content, mHomeFragment).commit();
        }
    }

    private void setTabState() {
        setHomeState();
        setStarState();
        setMessageState();
        setProfileState();

    }


    private void setHomeState() {
        if (mRadioHome.isChecked()) {
            //mRadioHome.setBackgroundColor(getResources().getColor(R.color.grey));
            mRadioHome.setTextColor(getResources().getColor(R.color.black));
        } else {
            mRadioHome.setTextColor(getResources().getColor(R.color.silver));
            //mRadioHome.setBackgroundColor(getResources().getColor(R.color.white));
        }
    }

    private void setStarState() {
        if (mRadioStar.isChecked()) {
            mRadioStar.setTextColor(getResources().getColor(R.color.black));
            //mRadioStar.setBackgroundColor(getResources().getColor(R.color.grey));
        } else {
            mRadioStar.setTextColor(getResources().getColor(R.color.silver));
            //mRadioStar.setBackgroundColor(getResources().getColor(R.color.white));
        }
    }

    private void setMessageState() {
        if (mRadioMessage.isChecked()) {
            mRadioMessage.setTextColor(getResources().getColor(R.color.black));
            //mRadioMessage.setBackgroundColor(getResources().getColor(R.color.grey));
        } else {
            mRadioMessage.setTextColor(getResources().getColor(R.color.silver));
            //mRadioMessage.setBackgroundColor(getResources().getColor(R.color.white));
        }
    }

    private void setProfileState() {
        if (mRadioProfile.isChecked()) {
            mRadioProfile.setTextColor(getResources().getColor(R.color.black));
            //mRadioProfile.setBackgroundColor(getResources().getColor(R.color.grey));
        } else {
            mRadioProfile.setTextColor(getResources().getColor(R.color.silver));
            //mRadioProfile.setBackgroundColor(getResources().getColor(R.color.white));
        }
    }


}
