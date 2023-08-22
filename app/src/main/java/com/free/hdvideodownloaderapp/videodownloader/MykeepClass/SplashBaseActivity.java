package com.free.hdvideodownloaderapp.videodownloader.MykeepClass;

import static com.free.hdvideodownloaderapp.videodownloader.in_ex.Speed_Ex_Clinet.getGeoApiService;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.free.hdvideodownloaderapp.videodownloader.in_ex.Speed_Ex_Res;
import com.free.hdvideodownloaderapp.videodownloader.in_ex.Speed_Ex_Serc;
import com.sdk.ads.Comman;
import com.sdk.ads.LocalSave.Common;
import com.sdk.ads.ResModel.MainResModel;
import com.sdk.ads.ads.AllNativeAds;
import com.sdk.ads.ads.IntertitialAdsEvent;
import com.sdk.ads.client.APIClient;
import com.sdk.ads.client.APIInterface;
import com.sdk.ads.setting.SettingClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class SplashBaseActivity extends AppCompatActivity {

    APIInterface apiInterface;
    String iType = "";
    public boolean excludeChecker = false;
    public boolean includeChecker = false;

    public void getSetting() {
        SettingInterface settingInterface = SettingClient.getData().create(SettingInterface.class);
        settingInterface.getSetting().enqueue(new Callback<Setting>() {
            @Override
            public void onResponse(Call<Setting> call, Response<Setting> response) {
                if (response.isSuccessful()) {
                    iType = response.body().getSettings().getLocType();
                    if (iType.equalsIgnoreCase("include") || iType.equalsIgnoreCase("exclude")) {
                        verifyUser(response.body().getSettings().getLocArray());
                    } else {
                        getData(1);
                    }
                } else {
                    getData(1);
                }
            }

            @Override
            public void onFailure(Call<Setting> call, Throwable t) {

                getData(1);
            }
        });
    }

    private void verifyUser(List<LocArray> locArray) {
        Speed_Ex_Serc apiService = getGeoApiService();
        apiService.getLocation().enqueue(new Callback<Speed_Ex_Res>() {
            @Override
            public void onResponse(Call<Speed_Ex_Res> call, Response<Speed_Ex_Res> response) {

                String uCountryCode = response.body().getCountryCode();
                String uStateCode = response.body().getRegion();
                String uCityName = response.body().getCity();


                switch (iType) {
                    case "include":
                        for (int k = 0; k < locArray.size(); k++) {
                            if (uCountryCode.equalsIgnoreCase(locArray.get(k).getCountryCode()) ||
                                    uStateCode.equalsIgnoreCase(locArray.get(k).getStateCode()) ||
                                    uCityName.equalsIgnoreCase(locArray.get(k).getCityName())) {
                                k = 999;
                                includeChecker = true;
                            }
                        }

                        if (includeChecker) {
                            getData(2);

                        } else {
                            getData(1);

                        }
                        break;

                    case "exclude":
                        for (int j = 0; j < locArray.size(); j++) {
                            if (uCountryCode.equalsIgnoreCase(locArray.get(j).getCountryCode()) ||
                                    uStateCode.equalsIgnoreCase(locArray.get(j).getStateCode()) ||
                                    uCityName.equalsIgnoreCase(locArray.get(j).getCityName())) {
                                j = 999;
                                excludeChecker = true;
                            }
                        }

                        if (excludeChecker) {
                            getData(2);

                        } else {
                            getData(1);

                        }
                        break;

                    default:

                        getData(1);
                        break;
                }
            }

            @Override
            public void onFailure(Call<Speed_Ex_Res> call, Throwable t) {

                getData(1);
            }
        });
    }

    public void getData(int index) {

        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<MainResModel> call;

        switch (index) {
            case 1:
                call = apiInterface.doGetListData();
                break;
            case 2:
                call = apiInterface.doGetListData2();
                break;
            default:
                call = apiInterface.doGetListData_Def();
                break;
        }


        call.enqueue(new Callback<MainResModel>() {
            @Override
            public void onResponse(Call<MainResModel> call, Response<MainResModel> response) {

                try {
                    if (response.body().getSuccess()) {
                        Comman.mainResModel = response.body();
                        setLoadAds();
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(Call<MainResModel> call, Throwable t) {
                setLoadAds();
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntertitialAdsEvent.Strcheckad = "StrOpen";

//        if (!isDevMode())
        getSetting();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public boolean isDevMode() {
        if (Integer.valueOf(android.os.Build.VERSION.SDK) == 16) {
            return android.provider.Settings.Secure.getInt(getApplicationContext().getContentResolver(),
                    android.provider.Settings.Secure.DEVELOPMENT_SETTINGS_ENABLED, 0) != 0;
        } else if (Integer.valueOf(android.os.Build.VERSION.SDK) >= 17) {
            return android.provider.Settings.Secure.getInt(getApplicationContext().getContentResolver(),
                    android.provider.Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0) != 0;
        } else return false;
    }

    public abstract void moveToNext();


    public void setLoadAds() {

        AllNativeAds.NativeAdsLoad(this);
        AllNativeAds.NativeBannerAdLoad(this);
        AllNativeAds.RectangleLoad(this);
        AllNativeAds.LargeBannerAdLoad(this);
        IntertitialAdsEvent.Strcheckad = "StrClosed";
        moveToNext();
    }


    public SharedPreferences getPrefs() {
        return getSharedPreferences(Common.SHARED_PREFS, Context.MODE_PRIVATE);
    }


    @Override
    public void onBackPressed() {

    }
}
