package com.aayush.biowear

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.aayush.biowear.util.NoLogTree
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import timber.log.Timber

class BioWearApp: Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        else {
            Timber.plant(NoLogTree())
        }

        ViewPump.init(ViewPump.builder()
            .addInterceptor(CalligraphyInterceptor(
                CalligraphyConfig.Builder()
                    .setDefaultFontPath("fonts/maven-pro/MavenPro-Regular.ttf")
                    .setFontAttrId(R.attr.fontPath)
                    .build()))
            .build())
    }
}