package com.mobilemonkeysoftware.whallalabs.bus;

import android.support.annotation.NonNull;

import com.mobilemonkeysoftware.whallalabs.model.ui.UserItem;

import lombok.Data;

/**
 * Created by AR on 13.02.2016.
 */
@Data
public final class UserRemovedEvent {

    private UserItem user;

    public UserRemovedEvent(@NonNull UserItem user) {
        this.user = user;
    }

}
