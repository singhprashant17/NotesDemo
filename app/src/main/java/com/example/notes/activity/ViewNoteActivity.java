package com.example.notes.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mvp.MessageType;
import com.example.mvp.MvpActivity;
import com.example.mvp.PresenterFactory;
import com.example.notes.R;
import com.example.notes.presenter.ViewNotePresenter;
import com.example.notes.viewinterface.ViewNoteView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewNoteActivity extends MvpActivity<ViewNotePresenter, ViewNoteView> implements ViewNoteView {
    @BindView(R.id.parentLayout)
    ViewGroup parentLayout;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvNote)
    TextView tvNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);
        ButterKnife.bind(this);
    }

    @Override
    protected void onPresenterCreatedOrRestored(ViewNotePresenter presenter) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        getPresenter().getNoteDetails();
    }

    @Override
    public PresenterFactory<ViewNotePresenter> getPresenterFactory() {
        return ViewNotePresenter::new;
    }

    @Override
    public Context getAndroidContext() {
        return this;
    }

    @Override
    public void showMessage(MessageType messageType, String msg) {
        Snackbar.make(parentLayout, msg, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showMessage(MessageType messageType, int msg) {
        showMessage(messageType, getString(msg));
    }

    @Override
    public long getNoteId() {
        return getIntent().getLongExtra("id", -1);
    }

    @Override
    public void displayNoteTitle(String title) {
        tvTitle.setText(title);
    }

    @Override
    public void displayNote(String note) {
        tvNote.setText(note);
    }
}
