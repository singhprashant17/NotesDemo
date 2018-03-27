package com.example.notes.presenter;

import com.example.mvp.MessageType;
import com.example.mvp.MvpPresenter;
import com.example.notes.ApplicationClass;
import com.example.notes.R;
import com.example.notes.model.NotesModel;
import com.example.notes.util.Constants;
import com.example.notes.viewinterface.ViewNoteView;

import javax.inject.Inject;

import io.realm.Realm;

public class ViewNotePresenter extends MvpPresenter<ViewNoteView> {
    @Inject
    Realm realm;
    private ViewNoteView view;

    public ViewNotePresenter() {
        ApplicationClass.getRealmComponent().inject(this);
    }

    @Override
    public void attach(ViewNoteView view) {
        this.view = view;
    }

    @Override
    public void detach() {

    }

    public void getNoteDetails() {
        final NotesModel notesModel = realm.where(NotesModel.class).equalTo(Constants.DatabaseKeys.NOTE_ID, view.getNoteId()).findFirst();
        if (notesModel == null) {
            view.showMessage(MessageType.ERROR, R.string.error_opening_note);
            return;
        }

        view.displayNoteTitle(notesModel.getTitle());
        view.displayNote(notesModel.getNote());
    }
}
