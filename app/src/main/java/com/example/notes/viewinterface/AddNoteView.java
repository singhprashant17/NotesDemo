package com.example.notes.viewinterface;

import com.example.mvp.MvpLoadingView;

public interface AddNoteView extends MvpLoadingView {
    String getNoteTitle();

    String getNote();
}
