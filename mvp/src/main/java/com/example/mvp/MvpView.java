package com.example.mvp;

import android.content.Context;
import android.support.annotation.StringRes;

/**
 * The main view. All view implementation must implement this interface.
 */
public interface MvpView {
    /**
     * @return the current context (ex. Activity, Fragment)
     */
    Context getAndroidContext();

    /**
     * This method is used to indicate any error/success messages that comes during the process
     *
     * @param msg Error string message
     */
    void showMessage(MessageType messageType, String msg);

    void showMessage(MessageType messageType, @StringRes int msg);
}
