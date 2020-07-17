package cn.vastsky.onlineshop.installment.view.activity;

import cn.vastsky.lib.base.common.base.CommonBaseActivity;
import cn.vastsky.lib.reten.ActivityConfig;
import cn.vastsky.onlineshop.installment.R;

@ActivityConfig(isHasTitleView = false)
public class MainActivity extends CommonBaseActivity {

    @Override
    protected int getContentLayout() {
        return R.layout.activity_main2;
    }
}