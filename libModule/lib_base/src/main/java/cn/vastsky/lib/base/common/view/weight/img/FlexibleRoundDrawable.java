package cn.vastsky.lib.base.common.view.weight.img;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * @Author Kezy
 * @Time 12/24/20
 * @Description 灵活的roundDrawable 可配置不需要画的圆角
 */
public class FlexibleRoundDrawable extends Drawable {
    //常量值对应Flexible属性值round_corner
    public static final int CORNER_TOP_LEFT = 1;
    public static final int CORNER_TOP_RIGHT = 1 << 1;
    public static final int CORNER_BOTTOM_LEFT = 1 << 2;
    public static final int CORNER_BOTTOM_RIGHT = 1 << 3;
    public static final int CORNER_ALL = CORNER_TOP_LEFT | CORNER_TOP_RIGHT | CORNER_BOTTOM_LEFT | CORNER_BOTTOM_RIGHT;

    private float cornerRadius;

    private final RectF mBounds = new RectF();
    private RectF mRect = new RectF(), mBitmapRect;
    private BitmapShader mBitmapShader;
    private Paint paint;
    private int corners;
    private ImageView.ScaleType mScaleType = ImageView.ScaleType.FIT_XY;
    private final Matrix mShaderMatrix = new Matrix();
    private Bitmap mBitmap;

    public static FlexibleRoundDrawable createFlexibleRoundDrawable(Drawable drawable, int cornerRadius, int corners) {
        if (drawable == null) return null;
        return new FlexibleRoundDrawable(drawable, cornerRadius, corners);
    }

    public static FlexibleRoundDrawable createFlexibleRoundDrawable(Context context, int resId, int cornerRadius, int corners) {
        if (context == null) return null;
        return new FlexibleRoundDrawable(context.getResources().getDrawable(resId), cornerRadius, corners);
    }

    public FlexibleRoundDrawable(Bitmap bitmap, int cornerRadius, int corners) {
        if (bitmap == null) return;
        mBitmap = bitmap;
        this.cornerRadius = cornerRadius;
        this.corners = corners;

        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mBitmapRect = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(mBitmapShader);
    }

    public FlexibleRoundDrawable(Drawable drawable, int cornerRadius, int corners) {
        if (drawable == null) return;
        Bitmap bitmap = drawableToBitmap(drawable);
        mBitmap = bitmap;
        this.cornerRadius = cornerRadius;
        this.corners = corners;

        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mBitmapRect = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(mBitmapShader);
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap;
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        if (width > 0 && height > 0) {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        } else {
            //适配纯色背景
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    @Override
    public int getIntrinsicHeight() {
        return (int) mBitmapRect.height();
    }

    @Override
    public int getIntrinsicWidth() {
        return (int) mBitmapRect.width();
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        mBounds.set(bounds);
        setMatrix();
    }

    @Override
    public void draw(Canvas canvas) {
        //先画一个圆角矩形将图片显示为圆角
        canvas.drawRoundRect(mRect, cornerRadius, cornerRadius, paint);
        int notRoundedCorners = corners ^ CORNER_ALL;
        //哪个角不是圆角我再把你用矩形画出来
        if ((notRoundedCorners & CORNER_TOP_LEFT) != 0) {
            canvas.drawRect(0, 0, cornerRadius, cornerRadius, paint);
        }
        if ((notRoundedCorners & CORNER_TOP_RIGHT) != 0) {
            canvas.drawRect(mRect.right - cornerRadius, 0, mRect.right, cornerRadius, paint);
        }
        if ((notRoundedCorners & CORNER_BOTTOM_LEFT) != 0) {
            canvas.drawRect(0, mRect.bottom - cornerRadius, cornerRadius, mRect.bottom, paint);
        }
        if ((notRoundedCorners & CORNER_BOTTOM_RIGHT) != 0) {
            canvas.drawRect(mRect.right - cornerRadius, mRect.bottom - cornerRadius, mRect.right, mRect.bottom, paint);
        }
    }

    public float getCornerRadius() {
        return cornerRadius;
    }

    public void setCornerRadius(float cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    public int getCorners() {
        return corners;
    }

    public void setCorners(int corners) {
        this.corners = corners;
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        paint.setColorFilter(cf);
    }

    public ImageView.ScaleType getScaleType() {
        return mScaleType;
    }

    public void setScaleType(ImageView.ScaleType scaleType) {
        if (scaleType == null) {
            scaleType = ImageView.ScaleType.FIT_XY;
        }
        if (mScaleType != scaleType) {
            mScaleType = scaleType;
            setMatrix();
        }
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    private void setMatrix() {
        mRect.set(0, 0, mBounds.width(), mBounds.height());
        mShaderMatrix.set(null);
        float scale;
        float dx;
        float dy;

        switch (mScaleType) {
            case CENTER:
                mShaderMatrix.setTranslate(
                        (int) ((mRect.width() - mBitmapRect.width()) * 0.5f + 0.5f),
                        (int) ((mRect.height() - mBitmapRect.height()) * 0.5f + 0.5f));
                break;
            case CENTER_CROP:
                dx = 0;
                dy = 0;

                if (mBitmapRect.width() * mRect.height() > mRect.width()
                        * mBitmapRect.height()) {
                    scale = mRect.height() / mBitmapRect.height();
                    dx = (mRect.width() - mBitmapRect.width()  * scale) * 0.5f;
                } else {
                    scale = mRect.width() / mBitmapRect.width();
                    dy = (mRect.height() - mBitmapRect.height() * scale) * 0.5f;
                }

                mShaderMatrix.setScale(scale, scale);
                mShaderMatrix.postTranslate((int) (dx + 0.5f), (int) (dy + 0.5f));
                break;
            default:
                mShaderMatrix.setRectToRect(mBitmapRect, mRect,
                        Matrix.ScaleToFit.FILL);
                break;
        }
        mBitmapShader.setLocalMatrix(mShaderMatrix);
    }
}
