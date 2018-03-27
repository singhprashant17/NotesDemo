package com.example.mvp;

/**
 * Creates a Presenter object on demand.
 *
 * @param <P> presenter type
 */
public interface PresenterFactory<P extends MvpPresenter> {
    P create();
}