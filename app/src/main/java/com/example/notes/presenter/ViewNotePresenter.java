package com.example.notes.presenter;

import com.example.mvp.MessageType;
import com.example.mvp.MvpPresenter;
import com.example.notes.ApplicationClass;
import com.example.notes.model.NotesModel;
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
        final NotesModel notesModel = realm.where(NotesModel.class).equalTo("noteId", view.getNoteId()).findFirst();
        if (notesModel == null) {
            view.showMessage(MessageType.ERROR, "Cannot view note");
            return;
        }

        view.displayNoteTitle(notesModel.getTitle());
        view.displayNote(notesModel.getNote());
    }
}
