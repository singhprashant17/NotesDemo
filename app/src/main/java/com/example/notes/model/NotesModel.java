package com.example.notes.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class NotesModel extends RealmObject implements Parcelable {
    public static final Parcelable.Creator<NotesModel> CREATOR = new Parcelable.Creator<NotesModel>() {
        @Override
        public NotesModel createFromParcel(Parcel source) {
            return new NotesModel(source);
        }

        @Override
        public NotesModel[] newArray(int size) {
            return new NotesModel[size];
        }
    };
    @PrimaryKey
    private long noteId;
    private String note;
    private String title;

    public NotesModel() {

    }

    protected NotesModel(Parcel in) {
        this.noteId = in.readLong();
        this.note = in.readString();
        this.title = in.readString();
    }

    public long getId() {
        return noteId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.noteId);
        dest.writeString(this.note);
        dest.writeString(this.title);
    }
}
