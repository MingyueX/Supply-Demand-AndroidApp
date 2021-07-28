package com.zh.testbottonnav;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.zh.testbottonnav.adapter.RSslipAdapter;
import com.zh.testbottonnav.net.Cars;
import com.zh.testbottonnav.ui.UiUtils;

import java.util.ArrayList;
import java.util.List;

public class RightSideslipLay extends RelativeLayout {
    private Context mCtx;
    private ListView selectList;
    private Button resetBrand;
    private Button okBrand;
    private ImageView backBrand;
    private RelativeLayout mRelateLay;
    private RSslipAdapter slidLayFrameAdapter;
    private String JsonStr = "{\"attr\": [{ \"isoPen\": true,\"single_check\": 0,\"key\": \"物品种类\", \"vals\": [ { \"val\": \"女装\"}, { \"val\": \"男装\"}, { \"val\": \"童装\"}, " +
            "{ \"val\": \"女鞋\"}, { \"val\": \"男鞋\"}, { \"val\": \"童鞋\"}, { \"val\": \"皮具\"}, { \"val\": \"箱包\"}, { \"val\": \"饰品\"}, " +
            "{ \"val\": \"钟表\"}, { \"val\": \"眼镜\"}, { \"val\": \"护肤品\"}, { \"val\": \"彩妆\"}, { \"val\": \"香水\"}, { \"val\": \"日用品\"}, " +
            "{ \"val\": \"运动用品\"}, { \"val\": \"户外装备\"}, { \"val\": \"清洁工具\"}, { \"val\": \"宠物用品\"}, { \"val\": \"手工艺品\"}, { \"val\": \"布艺软饰\"}, " +
            "{ \"val\": \"家纺\"}, { \"val\": \"厨具\"}, { \"val\": \"食品\"}, { \"val\": \"营养保健品\"}, { \"val\": \"母婴用品\"}, { \"val\": \"儿童玩具\"}, " +
            "{ \"val\": \"电器\"}, { \"val\": \"汽车配件\"}, { \"val\": \"文具\"}, { \"val\": \"图书\"}, { \"val\": \"数码产品\"}, { \"val\": \"影音设备\"}, " +
            "{ \"val\": \"礼物 \"}, { \"val\": \"奢侈品 \"}]},{\"single_check\": 0,\"key\": \"交货方式\", \"vals\": [{ \"val\": \"快递\"},{ \"val\": \"面交\"},{ \"val\": \"不限\"}]}," +
            "{\"single_check\": 0,\"key\": \"带货地\", \"vals\": [{ \"val\": \"海外\"}]}," +
            "{\"single_check\": 0,\"key\": \"物品数量\", \"vals\": [{\"val\": \"1个\"},{\"val\": \"2个\"},{\"val\": \"3个\"},{\"val\": \"4个\"},{\"val\": \"5个\"},{\"val\": \"5个以上\"},{\"val\": \"10个以上\"}]}]}";

    private Handler uiHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Log.d("RSslip", "getval");
                    menuCallBack.setupCloseMean();
            }
        }
    };

    public RightSideslipLay(Context context) {
        super(context);
        mCtx = context;
        inflateView();
    }

    private void inflateView() {
        View.inflate(getContext(), R.layout.include_right_sideslip_layout, this);
        selectList = (ListView) findViewById(R.id.selsectFrameLV);


        backBrand = (ImageView) findViewById(R.id.select_brand_back_im);
        resetBrand = (Button) findViewById(R.id.fram_reset_but);
        mRelateLay = (RelativeLayout) findViewById(R.id.select_frame_lay);
        okBrand = (Button) findViewById(R.id.fram_ok_but);
        resetBrand.setOnClickListener(mOnClickListener);
        okBrand.setOnClickListener(mOnClickListener);
        backBrand.setOnClickListener(mOnClickListener);
        mRelateLay.setOnClickListener(mOnClickListener);
        setUpList();
    }

    private List<AttrList.Attr.Vals> ValsData;


    private List<AttrList.Attr> setUpBrandList(List<AttrList.Attr> mAttrList) {
        /*
        if ("品牌".equals(mAttrList.get(0).getKey())) {
            ValsData = mAttrList.get(0).getVals();
            mAttrList.get(0).setVals(getValsDatas(mAttrList.get(0).getVals()));
        }

         */

        return mAttrList;
    }

    private AttrList attr;

    private void setUpList() {
        attr = new Gson().fromJson(JsonStr.toString(), AttrList.class);
        if (slidLayFrameAdapter == null) {
            slidLayFrameAdapter = new RSslipAdapter(mCtx, setUpBrandList(attr.getAttr()));
            selectList.setAdapter(slidLayFrameAdapter);
        } else {
            slidLayFrameAdapter.replaceAll(attr.getAttr());
        }
        slidLayFrameAdapter.setAttrCallBack(new RSslipAdapter.SelechDataCallBack() {
            @Override
            public void setupAttr(List<String> mSelectData, String key) {

            }
        });
        /*
        slidLayFrameAdapter.setMoreCallBack(new RSslipAdapter.SelechMoreCallBack() {

            @Override
            public void setupMore(List<AttrList.Attr.Vals> mSelectData) {
                getPopupWindow(mSelectData);
                mDownMenu.setOnMeanCallBack(meanCallBack);
            }
        });


         */

    }

    //在第二个页面改变后，返回时第一个界面随之改变，使用的接口回调
    /*
    private RightSideslipChildLay.onMeanCallBack meanCallBack = new RightSideslipChildLay.onMeanCallBack() {
        @Override
        public void isDisMess(boolean isDis, List<AttrList.Attr.Vals> mBrandData, String str) {
            if (mBrandData != null) {
                if (attr.getAttr().size() > 0) {
                    ((AttrList.Attr) attr.getAttr().get(0)).setVals(getValsDatas(mBrandData));
                    ((AttrList.Attr) attr.getAttr().get(0)).setShowStr(str);
                }
                slidLayFrameAdapter.replaceAll(attr.getAttr());
            }

            dismissMenuPop();
        }
    };

     */



    /*
    private List<AttrList.Attr.Vals> getValsDatas(List<AttrList.Attr.Vals> mBrandData) {
        List<AttrList.Attr.Vals> mVals = new ArrayList<AttrList.Attr.Vals>();
        if (mBrandData != null && mBrandData.size() > 0) {
            for (int i = 0; i < mBrandData.size(); i++) {
                if (mVals.size() >= 8) {
                    AttrList.Attr.Vals valsAdd = new AttrList.Attr.Vals();
                    valsAdd.setV("查看更多 >");
                    mVals.add(valsAdd);
                    continue;
                } else {
                    mVals.add(mBrandData.get(i));
                }
                mVals.add(mBrandData.get(i));
            }
            mVals = mVals.size() >= 9 ? mVals.subList(0, 9) : mVals;
            return mVals;

        }
        return null;
    }

     */


    private OnClickListenerWrapper mOnClickListener = new OnClickListenerWrapper() {
        @Override
        protected void onSingleClick(View v) {
            switch (v.getId()) {
                case R.id.fram_reset_but:
                    setUpList();
                    ScreeningActivity.mselected.clear();
                    break;
                case R.id.select_brand_back_im:
                case R.id.fram_ok_but:
                    List<List<String> > select = new ArrayList<List<String> >();
                    for (int i = 0; i < 4; i++) {
                        List<String> sdata = new ArrayList<String>();
                        List<AttrList.Attr.Vals> selectData = slidLayFrameAdapter.getItem(i).getSelectVals();
                        if (!selectData.isEmpty()) {
                            for (int j = 0; j < selectData.size(); j++) {
                                sdata.add(selectData.get(j).getV());
                            }
                        }
                        else {
                            sdata.add("不限");
                        }
                        select.add(sdata);
                    }
                    ScreeningActivity.mselected = select;
                    Message msg = new Message();
                    msg.what = 1;
                    uiHandler.sendMessage(msg);
                    // menuCallBack.setupCloseMean();
                    break;
            }
        }
    };

    /**
     * 关闭窗口
     */
    private void dismissMenuPop() {
        if (mMenuPop != null) {
            mMenuPop.dismiss();
            mMenuPop = null;
        }

    }

    /***
     * 获取PopupWindow实例
     */
    private void getPopupWindow(List<AttrList.Attr.Vals> mSelectData) {
        if (mMenuPop != null) {
            dismissMenuPop();
            return;
        } else {
            initPopuptWindow(mSelectData);
        }
    }

    /**
     * 创建PopupWindow
     */
    private PopupWindow mMenuPop;
    //public RightSideslipChildLay mDownMenu;

    protected void initPopuptWindow(List<AttrList.Attr.Vals> mSelectData) {
        //mDownMenu = new RightSideslipChildLay(getContext(), ValsData, mSelectData);
        if (mMenuPop == null) {
            mMenuPop = new PopupWindow(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
        }
        mMenuPop.setBackgroundDrawable(new BitmapDrawable());
        mMenuPop.setAnimationStyle(R.style.popupWindowAnimRight);
        mMenuPop.setFocusable(true);
        mMenuPop.showAtLocation(RightSideslipLay.this, Gravity.TOP, 100, UiUtils.getStatusBarHeight(mCtx));
        mMenuPop.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                dismissMenuPop();
            }
        });
    }


    private CloseMenuCallBack menuCallBack;

    public interface CloseMenuCallBack {
        void setupCloseMean();
    }

    public void setCloseMenuCallBack(CloseMenuCallBack menuCallBack) {
        this.menuCallBack = menuCallBack;
    }
}
