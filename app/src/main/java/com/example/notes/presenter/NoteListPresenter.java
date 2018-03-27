package com.example.notes.presenter;

import com.example.mvp.MessageType;
import com.example.mvp.MvpPresenter;
import com.example.notes.ApplicationClass;
import com.example.notes.R;
import com.example.notes.model.NotesModel;
import com.example.notes.util.Utility;
import com.example.notes.viewinterface.NoteListView;
import com.example.notes.webservice.ApiCallMethods;
import com.example.notes.webservice.NoInternetException;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import rx.SingleSubscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

public class NoteListPresenter extends MvpPresenter<NoteListView> {
    @Inject
    ApiCallMethods service;
    private Subscription subscription;
    private NoteListView view;

    public NoteListPresenter() {
        ApplicationClass.getNetComponent().inject(this);
        subscription = Subscriptions.empty();
    }

    @Override
    public void attach(NoteListView view) {
        this.view = view;
    }

    @Override
    public void detach() {
        view.stopLoading();
        subscription.unsubscribe();
    }

    public void getNotes() {
        subscription = service.getAllNotes()
                .doOnSubscribe(() -> {
                    // chk if network available
                    if (!Utility.checkNetwork()) {
                        throw new NoInternetException();
                    }
                })
                .doOnSuccess(response -> {
                    final Realm realm = Realm.getDefaultInstance();
                    try {
                        realm.copyToRealmOrUpdate(response);
                    } finally {
                        realm.close();
                    }
                })
                .onErrorReturn(throwable -> {
                    final Realm realm = Realm.getDefaultInstance();
                    try {
                        return realm.copyFromRealm(realm.where(NotesModel.class).findAll());
                    } finally {
                        realm.close();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> view.startLoading(R.string.loading))
                .doOnError(throwable -> {
                    throwable.printStackTrace();
                    if (throwable instanceof NoInternetException) {
                        view.showMessage(MessageType.NO_INTERNET, (R.string.no_connection));
                    }
                })
                .subscribe(new SingleSubscriber<List<NotesModel>>() {
                    @Override
                    public void onSuccess(List<NotesModel> notes) {
                        view.stopLoading();
                        view.displayNotes(notes);
                    }

                    @Override
                    public void onError(Throwable error) {
                        view.stopLoading();
                    }
                });
    }
}
