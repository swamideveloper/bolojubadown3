package com.free.hdvideodownloaderapp.videodownloader.Social_Download.extra;

import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;


public class Common {

    public static PushDownAnim Animation(View view) {
        PushDownAnim pushDownAnim = new PushDownAnim(view);
        PushDownAnim.setPushDownAnimTo(view);
        pushDownAnim.setScale(1, 8.0f);
        pushDownAnim.setDurationPush(50);
        pushDownAnim.setDurationRelease(125);
        AccelerateDecelerateInterpolator accelerateDecelerateInterpolator = PushDownAnim.DEFAULT_INTERPOLATOR;
        pushDownAnim.setInterpolatorPush(accelerateDecelerateInterpolator);
        pushDownAnim.setInterpolatorRelease(accelerateDecelerateInterpolator);
        return pushDownAnim;
    }


}
