package com.example.notes.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

@Module
public class RealmModule {
    Realm realm;

    public RealmModule() {
        realm = Realm.getDefaultInstance();
    }

    @Provides
    @Singleton
    Realm provideRealm() {
        return realm;
    }
}
