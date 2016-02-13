package com.mobilemonkeysoftware.whallalabs.rest;

import android.support.annotation.NonNull;

import com.mobilemonkeysoftware.whallalabs.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by AR on 12.02.2016.
 */
public final class Client {

    private Client() {
    }

    private enum ApiClient {

        INSTANCE;

        GitHubService mGitHubService;
        DailymotionService mDailymotionService;

        ApiClient() {
            init();
        }

        void init() {

            mGitHubService = getRetrofitForUrl(BuildConfig.GITHUB_API_HOST).create(GitHubService.class);
            mDailymotionService = getRetrofitForUrl(BuildConfig.DAILYMOTION_API_HOST).create(DailymotionService.class);
        }

        @NonNull private Retrofit getRetrofitForUrl(@NonNull String url) {
            return new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
    }

    public static GitHubService getGitHubService() {
        return ApiClient.INSTANCE.mGitHubService;
    }

    public static DailymotionService getDailymotionService() {
        return ApiClient.INSTANCE.mDailymotionService;
    }

}
