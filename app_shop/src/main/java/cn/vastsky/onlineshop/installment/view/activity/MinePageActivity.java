package cn.vastsky.onlineshop.installment.view.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.vastsky.libs.common.config.userinfo.LoginFacade;
import cn.vastsky.libs.common.view.dialog.CommonDialog;
import cn.vastsky.onlineshop.installment.R;
import cn.vastsky.onlineshop.installment.base.view.IBaseActivity;
import cn.vastsky.onlineshop.installment.contract.UserMineContract;
import cn.vastsky.onlineshop.installment.model.bean.response.UserMineResponse;
import cn.vastsky.onlineshop.installment.presenter.UserMinePresenter;
import cn.vastsky.onlineshop.installment.view.adapter.base.VCommonAdapter;
import cn.vastsky.onlineshop.installment.view.adapter.viewholder.UserCenterHeaderViewHolder;
import cn.vastsky.onlineshop.installment.view.adapter.viewholder.UserMineListViewHolder;

public class MinePageActivity
        extends IBaseActivity<UserMineContract.UserMineContractPresenter>
        implements UserMineContract.UserMineContractView {

    @BindView(R.id.rlv_mine_layout)
    RecyclerView rlvMineLayout;
    @BindView(R.id.tv_user_logout)
    TextView tvUserLogout;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_mine_page;
    }

    @Override
    protected UserMineContract.UserMineContractPresenter getPresenter() {
        return new UserMinePresenter(this);
    }

    @Override
    protected void initView() {
        initRecyclerView();
    }

    // head view
    List<UserMineResponse> userInfoList = new ArrayList<>();
    UserCenterHeaderViewHolder headerViewHolder = new UserCenterHeaderViewHolder(this);
    VCommonAdapter heanderAdapter = new VCommonAdapter<UserMineResponse>(userInfoList, headerViewHolder, 0);

    // item view
    List<UserMineResponse.UrlInfoBean> userMineList = new ArrayList<>();
    UserMineListViewHolder mineListViewHolder = new UserMineListViewHolder(this);
    VCommonAdapter userMineAdapter = new VCommonAdapter<UserMineResponse.UrlInfoBean>(userMineList, mineListViewHolder, 1);

    VirtualLayoutManager virtualLayoutManager;
    DelegateAdapter delegateAdapter;

    private void initRecyclerView() {
        virtualLayoutManager = new VirtualLayoutManager(this, VirtualLayoutManager.VERTICAL, false);
        delegateAdapter = new DelegateAdapter(virtualLayoutManager, true);

        delegateAdapter.addAdapter(heanderAdapter);
        delegateAdapter.addAdapter(userMineAdapter);

        rlvMineLayout.setLayoutManager(virtualLayoutManager);
        rlvMineLayout.setAdapter(delegateAdapter);
    }

    @Override
    protected void loadData() {
        if (LoginFacade.isLogin()) {
            presenter.getUserMinePageInfo();
        }
    }

    @Override
    public void showUserMinePageView(UserMineResponse response) {
        if (userInfoList != null) {
            userInfoList.clear();
        }
        userInfoList.add(response);
        heanderAdapter.notifyDataSetChanged();

        if (userMineList != null) {
            userMineList.clear();
        }
        userMineList.addAll(response.urlInfo);
        userMineAdapter.notifyDataSetChanged();

        tvUserLogout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.tv_user_logout)
    public void onClick() {
        CommonDialog dialog = new CommonDialog(MinePageActivity.this);
        dialog.setMessage("确定要退出登录吗")
                .setNegtive("取消")
                .setPositive("确定")
                .setOnClickBtnListener(new CommonDialog.OnClickBtnListener() {
                    @Override
                    public void onPositiveClick() {
                        LoginFacade.clearUserInfo();
                        Intent intent = new Intent(MinePageActivity.this, MainActivity.class);
                        intent.putExtra("tab_change", 0);
                        startActivity(intent);
                        LoginFacade.goLoginView(MinePageActivity.this);
                    }

                    @Override
                    public void onNegtiveClick() {

                    }
                }).show();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (LoginFacade.isLogin()) {
            loadData();
            tvUserLogout.setVisibility(View.GONE);
        }
    }

    @Override
    protected boolean hasTitle() {
        return false;
    }

    @Override
    protected void onBack() {
        finish();
    }

    @Override
    protected boolean hasErrorPage() {
        return true;
    }

    @Override
    protected void onErrorRefresh() {
        super.onErrorRefresh();
        loadData();
    }
}
