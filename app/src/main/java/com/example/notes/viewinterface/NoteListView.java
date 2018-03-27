package com.example.notes.viewinterface;

import com.example.mvp.MvpLoadingView;
import com.example.notes.model.NotesModel;

import java.util.List;

public interface NoteListView extends MvpLoadingView {
    void displayNotes(List<NotesModel> notes);
}
