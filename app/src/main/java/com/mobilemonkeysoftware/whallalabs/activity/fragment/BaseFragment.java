package com.mobilemonkeysoftware.whallalabs.activity.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobilemonkeysoftware.whallalabs.activity.Structure;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;

/**
 * Created by AR on 11.02.2016.
 */
public abstract class BaseFragment extends Fragment implements Structure {

    @Nullable
    @Override public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(getLayoutResID(), container, false);
        bind(view);

        return view;
    }

    public void bind(@NonNull View view) {
        ButterKnife.bind(this, view);
    }

    @Override public void onDestroyView() {
        super.onDestroyView();

        unbind();
    }

    public void unbind() {
        ButterKnife.unbind(this);
    }

    @Override public void onResume() {

        EventBus.getDefault().register(this);
        super.onResume();
    }

    @Override public void onPause() {

        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Subscribe public void onEvent(Object event) {
    }

    public void post(Object event) {
        EventBus.getDefault().post(event);
    }

}
