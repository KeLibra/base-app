package cn.vastsky.onlineshop.installment.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.vastsky.libs.common.router.PageRouter;
import cn.vastsky.onlineshop.installment.R;
import cn.vastsky.onlineshop.installment.base.view.SimpleBaseFragment;
import cn.vastsky.onlineshop.installment.model.bean.response.HomePageInfoResponse;
import cn.vastsky.onlineshop.installment.view.adapter.HomepageProductListAdapter;
import cn.vastsky.onlineshop.installment.view.adapter.ListEmptyAdapter;
import cn.vastsky.onlineshop.installment.view.adapter.base.OnItemClickListener;

/**
 * @author: kezy
 * @create_time 2019/11/12
 * @description:
 */
public class HomePagerProductListFragment extends SimpleBaseFragment {

    private String mTitle;
    private RecyclerView recyclerView;
    private HomepageProductListAdapter recyclerAdapter;
    private RelativeLayout emptyView;

    private String categoryId;

    private String type;

    private ListEmptyAdapter emptyAdapter;

    private List<HomePageInfoResponse.SubjectBean.ProductBean> productList = new ArrayList<>();

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_homepager_product_list;
    }


    public String getmTitle() {
        return mTitle;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        emptyView = mRootLayout.findViewById(R.id.include_empty_view);
        initRecycleView();
    }

    private void initRecycleView() {
        recyclerView = mRootLayout.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerAdapter = new HomepageProductListAdapter(getActivity(), productList);

        emptyAdapter = new ListEmptyAdapter(recyclerAdapter, mActivity);
        recyclerView.setAdapter(emptyAdapter);

        recyclerAdapter.setOnitemClickLintener(new OnItemClickListener() {
            @Override
            public void onItemClicked(@NotNull View view, int index) {
                String jumpUrl = productList.get(index).productDetailUrl;
                PageRouter.resolve(mActivity, jumpUrl);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();

        if (TextUtils.isEmpty(categoryId)) {
            return;
        }
    }


    public void setProductListData(List<HomePageInfoResponse.SubjectBean.ProductBean> productBeanList) {

        if (emptyView != null && recyclerView != null) {
            emptyView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        if (productList != null || productList.size() > 0) {
            productList.clear();
        }
        productList.addAll(productBeanList);
        if (recyclerAdapter != null) {
            recyclerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showEmptyPage(int errorCode) {
        super.showEmptyPage(errorCode);
    }

    public void scrollTop() {
        if (recyclerAdapter != null) {
            recyclerView.smoothScrollToPosition(0);
        }
    }
}
