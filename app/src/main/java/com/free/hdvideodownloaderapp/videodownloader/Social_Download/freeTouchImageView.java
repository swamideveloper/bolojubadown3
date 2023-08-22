package com.free.hdvideodownloaderapp.videodownloader.Social_Download;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.OverScroller;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;

import com.free.hdvideodownloaderapp.videodownloader.Log;
import com.free.hdvideodownloaderapp.videodownloader.R;

public class freeTouchImageView extends AppCompatImageView {

    private static final String DEBUG = "DEBUG";
    static final float SUPER_MIN_MULTIPLIER = .75f;
    static final float SUPER_MAX_MULTIPLIER = 1.25f;
    static final int DEFAULT_ZOOM_TIME = 500;
    float normalizedScale;
    Matrix matrix, prevMatrix;
    boolean zoomEnabled;
    boolean isRotateImageToFitScreen;
    public enum FixedPixel {CENTER, TOP_LEFT, BOTTOM_RIGHT}
    FixedPixel orientationChangeFixedPixel = FixedPixel.CENTER;
    FixedPixel viewSizeChangeFixedPixel = FixedPixel.CENTER;
    boolean orientationJustChanged = false;
    enum State {NONE, DRAG, ZOOM, FLING, ANIMATE_ZOOM}
    State state;
    public static final float AUTOMATIC_MIN_ZOOM = -1.0f;
    float userSpecifiedMinScale;
    float minScale;
    boolean maxScaleIsSetByMultiplier = false;
    float maxScaleMultiplier;
    float maxScale;
    float superMinScale;
    float superMaxScale;
    float[] m;
    float doubleTapScale;
    Fling fling;
    int orientation;
    ScaleType mScaleType;
    boolean imageRenderedAtLeastOnce;
    boolean onDrawReady;
    ZoomVariables delayedZoomVariables;
    int viewWidth, viewHeight, prevViewWidth, prevViewHeight;
    float matchViewWidth, matchViewHeight, prevMatchViewWidth, prevMatchViewHeight;
    ScaleGestureDetector mScaleDetector;
    GestureDetector mGestureDetector;
    GestureDetector.OnDoubleTapListener doubleTapListener = null;
    OnTouchListener userTouchListener = null;
    OnTouchImageViewListener touchImageViewListener = null;

    public freeTouchImageView(Context context) {
        this(context, null);
    }

    public freeTouchImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public freeTouchImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        configureImageView(context, attrs, defStyle);
    }

    private void configureImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super.setClickable(true);
        orientation = getResources().getConfiguration().orientation;
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        mGestureDetector = new GestureDetector(context, new GestureListener());
        matrix = new Matrix();
        prevMatrix = new Matrix();

        m = new float[9];
        normalizedScale = 1;
        if (mScaleType == null) {
            mScaleType = ScaleType.FIT_CENTER;
        }

        minScale = 1;
        maxScale = 3;
        superMinScale = SUPER_MIN_MULTIPLIER * minScale;
        superMaxScale = SUPER_MAX_MULTIPLIER * maxScale;
        setImageMatrix(matrix);
        setScaleType(ScaleType.MATRIX);
        setState(State.NONE);
        onDrawReady = false;

        super.setOnTouchListener(new PrivateOnTouchListener());

        final TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TouchImageView, defStyleAttr, 0);
        try {
            if (!isInEditMode()) {
                setZoomEnabled(attributes.getBoolean(R.styleable.TouchImageView_zoom_enabled, true));
            }
        } finally {
            attributes.recycle();
        }
    }

    public void setRotateImageToFitScreen(boolean rotateImageToFitScreen) {
        isRotateImageToFitScreen = rotateImageToFitScreen;
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        userTouchListener = l;
    }

    public void setOnTouchImageViewListener(OnTouchImageViewListener l) {
        touchImageViewListener = l;
    }

    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener l) {
        doubleTapListener = l;
    }

    public boolean isZoomEnabled() {
        return zoomEnabled;
    }

    public void setZoomEnabled(boolean zoomEnabled) {
        this.zoomEnabled = zoomEnabled;
    }

    @Override
    public void setImageResource(int resId) {
        imageRenderedAtLeastOnce = false;
        super.setImageResource(resId);
        savePreviousImageValues();
        fitImageToView();
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        imageRenderedAtLeastOnce = false;
        super.setImageBitmap(bm);
        savePreviousImageValues();
        fitImageToView();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        imageRenderedAtLeastOnce = false;
        super.setImageDrawable(drawable);
        savePreviousImageValues();
        fitImageToView();
    }

    @Override
    public void setImageURI(Uri uri) {
        imageRenderedAtLeastOnce = false;
        super.setImageURI(uri);
        savePreviousImageValues();
        fitImageToView();
    }

    @Override
    public void setScaleType(ScaleType type) {
        if (type == ScaleType.MATRIX) {
            super.setScaleType(ScaleType.MATRIX);

        } else {
            mScaleType = type;
            if (onDrawReady) {
                setZoom(this);
            }
        }
    }

    @Override
    public ScaleType getScaleType() {
        return mScaleType;
    }

    public FixedPixel getOrientationChangeFixedPixel() {
        return orientationChangeFixedPixel;
    }

    public void setOrientationChangeFixedPixel(FixedPixel fixedPixel) {
        this.orientationChangeFixedPixel = fixedPixel;
    }

    public FixedPixel getViewSizeChangeFixedPixel() {
        return viewSizeChangeFixedPixel;
    }

    public void setViewSizeChangeFixedPixel(FixedPixel viewSizeChangeFixedPixel) {
        this.viewSizeChangeFixedPixel = viewSizeChangeFixedPixel;
    }

    public boolean isZoomed() {
        return normalizedScale != 1;
    }

    public RectF getZoomedRect() {
        if (mScaleType == ScaleType.FIT_XY) {
            throw new UnsupportedOperationException("getZoomedRect() not supported with FIT_XY");
        }
        PointF topLeft = transformCoordTouchToBitmap(0, 0, true);
        PointF bottomRight = transformCoordTouchToBitmap(viewWidth, viewHeight, true);

        float w = getDrawableWidth(getDrawable());
        float h = getDrawableHeight(getDrawable());
        return new RectF(topLeft.x / w, topLeft.y / h, bottomRight.x / w, bottomRight.y / h);
    }

    public void savePreviousImageValues() {
        if (matrix != null && viewHeight != 0 && viewWidth != 0) {
            matrix.getValues(m);
            prevMatrix.setValues(m);
            prevMatchViewHeight = matchViewHeight;
            prevMatchViewWidth = matchViewWidth;
            prevViewHeight = viewHeight;
            prevViewWidth = viewWidth;
        }
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putInt("orientation", orientation);
        bundle.putFloat("saveScale", normalizedScale);
        bundle.putFloat("matchViewHeight", matchViewHeight);
        bundle.putFloat("matchViewWidth", matchViewWidth);
        bundle.putInt("viewWidth", viewWidth);
        bundle.putInt("viewHeight", viewHeight);
        matrix.getValues(m);
        bundle.putFloatArray("matrix", m);
        bundle.putBoolean("imageRendered", imageRenderedAtLeastOnce);
        bundle.putSerializable("viewSizeChangeFixedPixel", viewSizeChangeFixedPixel);
        bundle.putSerializable("orientationChangeFixedPixel", orientationChangeFixedPixel);
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            normalizedScale = bundle.getFloat("saveScale");
            m = bundle.getFloatArray("matrix");
            prevMatrix.setValues(m);
            prevMatchViewHeight = bundle.getFloat("matchViewHeight");
            prevMatchViewWidth = bundle.getFloat("matchViewWidth");
            prevViewHeight = bundle.getInt("viewHeight");
            prevViewWidth = bundle.getInt("viewWidth");
            imageRenderedAtLeastOnce = bundle.getBoolean("imageRendered");
            viewSizeChangeFixedPixel = (FixedPixel) bundle.getSerializable("viewSizeChangeFixedPixel");
            orientationChangeFixedPixel = (FixedPixel) bundle.getSerializable("orientationChangeFixedPixel");
            int oldOrientation = bundle.getInt("orientation");
            if (orientation != oldOrientation) {
                orientationJustChanged = true;
            }
            super.onRestoreInstanceState(bundle.getParcelable("instanceState"));
            return;
        }

        super.onRestoreInstanceState(state);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        onDrawReady = true;
        imageRenderedAtLeastOnce = true;
        if (delayedZoomVariables != null) {
            setZoom(delayedZoomVariables.scale, delayedZoomVariables.focusX, delayedZoomVariables.focusY, delayedZoomVariables.scaleType);
            delayedZoomVariables = null;
        }
        super.onDraw(canvas);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int newOrientation = getResources().getConfiguration().orientation;
        if (newOrientation != orientation) {
            orientationJustChanged = true;
            orientation = newOrientation;
        }
        savePreviousImageValues();
    }

    public float getMaxZoom() {
        return maxScale;
    }

    public void setMaxZoom(float max) {
        maxScale = max;
        superMaxScale = SUPER_MAX_MULTIPLIER * maxScale;
        maxScaleIsSetByMultiplier = false;
    }

    public float getDoubleTapScale() {
        return doubleTapScale;
    }

    public void setDoubleTapScale(float doubleTapScale) {
        this.doubleTapScale = doubleTapScale;
    }

    public void setMaxZoomRatio(float max) {
        maxScaleMultiplier = max;
        maxScale = minScale * maxScaleMultiplier;
        superMaxScale = SUPER_MAX_MULTIPLIER * maxScale;
        maxScaleIsSetByMultiplier = true;
    }

    public float getMinZoom() {
        return minScale;
    }

    public float getCurrentZoom() {
        return normalizedScale;
    }

    public void setMinZoom(float min) {
        userSpecifiedMinScale = min;
        if (min == AUTOMATIC_MIN_ZOOM) {
            if (mScaleType == ScaleType.CENTER || mScaleType == ScaleType.CENTER_CROP) {
                Drawable drawable = getDrawable();
                int drawableWidth = getDrawableWidth(drawable);
                int drawableHeight = getDrawableHeight(drawable);
                if (drawable != null && drawableWidth > 0 && drawableHeight > 0) {
                    float widthRatio = (float) viewWidth / drawableWidth;
                    float heightRatio = (float) viewHeight / drawableHeight;
                    if (mScaleType == ScaleType.CENTER) {
                        minScale = Math.min(widthRatio, heightRatio);
                    } else {
                        minScale = Math.min(widthRatio, heightRatio) / Math.max(widthRatio, heightRatio);
                    }
                }
            } else {
                minScale = 1.0f;
            }
        } else {
            minScale = userSpecifiedMinScale;
        }
        if (maxScaleIsSetByMultiplier) {
            setMaxZoomRatio(maxScaleMultiplier);
        }
        superMinScale = SUPER_MIN_MULTIPLIER * minScale;
    }

    public void resetZoom() {
        normalizedScale = 1;
        fitImageToView();
    }

    public void resetZoomAnimated() {
        setZoomAnimated(1f, 0.5f, 0.5f);
    }

    public void setZoom(float scale) {
        setZoom(scale, 0.5f, 0.5f);
    }

    public void setZoom(float scale, float focusX, float focusY) {
        setZoom(scale, focusX, focusY, mScaleType);
    }

    public void setZoom(float scale, float focusX, float focusY, ScaleType scaleType) {

        if (!onDrawReady) {
            delayedZoomVariables = new ZoomVariables(scale, focusX, focusY, scaleType);
            return;
        }
        if (userSpecifiedMinScale == AUTOMATIC_MIN_ZOOM) {
            setMinZoom(AUTOMATIC_MIN_ZOOM);
            if (normalizedScale < minScale) {
                normalizedScale = minScale;
            }
        }

        if (scaleType != mScaleType) {
            setScaleType(scaleType);
        }
        resetZoom();
        scaleImage(scale, viewWidth / 2, viewHeight / 2, true);
        matrix.getValues(m);
        m[Matrix.MTRANS_X] = -((focusX * getImageWidth()) - (viewWidth * 0.5f));
        m[Matrix.MTRANS_Y] = -((focusY * getImageHeight()) - (viewHeight * 0.5f));
        matrix.setValues(m);
        fixTrans();
        savePreviousImageValues();
        setImageMatrix(matrix);
    }

    public void setZoom(@NonNull freeTouchImageView img) {
        PointF center = img.getScrollPosition();
        setZoom(img.getCurrentZoom(), center.x, center.y, img.getScaleType());
    }

    public PointF getScrollPosition() {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return new PointF(.5F, .5F);
        }
        int drawableWidth = getDrawableWidth(drawable);
        int drawableHeight = getDrawableHeight(drawable);

        PointF point = transformCoordTouchToBitmap(viewWidth / 2, viewHeight / 2, true);
        point.x /= drawableWidth;
        point.y /= drawableHeight;
        return point;
    }

    private boolean orientationMismatch(Drawable drawable) {
        return viewWidth > viewHeight != drawable.getIntrinsicWidth() > drawable.getIntrinsicHeight();
    }

    private int getDrawableWidth(Drawable drawable) {
        if (orientationMismatch(drawable) && isRotateImageToFitScreen) {
            return drawable.getIntrinsicHeight();
        }
        return drawable.getIntrinsicWidth();
    }

    private int getDrawableHeight(Drawable drawable) {
        if (orientationMismatch(drawable) && isRotateImageToFitScreen) {
            return drawable.getIntrinsicWidth();
        }
        return drawable.getIntrinsicHeight();
    }

    public void setScrollPosition(float focusX, float focusY) {
        setZoom(normalizedScale, focusX, focusY);
    }

    private void fixTrans() {
        matrix.getValues(m);
        float transX = m[Matrix.MTRANS_X];
        float transY = m[Matrix.MTRANS_Y];

        float offset = 0;
        if (isRotateImageToFitScreen && orientationMismatch(getDrawable())) {
            offset = getImageWidth();
        }
        float fixTransX = getFixTrans(transX, viewWidth, getImageWidth(), offset);
        float fixTransY = getFixTrans(transY, viewHeight, getImageHeight(), 0);

        matrix.postTranslate(fixTransX, fixTransY);
    }

    private void fixScaleTrans() {
        fixTrans();
        matrix.getValues(m);
        if (getImageWidth() < viewWidth) {
            float xOffset = (viewWidth - getImageWidth()) / 2;
            if (isRotateImageToFitScreen && orientationMismatch(getDrawable())) {
                xOffset += getImageWidth();
            }
            m[Matrix.MTRANS_X] = xOffset;
        }

        if (getImageHeight() < viewHeight) {
            m[Matrix.MTRANS_Y] = (viewHeight - getImageHeight()) / 2;
        }
        matrix.setValues(m);
    }

    private float getFixTrans(float trans, float viewSize, float contentSize, float offset) {
        float minTrans, maxTrans;

        if (contentSize <= viewSize) {
            minTrans = offset;
            maxTrans = offset + viewSize - contentSize;

        } else {
            minTrans = offset + viewSize - contentSize;
            maxTrans = offset;
        }
        if (trans < minTrans)
            return -trans + minTrans;
        if (trans > maxTrans)
            return -trans + maxTrans;
        return 0;
    }

    private float getFixDragTrans(float delta, float viewSize, float contentSize) {
        if (contentSize <= viewSize) {
            return 0;
        }
        return delta;
    }

    private float getImageWidth() {
        return matchViewWidth * normalizedScale;
    }

    private float getImageHeight() {
        return matchViewHeight * normalizedScale;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        Drawable drawable = getDrawable();
        if (drawable == null || drawable.getIntrinsicWidth() == 0 || drawable.getIntrinsicHeight() == 0) {
            setMeasuredDimension(0, 0);
            return;
        }

        int drawableWidth = getDrawableWidth(drawable);
        int drawableHeight = getDrawableHeight(drawable);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int totalViewWidth = setViewSize(widthMode, widthSize, drawableWidth);
        int totalViewHeight = setViewSize(heightMode, heightSize, drawableHeight);

        if (!orientationJustChanged) {
            savePreviousImageValues();
        }

        int width = totalViewWidth - getPaddingLeft() - getPaddingRight();
        int height = totalViewHeight - getPaddingTop() - getPaddingBottom();
        setMeasuredDimension(width, height);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeight = h;
        fitImageToView();
    }

    private void fitImageToView() {
        FixedPixel fixedPixel = orientationJustChanged ?
                orientationChangeFixedPixel : viewSizeChangeFixedPixel;
        orientationJustChanged = false;
        Drawable drawable = getDrawable();
        if (drawable == null || drawable.getIntrinsicWidth() == 0 || drawable.getIntrinsicHeight() == 0) {
            return;
        }
        if (matrix == null || prevMatrix == null) {
            return;
        }
        if (userSpecifiedMinScale == AUTOMATIC_MIN_ZOOM) {
            setMinZoom(AUTOMATIC_MIN_ZOOM);
            if (normalizedScale < minScale) {
                normalizedScale = minScale;
            }
        }

        int drawableWidth = getDrawableWidth(drawable);
        int drawableHeight = getDrawableHeight(drawable);
        float scaleX = (float) viewWidth / drawableWidth;
        float scaleY = (float) viewHeight / drawableHeight;

        switch (mScaleType) {
            case CENTER:
                scaleX = scaleY = 1;
                break;
            case CENTER_CROP:
                scaleX = scaleY = Math.max(scaleX, scaleY);
                break;
            case CENTER_INSIDE:
                scaleX = scaleY = Math.min(1, Math.min(scaleX, scaleY));
            case FIT_CENTER:
            case FIT_START:
            case FIT_END:
                scaleX = scaleY = Math.min(scaleX, scaleY);
                break;
            case FIT_XY:
                break;
            default:
        }

        float redundantXSpace = viewWidth - (scaleX * drawableWidth);
        float redundantYSpace = viewHeight - (scaleY * drawableHeight);
        matchViewWidth = viewWidth - redundantXSpace;
        matchViewHeight = viewHeight - redundantYSpace;
        if (!isZoomed() && !imageRenderedAtLeastOnce) {

            if (isRotateImageToFitScreen && orientationMismatch(drawable)) {
                matrix.setRotate(90);
                matrix.postTranslate(drawableWidth, 0);
                matrix.postScale(scaleX, scaleY);
            } else {
                matrix.setScale(scaleX, scaleY);
            }

            switch (mScaleType) {
                case FIT_START:
                    matrix.postTranslate(0, 0);
                    break;
                case FIT_END:
                    matrix.postTranslate(redundantXSpace, redundantYSpace);
                    break;
                default:
                    matrix.postTranslate(redundantXSpace / 2, redundantYSpace / 2);
            }

            normalizedScale = 1;
        } else {

            if (prevMatchViewWidth == 0 || prevMatchViewHeight == 0) {
                savePreviousImageValues();
            }
            prevMatrix.getValues(m);
            m[Matrix.MSCALE_X] = matchViewWidth / drawableWidth * normalizedScale;
            m[Matrix.MSCALE_Y] = matchViewHeight / drawableHeight * normalizedScale;
            float transX = m[Matrix.MTRANS_X];
            float transY = m[Matrix.MTRANS_Y];
            float prevActualWidth = prevMatchViewWidth * normalizedScale;
            float actualWidth = getImageWidth();
            m[Matrix.MTRANS_X] = newTranslationAfterChange(transX, prevActualWidth, actualWidth, prevViewWidth, viewWidth, drawableWidth, fixedPixel);
            float prevActualHeight = prevMatchViewHeight * normalizedScale;
            float actualHeight = getImageHeight();
            m[Matrix.MTRANS_Y] = newTranslationAfterChange(transY, prevActualHeight, actualHeight, prevViewHeight, viewHeight, drawableHeight, fixedPixel);
            matrix.setValues(m);
        }
        fixTrans();
        setImageMatrix(matrix);
    }

    private int setViewSize(int mode, int size, int drawableWidth) {
        int viewSize;
        switch (mode) {
            case MeasureSpec.EXACTLY:
                viewSize = size;
                break;
            case MeasureSpec.AT_MOST:
                viewSize = Math.min(drawableWidth, size);
                break;
            case MeasureSpec.UNSPECIFIED:
                viewSize = drawableWidth;
                break;
            default:
                viewSize = size;
                break;
        }
        return viewSize;
    }

    private float newTranslationAfterChange(float trans, float prevImageSize, float imageSize, int prevViewSize, int viewSize, int drawableSize, FixedPixel sizeChangeFixedPixel) {
        if (imageSize < viewSize) {

            return (viewSize - (drawableSize * m[Matrix.MSCALE_X])) * 0.5f;

        } else if (trans > 0) {
            return -((imageSize - viewSize) * 0.5f);
        } else {

            float fixedPixelPositionInView = 0.5f;
            if (sizeChangeFixedPixel == FixedPixel.BOTTOM_RIGHT) {
                fixedPixelPositionInView = 1.0f;
            } else if (sizeChangeFixedPixel == FixedPixel.TOP_LEFT) {
                fixedPixelPositionInView = 0.0f;
            }
            float fixedPixelPositionInImage = (-trans + (fixedPixelPositionInView * prevViewSize)) / prevImageSize;
            return -((fixedPixelPositionInImage * imageSize) - (viewSize * fixedPixelPositionInView));
        }
    }

    private void setState(State state) {
        this.state = state;
    }

    @Deprecated
    public boolean canScrollHorizontallyFroyo(int direction) {
        return canScrollHorizontally(direction);
    }

    @Override
    public boolean canScrollHorizontally(int direction) {
        matrix.getValues(m);
        float x = m[Matrix.MTRANS_X];

        if (getImageWidth() < viewWidth) {
            return false;

        } else if (x >= -1 && direction < 0) {
            return false;

        } else return !(Math.abs(x) + viewWidth + 1 >= getImageWidth()) || direction <= 0;

    }

    @Override
    public boolean canScrollVertically(int direction) {
        matrix.getValues(m);
        float y = m[Matrix.MTRANS_Y];

        if (getImageHeight() < viewHeight) {
            return false;

        } else if (y >= -1 && direction < 0) {
            return false;

        } else return !(Math.abs(y) + viewHeight + 1 >= getImageHeight()) || direction <= 0;

    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            if (doubleTapListener != null) {
                return doubleTapListener.onSingleTapConfirmed(e);
            }
            return performClick();
        }

        @Override
        public void onLongPress(MotionEvent e) {
            performLongClick();
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (fling != null) {
                fling.cancelFling();
            }
            fling = new Fling((int) velocityX, (int) velocityY);
            compatPostOnAnimation(fling);
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            boolean consumed = false;
            if (isZoomEnabled()) {
                if (doubleTapListener != null) {
                    consumed = doubleTapListener.onDoubleTap(e);
                }
                if (state == State.NONE) {
                    float maxZoomScale = (doubleTapScale == 0) ? maxScale : doubleTapScale;
                    float targetZoom = (normalizedScale == minScale) ? maxZoomScale : minScale;
                    DoubleTapZoom doubleTap = new DoubleTapZoom(targetZoom, e.getX(), e.getY(), false);
                    compatPostOnAnimation(doubleTap);
                    consumed = true;
                }
            }
            return consumed;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            if (doubleTapListener != null) {
                return doubleTapListener.onDoubleTapEvent(e);
            }
            return false;
        }
    }

    public interface OnTouchImageViewListener {
        void onMove();
    }

    private class PrivateOnTouchListener implements OnTouchListener {

        private PointF last = new PointF();

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (getDrawable() == null) {
                setState(State.NONE);
                return false;
            }
            if (isZoomEnabled()) {
                mScaleDetector.onTouchEvent(event);
            }
            mGestureDetector.onTouchEvent(event);
            PointF curr = new PointF(event.getX(), event.getY());

            if (state == State.NONE || state == State.DRAG || state == State.FLING) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        last.set(curr);
                        if (fling != null)
                            fling.cancelFling();
                        setState(State.DRAG);
                        break;

                    case MotionEvent.ACTION_MOVE:
                        if (state == State.DRAG) {
                            float deltaX = curr.x - last.x;
                            float deltaY = curr.y - last.y;
                            float fixTransX = getFixDragTrans(deltaX, viewWidth, getImageWidth());
                            float fixTransY = getFixDragTrans(deltaY, viewHeight, getImageHeight());
                            matrix.postTranslate(fixTransX, fixTransY);
                            fixTrans();
                            last.set(curr.x, curr.y);
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP:
                        setState(State.NONE);
                        break;
                }
            }

            setImageMatrix(matrix);

            if (userTouchListener != null) {
                userTouchListener.onTouch(v, event);
            }

            if (touchImageViewListener != null) {
                touchImageViewListener.onMove();
            }

            return true;
        }
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            setState(State.ZOOM);
            return true;
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleImage(detector.getScaleFactor(), detector.getFocusX(), detector.getFocusY(), true);
            if (touchImageViewListener != null) {
                touchImageViewListener.onMove();
            }
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            super.onScaleEnd(detector);
            setState(State.NONE);
            boolean animateToZoomBoundary = false;
            float targetZoom = normalizedScale;
            if (normalizedScale > maxScale) {
                targetZoom = maxScale;
                animateToZoomBoundary = true;

            } else if (normalizedScale < minScale) {
                targetZoom = minScale;
                animateToZoomBoundary = true;
            }

            if (animateToZoomBoundary) {
                DoubleTapZoom doubleTap = new DoubleTapZoom(targetZoom, viewWidth / 2, viewHeight / 2, true);
                compatPostOnAnimation(doubleTap);
            }
        }
    }

    private void scaleImage(double deltaScale, float focusX, float focusY, boolean stretchImageToSuper) {
        float lowerScale, upperScale;
        if (stretchImageToSuper) {
            lowerScale = superMinScale;
            upperScale = superMaxScale;
        } else {
            lowerScale = minScale;
            upperScale = maxScale;
        }

        float origScale = normalizedScale;
        normalizedScale *= deltaScale;
        if (normalizedScale > upperScale) {
            normalizedScale = upperScale;
            deltaScale = upperScale / origScale;
        } else if (normalizedScale < lowerScale) {
            normalizedScale = lowerScale;
            deltaScale = lowerScale / origScale;
        }
        matrix.postScale((float) deltaScale, (float) deltaScale, focusX, focusY);
        fixScaleTrans();
    }

    private class DoubleTapZoom implements Runnable {

        private long startTime;
        private float startZoom, targetZoom;
        private float bitmapX, bitmapY;
        private boolean stretchImageToSuper;
        private AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();
        private PointF startTouch;
        private PointF endTouch;

        DoubleTapZoom(float targetZoom, float focusX, float focusY, boolean stretchImageToSuper) {
            setState(State.ANIMATE_ZOOM);
            startTime = System.currentTimeMillis();
            this.startZoom = normalizedScale;
            this.targetZoom = targetZoom;
            this.stretchImageToSuper = stretchImageToSuper;
            PointF bitmapPoint = transformCoordTouchToBitmap(focusX, focusY, false);
            this.bitmapX = bitmapPoint.x;
            this.bitmapY = bitmapPoint.y;

            startTouch = transformCoordBitmapToTouch(bitmapX, bitmapY);
            endTouch = new PointF(viewWidth / 2, viewHeight / 2);
        }

        @Override
        public void run() {
            if (getDrawable() == null) {
                setState(State.NONE);
                return;
            }
            float t = interpolate();
            double deltaScale = calculateDeltaScale(t);
            scaleImage(deltaScale, bitmapX, bitmapY, stretchImageToSuper);
            translateImageToCenterTouchPosition(t);
            fixScaleTrans();
            setImageMatrix(matrix);

            if (touchImageViewListener != null) {
                touchImageViewListener.onMove();
            }
            if (t < 1f) {
                compatPostOnAnimation(this);

            } else {
                setState(State.NONE);
            }
        }

        private void translateImageToCenterTouchPosition(float t) {
            float targetX = startTouch.x + t * (endTouch.x - startTouch.x);
            float targetY = startTouch.y + t * (endTouch.y - startTouch.y);
            PointF curr = transformCoordBitmapToTouch(bitmapX, bitmapY);
            matrix.postTranslate(targetX - curr.x, targetY - curr.y);
        }

        private float interpolate() {
            long currTime = System.currentTimeMillis();
            float elapsed = (currTime - startTime) / (float) DEFAULT_ZOOM_TIME;
            elapsed = Math.min(1f, elapsed);
            return interpolator.getInterpolation(elapsed);
        }

        private double calculateDeltaScale(float t) {
            double zoom = startZoom + t * (targetZoom - startZoom);
            return zoom / normalizedScale;
        }
    }

    protected PointF transformCoordTouchToBitmap(float x, float y, boolean clipToBitmap) {
        matrix.getValues(m);
        float origW = getDrawable().getIntrinsicWidth();
        float origH = getDrawable().getIntrinsicHeight();
        float transX = m[Matrix.MTRANS_X];
        float transY = m[Matrix.MTRANS_Y];
        float finalX = ((x - transX) * origW) / getImageWidth();
        float finalY = ((y - transY) * origH) / getImageHeight();
        if (clipToBitmap) {
            finalX = Math.min(Math.max(finalX, 0), origW);
            finalY = Math.min(Math.max(finalY, 0), origH);
        }
        return new PointF(finalX, finalY);
    }

    protected PointF transformCoordBitmapToTouch(float bx, float by) {
        matrix.getValues(m);
        float origW = getDrawable().getIntrinsicWidth();
        float origH = getDrawable().getIntrinsicHeight();
        float px = bx / origW;
        float py = by / origH;
        float finalX = m[Matrix.MTRANS_X] + getImageWidth() * px;
        float finalY = m[Matrix.MTRANS_Y] + getImageHeight() * py;
        return new PointF(finalX, finalY);
    }

    private class Fling implements Runnable {
        CompatScroller scroller;
        int currX, currY;

        Fling(int velocityX, int velocityY) {
            setState(State.FLING);
            scroller = new CompatScroller(getContext());
            matrix.getValues(m);
            int startX = (int) m[Matrix.MTRANS_X];
            int startY = (int) m[Matrix.MTRANS_Y];
            int minX, maxX, minY, maxY;
            if (isRotateImageToFitScreen && orientationMismatch(getDrawable())) {
                startX -= getImageWidth();
            }
            if (getImageWidth() > viewWidth) {
                minX = viewWidth - (int) getImageWidth();
                maxX = 0;
            } else {
                minX = maxX = startX;
            }
            if (getImageHeight() > viewHeight) {
                minY = viewHeight - (int) getImageHeight();
                maxY = 0;
            } else {
                minY = maxY = startY;
            }
            scroller.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY);
            currX = startX;
            currY = startY;
        }

        public void cancelFling() {
            if (scroller != null) {
                setState(State.NONE);
                scroller.forceFinished(true);
            }
        }

        @Override
        public void run() {
            if (touchImageViewListener != null) {
                touchImageViewListener.onMove();
            }
            if (scroller.isFinished()) {
                scroller = null;
                return;
            }
            if (scroller.computeScrollOffset()) {
                int newX = scroller.getCurrX();
                int newY = scroller.getCurrY();
                int transX = newX - currX;
                int transY = newY - currY;
                currX = newX;
                currY = newY;
                matrix.postTranslate(transX, transY);
                fixTrans();
                setImageMatrix(matrix);
                compatPostOnAnimation(this);
            }
        }
    }

    @TargetApi(VERSION_CODES.GINGERBREAD)
    private class CompatScroller {
        OverScroller overScroller;

        CompatScroller(Context context) {
            overScroller = new OverScroller(context);
        }

        void fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY) {
            overScroller.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY);
        }

        void forceFinished(boolean finished) {
            overScroller.forceFinished(finished);
        }

        public boolean isFinished() {
            return overScroller.isFinished();
        }

        boolean computeScrollOffset() {
            overScroller.computeScrollOffset();
            return overScroller.computeScrollOffset();
        }

        int getCurrX() {
            return overScroller.getCurrX();
        }

        int getCurrY() {
            return overScroller.getCurrY();
        }
    }

    @TargetApi(VERSION_CODES.JELLY_BEAN)
    private void compatPostOnAnimation(Runnable runnable) {
        if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN) {
            postOnAnimation(runnable);
        } else {
            postDelayed(runnable, 1000 / 60);
        }
    }

    private class ZoomVariables {
        float scale;
        float focusX;
        float focusY;
        ScaleType scaleType;

        ZoomVariables(float scale, float focusX, float focusY, ScaleType scaleType) {
            this.scale = scale;
            this.focusX = focusX;
            this.focusY = focusY;
            this.scaleType = scaleType;
        }
    }

    private void printMatrixInfo() {
        float[] n = new float[9];
        matrix.getValues(n);
        Log.d(DEBUG, "Scale: " + n[Matrix.MSCALE_X] + " TransX: " + n[Matrix.MTRANS_X] + " TransY: " + n[Matrix.MTRANS_Y]);
    }

    public interface OnZoomFinishedListener {
        void onZoomFinished();
    }

    public void setZoomAnimated(float scale, float focusX, float focusY) {
        setZoomAnimated(scale, focusX, focusY, DEFAULT_ZOOM_TIME);
    }

    public void setZoomAnimated(float scale, float focusX, float focusY, int zoomTimeMs) {
        AnimatedZoom animation = new AnimatedZoom(scale, new PointF(focusX, focusY), zoomTimeMs);
        compatPostOnAnimation(animation);
    }


    public void setZoomAnimated(float scale, float focusX, float focusY, int zoomTimeMs, OnZoomFinishedListener listener) {
        AnimatedZoom animation = new AnimatedZoom(scale, new PointF(focusX, focusY), zoomTimeMs);
        animation.setListener(listener);
        compatPostOnAnimation(animation);
    }

    public void setZoomAnimated(float scale, float focusX, float focusY, OnZoomFinishedListener listener) {
        AnimatedZoom animation = new AnimatedZoom(scale, new PointF(focusX, focusY), DEFAULT_ZOOM_TIME);
        animation.setListener(listener);
        compatPostOnAnimation(animation);
    }


    private class AnimatedZoom implements Runnable {

        private final int zoomTimeMillis;
        private long startTime;
        private float startZoom, targetZoom;
        private PointF startFocus, targetFocus;
        private LinearInterpolator interpolator = new LinearInterpolator();
        private OnZoomFinishedListener listener;

        AnimatedZoom(float targetZoom, PointF focus, int zoomTimeMillis) {
            setState(State.ANIMATE_ZOOM);
            startTime = System.currentTimeMillis();
            this.startZoom = normalizedScale;
            this.targetZoom = targetZoom;
            this.zoomTimeMillis = zoomTimeMillis;

            startFocus = getScrollPosition();
            targetFocus = focus;
        }

        @Override
        public void run() {
            float t = interpolate();

            float nextZoom = startZoom + (targetZoom - startZoom) * t;
            float nextX = startFocus.x + (targetFocus.x - startFocus.x) * t;
            float nextY = startFocus.y + (targetFocus.y - startFocus.y) * t;
            setZoom(nextZoom, nextX, nextY);


            if (t < 1f) {

                compatPostOnAnimation(this);
            } else {

                setState(State.NONE);
                if (listener != null) listener.onZoomFinished();
            }
        }


        private float interpolate() {
            float elapsed = (System.currentTimeMillis() - startTime) / (float) zoomTimeMillis;
            elapsed = Math.min(1f, elapsed);
            return interpolator.getInterpolation(elapsed);
        }

        void setListener(OnZoomFinishedListener listener) {
            this.listener = listener;
        }
    }

}
