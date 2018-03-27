package com.example.notes;

import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.example.notes.di.DaggerRealmComponent;
import com.example.notes.di.DaggerWebServiceComponent;
import com.example.notes.di.RealmComponent;
import com.example.notes.di.RealmModule;
import com.example.notes.di.WebServiceComponent;
import com.example.notes.di.WebServiceModule;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;

public class ApplicationClass extends MultiDexApplication implements RealmMigration {
    private static final long CURRENT_DB_SCHEMA_VERSION = 1;
    private static ApplicationClass instance;
    private static WebServiceComponent webServiceComponent;
    private static RealmComponent realmComponent;

    public static synchronized ApplicationClass getInstance() {
        return instance;
    }

    public static synchronized WebServiceComponent getNetComponent() {
        return webServiceComponent;
    }

    public static synchronized RealmComponent getRealmComponent() {
        return realmComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        instance = this;
        initRealmDB();
        resolveDependencies();
    }

    private void resolveDependencies() {
        webServiceComponent = DaggerWebServiceComponent.builder()
                .webServiceModule(new WebServiceModule(BuildConfig.BASE_URL))
                .build();
        realmComponent = DaggerRealmComponent.builder()
                .realmModule(new RealmModule())
                .build();
    }

    private void initRealmDB() {
        Realm.init(this);
        Realm.setDefaultConfiguration(new RealmConfiguration.Builder()
                .schemaVersion(CURRENT_DB_SCHEMA_VERSION)
                .migration(this)
                .build());
    }

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        throw new RuntimeException("Migration not handled....");
    }
}
