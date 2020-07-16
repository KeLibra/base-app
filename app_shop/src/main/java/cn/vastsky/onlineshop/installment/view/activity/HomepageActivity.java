package cn.vastsky.onlineshop.installment.view.activity;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import cn.vastsky.lib.base.config.AppConfigLib;
import cn.vastsky.lib.base.utils.LogUtils;
import cn.vastsky.libs.common.router.PageRouter;
import cn.vastsky.libs.common.utils.ImageLoadUtils;
import cn.vastsky.libs.common.utils.ViewUtils;
import cn.vastsky.onlineshop.installment.R;
import cn.vastsky.onlineshop.installment.base.view.IBaseActivity;
import cn.vastsky.onlineshop.installment.common.listener.AppBarStateChangeListener;
import cn.vastsky.onlineshop.installment.common.weight.CustomViewPager;
import cn.vastsky.onlineshop.installment.contract.HomepageContract;
import cn.vastsky.onlineshop.installment.model.bean.event.MessageEvent;
import cn.vastsky.onlineshop.installment.model.bean.response.HomePageInfoResponse;
import cn.vastsky.onlineshop.installment.presenter.HomepagePresenter;
import cn.vastsky.onlineshop.installment.view.adapter.NewFragmentViewpagerAdapter;
import cn.vastsky.onlineshop.installment.view.adapter.base.CommonRecyclerAdapter;
import cn.vastsky.onlineshop.installment.view.adapter.base.OnItemClickListener;
import cn.vastsky.onlineshop.installment.view.adapter.viewholder.HomepageHeaderViewHolder;
import cn.vastsky.onlineshop.installment.view.fragment.HomePagerProductListFragment;

public class HomepageActivity
        extends IBaseActivity<HomepageContract.HomepageContractPresenter>
        implements HomepageContract.HomepageContractView {


    @BindView(R.id.rl_head_layout)
    RelativeLayout rlHeadLayout;
    @BindView(R.id.tab_title)
    TabLayout tabTitle;
    @BindView(R.id.ll_head_title)
    LinearLayout llHeadTitle;
    @BindView(R.id.appbar_layout)
    AppBarLayout appbarLayout;
    @BindView(R.id.cvp_viewpager)
    CustomViewPager cvpViewpager;
    @BindView(R.id.coor_layout)
    CoordinatorLayout coorLayout;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.tv_homepager_title_big)
    TextView tvHomepagerTitleBig;
    @BindView(R.id.tv_homepager_title_small)
    TextView tvHomepagerTitleSmall;
    @BindView(R.id.rl_homepager_top_title_layout)
    RelativeLayout rlHomepagerTopTitleLayout;
    @BindView(R.id.recycle_header_item_view)
    RecyclerView recycleHeaderItemView;
    @BindView(R.id.rl_recycleview)
    RelativeLayout rlRecycleview;
    @BindView(R.id.iv_notice)
    ImageView ivNotice;
    @BindView(R.id.tv_notice_msg)
    TextView tvNoticeMsg;
    @BindView(R.id.rl_homepager_notice)
    RelativeLayout rlHomepagerNotice;
    @BindView(R.id.iv_loan_icon)
    ImageView ivLoanIcon;
    @BindView(R.id.tv_loan_title)
    TextView tvLoanTitle;
    @BindView(R.id.tv_loan_money_desc)
    TextView tvLoanMoneyDesc;
    @BindView(R.id.tv_loan_money)
    TextView tvLoanMoney;
    @BindView(R.id.tv_loan_detail)
    TextView tvLoanDetail;
    @BindView(R.id.rl_loan_layout)
    RelativeLayout rlLoanLayout;
    @BindView(R.id.iv_ad_banner_img)
    ImageView ivAdBannerImg;
    @BindView(R.id.iv_loan_ad_icon)
    ImageView ivLoanAdIcon;
    @BindView(R.id.rl_back_top)
    RelativeLayout rlBackTop;

    private boolean isViewPagerScolled = false; // view pager 是否 滑动过， 默认没有滑


    @Override
    protected int getContentLayout() {
        return R.layout.activity_homepage;
    }

    @Override
    protected HomepageContract.HomepageContractPresenter getPresenter() {
        return new HomepagePresenter(this);
    }

    @Override
    protected void initView() {
        initRefreshLayout();
        initTablayout();

        initViewPagerFragment();
        initListener();

        registerEventBus();
    }


    private void initListener() {
        rlHomepagerNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PageRouter.resolve(mActivity, noticeUrl);
            }
        });

        ivLoanAdIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PageRouter.resolve(mActivity, adImgUrl);
            }
        });

        ivAdBannerImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PageRouter.resolve(mActivity, bannerUrl);
//                PageRouter.resolve(mActivity, "http://10.102.110.81:8081/debug/main");
            }
        });

        rlBackTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollToTop();
            }
        });
    }


    public void scrollToTop() {

        try {
            if (fragmentLists.get(viewpagerCurrentPostion) != null) {
                fragmentLists.get(viewpagerCurrentPostion).scrollTop();
            }

            CoordinatorLayout.Behavior behavior =
                    ((CoordinatorLayout.LayoutParams) appbarLayout.getLayoutParams()).getBehavior();
            if (behavior instanceof AppBarLayout.Behavior) {
                AppBarLayout.Behavior appBarLayoutBehavior = (AppBarLayout.Behavior) behavior;
                int topAndBottomOffset = appBarLayoutBehavior.getTopAndBottomOffset();
                if (topAndBottomOffset != 0) {
                    appBarLayoutBehavior.setTopAndBottomOffset(0);
                }
            }

            rlBackTop.setVisibility(View.GONE);
        } catch (Exception e) {
            LogUtils.e("---------------- ---- err: " + e.toString());
        }
    }


    private CommonRecyclerAdapter headRecycleAdapter;
    private List<HomePageInfoResponse.PositionBean.AdIconBean> headItemList = new ArrayList();

    private void initHeaderRecycleview() {
        recycleHeaderItemView.setHasFixedSize(true);
        GridLayoutManager manager = new GridLayoutManager(getContext(), headItemList.size());
        recycleHeaderItemView.setLayoutManager(manager);

        headRecycleAdapter = new CommonRecyclerAdapter<HomePageInfoResponse.PositionBean.AdIconBean>(headItemList, new HomepageHeaderViewHolder(getContext()));
        recycleHeaderItemView.setAdapter(headRecycleAdapter);
    }


    private void initTablayout() {

        appbarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {

                if (state == State.EXPANDED) {  //展开状态
                    refreshLayout.setEnabled(true);
                    rlBackTop.setVisibility(View.GONE);

                } else if (state == State.COLLAPSED) {  //折叠状态
                    refreshLayout.setEnabled(false);

                    rlBackTop.setVisibility(View.VISIBLE);

                } else { //中间状态
                    refreshLayout.setEnabled(false);
                    rlBackTop.setVisibility(View.GONE);
                }
            }
        });

    }


    private List<HomePagerProductListFragment> fragmentLists = new ArrayList<>();
    private NewFragmentViewpagerAdapter fragmentAdapter;

    private void initViewPagerFragment() {

        fragmentAdapter = new NewFragmentViewpagerAdapter(getSupportFragmentManager(), fragmentLists);

        cvpViewpager.setAdapter(fragmentAdapter);

        cvpViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                isViewPagerScolled = true;
                viewpagerCurrentPostion = position;
                LogUtils.d("-------------------fragment onPageSelected 展示 :" + fragmentLists.get(position).getmTitle());
                refreshProductListData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        cvpViewpager.setOffscreenPageLimit(fragmentLists.size());

        try {
            /**
             * 设置TabLayout的选中监听
             */
            tabTitle.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    TextView textView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.tab_layout_text, null);
                    textView.setTextAppearance(HomepageActivity.this, R.style.TabLayoutTextSelected);
                    textView.setText(tab.getText());
                    tab.setCustomView(textView);
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                    tab.setCustomView(null);
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                }
            });

        } catch (Exception e) {
            LogUtils.e("--- err: " + e.toString());
        }

        tabTitle.setupWithViewPager(cvpViewpager);
    }


    private void initRefreshLayout() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }


    @Override
    protected void loadData() {
        presenter.loadHomepageData();
    }

    private String noticeUrl = ""; // notice 跳转链接
    private String adImgUrl = ""; // 广告主图 跳转链接
    private String bannerUrl = ""; // banner 图 跳转链接


    private void loadAllData(HomePageInfoResponse homePageInfoBean) {
        if (AppConfigLib.isCkFlag()) {
            tvHomepagerTitleSmall.setVisibility(View.GONE);
        } else {
            tvHomepagerTitleSmall.setVisibility(View.VISIBLE);
        }

//        try {

        if (homePageInfoBean.position != null && homePageInfoBean.position.adIcon != null) {
            loadTopItemData(homePageInfoBean.position.adIcon);
        } else {
            loadTopItemData(null);
        }

        if (homePageInfoBean.position != null && homePageInfoBean.position.adBroadcast != null) {
            loadTopNoticeData(homePageInfoBean.position.adBroadcast.get(0));
        } else {
            loadTopNoticeData(null);
        }

        if (homePageInfoBean.position != null && homePageInfoBean.position.adMain != null) {
            loadAdMainImgData(homePageInfoBean.position.adMain.get(0));
        } else {
            loadAdMainImgData(null);
        }

        if (homePageInfoBean.position.adSub1 != null) {
            loadAdBannerImgData(homePageInfoBean.position.adSub1.get(0));
        } else {
            loadAdBannerImgData(null);
        }

        if (homePageInfoBean.category != null && homePageInfoBean.subject.product != null) {
            loadProductListData(homePageInfoBean.category, homePageInfoBean.subject.product);
        } else {
            loadProductListData(null, null);
        }

//        } catch (Exception e) {
//            LogUtils.e("-----msg err" + e.toString());
//        }

    }

    /**
     * banner 广告位图片
     *
     * @param adSub1Bean
     */
    private void loadAdBannerImgData(HomePageInfoResponse.PositionBean.AdSub1Bean adSub1Bean) {
        if (adSub1Bean != null) {
            if (TextUtils.isEmpty(adSub1Bean.adImage)) {
                ivAdBannerImg.setVisibility(View.GONE);
            } else {
                ivAdBannerImg.setVisibility(View.VISIBLE);
                ImageLoadUtils.loadRoundImage(this, adSub1Bean.adImage, ViewUtils.dip2px(this, 6), ivAdBannerImg);
            }

            bannerUrl = adSub1Bean.adUrl;
        } else {
            ivAdBannerImg.setVisibility(View.GONE);
        }
    }

    /**
     * 主广告位图片
     *
     * @param adMainBean
     */
    private void loadAdMainImgData(HomePageInfoResponse.PositionBean.AdMainBean adMainBean) {

        if (adMainBean != null) {

            if (TextUtils.isEmpty(adMainBean.adImage)) {
                ivLoanAdIcon.setVisibility(View.GONE);
            } else {
                ivLoanAdIcon.setVisibility(View.VISIBLE);
                ImageLoadUtils.loadRoundImage(this, adMainBean.adImage, ViewUtils.dip2px(this, 6), ivLoanAdIcon);
            }
            adImgUrl = adMainBean.adUrl;
        } else {
            ivLoanAdIcon.setVisibility(View.GONE);
        }
    }

    /**
     * 通知 广告位 文字
     *
     * @param adBroadcastBean
     */
    private void loadTopNoticeData(HomePageInfoResponse.PositionBean.AdBroadcastBean adBroadcastBean) {

        if (adBroadcastBean != null) {
            rlHomepagerNotice.setVisibility(View.VISIBLE);
            if (TextUtils.isEmpty(adBroadcastBean.adText)) {
                rlHomepagerNotice.setVisibility(View.GONE);
            } else {
                tvNoticeMsg.setText(adBroadcastBean.adText);
                rlHomepagerNotice.setVisibility(View.VISIBLE);
            }

            noticeUrl = adBroadcastBean.adUrl;
        } else {
            rlHomepagerNotice.setVisibility(View.GONE);
        }
    }

    /**
     * 第一排广告icon
     *
     * @param adIcon
     */
    private void loadTopItemData(List<HomePageInfoResponse.PositionBean.AdIconBean> adIcon) {

        if (adIcon == null) {
            recycleHeaderItemView.setVisibility(View.GONE);
            return;
        } else {
            recycleHeaderItemView.setVisibility(View.VISIBLE);

            if (headItemList != null) {
                headItemList.clear();
            }
            if (adIcon.size() > 5) {
                headItemList.addAll(adIcon.subList(0, 5));
            }
            headItemList.addAll(adIcon);
            initHeaderRecycleview();
            headRecycleAdapter.notifyDataSetChanged();

            headRecycleAdapter.setOnItemClickListenr(new OnItemClickListener() {
                @Override
                public void onItemClicked(View rootView, int position) {
                    String jumpUrl = headItemList.get(position).adUrl;
                    PageRouter.resolve(mActivity, jumpUrl);
                }
            });
        }
    }


    private int viewpagerCurrentPostion = 0; //默认第一页

    /**
     * 下面产品信息
     *
     * @param category
     * @param subjectList
     */
    private void loadProductListData(List<HomePageInfoResponse.CategoryBean> category, List<HomePageInfoResponse.SubjectBean.ProductBean> subjectList) {

        if (fragmentLists != null && fragmentLists.size() > 0) {
            fragmentLists.clear();
        }

        if (category != null) {
            for (int i = 0; i < category.size(); i++) {
                HomePagerProductListFragment fragment = new HomePagerProductListFragment();
                fragment.setmTitle(category.get(i).name);
                fragment.setCategoryId(category.get(i).id);
                fragment.setType(category.get(i).type);
                fragmentLists.add(fragment);
            }

            if (fragmentLists != null && fragmentLists.size() > 0 && viewpagerCurrentPostion == 0) {
                fragmentLists.get(0).setProductListData(subjectList);
            }

            fragmentAdapter.notifyDataSetChanged();

            if (isViewPagerScolled && viewpagerCurrentPostion != 0) {
                refreshProductListData();
            }
        }
    }

    /**
     * 刷新 当前 商品列表
     */
    private void refreshProductListData() {
        if (fragmentLists != null
                && fragmentLists.get(viewpagerCurrentPostion) != null
                && !TextUtils.isEmpty(fragmentLists.get(viewpagerCurrentPostion).getCategoryId())) {
            presenter.loadProductListData(fragmentLists.get(viewpagerCurrentPostion).getCategoryId(),
                    fragmentLists.get(viewpagerCurrentPostion).getType(), viewpagerCurrentPostion);
        }
    }

    @Override
    protected boolean hasTitle() {
        return false;
    }

    @Override
    public void showHomepageView(HomePageInfoResponse homePageBean) {
        loadAllData(homePageBean);
    }

    @Override
    public void showProductListView(List<HomePageInfoResponse.SubjectBean.ProductBean> productBeanList, int currentPosition) {
        LogUtils.d("---------------msg: " + productBeanList.size() + " , position: " + currentPosition);
        if (fragmentLists != null && fragmentLists.size() > currentPosition) {
            fragmentLists.get(currentPosition).setProductListData(productBeanList);
        }
    }

    @Override
    public void showProductEmptyView(int currentPosition) {
        if (fragmentLists != null && fragmentLists.size() > currentPosition) {
            fragmentLists.get(currentPosition).showEmptyPage(2019);
        }
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        if (refreshLayout != null) {
            refreshLayout.setRefreshing(false);
            reSetAppbarLayout();
        }
    }

    /**
     * 解决 refresh loading finish 后 appbarlayout 偶现 不能滑动问题
     */
    private void reSetAppbarLayout() {
        appbarLayout.post(new Runnable() {
            @Override
            public void run() {
                CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) appbarLayout.getLayoutParams();
                AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) layoutParams.getBehavior();

                behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
                    @Override
                    public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                        return true;
                    }
                });
            }
        });
    }

    @Override
    protected void onBack() {
        finish();
    }

    @Override
    protected boolean hasErrorPage() {
        return true;
    }

    /**
     * 响应 eventbus 消息， 在主线程处理
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void responseEventBusMainThread(MessageEvent event) {
        if (MessageEvent.TYPE_LOGIN_SUCC.equals(event.eventType)) {
            LogUtils.d("---------------------- 登陆成功了 ------------- 首页刷新了");
            loadData();
        }
    }
}
