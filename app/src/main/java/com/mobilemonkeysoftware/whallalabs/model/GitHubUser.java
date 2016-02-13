package com.mobilemonkeysoftware.whallalabs.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by AR on 12.02.2016.
 */
@Data
public class GitHubUser implements User {

    @SerializedName("login")
    private String name;
    @SerializedName("avatar_url")
    private String url;

    @NonNull
    @Override public String getUserName() {
        return getName();
    }

    @NonNull
    @Override public String getUserAvatarUrl() {
        return getUrl();
    }

}
