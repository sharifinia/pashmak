package com.cheshmak.tazhan.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.cheshmak.tazhan.Model.County;
import com.cheshmak.tazhan.Model.Province;
import com.cheshmak.tazhan.cheshmak.R;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by mmr on 01/03/2018.
 */

public class Utils {
    private static Drawable status_background_service;
    private static Drawable status_background_using;
    private static Drawable status_background_auction;
    private static Drawable status_background_ready;

    private static RealmResults<County> counties;
    private static RealmResults<Province> provinces;

    public static RealmResults<County> getCounties() {
        return counties;
    }

    public static RealmResults<Province> getProvinces() {
        return provinces;
    }


    public static void prepareResources(Context context) {
        status_background_service = context.getResources().getDrawable(R.drawable.status_service_background);
        status_background_using = context.getResources().getDrawable(R.drawable.status_using_background);
        status_background_auction = context.getResources().getDrawable(R.drawable.status_auction_background);
        status_background_ready = context.getResources().getDrawable(R.drawable.status_ready_background);
    }


    public static void changeAppbarFont(Toolbar toolbar, TabLayout tabLayout, Context ctx) {
        Typeface myCustomFont = Typeface.createFromAsset(ctx.getAssets(), "fonts/B Koodak.ttf");

        for (int i = 0; i < toolbar.getChildCount(); i++) {
            View view = toolbar.getChildAt(i);

            if (view instanceof TextView) {
                TextView textView = (TextView) view;

                textView.setTypeface(myCustomFont);
            }
        }

        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(myCustomFont);
                }
            }
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public static void mToast(Context ctx, String message, int duration) {
        Toast toast = Toast.makeText(ctx, message, duration);
        View view = toast.getView();

        view.setBackgroundResource(R.drawable.row_billboard_background);
        TextView text = toast.getView().findViewById(android.R.id.message);
        text.setShadowLayer(5, 5, 1, Color.TRANSPARENT);
//        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        text.setTextColor(ContextCompat.getColor(ctx, R.color.colorPrimaryDark));
        text.setTextSize(ctx.getResources().getDimension(R.dimen.textSize_toast));
        toast.show();
    }

    public static void setupStatus(TextView tvStatus, String status) {

        Drawable drawable;
        String splitStatus = status.split("-")[0];

        switch (splitStatus) {
            case "r":
                tvStatus.setText("آماده");
                drawable = status_background_ready;
                break;
            case "s":
                tvStatus.setText("در سرویس");
                drawable = status_background_service;
                break;
            case "a":
                tvStatus.setText("در مزایده");
                drawable = status_background_auction;
                break;
            case "u":
                tvStatus.setText("رزرو شده");
                drawable = status_background_using;
                break;
            default:
                tvStatus.setText("نامشخص");
                drawable = status_background_service;
        }

        if (Build.VERSION.SDK_INT >= 16) {
            tvStatus.setBackground(drawable);
        } else {
            tvStatus.setBackgroundDrawable(drawable);
        }
    }

    public static void prepareRealm(Context context) {
        Realm.init(context);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .readOnly()
                .assetFile("province_county.realm")
                .build();

        Realm.setDefaultConfiguration(config);

        Realm realm = Realm.getDefaultInstance();
        try {

            counties = realm.where(County.class).findAll();
            provinces = realm.where(Province.class).findAll();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
          
        }
//        RealmConfiguration config = new RealmConfiguration.Builder()
//                .name("province_county.realm")
////                .schemaVersion(1)
////                .readOnly()
////                .assetFile("province_county.realm")
//                .deleteRealmIfMigrationNeeded()
////                .modules(new MyModule())
//                .build();


//        Realm.deleteRealm(config);


    }

//    @RealmModule(classes = {Province.class, County.class})
//    public class MyModule {
//    }
}
