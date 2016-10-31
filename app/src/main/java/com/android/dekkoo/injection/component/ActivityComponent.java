package com.android.dekkoo.injection.component;

import android.app.LauncherActivity;

import com.android.dekkoo.injection.PerActivity;
import com.android.dekkoo.injection.module.ActivityModule;
import com.android.dekkoo.ui.activity.MainActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(LauncherActivity launcherActivity);
    void inject(MainActivity mainActivity);

}

