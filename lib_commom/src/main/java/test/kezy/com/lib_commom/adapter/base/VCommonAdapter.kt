package test.kezy.com.lib_commom.adapter.base

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.LinearLayoutHelper

/**
 * @date 2018/7/9.
 */
class VCommonAdapter<Model>() : DelegateAdapter.Adapter<VCommonAdapter.ViewHolder<Model>>() {
    lateinit var viewholder: BaseViewHolder<Model>
    lateinit var dataList: List<Model>
    var type: Int = 0

    constructor(datas: List<Model>, holder: BaseViewHolder<Model>, type: Int) : this() {
        viewholder = holder
        dataList = datas
        this.type = type
    }

    constructor(datas: List<Model>, holder: BaseViewHolder<Model>, type: Int, layoutHelper: LayoutHelper) : this() {
        viewholder = holder
        dataList = datas
        this.type = type
        this.layoutHelper = layoutHelper
    }

    override fun getItemViewType(position: Int): Int {
        return type
    }

    var onItemClickListenr: OnItemClickListener? = null

    override fun onBindViewHolder(holder: ViewHolder<Model>, position: Int) {

        holder.rootView.setOnClickListener(View.OnClickListener {
            if (onItemClickListenr != null) {
                onItemClickListenr?.onItemClicked((holder as ViewHolder<*>).rootView, position)
            }
        })
        holder.bindData(dataList[position], position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<Model> {
        val v = viewholder.getInstance()
        v.initview(parent)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    var layoutHelper: LayoutHelper = LinearLayoutHelper()

    override fun onCreateLayoutHelper(): LayoutHelper {
        return layoutHelper
    }

    class ViewHolder<Model>(internal var viewholder: BaseViewHolder<Model>) : RecyclerView.ViewHolder(viewholder.rootView) {

        val rootView: View
            get() = itemView

        fun bindData(model: Model, flatPosition: Int) {
            viewholder.setData(model, flatPosition)

        }
    }


}
