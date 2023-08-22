package com.sdk.ads.ads;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.sdk.ads.Comman;
import com.sdk.ads.LocalSave.ModelPrefrences;
import com.sdk.ads.Ui.Country_ConnectActivity;
import com.sdk.ads.Ui.DoPermissionActivity;
import com.sdk.ads.Ui.LanguageActivity;
import com.sdk.ads.Ui.PermissionActivity;
import com.sdk.ads.Ui.PrivacyPolicyAcitivty;
import com.sdk.ads.Ui.StartButtonActivity;

public class TransferClass {
    public static Class Destination;

    public static void setToNextActivity(Context sourceActivity, Class DestinationClass) {
        Destination = DestinationClass;
        ModelPrefrences modelPrefrences = new ModelPrefrences(sourceActivity);
        if (Comman.mainResModel != null && Comman.mainResModel.getSuccess()) {
            extraclass(sourceActivity, DestinationClass);
        } else {
            sourceActivity.startActivity(new Intent(sourceActivity, DestinationClass).putExtra("show", false));
            ((Activity) sourceActivity).finish();
        }
    }

    public static void extraclass(Context sourceActivity, Class DestinationClass) {
        ModelPrefrences modelPrefrences = new ModelPrefrences(sourceActivity);
        if (Comman.mainResModel.getData().getExtraFields().getCountry().equalsIgnoreCase("on")) {
            sourceActivity.startActivity(new Intent(sourceActivity, Country_ConnectActivity.class).putExtra("connect", false));
            ((Activity) sourceActivity).finish();
        } else if (Comman.mainResModel.getData().getExtraFields().getPrivacyPolicy().equalsIgnoreCase("on")) {
            sourceActivity.startActivity(new Intent(sourceActivity, PrivacyPolicyAcitivty.class).putExtra("show", false));
            ((Activity) sourceActivity).finish();
        } else if (Comman.mainResModel.getData().getExtraFields().getLanguage().equalsIgnoreCase("on")) {
            sourceActivity.startActivity(new Intent(sourceActivity, LanguageActivity.class).putExtra("show", false));
            ((Activity) sourceActivity).finish();
        } else if (Comman.mainResModel.getData().getExtraFields().getPermission().equalsIgnoreCase("on")) {
            sourceActivity.startActivity(new Intent(sourceActivity, PermissionActivity.class).putExtra("show", false));
            ((Activity) sourceActivity).finish();
        } else if (Comman.mainResModel.getData().getExtraFields().getStartButton().equalsIgnoreCase("on")) {
            sourceActivity.startActivity(new Intent(sourceActivity, StartButtonActivity.class).putExtra("show", false));
            ((Activity) sourceActivity).finish();
        } else if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getDoPermission().equalsIgnoreCase("on")) {
            sourceActivity.startActivity(new Intent(sourceActivity, DoPermissionActivity.class).putExtra("show", false));
            ((Activity) sourceActivity).finish();
        } else {
            sourceActivity.startActivity(new Intent(sourceActivity, DestinationClass).putExtra("show", false));
            ((Activity) sourceActivity).finish();
        }
    }

}
