package cn.vastsky.onlineshop.installment.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;

import androidx.recyclerview.widget.RecyclerView;
import cn.vastsky.lib.base.config.AppConfigLib;
import cn.vastsky.onlineshop.installment.R;

/**
 * @author: kezy
 * @create_time 2019/11/14
 * @description:
 */
public class ListEmptyAdapter extends DelegateAdapter.Adapter<RecyclerView.ViewHolder> {
    private RecyclerView.Adapter mAdapter; //需要装饰的Adapter
    private Context mContext;
    private final int EMPTY_VIEW = 0;
    private final int NOT_EMPTY_VIEW = 1;

    public ListEmptyAdapter(RecyclerView.Adapter adapter, Context context) {
        mAdapter = adapter;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //展示空视图或者调用传入adapter方法
        if (viewType == EMPTY_VIEW) {
            return new EmptyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.adapter_no_data_layout, parent, false));
        }
        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof EmptyViewHolder) {
            if (AppConfigLib.isCkFlag()) {
                ((EmptyViewHolder) holder).emptyView.setVisibility(View.GONE);
            } else {
                ((EmptyViewHolder) holder).emptyView.setVisibility(View.VISIBLE);
            }
            return;
        }
        mAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        //获取传入adapter的条目数，没有则返回 1
        if (mAdapter != null) {
            if (mAdapter.getItemCount() > 0) {
                return mAdapter.getItemCount();
            }
        }
        //位空视图保留一个条目
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        //根据传入adapter来判断是否有数据
        if (mAdapter != null) {
            if (mAdapter.getItemCount() > 0) {
                return NOT_EMPTY_VIEW;
            }
        }
        return EMPTY_VIEW;
    }

    public void notifyAdapter() {
        //有些时候数据的变化不仅要刷新当前adapter还要刷新传入的原始adapter
//        if(mAdapter!=null){
//            mAdapter.notifyDataSetChanged();
//        }
        notifyDataSetChanged();
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new LinearLayoutHelper();
    }

    class EmptyViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout emptyView;

        public EmptyViewHolder(View itemView) {
            super(itemView);
            emptyView = itemView.findViewById(R.id.include_empty);
        }
    }
}
