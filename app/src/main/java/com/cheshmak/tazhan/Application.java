package com.cheshmak.tazhan;

import com.cheshmak.tazhan.Model.County;
import com.cheshmak.tazhan.Model.Province;
import com.cheshmak.tazhan.Utils.FontsOverride;
import com.cheshmak.tazhan.Utils.Utils;

import io.realm.annotations.RealmModule;

public final class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FontsOverride.setDefaultFont(this, "DEFAULT", "B Koodak.ttf");
        FontsOverride.setDefaultFont(this, "MONOSPACE", "B Koodak.ttf");
        FontsOverride.setDefaultFont(this, "SERIF", "B Koodak.ttf");
        FontsOverride.setDefaultFont(this, "SANS_SERIF", "B Koodak.ttf");

        Utils.prepareResources(this);
        Utils.prepareRealm(this);

    }


}
