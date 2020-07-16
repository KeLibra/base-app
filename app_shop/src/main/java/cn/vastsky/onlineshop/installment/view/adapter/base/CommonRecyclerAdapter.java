package cn.vastsky.onlineshop.installment.view.adapter.base;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


/**
 * @date 2017/12/8
 */
public class CommonRecyclerAdapter<Model> extends BaseLoadMoreAdapter {
	private List<Model> dataList;
	public BaseViewHolder<Model> viewholder;
	private OnItemClickListener onItemClickListenr;
	private static final int TYPE_HEADER = 1;  //说明是带有Header的
	private static final int TYPE_FOOTER = 2;  //说明是带有Footer的
	private static final int TYPE_NORMAL = 0;  //说明是不带有header和footer的

	public CommonRecyclerAdapter(List<Model> datas, BaseViewHolder<Model> viewholder) {
		if (datas == null) {
			datas = new ArrayList<>();
		}
		this.dataList = datas;
		this.viewholder = viewholder;
	}

	public void setOnItemClickListenr(OnItemClickListener onItemClickListenr) {
		this.onItemClickListenr = onItemClickListenr;
	}

	@Override
	public int getItemCount() {
		if (mHeaderView == null && mFooterView == null) {
			return dataList.size();
		} else if (mHeaderView == null) {
			return dataList.size() + 1;
		} else if (mFooterView == null) {
			return dataList.size() + 1;
		} else {
			return dataList.size() + 2;
		}
	}

	public View getHeaderView() {
		return mHeaderView;
	}

	public void setHeaderView(View headerView) {
		mHeaderView = headerView;
		notifyItemInserted(0);
	}

	public View getFooterView() {
		return mFooterView;
	}

	public void setFooterView(View footerView) {
		mFooterView = footerView;
		notifyItemInserted(getItemCount() - 1);
	}

	private View mHeaderView;
	private View mFooterView;

	@Override
	public int getItemViewType(int position) {
		if (mHeaderView == null && mFooterView == null) {
			return TYPE_NORMAL;
		}
		if (position == 0 && mHeaderView!=null) {
			//第一个item应该加载Header
			return TYPE_HEADER;
		}
		if (position == getItemCount() - 1 && mFooterView!=null) {
			//最后一个,应该加载Footer
			return TYPE_FOOTER;
		}
		return TYPE_NORMAL;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		if (mHeaderView != null && viewType == TYPE_HEADER) {
			return new JustViewHolder(mHeaderView);
		}
		if (mFooterView != null && viewType == TYPE_FOOTER) {
			return new JustViewHolder(mFooterView);
		}
		BaseViewHolder v = viewholder.getInstance();
		v.initview(parent);
		return new ViewHolder(v);
	}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
		if (holder instanceof ViewHolder) {
			final int index;
			if (getHeaderView() != null) {
				index = position - 1;
			} else {
				index = position;
			}
			((ViewHolder) holder).getRootView().setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					if (onItemClickListenr != null) {
						onItemClickListenr.onItemClicked(((ViewHolder) holder).getRootView(), index);
					}
				}
			});
			((ViewHolder) holder).bindData(dataList.get(index), index);
		}
	}

	public static class ViewHolder<Model> extends RecyclerView.ViewHolder {
		BaseViewHolder<Model> viewholder;

		public ViewHolder(BaseViewHolder<Model> viewholder) {
			super(viewholder.getRootView());
			this.viewholder = viewholder;
		}

		public void bindData(Model model, int flatPosition) {
			viewholder.setData(model, flatPosition);

		}

		public View getRootView() {
			return itemView;
		}
	}

	private class JustViewHolder extends RecyclerView.ViewHolder {
		public JustViewHolder(View mFooterView) {
			super(mFooterView);
		}
	}
}
