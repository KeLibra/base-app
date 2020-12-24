package cn.vastsky.lib.base.common.view.weight.img;

import android.graphics.Bitmap;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author Kezy
 * @Time 12/24/20
 * @Description
 */
public class RoundDrawableCache {
    private volatile static RoundDrawableCache mRoundDrawableCache;
    private Map<Long,WeakReference<RoundDrawable>> mCacheMap;

    private RoundDrawableCache() {
        mCacheMap = new HashMap<>();
    }

    public static RoundDrawableCache getSingleInstance() {
        if (mRoundDrawableCache == null) {
            synchronized (RoundDrawableCache.class) {
                if (mRoundDrawableCache == null) {
                    mRoundDrawableCache = new RoundDrawableCache();
                }
            }
        }
        return mRoundDrawableCache;
    }

    public RoundDrawable getRoundDrawable(Bitmap bitmap, float cornerRadius, int border,
                                          int borderColor, boolean isCircle) {
        long hashCode = getRoundDrawableCode(bitmap,cornerRadius,border,borderColor,isCircle);
        RoundDrawable drawable = null;
        if(mCacheMap.get(hashCode)!=null){
            drawable = mCacheMap.get(hashCode).get();
        }
        if (drawable==null){
            drawable = new RoundDrawable(bitmap, cornerRadius, border,
                    borderColor, isCircle);
            mCacheMap.put(hashCode,new WeakReference<>(drawable));
            checkNullAndRemove();
        }
        return drawable;
    }

    private void checkNullAndRemove(){
        if (mCacheMap.size()>50){
            Iterator iterator = mCacheMap.values().iterator();
            while (iterator.hasNext()){
                WeakReference reference = (WeakReference) (iterator.next());
                if(reference == null||reference.get() == null){
                    iterator.remove();
                }
            }
        }
    }

    private long getRoundDrawableCode(Bitmap bitmap, float cornerRadius, int border,
                                      int borderColor, boolean isCircle) {
        return (isCircle ? 1 : 0)
                + 10 * borderColor
                + 10000 * border
                + 1000000 * (long) cornerRadius
                + 100000000 * bitmap.hashCode();
    }

}
