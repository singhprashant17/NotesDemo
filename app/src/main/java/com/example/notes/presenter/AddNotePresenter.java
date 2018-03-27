package com.example.notes.presenter;

import com.example.mvp.MessageType;
import com.example.mvp.MvpPresenter;
import com.example.notes.ApplicationClass;
import com.example.notes.R;
import com.example.notes.model.NotesModel;
import com.example.notes.util.Utility;
import com.example.notes.viewinterface.AddNoteView;
import com.example.notes.webservice.ApiCallMethods;
import com.example.notes.webservice.NoInternetException;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.inject.Inject;

import io.realm.Realm;
import rx.SingleSubscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

public class AddNotePresenter extends MvpPresenter<AddNoteView> {
    @Inject
    ApiCallMethods service;
    private Subscription subscription;
    private AddNoteView view;
    private Realm realm;

    public AddNotePresenter() {
        ApplicationClass.getNetComponent().inject(this);
        subscription = Subscriptions.empty();
    }

    @Override
    public void attach(AddNoteView view) {
        this.view = view;
        if (realm == null || realm.isClosed()) {
            realm = Realm.getDefaultInstance();
        }
    }

    @Override
    public void detach() {
        realm.close();
        subscription.unsubscribe();
    }

    public void saveNote() {

        final String noteTitle = view.getNoteTitle();
        if (Utility.isEmpty(noteTitle)) {
            view.showMessage(MessageType.ERROR, (R.string.please_enter_a_title_to_note));
            return;
        }
        final String note = view.getNote();
        if (Utility.isEmpty(note)) {
            view.showMessage(MessageType.ERROR, (R.string.please_enter_some_note_text_to_save));
            return;
        }

        subscription = service.saveNote()
                .doOnSubscribe(() -> {
                    // chk if network available
                    if (!Utility.checkNetwork()) {
                        throw new NoInternetException();
                    }
                })
                .onErrorReturn(throwable -> new JsonObject())
                .doOnSuccess(jsonElement -> {
                    // write to db
                    final Realm realm = Realm.getDefaultInstance();
                    try {
                        realm.executeTransaction(realm1 -> {
                            final NotesModel notesModel = realm1.createObject(NotesModel.class, System.currentTimeMillis());
                            notesModel.setTitle(noteTitle);
                            notesModel.setNote(note);
                        });
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
                }).subscribe(new SingleSubscriber<JsonElement>() {
                    @Override
                    public void onSuccess(JsonElement jsonElement) {
                        view.stopLoading();
                        view.showMessage(MessageType.SUCCESS, (R.string.note_successfully_saved));
                    }

                    @Override
                    public void onError(Throwable error) {
                        view.stopLoading();
                        view.showMessage(MessageType.ERROR, (R.string.note_cannot_be_saved));
                    }
                });
    }
}
