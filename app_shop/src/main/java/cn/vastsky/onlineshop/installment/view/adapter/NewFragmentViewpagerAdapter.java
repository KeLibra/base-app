package cn.vastsky.onlineshop.installment.view.adapter;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import cn.vastsky.onlineshop.installment.view.fragment.HomePagerProductListFragment;

/**
 * @author: kezy
 * @create_time 2019/11/20
 * @description:
 */
public class NewFragmentViewpagerAdapter extends FragmentStatePagerAdapter {

    private List<HomePagerProductListFragment> fragments;

    public NewFragmentViewpagerAdapter(FragmentManager fm, List<HomePagerProductListFragment> fragmentLists) {
        super(fm);
        this.fragments = fragmentLists;
    }


    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void updateList(List<HomePagerProductListFragment> fragmentLists) {
        if (fragments != null) {
            fragments.clear();
        }
        this.fragments.addAll(fragmentLists);
        notifyDataSetChanged();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).getmTitle();
    }

    /**
     * 使用这个方式，让页面不缓存，能够在清除fragment的时候对其做了删除
     *
     * @param object
     * @return
     */
    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }
}
