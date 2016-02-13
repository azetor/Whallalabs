package com.mobilemonkeysoftware.whallalabs.activity.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobilemonkeysoftware.whallalabs.R;
import com.mobilemonkeysoftware.whallalabs.adapter.BaseAdapter;
import com.mobilemonkeysoftware.whallalabs.model.ui.ListItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by AR on 11.02.2016.
 */
public abstract class BaseListFragment<T extends Parcelable & ListItem, AT extends BaseAdapter> extends BaseFragment implements ListFragmentStructure<AT> {

    private static final String TAG = BaseListFragment.class.getName();

    public static final String EXTRA_ITEMS = "extra_items";
    public static final String EXTRA_IN_REFRESH = "extra_in_refresh";

    @Bind(R.id.recycler_view) RecyclerView recyclerView;
    @Bind(R.id.swipe_refresh_layout) SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.empty_text_info) TextView emptyView;

    @NonNull private RecyclerView.LayoutManager mLayoutManager;

    private boolean mInRefresh;

    @LayoutRes
    @Override public int getLayoutResID() {
        return R.layout.fragment_base_list;
    }

    @Nullable
    @Override public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        emptyView.setText(getEmptyListTextResId());
        swipeRefreshLayout.setEnabled(canRefreshOnSwipe());
        swipeRefreshLayout.setColorSchemeColors(getRefreshColors());
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override public void onRefresh() {

                Log.d(TAG, "onRefresh");
                refresh(true);
                refreshOnSwipe();
            }
        });

        recyclerView.setHasFixedSize(true);
        mLayoutManager = new StaggeredGridLayoutManager(getSpanCount(), StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(createEmptyAdapter());
        recyclerView.addOnScrollListener(mOnScrollListener);

        return view;
    }

    private int[] getRefreshColors() {
        return getResources().getIntArray(R.array.swipe_refresh_colors);
    }

    public void refresh(boolean refresh) {

        mInRefresh = refresh;
        swipeRefreshLayout.post(new Runnable() {

            @Override public void run() {
                swipeRefreshLayout.setRefreshing(mInRefresh);
            }
        });
    }

    public int getSpanCount() {
        return getResources().getInteger(getSpanResId());
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            if (canRefreshOnSwipe()) {
                int topRowVerticalPosition = (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                swipeRefreshLayout.setEnabled(topRowVerticalPosition >= 0);
            }
        }
    };

    @Override public void onDestroyView() {

        recyclerView.removeOnScrollListener(mOnScrollListener);
        super.onDestroyView();
    }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null) {
            mInRefresh = savedInstanceState.getBoolean(EXTRA_IN_REFRESH, false);
            refresh(mInRefresh);

            List<T> items = new ArrayList<>();
            if (savedInstanceState.containsKey(EXTRA_ITEMS)) {
                items = savedInstanceState.getParcelableArrayList(EXTRA_ITEMS);
            }
            Log.d(TAG, "Load data form savedInstanceState");
            addItems(items);
        } else {
            Log.d(TAG, "New data load");
            startItemsLoad();
        }
    }

    public void clearItems() {

        if (getAdapter() != null) {
            getAdapter().clear();
            updateEmptyInfo();
        }
    }

    public void addItems(@NonNull List<T> items) {

        if (getAdapter() != null) {
            getAdapter().addAll(items);
            updateEmptyInfo();
        }
    }

    public void updateEmptyInfo() {

        if (getAdapter() != null) {
            showEmptyListInfo(getAdapter().isEmpty());
        }
    }

    private void showEmptyListInfo(boolean show) {
        emptyView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Nullable protected AT getAdapter() {
        return recyclerView != null ? (AT) recyclerView.getAdapter() : null;
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (getAdapter() != null) {
            outState.putParcelableArrayList(EXTRA_ITEMS, (ArrayList<T>) getAdapter().getItems());
        }
        outState.putBoolean(EXTRA_IN_REFRESH, mInRefresh);
    }

}
