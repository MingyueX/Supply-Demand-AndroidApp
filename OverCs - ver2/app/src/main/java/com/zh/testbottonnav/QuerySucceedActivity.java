package com.zh.testbottonnav;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.alibaba.fastjson.JSON;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zh.testbottonnav.adapter.HomePostAdapter;
import com.zh.testbottonnav.net.Post;

import java.util.ArrayList;
import java.util.List;

public class QuerySucceedActivity extends AppCompatActivity {
    private XRecyclerView mRecyclerView;
    private List<Post> postList = new ArrayList<>();

    private TextView backBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.query_result);

        Intent intent = getIntent();
        String queryResult = intent.getStringExtra("result");

        backBtn = (TextView) findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mRecyclerView = (XRecyclerView) findViewById(R.id.recyclerView);

        //initFruits();
        // initFruits2();

        //RecyclerView recyclerView1 = (RecyclerView) getView().findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

        HomePostAdapter adapter = new HomePostAdapter(postList);
        adapter.setIs_gridview(true);
        mRecyclerView.setAdapter(adapter);

        postList = JSON.parseArray(queryResult, Post.class);
        for (int i = 0; i < postList.size(); i++) {
            Post post = postList.get(i);
            post.setImage1(post.getImage1() == null ? null : (Constant.address + "upload/" + post.getImage1()));
            post.setImage2(post.getImage2() == null ? null : (Constant.address + "upload/" + post.getImage2()));
            post.setImage3(post.getImage3() == null ? null : (Constant.address + "upload/" + post.getImage3()));
            post.setImage4(post.getImage4() == null ? null : (Constant.address + "upload/" + post.getImage4()));
            post.setImage5(post.getImage5() == null ? null : (Constant.address + "upload/" + post.getImage5()));
            post.setImage6(post.getImage6() == null ? null : (Constant.address + "upload/" + post.getImage6()));
            post.setImage7(post.getImage7() == null ? null : (Constant.address + "upload/" + post.getImage7()));
            post.setImage8(post.getImage8() == null ? null : (Constant.address + "upload/" + post.getImage8()));
            post.setImage9(post.getImage9() == null ? null : (Constant.address + "upload/" + post.getImage9()));
        }
        adapter.addList(postList);

    }
}
