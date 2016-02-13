package com.mobilemonkeysoftware.whallalabs.rest.response;

import com.google.gson.annotations.SerializedName;
import com.mobilemonkeysoftware.whallalabs.model.DailymotionUser;

import java.util.List;

import lombok.Data;

/**
 * Created by AR on 12.02.2016.
 */
@Data
public class DailymotionUsersResponse {

    @SerializedName("list")
    private List<DailymotionUser> list;

}
