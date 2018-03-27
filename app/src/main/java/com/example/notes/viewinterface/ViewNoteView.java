package com.example.notes.viewinterface;

import com.example.mvp.MvpView;

public interface ViewNoteView extends MvpView {
    long getNoteId();

    void displayNoteTitle(String title);

    void displayNote(String note);
}
