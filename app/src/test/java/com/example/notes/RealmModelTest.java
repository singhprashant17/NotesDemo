package com.example.notes;

import com.example.notes.model.NotesModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.internal.RealmCore;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
@PrepareForTest({Realm.class, RealmConfiguration.class, RealmQuery.class, RealmResults.class, RealmCore.class})
public class RealmModelTest {

    @Rule
    public PowerMockRule rule = new PowerMockRule();
    Realm mockRealm;

    @Before
    public void setup() {
        mockStatic(Realm.class);
        final Realm mockRealm = mock(Realm.class);
        when(Realm.getDefaultInstance()).thenReturn(mockRealm);
        this.mockRealm = mockRealm;
    }

    @Test
    public void testRealmGetDefaultInstanceCalled() {
        assertThat(Realm.getDefaultInstance(), is(mockRealm));
    }

    @Test
    public void testAbleToMockRealmMethods() {
        when(mockRealm.isAutoRefresh()).thenReturn(true);
        assertThat(mockRealm.isAutoRefresh(), is(true));

        when(mockRealm.isAutoRefresh()).thenReturn(false);
        assertThat(mockRealm.isAutoRefresh(), is(false));
    }

    @Test
    public void testAbleToCreateARealmObject() {
        final NotesModel notesModel = new NotesModel();
        when(mockRealm.createObject(NotesModel.class)).thenReturn(notesModel);
        final NotesModel mockRealmObject = mockRealm.createObject(NotesModel.class);
        assertThat(mockRealmObject, is(notesModel));
    }
}
