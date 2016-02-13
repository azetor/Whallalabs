package com.mobilemonkeysoftware.whallalabs.rest;

import com.mobilemonkeysoftware.whallalabs.model.GitHubUser;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by AR on 12.02.2016.
 */
public interface GitHubService {

    @GET("users") Observable<List<GitHubUser>> getUsers();

}
