package com.mobilemonkeysoftware.whallalabs.utilities;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

/**
 * Created by AR on 13.02.2016.
 */
public final class ColorUtil {

    private ColorUtil() {
    }

    @ColorInt public static int getColor(@NonNull Context context, @ColorRes int colorResId) {
        return ContextCompat.getColor(context, colorResId);
    }

}
