package com.mobilemonkeysoftware.whallalabs.rx;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mobilemonkeysoftware.whallalabs.model.User;
import com.mobilemonkeysoftware.whallalabs.model.ui.UserItem;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by AR on 13.02.2016.
 */
public final class RxHelper {

    private RxHelper() {
    }

    public static Observable<List<UserItem>> createUsersItems(@Nullable final List<? extends User> users, @NonNull final UserItem.Type type) {
        return Observable.create(new Observable.OnSubscribe<List<UserItem>>() {
            @Override public void call(Subscriber<? super List<UserItem>> subscriber) {

                try {
                    List<UserItem> results = new ArrayList<>();
                    for (int i = 0, size = users != null ? users.size() : 0; i < size; i++) {
                        User user = users.get(i);
                        results.add(new UserItem(user.getUserName(), user.getUserAvatarUrl(), type));
                    }
                    subscriber.onNext(results);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

}
