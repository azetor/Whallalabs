package com.mobilemonkeysoftware.whallalabs.model.ui;

import android.os.Parcel;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;

import com.mobilemonkeysoftware.whallalabs.BuildConfig;
import com.mobilemonkeysoftware.whallalabs.R;

import lombok.Data;

/**
 * Created by AR on 11.02.2016.
 */
@Data
public class UserItem implements ListItem {

    private String name;
    private String url;
    private Type type;

    public UserItem(@NonNull String name, @NonNull String url, @NonNull Type type) {
        this.name = name;
        this.url = url;
        this.type = type;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.url);
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
    }

    protected UserItem(Parcel in) {
        this.name = in.readString();
        this.url = in.readString();
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : Type.values()[tmpType];
    }

    public static final Creator<UserItem> CREATOR = new Creator<UserItem>() {
        public UserItem createFromParcel(Parcel source) {
            return new UserItem(source);
        }

        public UserItem[] newArray(int size) {
            return new UserItem[size];
        }
    };

    public enum Type {

        GITHUB(R.color.github, BuildConfig.GITHUB_LOGO_URL),
        DAILYMOTION(R.color.dailymotion, BuildConfig.DAILYMOTION_LOGO_URL);

        @ColorRes final int colorResId;
        @NonNull final String logoUrl;

        Type(@ColorRes int colorResId, @NonNull String logoUrl) {
            this.colorResId = colorResId;
            this.logoUrl = logoUrl;
        }

        @ColorRes public int getColorResId() {
            return colorResId;
        }

        @NonNull public String getLogoUrl() {
            return logoUrl;
        }
    }

}
