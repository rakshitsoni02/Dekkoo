package com.android.dekkoo.data;

import com.android.dekkoo.data.local.PreferencesHelper;
import com.android.dekkoo.data.model.Base;
import com.android.dekkoo.data.model.Character;
import com.android.dekkoo.data.remote.DekkooWebService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

public class DataManager {
    private final DekkooWebService mDekkooWebService;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public DataManager(DekkooWebService watchTowerService,
                       PreferencesHelper preferencesHelper) {
        mDekkooWebService = watchTowerService;
        mPreferencesHelper = preferencesHelper;
    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

    public Observable<List<Character>> getCharacters(int[] ids) {
        List<Integer> characterIds = new ArrayList<>(ids.length);
        for (int id : ids) {
            characterIds.add(id);
        }
        return Observable.from(characterIds).concatMap(new Func1<Integer, Observable<Character>>() {
            @Override
            public Observable<Character> call(Integer integer) {
                return mDekkooWebService.getCharacter(integer);
            }
        }).toList();
    }

    public Observable<Base> getCategories() {
        return mDekkooWebService.getCategories("fB9gMa3z-SL1liR2AKs83YREWcvOCXZ0jogDfeYv9Qzdb9ZBtAb5GTOPSVVbLZl-");
    }

    public Observable<Base> getVideos(String videoForValue) {
        HashMap<String, String> options = new HashMap<>();
        options.put("app_key", "fB9gMa3z-SL1liR2AKs83YREWcvOCXZ0jogDfeYv9Qzdb9ZBtAb5GTOPSVVbLZl-");
        options.put("category[" + getPreferencesHelper().getTittle() + "]", videoForValue);
        return mDekkooWebService.getVideos(options);
    }
}
