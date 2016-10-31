package com.android.dekkoo.injection.component;

import android.app.Application;
import android.content.Context;

import com.android.dekkoo.DekkooApplication;
import com.android.dekkoo.data.DataManager;
import com.android.dekkoo.data.local.PreferencesHelper;
import com.android.dekkoo.data.remote.DekkooWebService;
import com.android.dekkoo.injection.ApplicationContext;
import com.android.dekkoo.injection.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(DekkooApplication androidBoilerplateApplication);

    @ApplicationContext
    Context context();
    Application application();
    DekkooWebService androidBoilerplateService();
    PreferencesHelper preferencesHelper();
    DataManager dataManager();

}
