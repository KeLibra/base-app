package cn.vastsky.onlineshop.installment.view.activity;

import cn.vastsky.libs.common.base.CommonBaseActivity;
import cn.vastsky.libs.common.reten.ActivityConfig;
import cn.vastsky.onlineshop.installment.R;

@ActivityConfig(isHasTitleView = false)
public class MainActivity extends CommonBaseActivity {

    @Override
    protected int getContentLayout() {
        return R.layout.activity_main2;
    }
}