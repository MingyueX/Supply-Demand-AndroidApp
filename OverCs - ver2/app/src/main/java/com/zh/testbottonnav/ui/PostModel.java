package com.zh.testbottonnav.ui;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zh.testbottonnav.Constant;
import com.zh.testbottonnav.SPUtil;
import com.zh.testbottonnav.net.Post;
import com.zh.testbottonnav.net.QueryPost;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PostModel {

    public void getPostByStar(final HttpCallback callback) {
        RequestParams params = new RequestParams(Constant.address + "hello/getPostByStar");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess (String result) {
                callback.onSuccess(result);
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                callback.onError(ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public void getPost(Integer id, final HttpCallback callback) {

        RequestParams params = new RequestParams(Constant.address + "hello/getPost");
        if (id != 0) {
            params.addQueryStringParameter("id", id.toString());
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess (String result) {
                callback.onSuccess(result);
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                callback.onError(ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public void deletePost(Integer postId, final HttpCallback callback) {
        RequestParams params = new RequestParams(Constant.address + "hello/delete");
        params.addQueryStringParameter("postId", postId.toString());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                callback.onSuccess(result);
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("TAG", "请求失败" + ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public void setPost(Post post, int uid) {
        RequestParams params = new RequestParams(Constant.address + "hello/send");
        params.addQueryStringParameter("type", post.getType());
        params.addQueryStringParameter("title", post.getTitle());
        params.addQueryStringParameter("description", post.getDescription());
        params.addQueryStringParameter("categoryId", post.getCategory_id().toString());
        params.addQueryStringParameter("country", post.getCountry());
        params.addQueryStringParameter("province", post.getProvince());
        params.addQueryStringParameter("city", post.getCity());
        params.addQueryStringParameter("lengthMin", post.getLengthMin().toString());
        params.addQueryStringParameter("lengthMax", post.getLengthMax().toString());
        params.addQueryStringParameter("widthMin", post.getWidthMin().toString());
        params.addQueryStringParameter("widthMax", post.getWidthMax().toString());
        params.addQueryStringParameter("heightMin", post.getHeightMin().toString());
        params.addQueryStringParameter("heightMax", post.getHeightMax().toString());
        params.addQueryStringParameter("weightMin", post.getWeightMin().toString());
        params.addQueryStringParameter("weightMax", post.getWeightMax().toString());
        params.addQueryStringParameter("price", post.getPrice().toString());
        params.addQueryStringParameter("stars", post.getStars() == null ? "0" : post.getStars().toString());
        params.addQueryStringParameter("uid", Integer.toString(uid));
        params.addQueryStringParameter("categoryId", post.getCategory_id() == null ? null : post.getCategory_id().toString());
        params.addQueryStringParameter("quantity", post.getQuantity() == null ? null : post.getQuantity().toString());
        params.addQueryStringParameter("delivery", post.getDelivery());
        //params.addQueryStringParameter("status", post.getStatus());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess (String result) {
                System.out.println(result);
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("TAG", "请求失败" + ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

        List<String> imgList = new ArrayList<String>();
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

        RequestParams paramsImg = new RequestParams(Constant.address + "hello/saveImg");
        paramsImg.setMultipart(true);

        for (int i = 1; i <= 9; i++) {
            if  (i <= imgList.size()) {
                File img = new File(imgList.get(i - 1));
                paramsImg.addBodyParameter("img" + i, img);
            } else {
                File img = new File(imgList.get(0));
                paramsImg.addBodyParameter("img" + i, img);
            }
            x.http().post(paramsImg, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Log.e("TAG", "请求失败" + ex.toString());
                }

                @Override
                public void onCancelled(CancelledException cex) {
                }

                @Override
                public void onFinished() {
                }
            });
        }

    }

    public void queryPost(QueryPost queryPost, final HttpCallback callback) {
        RequestParams params = new RequestParams(Constant.address + "hello/query");
        String query = JSONObject.toJSONString(queryPost);
        params.addParameter("queryPost", query);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                callback.onSuccess(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("TAG", "请求失败" + ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }
}
