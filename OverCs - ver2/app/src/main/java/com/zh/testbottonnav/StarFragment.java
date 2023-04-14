package com.zh.testbottonnav;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.alibaba.fastjson.JSON;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zh.testbottonnav.adapter.HomePostAdapter;
import com.zh.testbottonnav.net.Post;
import com.zh.testbottonnav.ui.HttpCallback;
import com.zh.testbottonnav.ui.PostModel;

import java.util.ArrayList;
import java.util.List;

public class StarFragment extends Fragment {
    private int i;
    private XRecyclerView mRecyclerView;
    private HomePostAdapter adapter;
    private List<Post> postList = new ArrayList<>();
    private List<Post> displayList = new ArrayList<>();
    // private List<Fruit> fruitList2 = new ArrayList<>();

    private Handler uiHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Log.d("StarFragment", "got starpost");
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
                    displayList.clear();
                    for (i = 0; i < 8; i++) {
                        if (i >= postList.size()) {
                            break;
                        }
                        displayList.add(postList.get(i));
                    }
                    adapter.addList(displayList);
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.starpage_edited, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Toolbar toolbar  = (Toolbar) getView().findViewById(R.id.toolbarStar) ;
        //getActivity().setActionBar(toolbar);
        //getActivity().getActionBar().setDisplayHomeAsUpEnabled(false);

        mRecyclerView = (XRecyclerView) getView().findViewById(R.id.recyclerview);

        //initFruits();
        // initFruits2();

        //RecyclerView recyclerView1 = (RecyclerView) getView().findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this.getContext(), 2);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

        adapter = new HomePostAdapter(postList);
        adapter.setIs_gridview(true);
        mRecyclerView.setAdapter(adapter);

        PostModel postModel = new PostModel();
        postModel.getPostByStar(new HttpCallback() {

            @Override
            public void onSuccess(String result) {
                postList = JSON.parseArray(result, Post.class);
                Message msg = new Message();
                msg.what = 1;
                uiHandler.sendMessage(msg);
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

        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        postList.clear();
                        postModel.getPostByStar(new HttpCallback(){

                            @Override
                            public void onSuccess(String result) {
                                postList = JSON.parseArray(result, Post.class);
                                Message msg = new Message();
                                msg.what = 1;
                                uiHandler.sendMessage(msg);
                            }

                            @Override
                            public void onError(String s) {
                                Log.e("TAG", "请求失败" + s);
                            }
                        });
                        adapter.notifyDataSetChanged();
                        mRecyclerView.refreshComplete();
                    }
                }, 1000);
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable(){
                    public void run() {
                        int j = i + 8;
                        for (; i < j; i++) {
                            if (i >= postList.size()) {
                                break;
                            }
                            displayList.add(postList.get(i));
                        }
                        mRecyclerView.loadMoreComplete();
                        adapter.notifyDataSetChanged();
                    }
                }, 1000);
            }
        });
    }


    /*
        RecyclerView recyclerView2 = (RecyclerView) getView().findViewById(R.id.recycler_view2);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this.getContext());
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView2.setLayoutManager(layoutManager2);
        FruitAdapter adapter2 = new FruitAdapter(fruitList2);
        recyclerView2.setAdapter(adapter2);
        */

    /*
    private void initFruits() {
        for (int i = 0; i < 2; i++) {
            Fruit apple = new Fruit("pic111", "1", R.drawable.interact2);
            fruitList.add(apple);
            Fruit banana = new Fruit("pic222", "2", R.drawable.interact2);
            fruitList.add(banana);
            Fruit orange = new Fruit("pic333", "3", R.drawable.interact2);
            fruitList.add(orange);
            Fruit watermelon = new Fruit("pic444", "4", R.drawable.interact2);
            fruitList.add(watermelon);
            Fruit pear = new Fruit("pic555", "5", R.drawable.interact2);
            fruitList.add(pear);

        }
    }

     */

    /*
    private void initFruits2() {
        for  (int i = 0; i < 2; i++) {
            Fruit grape = new Fruit("pic666", R.drawable.interact1);
            fruitList2.add(grape);
            Fruit pineapple = new Fruit("pic777", R.drawable.interact1);
            fruitList2.add(pineapple);
            Fruit strawberry = new Fruit("pic888", R.drawable.interact1);
            fruitList2.add(strawberry);
            Fruit cherry = new Fruit("pic999", R.drawable.interact1);
            fruitList2.add(cherry);
            Fruit mango = new Fruit("pic000", R.drawable.interact1);
            fruitList2.add(mango);
        }
    }
     */

    public static StarFragment newInstance(String string) {

        Bundle args = new Bundle();
        args.putString("Topic", string);

        StarFragment fragment = new StarFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
