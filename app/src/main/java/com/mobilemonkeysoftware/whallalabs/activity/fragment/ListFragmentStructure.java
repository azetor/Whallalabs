package com.mobilemonkeysoftware.whallalabs.activity.fragment;

import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.mobilemonkeysoftware.whallalabs.adapter.BaseAdapter;

/**
 * Created by AR on 11.02.2016.
 */
public interface ListFragmentStructure<AT extends BaseAdapter> {

    @IntegerRes int getSpanResId();
    @StringRes int getEmptyListTextResId();
    boolean canRefreshOnSwipe();
    void refreshOnSwipe();
    void startItemsLoad();
    @NonNull AT createEmptyAdapter();

}
