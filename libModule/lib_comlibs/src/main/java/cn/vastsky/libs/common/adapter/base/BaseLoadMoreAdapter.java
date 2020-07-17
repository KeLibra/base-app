package cn.vastsky.libs.common.adapter.base;

import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * Created by airal on 2018/3/20.
 */

public class BaseLoadMoreAdapter extends RecyclerView.Adapter{
    private OnLoadMoreListener onLoadMoreListener;

    private boolean canloadMore;
    private boolean isLoadMoreIng;
    private boolean hasHeader;
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setEnableLoadmore(boolean canLoadMore) {
        this.canloadMore = canLoadMore;
        isLoadMoreIng = false;
    }

    public void finishLoadmore() {
        isLoadMoreIng = false;
    }

    private int[] lastScrollPositions;

    private int caseStaggeredGrid(RecyclerView.LayoutManager layoutManager) {
        StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
        if (lastScrollPositions == null) {
            lastScrollPositions = new int[staggeredGridLayoutManager.getSpanCount()];
        }

        staggeredGridLayoutManager.findLastVisibleItemPositions(lastScrollPositions);
        return findMax(lastScrollPositions);
    }


    private int findMax(int[] lastPositions) {
        int max = Integer.MIN_VALUE;
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    public void initLoadMore(RecyclerView recyclerView, BaseLoadMoreAdapter.OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
        RecyclerView.OnScrollListener onScrollChangeListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    int lastVisiblePosition;
                    if (layoutManager instanceof GridLayoutManager) {
                        lastVisiblePosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                    } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                        int into[] = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                        ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(into);
                        lastVisiblePosition = findMax(into);
                    } else {
                        lastVisiblePosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    }
                    if (layoutManager.getChildCount() > 0             //当当前显示的item数量>0
                            && lastVisiblePosition >= layoutManager.getItemCount() - 1           //当当前屏幕最后一个加载项位置>=所有item的数量
                            && layoutManager.getItemCount() > layoutManager.getChildCount()) { // 当当前总Item数大于可见Item数
                        onLoadMore();
                    }
                    if(hasHeader){//有头部count减1
                        if (layoutManager.getChildCount() > 0             //当当前显示的item数量>0
                                && lastVisiblePosition >= layoutManager.getItemCount() - 1           //当当前屏幕最后一个加载项位置>=所有item的数量
                                && layoutManager.getItemCount() > layoutManager.getChildCount()) { // 当当前总Item数大于可见Item数
                            onLoadMore();
                        }
                    }
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        };
        recyclerView.addOnScrollListener(onScrollChangeListener);

    }

    private void onLoadMore() {
        if (canloadMore) {
            if (onLoadMoreListener != null) {
                if (!isLoadMoreIng) {
                    isLoadMoreIng = true;
                    onLoadMoreListener.onLoadMore();
                }
            }
        }
    }

    public void setHasHeader(boolean hasHeader){
        this.hasHeader=hasHeader;
    }
}
