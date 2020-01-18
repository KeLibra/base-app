package cn.kezy.libs.common.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;

import cn.kezy.libs.common.view.weight.GlideRoundTransform;

/**
 * @author: kezy
 * @create_time 2019/11/5
 * @description: 图片加载工具类
 */
public class ImageLoadUtils {

    public static void loadImage(Context context, Object imageUrl, ImageView imageView) {

        loadImage(context, imageUrl, -1, -1, -1, -1, imageView, null);

    }

    public static void loadImage(Context context, Object imageUrl, ImageView imageView, RequestListener listener) {

        loadImage(context, imageUrl, -1, -1, -1, -1, imageView, listener);

    }

    /**
     * 加载图片
     *
     * @param context
     * @param imageUrl  网图url
     * @param options   自定义 glide 配置
     * @param imageView
     */
    public static void loadImage(Context context, Object imageUrl, RequestOptions options, ImageView imageView) {

        try {
            if (!isDestroy((Activity) context)) {
                Glide.with(context)
                        .load(imageUrl)
                        .apply(options)
                        .into(imageView);
            }
        } catch (Exception e) {
            LogUtils.e(" load image err: " + e.toString());
        }

    }

    /**
     * 加载图片的方法
     *
     * @param context
     * @param imageUrl  网图url
     * @param placeImg  占位图
     * @param errImg    错误图
     * @param width     宽度
     * @param height    高度
     * @param imageView
     */
    public static void loadImage(Context context, Object imageUrl, int placeImg, int errImg, int width, int height, ImageView imageView, RequestListener listener) {

        try {

            if (!isDestroy((Activity) context)) {
                RequestOptions options = new RequestOptions();
                if (placeImg != -1) {
                    options.placeholder(placeImg);
                }
                if (errImg != -1) {
                    options.error(errImg);
                }
                if (width != -1 && height != -1) {
                    options.override(width, height);
                }
                options.diskCacheStrategy(DiskCacheStrategy.ALL);

                Glide.with(context)
                        .load(imageUrl)
                        .apply(options)
                        .listener(listener)
                        .into(imageView);
            }
        } catch (Exception e) {
            LogUtils.e(" load image err: " + e.toString());
        }
    }


    /**
     * 加载图片
     *
     * @param context
     * @param imageUrl  网图url
     * @param imageView
     */
    public static void loadRoundImage(Context context, String imageUrl, int radius, ImageView imageView) {

        RequestOptions options = new RequestOptions();
        options.transform(new GlideRoundTransform(context,radius));
        options.diskCacheStrategy(DiskCacheStrategy.ALL);

        loadImage(context, imageUrl, options, imageView);
    }
    /**
     * 加载圆形图片
     *
     * @param context
     * @param imageUrl  网图url
     * @param imageView
     */
    public static void loadCircleCropImage(Context context, String imageUrl, ImageView imageView) {

        RequestOptions options = RequestOptions.circleCropTransform();
        options.diskCacheStrategy(DiskCacheStrategy.ALL);

        loadImage(context, imageUrl, options, imageView);
    }
    /**
     * 加载圆形图片
     *
     * @param context
     * @param imageResource  本地图片地址
     * @param imageView
     */
    public static void loadCircleCropImage(Context context, int imageResource, ImageView imageView) {

        RequestOptions options = RequestOptions.circleCropTransform();
        options.diskCacheStrategy(DiskCacheStrategy.ALL);

        loadImage(context, imageResource, options, imageView);
    }


    /**
     * 判断Activity是否Destroy
     *
     * @param mActivity
     * @return
     */
    public static boolean isDestroy(Activity mActivity) {
        if (mActivity == null || mActivity.isFinishing() || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && mActivity.isDestroyed())) {
            return true;
        } else {
            return false;
        }
    }
}
