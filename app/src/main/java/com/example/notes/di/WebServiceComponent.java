package com.example.notes.di;

import com.example.notes.presenter.AddNotePresenter;
import com.example.notes.presenter.NoteListPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {WebServiceModule.class})
public interface WebServiceComponent {
    void inject(NoteListPresenter presenter);

    void inject(AddNotePresenter presenter);
}
