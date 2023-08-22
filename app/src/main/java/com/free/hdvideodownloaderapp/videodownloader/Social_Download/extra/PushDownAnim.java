package com.free.hdvideodownloaderapp.videodownloader.Social_Download.extra;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.lang.ref.WeakReference;

public class PushDownAnim implements PushDown {
    public static final AccelerateDecelerateInterpolator DEFAULT_INTERPOLATOR = new AccelerateDecelerateInterpolator();
    public final float defaultScale;
    public long durationPush = 50;
    public long durationRelease = 125;
    public AccelerateDecelerateInterpolator interpolatorPush;
    public AccelerateDecelerateInterpolator interpolatorRelease;
    public int mode = 0;
    public float pushScale = 0.97f;
    public float pushStatic = 2.0f;
    public AnimatorSet scaleAnimSet;
    public WeakReference<View> weakView;

    public PushDownAnim(View view) {
        AccelerateDecelerateInterpolator accelerateDecelerateInterpolator = DEFAULT_INTERPOLATOR;
        this.interpolatorPush = accelerateDecelerateInterpolator;
        this.interpolatorRelease = accelerateDecelerateInterpolator;
        WeakReference<View> weakReference = new WeakReference<>(view);
        this.weakView = weakReference;
        ((View) weakReference.get()).setClickable(true);
        this.defaultScale = view.getScaleX();
    }

    public static PushDownAnim setPushDownAnimTo(View view) {
        PushDownAnim pushDownAnim = new PushDownAnim(view);
        pushDownAnim.setOnTouchEvent((View.OnTouchListener) null);
        return pushDownAnim;
    }

    public PushDown setScale(float f) {
        int i = this.mode;
        if (i == 0) {
            this.pushScale = f;
        } else if (i == 1) {
            this.pushStatic = f;
        }
        return this;
    }

    public PushDown setScale(int i, float f) {
        this.mode = i;
        setScale(f);
        return this;
    }

    public PushDown setDurationPush(long j) {
        this.durationPush = j;
        return this;
    }

    public PushDown setDurationRelease(long j) {
        this.durationRelease = j;
        return this;
    }

    public PushDown setInterpolatorPush(AccelerateDecelerateInterpolator accelerateDecelerateInterpolator) {
        this.interpolatorPush = accelerateDecelerateInterpolator;
        return this;
    }

    public PushDown setInterpolatorRelease(AccelerateDecelerateInterpolator accelerateDecelerateInterpolator) {
        this.interpolatorRelease = accelerateDecelerateInterpolator;
        return this;
    }

    public PushDown setOnClickListener(View.OnClickListener onClickListener) {
        if (this.weakView.get() != null) {
            ((View) this.weakView.get()).setOnClickListener(onClickListener);
        }
        return this;
    }

    public PushDown setOnTouchEvent(final View.OnTouchListener onTouchListener) {
        if (this.weakView.get() != null) {
            if (onTouchListener == null) {
                ((View) this.weakView.get()).setOnTouchListener(new View.OnTouchListener() {
                    public boolean isOutSide;
                    public Rect rect;

                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if (view.isClickable()) {
                            int action = motionEvent.getAction();
                            if (action == 0) {
                                this.isOutSide = false;
                                this.rect = new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
                                PushDownAnim pushDownAnim = PushDownAnim.this;
                                pushDownAnim.makeDecisionAnimScale(view, pushDownAnim.mode, PushDownAnim.this.pushScale, PushDownAnim.this.pushStatic, PushDownAnim.this.durationPush, PushDownAnim.this.interpolatorPush, action);
                            } else if (action == 2) {
                                Rect rect2 = this.rect;
                                if (rect2 != null && !this.isOutSide && !rect2.contains(view.getLeft() + ((int) motionEvent.getX()), view.getTop() + ((int) motionEvent.getY()))) {
                                    this.isOutSide = true;
                                    PushDownAnim pushDownAnim2 = PushDownAnim.this;
                                    pushDownAnim2.makeDecisionAnimScale(view, pushDownAnim2.mode, PushDownAnim.this.defaultScale, 0.0f, PushDownAnim.this.durationRelease, PushDownAnim.this.interpolatorRelease, action);
                                }
                            } else if (action == 3 || action == 1) {
                                PushDownAnim pushDownAnim3 = PushDownAnim.this;
                                pushDownAnim3.makeDecisionAnimScale(view, pushDownAnim3.mode, PushDownAnim.this.defaultScale, 0.0f, PushDownAnim.this.durationRelease, PushDownAnim.this.interpolatorRelease, action);
                            }
                        }
                        return false;
                    }
                });
            } else {
                ((View) this.weakView.get()).setOnTouchListener(new View.OnTouchListener() {
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return onTouchListener.onTouch((View) PushDownAnim.this.weakView.get(), motionEvent);
                    }
                });
            }
        }
        return this;
    }

    public final void makeDecisionAnimScale(View view, int i, float f, float f2, long j, TimeInterpolator timeInterpolator, int i2) {
        if (i == 1) {
            f = getScaleFromStaticSize(f2);
        }
        animScale(view, f, j, timeInterpolator);
    }

    public final void animScale(final View view_p, float f, long j, TimeInterpolator timeInterpolator) {

        view_p.animate().cancel();
        AnimatorSet animatorSet = this.scaleAnimSet;
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view_p, "scaleX", new float[]{f});
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view_p, "scaleY", new float[]{f});
        ofFloat.setInterpolator(timeInterpolator);
        ofFloat.setDuration(j);
        ofFloat2.setInterpolator(timeInterpolator);
        ofFloat2.setDuration(j);
        AnimatorSet animatorSet2 = new AnimatorSet();
        this.scaleAnimSet = animatorSet2;
        animatorSet2.play(ofFloat).with(ofFloat2);
        ofFloat.addListener(new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
            }

            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
            }
        });
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                View view = (View) view_p.getParent();
                if (view != null) {
                    view.invalidate();
                }
            }
        });
        this.scaleAnimSet.start();
    }

    public final float getScaleFromStaticSize(float f) {
        float viewHeight;
        int viewHeight2;
        if (f <= 0.0f) {
            return this.defaultScale;
        }
        float dpToPx = dpToPx(f);
        if (getViewWidth() > getViewHeight()) {
            if (dpToPx > ((float) getViewWidth())) {
                return 1.0f;
            }
            viewHeight = ((float) getViewWidth()) - (dpToPx * 2.0f);
            viewHeight2 = getViewWidth();
        } else if (dpToPx > ((float) getViewHeight())) {
            return 1.0f;
        } else {
            viewHeight = ((float) getViewHeight()) - (dpToPx * 2.0f);
            viewHeight2 = getViewHeight();
        }
        return viewHeight / ((float) viewHeight2);
    }

    public final int getViewHeight() {
        return ((View) this.weakView.get()).getMeasuredHeight();
    }

    public final int getViewWidth() {
        return ((View) this.weakView.get()).getMeasuredWidth();
    }

    @SuppressLint("WrongConstant")
    public final float dpToPx(float f) {
        return TypedValue.applyDimension(1, f, ((View) this.weakView.get()).getResources().getDisplayMetrics());
    }
}
