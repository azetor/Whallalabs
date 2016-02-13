package com.mobilemonkeysoftware.whallalabs.activity.fragment;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.mobilemonkeysoftware.whallalabs.R;
import com.mobilemonkeysoftware.whallalabs.adapter.UserListAdapter;
import com.mobilemonkeysoftware.whallalabs.bus.EmptyAdapterEvent;
import com.mobilemonkeysoftware.whallalabs.bus.RefreshDataEvent;
import com.mobilemonkeysoftware.whallalabs.bus.UserRemovedEvent;
import com.mobilemonkeysoftware.whallalabs.model.GitHubUser;
import com.mobilemonkeysoftware.whallalabs.model.ui.UserItem;
import com.mobilemonkeysoftware.whallalabs.rest.response.DailymotionUsersResponse;
import com.mobilemonkeysoftware.whallalabs.rx.RxHelper;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.mobilemonkeysoftware.whallalabs.rest.Client.getDailymotionService;
import static com.mobilemonkeysoftware.whallalabs.rest.Client.getGitHubService;

/**
 * Created by AR on 11.02.2016.
 */
public class UserListFragment extends BaseListFragment<UserItem, UserListAdapter> {

    private static final String TAG = UserListFragment.class.getName();

    @Override public int getSpanResId() {
        return R.integer.user_list_span_count;
    }

    @Override public int getEmptyListTextResId() {
        return R.string.empty_list_users;
    }

    @Override public boolean canRefreshOnSwipe() {
        return true;
    }

    @Override public void refreshOnSwipe() {
        startItemsLoad();
    }

    @Override public void startItemsLoad() {

        Log.d(TAG, "startItemsLoad");
        refresh(true);
        final List<UserItem> items = new ArrayList<>();
        Observable<List<UserItem>> gitHubObservable = getGitHubService().getUsers().flatMap(new Func1<List<GitHubUser>, Observable<List<UserItem>>>() {
            @Override public Observable<List<UserItem>> call(final List<GitHubUser> gitHubUsers) {
                return RxHelper.createUsersItems(gitHubUsers, UserItem.Type.GITHUB);
            }
        });
        Observable<List<UserItem>> dailymotionObservable = getDailymotionService().getUsers().flatMap(new Func1<DailymotionUsersResponse, Observable<List<UserItem>>>() {
            @Override public Observable<List<UserItem>> call(final DailymotionUsersResponse dailymotionUsersResponse) {
                return RxHelper.createUsersItems(dailymotionUsersResponse.getList(), UserItem.Type.DAILYMOTION);
            }
        });
        Observable<List<UserItem>> userItemsObservable = Observable.merge(gitHubObservable, dailymotionObservable)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        userItemsObservable.subscribe(new Subscriber<List<UserItem>>() {
            @Override public void onCompleted() {

                Log.d(TAG, "onCompleted");
                clearItems();
                addItems(items);
                refresh(false);
            }

            @Override public void onError(Throwable e) {

                Log.e(TAG, "Users download error", e);
                updateEmptyInfo();
                refresh(false);
            }

            @Override public void onNext(List<UserItem> userItems) {

                Log.d(TAG, "onNext=" + userItems.toString());
                items.addAll(userItems);
            }
        });
    }

    @NonNull @Override public UserListAdapter createEmptyAdapter() {
        return new UserListAdapter(UserListFragment.this, new ArrayList<UserItem>());
    }

    /**
     * Handler for {@link com.mobilemonkeysoftware.whallalabs.bus.RefreshDataEvent}
     *
     * @param event Event {@link com.mobilemonkeysoftware.whallalabs.bus.RefreshDataEvent}
     */
    @Subscribe public void onEventMainThread(RefreshDataEvent event) {
        startItemsLoad();
    }

    /**
     * Handler for {@link com.mobilemonkeysoftware.whallalabs.bus.EmptyAdapterEvent}
     *
     * @param event Event {@link com.mobilemonkeysoftware.whallalabs.bus.EmptyAdapterEvent}
     */
    @Subscribe public void onEventMainThread(EmptyAdapterEvent event) {
        updateEmptyInfo();
    }

    /**
     * Handler for {@link com.mobilemonkeysoftware.whallalabs.bus.UserRemovedEvent}
     *
     * @param event Event {@link com.mobilemonkeysoftware.whallalabs.bus.UserRemovedEvent}
     */
    @Subscribe public void onEventMainThread(UserRemovedEvent event) {

        if (isAdded() && getView() != null) {
            UserItem user = event.getUser();
            String text = getString(R.string.user_activity_user_removed, user.getName(), user.getType());
            Snackbar.make(getView(), text, Snackbar.LENGTH_LONG).show();
        }
    }

}
