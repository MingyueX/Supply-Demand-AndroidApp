package com.zh.testbottonnav;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.Permission;
import com.luck.picture.lib.permissions.RxPermissions;
import com.zh.testbottonnav.adapter.CategoryAdapter;
import com.zh.testbottonnav.adapter.GridImageAdapter;
import com.zh.testbottonnav.net.Post;
import com.zh.testbottonnav.ui.PostModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.functions.Consumer;

public class Sendpage extends Activity {
    //private ForwardFragment mFwFragment;
    //private BackwardFragment mBwFragment;
    private RadioGroup SendRG;
    private RadioGroup DeliveryRG;
    private RadioButton mRadioForward;
    private RadioButton mRadioBackward;
    private TextView cancel;
    private TextView send;
    private EditText title;
    private EditText description;
    private EditText lengthMin;
    private EditText lengthMax;
    private EditText widthMin;
    private EditText widthMax;
    private EditText heightMin;
    private EditText heightMax;
    private EditText weightMin;
    private EditText weightMax;
    private EditText price;
    private RadioButton express;
    private RadioButton faceToFace;
    private RadioButton both;
    private ImageView addCate;
    private TextView chosenCate;

    //private Uri uri;

    private String image1;
    private String image2;
    private String image3;
    private String image4;
    private String image5;
    private String image6;
    private String image7;
    private String image8;
    private String image9;

    private int maxSelectNum = 9;
    private List<LocalMedia> selectList = new ArrayList<>();
    private GridImageAdapter  adapter;
    private RecyclerView mRecyclerView;
    private PopupWindow pop;

    public int selectedCatePos;

    public List<String> categories = Arrays.asList("女装", "男装", "童装", "女鞋", "男鞋", "童鞋", "皮具",
            "箱包", "饰品", "钟表", "眼镜", "护肤品", "彩妆", "香水", "日用品", "运动用品", "户外装备",
            "清洁工具", "宠物用品", "手工艺品", "布艺软饰", "家纺", "厨具", "食品", "营养保健品", "母婴用品",
            "儿童玩具", "电器", "汽车配件", "文具", "图书", "数码产品", "影音设备", "礼物", "奢侈品", "不限");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendpage);

        initView();

        SendRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkId) {
                setSendClipState();
            }
        });

        DeliveryRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                setDeliveryClipState();
            }
        });

        addCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chosenCate.getText().toString().equals("")) {
                    onButtonShowPopupWindowClick(v);
                }
                else {
                    addCate.animate().rotation(0);
                    chosenCate.setText("");
                }
            }
        });

        mRadioForward.setChecked(true);

        both.setChecked(true);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 检查填写项

                String t = title.getText().toString();
                if (title.getText().toString().equals("")) {
                    Toast.makeText(Sendpage.this, "标题不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (description.getText().toString().equals("")) {
                    Toast.makeText(Sendpage.this, "内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (lengthMax.getText().toString().equals("")) {
                    Toast.makeText(Sendpage.this, "长度最大值不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (widthMax.getText().toString().equals("")) {
                    Toast.makeText(Sendpage.this, "宽度最大值不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (heightMax.getText().toString().equals("")) {
                    Toast.makeText(Sendpage.this, "高度最大值不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (weightMax.getText().toString().equals("")) {
                    Toast.makeText(Sendpage.this, "重量最大值不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (price.getText().toString().equals("")) {
                    Toast.makeText(Sendpage.this, "价格不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (lengthMin.getText().toString().equals("")) {
                    lengthMin.setText("0");
                }
                if (widthMin.getText().toString().equals("")) {
                    widthMin.setText("0");
                }
                if (heightMin.getText().toString().equals("")) {
                    heightMin.setText("0");
                }
                if (weightMin.getText().toString().equals("")) {
                    weightMin.setText("0");
                }

                Integer length_min = Integer.parseInt(lengthMin.getText().toString());
                Integer length_max = Integer.parseInt(lengthMax.getText().toString());
                Integer width_min = Integer.parseInt(widthMin.getText().toString());
                Integer width_max = Integer.parseInt(widthMax.getText().toString());
                Integer height_min = Integer.parseInt(heightMin.getText().toString());
                Integer height_max = Integer.parseInt(heightMax.getText().toString());
                Double weight_min = Double.parseDouble(weightMin.getText().toString());
                Double weight_max = Double.parseDouble(weightMax.getText().toString());

                if (length_min > length_max) {
                    Toast.makeText(Sendpage.this, "长度最大值不能小于最小值", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (width_min > width_max) {
                    Toast.makeText(Sendpage.this, "宽度最大值不能小于最小值", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (height_min > height_max) {
                    Toast.makeText(Sendpage.this, "高度最大值不能小于最小值", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (weight_min > weight_max) {
                    Toast.makeText(Sendpage.this, "重量最大值不能小于最小值", Toast.LENGTH_SHORT).show();
                    return;
                }

                Post post = new Post();
                if (mRadioForward.isChecked()) {
                    post.setType("HELP");
                } else if (mRadioBackward.isChecked()) {
                    post.setType("REQUEST");
                }
                post.setTitle(title.getText().toString());
                post.setDescription(description.getText().toString());
                post.setLengthMin(length_min);
                post.setLengthMax(length_max);
                post.setWidthMin(width_min);
                post.setWidthMax(width_max);
                post.setHeightMin(height_min);
                post.setHeightMax(height_max);
                post.setWeightMax(weight_max);
                post.setWeightMin(weight_min);
                post.setPrice(price.getText().toString());
                if (express.isChecked()) {
                    post.setDelivery("EXPRESS");
                } else if (faceToFace.isChecked()) {
                    post.setDelivery("FACETOFACE");
                } else if (both.isChecked()) {
                    post.setDelivery("BOTH");
                }

                String category = chosenCate.getText().toString();
                if (category.equals("")) {
                    post.setCategory_id(categories.size());
                } else {
                    post.setCategory_id(selectedCatePos + 1);
                }

                for (int i = 0; i < selectList.size(); i++) {
                    if (i == 0) {
                        image1 = selectList.get(i).getPath();
                        post.setImage1(image1);
                    }
                    if (i == 1) {
                        image2 = selectList.get(i).getPath();
                        post.setImage2(image2);
                    }
                    if (i == 2) {
                        image3 = selectList.get(i).getPath();
                        post.setImage3(image3);
                    }
                    if (i == 3) {
                        image4 = selectList.get(i).getPath();
                        post.setImage4(image4);
                    }
                    if (i == 4) {
                        image5 = selectList.get(i).getPath();
                        post.setImage5(image5);
                    }
                    if (i == 5) {
                        image6 = selectList.get(i).getPath();
                        post.setImage6(image6);
                    }
                    if (i == 6) {
                        image7 = selectList.get(i).getPath();
                        post.setImage7(image7);
                    }
                    if (i == 7) {
                        image8 = selectList.get(i).getPath();
                        post.setImage8(image8);
                    }
                    if (i == 8) {
                        post.setImage9(selectList.get(i).getPath());
                    }
                }
                PostModel postModel = new PostModel();
                postModel.setPost(post, SPUtil.getId("id"));

                Intent i = new Intent();
                i.setComponent(new ComponentName("com.zh.testbottonnav", "com.zh.testbottonnav.SendSucceedActivity"));
                startActivity(i);
                finish();
            }
        });



        mRecyclerView = findViewById(R.id.recyclerView);

        initWidget();
    }

    private void initWidget() {
        GridLayoutManager manager  =  new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        adapter = new GridImageAdapter(this, onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setSelectMax(maxSelectNum);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnitemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(Sendpage.this).externalPicturePreview(position, selectList);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(Sendpage.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(Sendpage.this).externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }
        });
    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {

        @SuppressLint("CheckResult")
        @Override
        public void onAddPicClick() {
            RxPermissions rxPermissions = new RxPermissions(Sendpage.this);
            rxPermissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(new Consumer<Permission>() {
                        @Override
                        public void accept(Permission permission) throws Exception {
                            if (permission.granted) {
                                showPop();
                            } else {
                                Toast.makeText(Sendpage.this, "拒绝", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    };

    private void showPop() {
        View bottomView = View.inflate(Sendpage.this, R.layout.layout_bottom_dialog, null);
        TextView mAlbum = bottomView.findViewById(R.id.tv_album);
        TextView mCamera = bottomView.findViewById(R.id.tv_camera);
        TextView mCancel = bottomView.findViewById(R.id.tv_cancel);

        pop = new PopupWindow(bottomView, -1, -2);
        pop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop.setOutsideTouchable(true);
        pop.setFocusable(true);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
        pop.setAnimationStyle(R.style.main_menu_photo_anim);
        pop.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tv_album:
                        PictureSelector.create(Sendpage.this)
                                .openGallery(PictureMimeType.ofImage())
                                .maxSelectNum(maxSelectNum)
                                .minSelectNum(1)
                                .imageSpanCount(4)
                                .selectionMode(PictureConfig.MULTIPLE)
                                .forResult(PictureConfig.CHOOSE_REQUEST);
                        break;
                    case R.id.tv_camera:
                        //拍照
                        PictureSelector.create(Sendpage.this)
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<LocalMedia> images;
        if (resultCode == RESULT_OK) {
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
                adapter.setList(selectList);
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void initView() {
        SendRG = (RadioGroup) findViewById(R.id.SendRG);
        DeliveryRG = (RadioGroup) findViewById(R.id.delivery);
        cancel = (TextView) findViewById(R.id.myCancel);
        mRadioForward = (RadioButton) findViewById(R.id.ForwardRadioBtn);
        mRadioBackward = (RadioButton) findViewById(R.id.BackwardRadioBtn);
        send = (TextView) findViewById(R.id.send);
        title = (EditText) findViewById(R.id.et_title);
        description = (EditText) findViewById(R.id.description);
        lengthMin = (EditText) findViewById(R.id.lengthMin);
        lengthMax = (EditText) findViewById(R.id.lengthMax);
        widthMin = (EditText) findViewById(R.id.widthMin);
        widthMax = (EditText) findViewById(R.id.widthMax);
        heightMin = (EditText) findViewById(R.id.heightMin);
        heightMax = (EditText) findViewById(R.id.heightMax);
        weightMin = (EditText) findViewById(R.id.weightMin);
        weightMax = (EditText) findViewById(R.id.weightMax);
        price = (EditText) findViewById(R.id.price);
        express = (RadioButton) findViewById(R.id.express);
        faceToFace = (RadioButton) findViewById(R.id.face_to_face);
        both = (RadioButton) findViewById(R.id.both);
        addCate = (ImageView) findViewById(R.id.addCate);
        chosenCate = (TextView)findViewById(R.id.chosenCate);
    }

    private void setSendClipState() {
        setForwardState();
        setBackwardState();
    }

    private void setDeliveryClipState(){
        setExpressState();
        setFaceToFaceState();
        setBothState();
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

    private void setExpressState() {
        if (express.isChecked()) {
            express.setTextColor(getResources().getColor(R.color.white));
            express.setBackground(getDrawable(R.drawable.price_clicked));
        } else {
            express.setTextColor(getResources().getColor(R.color.black));
            express.setBackground(getDrawable(R.drawable.price));
        }
    }

    private void setFaceToFaceState() {
        if (faceToFace.isChecked()) {
            faceToFace.setTextColor(getResources().getColor(R.color.white));
            faceToFace.setBackground(getDrawable(R.drawable.price_clicked));
        } else {
            faceToFace.setTextColor(getResources().getColor(R.color.black));
            faceToFace.setBackground(getDrawable(R.drawable.price));
        }
    }

    private void setBothState() {
        if (both.isChecked()) {
            both.setTextColor(getResources().getColor(R.color.white));
            both.setBackground(getDrawable(R.drawable.price_clicked));
        } else {
            both.setTextColor(getResources().getColor(R.color.black));
            both.setBackground(getDrawable(R.drawable.price));
        }
    }

    public void onButtonShowPopupWindowClick(View view) {
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window, null);

        RecyclerView recyclerView = (RecyclerView) popupView.findViewById(R.id.cate_grid);
        //add the checkboxes into gridview
        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);
        CategoryAdapter mcategoryAdapter = new CategoryAdapter(this, categories);
        recyclerView.setAdapter(mcategoryAdapter);


        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        ImageView okBtn = (ImageView) popupView.findViewById(R.id.okBtn);
        ImageView cancelBtn = (ImageView) popupView.findViewById(R.id.cancelBtn);
        // dismiss the popup window when touched
        /*
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });

         */
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mcategoryAdapter.getSelectedText().equals("")) {
                    chosenCate.setText(mcategoryAdapter.getSelectedText());
                    selectedCatePos = mcategoryAdapter.getmSelectedItem();
                    addCate.animate().rotation(-45);
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