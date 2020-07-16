package cn.vastsky.onlineshop.installment.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import cn.vastsky.lib.base.config.AppConfigLib;
import cn.vastsky.libs.common.utils.ImageLoadUtils;
import cn.vastsky.libs.common.utils.ViewUtils;
import cn.vastsky.onlineshop.installment.R;
import cn.vastsky.onlineshop.installment.model.bean.response.HomePageInfoResponse;
import cn.vastsky.onlineshop.installment.view.adapter.base.OnItemClickListener;

/**
 * @author: kezy
 * @create_time 2019/11/12
 * @description:
 */
public class HomepageProductListAdapter extends RecyclerView.Adapter<HomepageProductListAdapter.MyViewHolder> {

    private List<HomePageInfoResponse.SubjectBean.ProductBean> mData;
    private Context mContext;


    private OnItemClickListener onItemClick;   //定义点击事件接口

    //定义设置点击事件监听的方法
    public void setOnitemClickLintener(OnItemClickListener onitemClick) {
        this.onItemClick = onitemClick;
    }

    public HomepageProductListAdapter(Context context, List<HomePageInfoResponse.SubjectBean.ProductBean> list) {
        mData = list;
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_homepager_product_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        if ((position == mData.size() - 1)) {
            myViewHolder.viewBottomLine.setVisibility(View.VISIBLE);
            if (!AppConfigLib.isCkFlag()) {
                myViewHolder.emptyView.setVisibility(View.VISIBLE);
            } else {
                myViewHolder.emptyView.setVisibility(View.GONE);
            }

        } else {
            myViewHolder.viewBottomLine.setVisibility(View.GONE);
            myViewHolder.emptyView.setVisibility(View.GONE);
        }

        HomePageInfoResponse.SubjectBean.ProductBean productBean = mData.get(position);
        if (productBean != null) {

            if (productBean.stock == 0) { // 已售罄
                myViewHolder.tvSaleOut.setVisibility(View.VISIBLE);
                myViewHolder.tvProductPrice.setTextColor(Color.parseColor("#999999"));
            } else {
                myViewHolder.tvSaleOut.setVisibility(View.GONE);
                myViewHolder.tvProductPrice.setTextColor(Color.parseColor("#FF6F24"));
            }

            ImageLoadUtils.loadRoundImage(mContext, productBean.pic, ViewUtils.dip2px(mContext, 2), myViewHolder.ivProductIcon);

            myViewHolder.tvProductTitle.setText(productBean.name);

            if (productBean.keywords != null) {
                if (productBean.keywords.size() < 1 || TextUtils.isEmpty(productBean.keywords.get(0))) {
                    myViewHolder.tvProductMark1.setVisibility(View.GONE);
                } else {
                    myViewHolder.tvProductMark1.setVisibility(View.VISIBLE);
                    myViewHolder.tvProductMark1.setText(productBean.keywords.get(0));
                }
                if (productBean.keywords.size() < 2 || TextUtils.isEmpty(productBean.keywords.get(1))) {
                    myViewHolder.tvProductMark2.setVisibility(View.GONE);
                } else {
                    myViewHolder.tvProductMark2.setVisibility(View.VISIBLE);
                    myViewHolder.tvProductMark2.setText(productBean.keywords.get(1));
                }
                if (productBean.keywords.size() < 3 || TextUtils.isEmpty(productBean.keywords.get(2))) {
                    myViewHolder.tvProductMark3.setVisibility(View.GONE);
                } else {
                    myViewHolder.tvProductMark3.setVisibility(View.VISIBLE);
                    myViewHolder.tvProductMark3.setText(productBean.keywords.get(2));
                }
            } else {
                myViewHolder.tvProductMark1.setVisibility(View.GONE);
                myViewHolder.tvProductMark2.setVisibility(View.GONE);
                myViewHolder.tvProductMark3.setVisibility(View.GONE);
            }

            String textStr = "￥" + productBean.originalPrice;
            SpannableStringBuilder spannable = new SpannableStringBuilder(textStr);
            //改变第0个字体大小
            spannable.setSpan(new AbsoluteSizeSpan(ViewUtils.sp2px(mContext, 12)), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            //改变第1-第3个字体大小
            spannable.setSpan(new AbsoluteSizeSpan(ViewUtils.sp2px(mContext, 16)), 1, textStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            myViewHolder.tvProductPrice.setText(spannable); // 原价
//            myViewHolder.tvProductPrice.setText("￥" + productBean.originalPrice); // 原价


            if (TextUtils.isEmpty(productBean.salePrice)) {
                myViewHolder.rlProductPriceHint.setVisibility(View.GONE);
            } else {
                myViewHolder.rlProductPriceHint.setVisibility(View.VISIBLE);
                myViewHolder.tvProductBottomMoney.setText("￥" + productBean.salePrice);
            }

            List<HomePageInfoResponse.SubjectBean.ProductBean.ProductSloganBean> sloganBean = productBean.productSlogan;

            if (sloganBean == null || sloganBean.size() == 0) {
                myViewHolder.rlProductBottomLayout.setVisibility(View.GONE);
            } else {
                myViewHolder.rlProductBottomLayout.setVisibility(View.VISIBLE);

                if (sloganBean.size() >= 1) {
                    myViewHolder.ivProductJie.setVisibility(View.VISIBLE);
                    myViewHolder.tvProductJie.setVisibility(View.VISIBLE);
                    ImageLoadUtils.loadImage(mContext, sloganBean.get(0).icon, myViewHolder.ivProductJie);
                    myViewHolder.tvProductJie.setText(Html.fromHtml(sloganBean.get(0).text));
                } else {
                    myViewHolder.ivProductJie.setVisibility(View.GONE);
                    myViewHolder.tvProductJie.setVisibility(View.GONE);
                }

                if (sloganBean.size() >= 2) {
                    myViewHolder.ivProductMai.setVisibility(View.VISIBLE);
                    myViewHolder.tvProductMai.setVisibility(View.VISIBLE);
                    ImageLoadUtils.loadImage(mContext, sloganBean.get(1).icon, myViewHolder.ivProductMai);
                    myViewHolder.tvProductMai.setText(Html.fromHtml(sloganBean.get(1).text));
                } else {
                    myViewHolder.ivProductMai.setVisibility(View.GONE);
                    myViewHolder.tvProductMai.setVisibility(View.GONE);
                }
            }
        }

        myViewHolder.rlProductDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClick != null) {
                    onItemClick.onItemClicked(v, position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout emptyView;
        private View viewBottomLine;

        ImageView ivProductIcon;
        TextView tvSaleOut;
        TextView tvProductTitle;
        TextView tvProductMark1;
        TextView tvProductMark2;
        TextView tvProductMark3;
        TextView tvProductPrice;
        TextView tvProductBottomTag;
        TextView tvProductBottomMoney;
        RelativeLayout rlProductPriceHint;
        ImageView ivProductJie;
        TextView tvProductJie;
        ImageView ivProductMai;
        TextView tvProductMai;
        RelativeLayout rlProductBottomLayout;
        RelativeLayout rlProductDetail;


        public MyViewHolder(View itemview) {
            super(itemview);
            emptyView = itemview.findViewById(R.id.include_empty);
            viewBottomLine = itemview.findViewById(R.id.view_bottom_line);
            ivProductIcon = itemview.findViewById(R.id.iv_product_icon);
            tvSaleOut = itemview.findViewById(R.id.tv_sale_out);
            tvProductTitle = itemview.findViewById(R.id.tv_product_title);
            tvProductMark1 = itemview.findViewById(R.id.tv_product_mark_1);
            tvProductMark2 = itemview.findViewById(R.id.tv_product_mark_2);
            tvProductMark3 = itemview.findViewById(R.id.tv_product_mark_3);
            tvProductPrice = itemview.findViewById(R.id.tv_product_price);
            tvProductBottomTag = itemview.findViewById(R.id.tv_product_bottom_tag);
            tvProductBottomMoney = itemview.findViewById(R.id.tv_product_bottom_money);
            rlProductPriceHint = itemview.findViewById(R.id.rl_product_price_hint);
            ivProductJie = itemview.findViewById(R.id.iv_product_jie);
            tvProductJie = itemview.findViewById(R.id.tv_product_jie);

            ivProductMai = itemview.findViewById(R.id.iv_product_mai);
            tvProductMai = itemview.findViewById(R.id.tv_product_mai);
            rlProductBottomLayout = itemview.findViewById(R.id.rl_product_bottom_layout);
            rlProductDetail = itemview.findViewById(R.id.rl_product_detail);
            tvProductJie = itemview.findViewById(R.id.tv_product_jie);
        }
    }
}
