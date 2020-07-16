package cn.vastsky.onlineshop.installment.view.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.vastsky.lib.base.config.AppConfigLib;
import cn.vastsky.lib.base.store.Store;
import cn.vastsky.lib.base.utils.LogUtils;
import cn.vastsky.libs.common.base.CommonBaseActivity;
import cn.vastsky.libs.common.config.userinfo.LoginFacade;
import cn.vastsky.libs.common.config.userinfo.LoginListener;
import cn.vastsky.libs.gdlocation.config.LocationConfig;
import cn.vastsky.onlineshop.installment.R;
import cn.vastsky.onlineshop.installment.common.VsConstants;
import cn.vastsky.onlineshop.installment.common.delegate.ActivityGroupDelegate;
import cn.vastsky.onlineshop.installment.common.net.ICallBack;
import cn.vastsky.onlineshop.installment.common.utils.download.AppUpLoadUtils;
import cn.vastsky.onlineshop.installment.common.utils.download.DownloadService;
import cn.vastsky.onlineshop.installment.common.utils.download.UpdataBroadcastReceiver;
import cn.vastsky.onlineshop.installment.model.BottomItemBean;
import cn.vastsky.onlineshop.installment.model.bean.response.InitConfigResponse;
import cn.vastsky.onlineshop.installment.router.ActivityDelegateUtils;
import cn.vastsky.onlineshop.installment.service.HomePageModel;
import cn.vastsky.onlineshop.installment.view.adapter.base.BaseViewHolder;
import cn.vastsky.onlineshop.installment.view.adapter.base.CommonRecyclerAdapter;
import cn.vastsky.onlineshop.installment.view.adapter.base.OnItemClickListener;


public class MainActivity extends CommonBaseActivity implements LoginListener {


    private ActivityGroupDelegate groupDelegate;
    private Unbinder unbinder;

    @BindView(R.id.fl_content_layout)
    FrameLayout flContentLayout;
    @BindView(R.id.rlv_bottom_layout)
    RecyclerView rlvBottomLayout;

    private boolean isGoLogin = false; // 是否去跳登陆

    @Override
    protected int getContentLayout() {

        return R.layout.activity_main;
    }

    @Override
    protected void initContentView() {
        super.initContentView();

        LoginFacade.registerLoginListener(this);

        unbinder = ButterKnife.bind(this);
        groupDelegate = new ActivityGroupDelegate(this);
        initBottomLayoutView();

        registBroadCastManager();
    }

    @Override
    protected void initData() {
        super.initData();

        requestPermission();

        initActivityMap();
        loadData();

        AppUpLoadUtils.appUpLoad(this, false);
    }

    //动态申请权限
    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    private UpdataBroadcastReceiver receiver = new UpdataBroadcastReceiver();

    //注册广播
    private void registBroadCastManager() {
        IntentFilter intentFilter = new IntentFilter(DownloadService.BROADCAST_ACTION);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, intentFilter);
    }

    protected void loadData() {
        LogUtils.d("====msg_location:  " + LocationConfig.getLocationAddress());
        bottomList.add(new BottomItemBean(R.drawable.tabbar_icon_home_f, R.drawable.tabbar_icon_home_nomal, "首页"));
        bottomList.add(new BottomItemBean(R.drawable.tabbar_icon_order_f, R.drawable.tabbar_icon_order_nomal, "订单"));
        bottomList.add(new BottomItemBean(R.drawable.tabbar_icon_mine_f, R.drawable.tabbar_icon_mine_normal, "我的"));
        bottomAdapter.notifyDataSetChanged();

        if (currentFocus == -1) {
            currentFocus = 0;
            selectBottomTab(currentFocus);
        }
        initConfigData();
    }


    private void initConfigData() {

        HomePageModel.getInitConfig(new ICallBack<InitConfigResponse>() {
            @Override
            public void onSuccess(InitConfigResponse response) {
                if (response != null) {
                    if (response.bottomTab != null) {
                        Store.sp(AppConfigLib.getContext()).putString(VsConstants.Key.KEY_ORDER_LIST, response.bottomTab.orderUrl);
                    }
                    if (response.docUrl != null) {
                        if (!TextUtils.isEmpty(response.docUrl.registerDocUrl)) {
                            Store.sp(AppConfigLib.getContext()).putString(VsConstants.Key.KEY_REGISTER_DOC_URL, response.docUrl.registerDocUrl);
                        }
                        if (!TextUtils.isEmpty(response.docUrl.privacyPolicyDocUrl)) {
                            Store.sp(AppConfigLib.getContext()).putString(VsConstants.Key.KEY_PRIVACY_DOC_URL, response.docUrl.privacyPolicyDocUrl);
                        }
                    }
                    AppConfigLib.setCkFlag(response.ckFlag);
                }
            }

            @Override
            public void onFailure(String err_msg) {
                LogUtils.e("------------- err : " + err_msg);
            }
        });
    }


    //
    private List<BottomItemBean> bottomList = new ArrayList();
    private CommonRecyclerAdapter bottomAdapter = new CommonRecyclerAdapter<BottomItemBean>(bottomList, new TabBottomViewHolder(MainActivity.this));

    private void initBottomLayoutView() {
        rlvBottomLayout.setLayoutManager(new GridLayoutManager(this, 3));
        rlvBottomLayout.setAdapter(bottomAdapter);

        bottomAdapter.setOnItemClickListenr(new OnItemClickListener() {
            @Override
            public void onItemClicked(View rootView, int position) {

                if (currentFocus != position) {
                    selectBottomTab(position);
                }
            }
        });
    }

    private void selectBottomTab(int position) {

        initConfigData(); // 每次点击下边底部item 都刷新

        ACTIVITY_TYPE type;
        String url = "";
        String params = "";

        if (position == 1 || position == 2) {
            if (!LoginFacade.isLogin()) {
                isGoLogin = true;
                unLoginSelectFocus = position;
                LogUtils.d("----------- err:" + " 用户未登陆 , 点击位置： " + position);
                LoginFacade.goLoginView(mActivity);
                return;
            }
        }

        switch (position) {
            case 0:
                type = ACTIVITY_TYPE.ACTIVITY_TYPE_PAGE_HOME;
                break;
            case 1:
                url = Store.sp(AppConfigLib.getContext()).getString(VsConstants.Key.KEY_ORDER_LIST, "");
                params = "NoTitle";
//                url = "http://10.102.110.81:8080/debug/main";
                type = ACTIVITY_TYPE.ACTIVITY_TYPE_PAGE_ORDER;
                break;
            case 2:
                type = ACTIVITY_TYPE.ACTIVITY_TYPE_PAGE_MINE;
                break;
            case 3:
                type = ACTIVITY_TYPE.ACTIVITY_TYPE_PAGE_WEB;
                break;
            default:
                type = ACTIVITY_TYPE.ACTIVITY_TYPE_PAGE_HOME;
                break;
        }
        if (type == ACTIVITY_TYPE.ACTIVITY_TYPE_PAGE_ORDER && TextUtils.isEmpty(url)) {
            return;
        }
        currentFocus = position;
        bottomAdapter.notifyDataSetChanged();
        switchToActivity(flContentLayout, type, url, params);
    }


    private Map<ACTIVITY_TYPE, String> activityMap;


    public void switchToActivity(ViewGroup fl_layout, ACTIVITY_TYPE activityType, String url, String parameters) {
        Intent intent = new Intent();
        String activityName = activityMap.get(activityType);

        if (TextUtils.isEmpty(activityName)) {
            LogUtils.e("====msg switch activity name = " + activityName);
            return;
        }
        intent.setClassName(mActivity.getBaseContext(), activityName);
        intent.putExtra("params", parameters);
        intent.putExtra("h5_url", url);
        groupDelegate.startChildActivity(fl_layout, activityName + activityType, intent);
    }


    private void initActivityMap() {
        activityMap = new HashMap<>();
        activityMap.put(ACTIVITY_TYPE.ACTIVITY_TYPE_PAGE_HOME, VsConstants.DelegatePage.CLASS_PAGE_HOME);
        activityMap.put(ACTIVITY_TYPE.ACTIVITY_TYPE_PAGE_ORDER, VsConstants.DelegatePage.CLASS_PAGE_ORDER);
        activityMap.put(ACTIVITY_TYPE.ACTIVITY_TYPE_PAGE_MINE, VsConstants.DelegatePage.CLASS_PAGE_MINE);
        activityMap.put(ACTIVITY_TYPE.ACTIVITY_TYPE_PAGE_WEB, VsConstants.DelegatePage.CLASS_PAGE_WEB);
    }

    boolean isLoginSucc = false;

    @Override
    public void loginSucceed(String userId, String token, Object extend) {
        isLoginSucc = true;
    }

    @Override
    public void loginFailed(String userId, Throwable e) {

    }

    @Override
    public void logoutSucceed(String userId) {
        isLoginSucc = true;
    }

    @Override
    public void logoutFailed(String usedId, Throwable e) {

    }

    // 跳转类型
    public enum ACTIVITY_TYPE {
        ACTIVITY_TYPE_PAGE_HOME, // 首页
        ACTIVITY_TYPE_PAGE_ORDER, // 订单页
        ACTIVITY_TYPE_PAGE_MINE, // 我的页面
        ACTIVITY_TYPE_PAGE_WEB, // web页面
    }


    @Override
    protected boolean hasTitle() {
        return false;
    }

    private int currentFocus = -1; // 当前的点击位置

    private int unLoginSelectFocus = -1;

    public class TabBottomViewHolder extends BaseViewHolder<BottomItemBean> {

        Context context;
        ImageView imgIcon;
        CheckedTextView tvTab;


        public TabBottomViewHolder(Context context) {
            super(context);
            this.context = context;
        }

        @Override
        protected int getRootViewId() {
            return R.layout.adapter_main_bottom_tab;
        }

        @Override
        public void onCreateView() {
            tvTab = findViewById(R.id.tv_tab);
            imgIcon = findViewById(R.id.img_icon);
        }

        @Override
        public void setData(BottomItemBean bottomItemBean, int flatPosition) {
            if (flatPosition == currentFocus) {
                imgIcon.setImageResource(bottomItemBean.getSelectedResource());
                tvTab.setTextColor(Color.parseColor("#2C84FF"));
            } else {
                tvTab.setTextColor(Color.parseColor("#222222"));
                imgIcon.setImageResource(bottomItemBean.getUnSelectResource());
            }
            tvTab.setText(bottomItemBean.getItemName());
        }

        @Override
        public BaseViewHolder<BottomItemBean> getInstance() {
            return new TabBottomViewHolder(context);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Activity activity = ActivityDelegateUtils.getCurrentActivity();
        if (activity != null && activity instanceof CommonBaseActivity) {
            ((CommonBaseActivity) activity).handleActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        currentFocus = intent.getIntExtra("tab_change", 0);
        selectBottomTab(currentFocus);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.d("================== isGoLogin ： " + isGoLogin + "  currentFous: " + currentFocus);
        if (isGoLogin && unLoginSelectFocus != -1) {
            if (LoginFacade.isLogin()) {
                isGoLogin = false;
                selectBottomTab(unLoginSelectFocus);
                unLoginSelectFocus = -1;
            } else {
                unLoginSelectFocus = -1;
            }
        }
    }

    //    private long exitTime = 0;
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if ((System.currentTimeMillis() - exitTime) > 2000) {
//                Toast.makeText(getApplicationContext(), "再按一下返回到桌面",
//                        Toast.LENGTH_SHORT).show();
//                exitTime = System.currentTimeMillis();
//                return true;
//            } else {
//                finish();
//            }
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
        LoginFacade.unRegisterLoginListener(this);

        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    @Override
    protected void onBack() {
        finish();
    }
}
