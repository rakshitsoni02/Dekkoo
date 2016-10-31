package com.android.dekkoo;

import android.app.Application;
import android.content.Context;

import com.android.dekkoo.injection.component.ApplicationComponent;
import com.android.dekkoo.injection.component.DaggerApplicationComponent;
import com.android.dekkoo.injection.module.ApplicationModule;

import timber.log.Timber;

public class DekkooApplication extends Application {

    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) Timber.plant(new Timber.DebugTree());

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        mApplicationComponent.inject(this);
    }

    public static DekkooApplication get(Context context) {
        return (DekkooApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
