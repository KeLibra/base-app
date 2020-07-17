package test.kezy.com.lib_commom.adapter.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;


/**
 * @author dingzhongsheng
 */
public abstract class BaseViewHolder<Model> {
	private final SparseArray<View> views;

	protected View rootView;
	private int index;

	public Context getContext() {
		return mContext;
	}

	public View getRootView() {
		return rootView;
	}

	protected Context mContext;

	public BaseViewHolder(Context context) {
		mContext = context;
		views = new SparseArray<>();
	}

	protected void setRootView(ViewGroup parent) {
		if (rootView == null) {
			rootView = LayoutInflater.from(getContext()).inflate(getRootViewId(), parent, false);
			onCreateView();
		}
	}

	/**
	 * @return
	 */
	protected abstract int getRootViewId();

	protected <T extends View> T findViewById(int id) {
		View view = views.get(id);
		if (view != null) {
			return (T) view;
		}
		return (T) rootView.findViewById(id);
	}

	public void initview(ViewGroup parent) {
		setRootView(parent);
	}

	/**
	 * Set a view visibility to VISIBLE (true) or GONE (false).
	 *
	 * @param viewId  The view id.
	 * @param visible True for VISIBLE, false for GONE.
	 * @return The BaseViewHolder for chaining.
	 */
	public BaseViewHolder setGone(@IdRes int viewId, boolean visible) {
		View view = findViewById(viewId);
		view.setVisibility(visible ? View.VISIBLE : View.GONE);
		return this;
	}

	/**
	 * Set a view visibility to VISIBLE (true) or INVISIBLE (false).
	 *
	 * @param viewId  The view id.
	 * @param visible True for VISIBLE, false for INVISIBLE.
	 * @return The BaseViewHolder for chaining.
	 */
	public BaseViewHolder setVisible(@IdRes int viewId, boolean visible) {
		View view = findViewById(viewId);
		view.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
		return this;
	}

	public abstract void onCreateView();

	public abstract void setData(Model model, int flatPosition);

	public abstract BaseViewHolder<Model> getInstance();

	public void setIndex(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}
}
