package com.android.dekkoo.data.remote;

import android.content.Context;

import com.android.dekkoo.BuildConfig;
import com.android.dekkoo.data.model.Base;
import com.android.dekkoo.data.model.Character;

import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface DekkooWebService {

    String ENDPOINT = "https://api.zype.com/";
    //String ENDPOINT = "http://swapi.co/api/";
    @GET("people/{personId}")
    Observable<Character> getCharacter(@Path("personId") int id);

    @GET("categories")
    Observable<Base> getCategories(@Query("app_key") String appKey);



    @GET("videos")
    Observable<Base> getVideos(@QueryMap Map<String, String> options);
    /********
     * Factory class that sets up a new boilerplate service
     *******/
    class Factory {

        public static DekkooWebService makeAndroidBoilerplateService(Context context) {
            OkHttpClient okHttpClient = new OkHttpClient();
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY
                    : HttpLoggingInterceptor.Level.NONE);
//            okHttpClient.interceptors().add(logging);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(DekkooWebService.ENDPOINT)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(DekkooWebService.class);
        }

    }
}
