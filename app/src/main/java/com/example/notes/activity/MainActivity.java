package com.example.notes.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.mvp.MessageType;
import com.example.mvp.MvpActivity;
import com.example.mvp.PresenterFactory;
import com.example.notes.R;
import com.example.notes.adapter.NotesListAdapter;
import com.example.notes.adapter.RecyclerViewItemClick;
import com.example.notes.model.NotesModel;
import com.example.notes.presenter.NoteListPresenter;
import com.example.notes.util.Constants;
import com.example.notes.viewinterface.NoteListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends MvpActivity<NoteListPresenter, NoteListView> implements NoteListView, RecyclerViewItemClick {
    @BindView(R.id.swipeRefreshView)
    SwipeRefreshLayout swipeRefreshView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private NotesListAdapter notesListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshView.setOnRefreshListener(this::getSavedNotes);
    }

    private void getSavedNotes() {
        getPresenter().getNotes();
    }

    @Override
    protected void onPresenterCreatedOrRestored(NoteListPresenter presenter) {

    }

    @Override
    public PresenterFactory<NoteListPresenter> getPresenterFactory() {
        return NoteListPresenter::new;
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSavedNotes();
    }

    @Override
    public Context getAndroidContext() {
        return this;
    }

    @Override
    public void showMessage(MessageType messageType, String msg) {
        switch (messageType) {
            case ERROR:
                Snackbar.make(swipeRefreshView, msg, Snackbar.LENGTH_SHORT);
                break;
            case NO_INTERNET:
                Snackbar.make(swipeRefreshView, msg, Snackbar.LENGTH_SHORT);
                break;
        }
    }

    @Override
    public void showMessage(MessageType messageType, int msg) {
        showMessage(messageType, getString(msg));
    }

    @Override
    public void startLoading(int message) {
        swipeRefreshView.setRefreshing(true);
    }

    @Override
    public void stopLoading() {
        swipeRefreshView.setRefreshing(false);
    }

    @Override
    public void displayNotes(List<NotesModel> notes) {
        notesListAdapter = new NotesListAdapter(this, notes);
        notesListAdapter.setItemClick(this);
        recyclerView.setAdapter(notesListAdapter);
    }

    @OnClick(R.id.fab)
    void createNewNote() {
        startActivity(new Intent(this, AddNewNoteActivity.class));
    }

    @Override
    public void onItemClicked(RecyclerView.Adapter adapter, RecyclerView.ViewHolder viewHolder, int position) {
        final long noteId = ((NotesListAdapter.ViewHolder) viewHolder).getData().getId();
        final Intent intent = new Intent(this, ViewNoteActivity.class);
        intent.putExtra(Constants.IntentKeys.ID, noteId);
        startActivity(intent);
    }
}
