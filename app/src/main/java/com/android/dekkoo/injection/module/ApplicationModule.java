package com.android.dekkoo.injection.module;

import android.app.Application;
import android.content.Context;

import com.android.dekkoo.data.remote.DekkooWebService;
import com.android.dekkoo.injection.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Provide application-level dependencies. Mainly singleton object that can be injected from
 * anywhere in the app.
 */
@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    DekkooWebService provideAndroidBoilerplateService() {
        return DekkooWebService.Factory.makeAndroidBoilerplateService(mApplication);
    }

}