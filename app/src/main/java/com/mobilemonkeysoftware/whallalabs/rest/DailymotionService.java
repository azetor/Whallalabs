package com.mobilemonkeysoftware.whallalabs.rest;

import com.mobilemonkeysoftware.whallalabs.rest.response.DailymotionUsersResponse;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by AR on 12.02.2016.
 */
public interface DailymotionService {

    @GET("users?fields=avatar_360_url,username") Observable<DailymotionUsersResponse> getUsers();

}
