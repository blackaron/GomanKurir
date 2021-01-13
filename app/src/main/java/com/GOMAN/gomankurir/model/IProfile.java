package com.GOMAN.gomankurir.model;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import androidx.annotation.DrawableRes;

import com.mikepenz.fastadapter.IIdentifyable;
import com.mikepenz.iconics.typeface.IIcon;
import com.mikepenz.materialdrawer.holder.ImageHolder;
import com.mikepenz.materialdrawer.holder.StringHolder;

public interface IProfile<T> extends IIdentifyable<T> {
    T withName(CharSequence name);

    StringHolder getName();

    T withEmail(String email);

    StringHolder getEmail();

    T withIcon(Drawable icon);

    T withIcon(Bitmap bitmap);

    T withIcon(@DrawableRes int iconRes);

    T withIcon(String url);

    T withIcon(Uri uri);

    T withIcon(IIcon icon);

    ImageHolder getIcon();

    T withSelectable(boolean selectable);

    boolean isSelectable();
}
