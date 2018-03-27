package com.example.notes.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.mvp.MessageType;
import com.example.mvp.MvpActivity;
import com.example.mvp.PresenterFactory;
import com.example.notes.R;
import com.example.notes.presenter.AddNotePresenter;
import com.example.notes.viewinterface.AddNoteView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddNewNoteActivity extends MvpActivity<AddNotePresenter, AddNoteView> implements AddNoteView {
    @BindView(R.id.edtTitle)
    EditText edtTitle;
    @BindView(R.id.edtNote)
    EditText edtNote;
    @BindView(R.id.parentLayout)
    ViewGroup parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note_detail);
        ButterKnife.bind(this);
    }

    @Override
    protected void onPresenterCreatedOrRestored(AddNotePresenter presenter) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveNote:
                saveNote();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveNote() {
        getPresenter().saveNote();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_note, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public PresenterFactory<AddNotePresenter> getPresenterFactory() {
        return AddNotePresenter::new;
    }

    @Override
    public Context getAndroidContext() {
        return this;
    }

    @Override
    public void showMessage(MessageType messageType, String msg) {
        switch (messageType) {
            case SUCCESS:
                finish();
                break;
            default:
                Snackbar.make(parentLayout, msg, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void showMessage(MessageType messageType, int msg) {
        showMessage(messageType, getString(msg));
    }

    @Override
    public String getNoteTitle() {
        return edtTitle.getText().toString().trim();
    }

    @Override
    public String getNote() {
        return edtNote.getText().toString().trim();
    }

    @Override
    public void startLoading(int message) {

    }

    @Override
    public void stopLoading() {

    }
}
