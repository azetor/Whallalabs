package com.mobilemonkeysoftware.whallalabs.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobilemonkeysoftware.whallalabs.R;
import com.mobilemonkeysoftware.whallalabs.activity.UserActivity;
import com.mobilemonkeysoftware.whallalabs.bus.EmptyAdapterEvent;
import com.mobilemonkeysoftware.whallalabs.bus.UserRemovedEvent;
import com.mobilemonkeysoftware.whallalabs.image.ImageHelper;
import com.mobilemonkeysoftware.whallalabs.model.ui.UserItem;
import com.mobilemonkeysoftware.whallalabs.utilities.ColorUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * Created by AR on 11.02.2016.
 */
public class UserListAdapter extends BaseAdapter<UserListAdapter.ViewHolder, UserItem> {

    private static final String TAG = UserListAdapter.class.getName();

    private Fragment mFragment;

    public UserListAdapter(@NonNull Fragment fragment, @NonNull List<UserItem> items) {
        super(fragment.getContext(), items);
        mFragment = fragment;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflate(R.layout.adapter_user_list_item, parent));
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {

        UserItem item = getItem(position);
        holder.view.setTag(item);
        holder.view.setCardBackgroundColor(getColor(item.getType()));
        holder.name.setText(item.getName());
        loadUrl(holder.image, item.getUrl());
    }

    private int getColor(@NonNull UserItem.Type type) {
        return ColorUtil.getColor(getContext(), type.getColorResId());
    }

    private void loadUrl(@NonNull ImageView view, @NonNull String url) {
        ImageHelper.loadUrl(view, url);
    }

    public class ViewHolder extends BaseViewHolder {

        @Bind(R.id.root_view) CardView view;
        @Bind(R.id.image) ImageView image;
        @Bind(R.id.name) TextView name;

        public ViewHolder(View view) {
            super(view);
        }

        @OnClick(R.id.root_view) public void onClick(View v) {

            UserItem item = (UserItem) v.getTag();
            if (item != null) {
                mFragment.startActivity(UserActivity.buildIntent(getContext(), item));
            }
        }

        @OnLongClick(R.id.root_view) public boolean onLongClick(View v) {

            boolean removed = false;
            UserItem item = (UserItem) v.getTag();
            if (item != null) {
                removed = remove(item);
                Log.d(TAG, "Item remove status=" + removed + ", value=" + item.toString());

                if (removed) {
                    post(new UserRemovedEvent(item));
                }
                if (isEmpty()) {
                    post(new EmptyAdapterEvent());
                }
            }
            return removed;
        }
    }

}
