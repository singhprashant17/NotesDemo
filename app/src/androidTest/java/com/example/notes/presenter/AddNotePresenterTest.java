package com.example.notes.presenter;

import com.example.mvp.MessageType;
import com.example.notes.viewinterface.AddNoteView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AddNotePresenterTest {
    private AddNotePresenter presenter;
    private AddNoteView view;

    @Before
    public void setUp() {
        view = mock(AddNoteView.class);
        presenter = new AddNotePresenter();
        presenter.attach(view);
    }

    @After
    public void tearDown() {
        view = null;
        presenter = null;
    }

    @Test
    public void saveNote() {
        when(view.getNoteTitle()).thenReturn(null);
        presenter.saveNote();
        verify(view, times(1)).showMessage(eq(MessageType.ERROR), any(Integer.class));
        verify(view, times(0)).startLoading(anyInt());
        verify(view, times(0)).stopLoading();
    }

    @Test
    public void saveNote2() {
        when(view.getNote()).thenReturn(null);
        presenter.saveNote();
        verify(view, times(1)).showMessage(eq(MessageType.ERROR), any(Integer.class));
        verify(view, times(0)).startLoading(anyInt());
        verify(view, times(0)).stopLoading();
    }
}