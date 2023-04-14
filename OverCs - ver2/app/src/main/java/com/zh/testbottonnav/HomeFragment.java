package com.zh.testbottonnav;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.impl.RefreshFooterWrapper;
import com.scwang.smartrefresh.layout.impl.RefreshHeaderWrapper;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zh.testbottonnav.adapter.HomePostAdapter;
import com.zh.testbottonnav.net.Post;
import com.zh.testbottonnav.ui.HttpCallback;
import com.zh.testbottonnav.ui.PostModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private int i1;
    private int i2;
    private List<Post> homePost1 = new ArrayList<>();
    private List<Post> homePost2 = new ArrayList<>();
    private List<Post> displayPost1 = new ArrayList<>();
    private List<Post> displayPost2 = new ArrayList<>();
    private HomePostAdapter adapter1;
    private HomePostAdapter adapter2;

    //uiHandler在主线程中创建，所以自动绑定主线程
    private Handler uiHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Log.d("HomeFragment", "got homepost1");
                    for (int i = 0; i < homePost1.size(); i++) {
                        Post post = homePost1.get(i);
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
                    displayPost1.clear();
                    for (i1 = 0; i1 < 8; i1++) {
                        if (i1 >= homePost1.size()) {
                            break;
                        }
                        displayPost1.add(homePost1.get(i1));
                    }
                    adapter1.addList(displayPost1);
                    break;
                case 2:
                    Log.d("HomeFragment", "got homepost2");
                    for (int i = 0; i < homePost2.size(); i++) {
                        Post post = homePost2.get(i);
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
                    displayPost2.clear();
                    for (i2 = 0; i2 < 8; i2++) {
                        if (i2 >= homePost2.size()) {
                            break;
                        }
                        displayPost2.add(homePost2.get(i2));
                    }
                    adapter2.addList(homePost2);
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.homepage_edited, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        /*
        Toolbar toolbar  = (Toolbar) getView().findViewById(R.id.toolbarHome) ;
        getActivity().setActionBar(toolbar);
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(false);

         */

        RefreshLayout refreshLayout = (RefreshLayout) getView().findViewById(R.id.swipe_view1);
        refreshLayout.setRefreshHeader(new ClassicsHeader(this.getContext()));
        refreshLayout.setRefreshFooter(new RefreshFooterWrapper(new ClassicsFooter((this.getContext()))), -1, -2);

        RecyclerView recyclerView1 = (RecyclerView) getView().findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this.getContext());
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView1.setLayoutManager(layoutManager1);
        adapter1 = new HomePostAdapter(homePost1);
        recyclerView1.setAdapter(adapter1);

        RefreshLayout refreshLayout2 = (RefreshLayout) getView().findViewById(R.id.swipe_view2);
        refreshLayout2.setRefreshHeader(new RefreshHeaderWrapper(new ClassicsFooter(this.getContext())), -1, -2);
        refreshLayout2.setRefreshFooter(new RefreshFooterWrapper(new ClassicsHeader(this.getContext())));

        RecyclerView recyclerView2 = (RecyclerView) getView().findViewById(R.id.recycler_view2);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this.getContext());
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutManager2.setReverseLayout(true);
        recyclerView2.setLayoutManager(layoutManager2);
        adapter2 = new HomePostAdapter(homePost2);
        recyclerView2.setAdapter(adapter2);
        //LinearLayout mLinearLayout = (LinearLayout) getView().findViewById(R.id.reversed);
        //mLinearLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);


        PostModel postModel = new PostModel();
        postModel.getPostByStar(new HttpCallback(){

            @Override
            public void onSuccess(String result) {
                homePost1 = JSON.parseArray(result, Post.class);
                Message msg = new Message();
                msg.what = 1;
                uiHandler.sendMessage(msg);
                // final List<Post> posts = JSON.parseArray(result, Post.class);
                /*
                HomePost mPost;
                for (int i = 0; i < posts.size(); i++) {
                    if(posts.get(i) != null) {
                        Post post = posts.get(i);
                        String url = "http://192.168.17.80:8080/spring_helloworld_war_exploded/upload/" + post.getImage1();
                        String type = post.getType();
                        mPost = new HomePost(post.getId(), post.getTitle(), post.getDescription(), url == null ? "" : url, type);
                    } else {
                        mPost = new HomePost(0, "No Info", "No Info", "", null);
                    }
                    homePost1.add(mPost);
                }
                 */
            }

            @Override
            public void onError(String s) {
                Log.e("TAG", "请求失败" + s);
            }
        });

        postModel.getPost(0, new HttpCallback(){

            @Override
            public void onSuccess(String result) {
                homePost2 = JSON.parseArray(result, Post.class);
                Message msg = new Message();
                msg.what = 2;
                uiHandler.sendMessage(msg);
                // final List<Post> posts = JSON.parseArray(result, Post.class);
                /*
                HomePost mPost;
                for (int i = 0; i < posts.size(); i++) {
                    if(posts.get(i) != null) {
                        Post post = posts.get(i);
                        String url = "http://192.168.17.80:8080/spring_helloworld_war_exploded/upload/" + post.getImage1();
                        String type = post.getType();
                        mPost = new HomePost(post.getId(), post.getTitle(), post.getDescription(), url == null ? "" : url, type);
                    } else {
                        mPost = new HomePost(0, "No Info", "No Info", "", null);
                    }
                    homePost2.add(mPost);
                }

                 */
            }

            @Override
            public void onError(String s) {
                Log.e("TAG", "请求失败" + s);
            }
        });

        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        homePost1.clear();
                        postModel.getPostByStar(new HttpCallback(){

                            @Override
                            public void onSuccess(String result) {
                                homePost1 = JSON.parseArray(result, Post.class);
                                Message msg = new Message();
                                msg.what = 1;
                                uiHandler.sendMessage(msg);
                            }

                            @Override
                            public void onError(String s) {
                                Log.e("TAG", "请求失败" + s);
                            }
                        });
                        adapter1.notifyDataSetChanged();
                        refreshLayout.finishRefresh();
                    }
                }, 1500);
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                recyclerView1.stopScroll();
                recyclerView1.stopNestedScroll();
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                            int j = i1 + 8;
                            for (; i1 < j; i1++) {
                                if (i1 >= homePost1.size()) {
                                    break;
                                }
                                displayPost1.add(homePost1.get(i1));
                            }
                        adapter1.notifyDataSetChanged();
                        refreshLayout.finishLoadMore();
                    }
                }, 1500);
            }
        });

        refreshLayout2.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout2) {
                refreshLayout2.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int j = i2 + 8;
                        for (; i2 < j; i2++) {
                            if (i2 >= homePost2.size()) {
                                break;
                            }
                            displayPost2.add(homePost2.get(i2));
                        }
                        adapter2.notifyDataSetChanged();
                        refreshLayout2.finishRefresh();
                    }
                }, 1500);
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout2) {
                recyclerView2.stopScroll();
                recyclerView2.stopNestedScroll();
                refreshLayout2.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        homePost2.clear();
                        postModel.getPost(0, new HttpCallback() {

                            @Override
                            public void onSuccess(String result) {
                                homePost2 = JSON.parseArray(result, Post.class);
                                Message msg = new Message();
                                msg.what = 2;
                                uiHandler.sendMessage(msg);
                            }

                            @Override
                            public void onError(String s) {
                                Log.e("TAG", "请求失败" + s);
                            }
                        });
                        adapter2.notifyDataSetChanged();
                        refreshLayout2.finishLoadMore();
                    }
                }, 1500);
            }
        });
    }

    //----------------------------------------------------

    public static HomeFragment newInstance(String string) {

        Bundle args = new Bundle();
        args.putString("Topic", string);

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
