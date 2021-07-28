package com.zh.testbottonnav;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.Permission;
import com.luck.picture.lib.permissions.RxPermissions;
import com.squareup.picasso.Picasso;
import com.zh.testbottonnav.adapter.CategoryAdapter;
import com.zh.testbottonnav.net.User;
import com.zh.testbottonnav.ui.HttpCallback;
import com.zh.testbottonnav.ui.UserInfoModel;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class ProfileFragment extends Fragment {

    private Button login;
    private TextView textView;
    private ImageView imageView;
    private String avatarUrl = null;
    private String username = "未登陆";
    private ImageView editAvatar;
    private List<LocalMedia> selectList = new ArrayList<>();
    private PopupWindow pop;

    //uiHandler在主线程中创建，所以自动绑定主线程
    private Handler uiHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    SPUtil.saveAvatar("avatar", avatarUrl);
                    Log.d("ProfileFragment", avatarUrl);
                    Picasso.get().load(avatarUrl).placeholder(R.drawable.avatardefault).centerCrop().transform(new CropCircleTransformation()).fit().into(imageView);
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SPUtil.getState("isLogin")) {
                    avatarUrl = SPUtil.getAvatar("avatar");
                    username = SPUtil.getUsername("username");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profilepage, container, false);
        textView = (TextView) view.findViewById(R.id.textView3);
        imageView = (ImageView) view.findViewById(R.id.avatar);
        login = (Button) view.findViewById(R.id.loginBtn);
        editAvatar = (ImageView) view.findViewById(R.id.editAvatar);
        Picasso.get().load(avatarUrl).placeholder(R.drawable.avatardefault).centerCrop().transform(new CropCircleTransformation()).fit().into(imageView);
        textView.setText(username);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textView.getText().toString() != "未登陆") {
                    onButtonShowPopupWindowClick(v);
                }
            }
        });
        if (SPUtil.getState("isLogin")) {
            login.setText("注销");
        } else {
            editAvatar.setVisibility(View.GONE);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login.getText().toString() == "注销"){
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.avatardefault));
                    textView.setText("未登陆");
                    login.setText("登陆/注册");
                    SPUtil.saveId("id", 0);
                    SPUtil.setState("isLogin", false);
                    editAvatar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "已注销", Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent();
                    i.setComponent(new ComponentName("com.zh.testbottonnav", "com.zh.testbottonnav.LoginActivity"));
                    startActivityForResult(i, 1);
                }
            }
        });
        editAvatar.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View v) {
                    RxPermissions rxPermissions = new RxPermissions(getActivity());
                    rxPermissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .subscribe(new Consumer<Permission>() {
                                @Override
                                public void accept(Permission permission) throws Exception {
                                    if (permission.granted) {
                                        showPop();
                                    } else {
                                        Toast.makeText(getActivity(), "拒绝", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
        });
    }

    private void showPop() {
        View bottomView = View.inflate(getActivity(), R.layout.layout_bottom_dialog, null);
        TextView mAlbum = bottomView.findViewById(R.id.tv_album);
        TextView mCamera = bottomView.findViewById(R.id.tv_camera);
        TextView mCancel = bottomView.findViewById(R.id.tv_cancel);

        pop = new PopupWindow(bottomView, -1, -2);
        pop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop.setOutsideTouchable(true);
        pop.setFocusable(true);
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.5f;
        getActivity().getWindow().setAttributes(lp);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
            }
        });
        pop.setAnimationStyle(R.style.main_menu_photo_anim);
        pop.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);

        View.OnClickListener clickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tv_album:
                        PictureSelector.create(ProfileFragment.this)
                                .openGallery(PictureMimeType.ofImage())
                                .imageSpanCount(4)
                                .selectionMode(PictureConfig.SINGLE)
                                .forResult(PictureConfig.CHOOSE_REQUEST);
                        break;
                    case R.id.tv_camera:
                        //拍照
                        PictureSelector.create(ProfileFragment.this)
                                .openCamera(PictureMimeType.ofImage())
                                .forResult(PictureConfig.CHOOSE_REQUEST);
                        break;
                    case R.id.tv_cancel:
                        //取消
                        //closePopupWindow();
                        break;
                }
                closePopupWindow();
            }
        };

        mAlbum.setOnClickListener(clickListener);
        mCamera.setOnClickListener(clickListener);
        mCancel.setOnClickListener(clickListener);
    }

    public void closePopupWindow() {
        if (pop != null && pop.isShowing()) {
            pop.dismiss();
            pop = null;
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == super.getActivity().RESULT_OK) {
            List<LocalMedia> images;
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {// 图片选择结果回调

                images = PictureSelector.obtainMultipleResult(data);
                selectList.addAll(images);

                //uri = data.getData();

                //selectList = PictureSelector.obtainMultipleResult(data);

                // 例如 LocalMedia 里面返回三种path
                // 1.media.getPath(); 为原图path
                // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                LocalMedia media= selectList.get(0);
                String path;
                //裁剪过
                if (media.isCut() && !media.isCompressed()) {
                    path = media.getCutPath();
                }
                //压缩过
                else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                    path = media.getCompressPath();
                }
                else {
                    path = media.getPath();
                }
                UserInfoModel userInfoModel = new UserInfoModel();
                userInfoModel.setAvatar(SPUtil.getId("id"), path, new HttpCallback() {
                    @Override
                    public void onSuccess(String s) {
                        avatarUrl = Constant.address + "upload/" + s;
                        Message msg = new Message();
                        msg.what = 1;
                        uiHandler.sendMessage(msg);
                    }

                    @Override
                    public void onError(String s) {

                    }
                });
            }
        }

        if (resultCode == 2) {
                if (requestCode == 1) {
                    String returnData = data.getStringExtra("data");
                    if (returnData.equals("Connected")) {
                        avatarUrl = data.getStringExtra("avatar");
                        username = data.getStringExtra("name");
                        textView.setText(username);
                        login.setText("注销");
                        editAvatar.setVisibility(View.VISIBLE);
                        Picasso.get().load(avatarUrl).placeholder(R.drawable.avatardefault).centerCrop().transform(new CropCircleTransformation()).fit().into(imageView);
                    }
                }
        }
    }

    public static ProfileFragment newInstance(String string) {

        Bundle args = new Bundle();
        args.putString("Topic", string);

        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void onButtonShowPopupWindowClick(View view) {
        // inflate the layout of the popup window
        /*
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popwindow_editname, null);
         */
        View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.popwindow_editname, null);
        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        EditText editname = (EditText) popupView.findViewById(R.id.editName);
        ImageView okBtn = (ImageView) popupView.findViewById(R.id.okBtn);
        ImageView cancelBtn = (ImageView) popupView.findViewById(R.id.cancelBtn);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = editname.getText().toString();
                if (!newName.equals("")) {
                    textView.setText(newName);
                    UserInfoModel userInfoModel = new UserInfoModel();
                    userInfoModel.setUsername(SPUtil.getId("id"), newName);
                    SPUtil.saveUsername("username", newName);
                    popupWindow.dismiss();
                }
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

}
