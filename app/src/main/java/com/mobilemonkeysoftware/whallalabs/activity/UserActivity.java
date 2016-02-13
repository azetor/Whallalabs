package com.mobilemonkeysoftware.whallalabs.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.widget.ImageView;

import com.mobilemonkeysoftware.whallalabs.R;
import com.mobilemonkeysoftware.whallalabs.image.ImageHelper;
import com.mobilemonkeysoftware.whallalabs.model.ui.UserItem;

import butterknife.Bind;

/**
 * Created by AR on 11.02.2016.
 */
public class UserActivity extends BaseActivity {

    public static final String EXTRA_USER = "extra_user";

    @Bind(R.id.image) ImageView image;

    @NonNull public static Intent buildIntent(@NonNull Context context, @NonNull UserItem item) {
        return new Intent(context, UserActivity.class).putExtra(EXTRA_USER, item);
    }

    @LayoutRes
    @Override public int getLayoutResID() {
        return R.layout.activity_user;
    }

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        UserItem user = getIntent().getParcelableExtra(EXTRA_USER);
        if (user != null) {
            setTitle(getString(R.string.user_activity_name, user.getName(), user.getType()));
            ImageHelper.loadUrl(image, user.getUrl());
        }
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
