package com.example.notes.di;

import com.example.notes.presenter.ViewNotePresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {RealmModule.class})
public interface RealmComponent {
    void inject(ViewNotePresenter presenter);
}
