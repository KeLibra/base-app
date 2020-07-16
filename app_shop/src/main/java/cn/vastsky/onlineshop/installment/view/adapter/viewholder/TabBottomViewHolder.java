//package cn.vastsky.onlineshop.view.adapter.viewholder;
//
//import android.content.Context;
//import android.widget.CheckedTextView;
//import android.widget.ImageView;
//
//import cn.vastsky.onlineshop.R;
//import cn.vastsky.onlineshop.model.BottomItemBean;
//import BaseViewHolder;
//
///**
// * @author: kezy
// * @create_time 2019/11/11
// * @description:
// */
//public class TabBottomViewHolder extends BaseViewHolder<BottomItemBean> {
//
//    Context context;
//    ImageView imgIcon;
//    CheckedTextView tvTab;
//
//
//    public TabBottomViewHolder(Context context) {
//        super(context);
//        this.context = context;
//    }
//
//    @Override
//    protected int getRootViewId() {
//        return R.layout.loan_item_tab;
//    }
//
//    @Override
//    public void onCreateView() {
//        tvTab = findViewById(R.id.tv_tab);
//        imgIcon = findViewById(R.id.img_icon);
//    }
//
//    @Override
//    public void setData(BottomItemBean bottomItemBean, int flatPosition) {
//        imgIcon.setImageResource(bottomItemBean.getUnSelectResource());
//        tvTab.setText(bottomItemBean.getItemName());
//    }
//
//    @Override
//    public BaseViewHolder<BottomItemBean> getInstance() {
//        return new TabBottomViewHolder(context);
//    }
//}
