package com.example.notes.webservice;

import com.example.notes.model.NotesModel;
import com.google.gson.JsonElement;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Single;

public interface ApiCallMethods {

    @GET("json")
    Single<List<NotesModel>> getAllNotes();

    @POST("json")
    Single<JsonElement> saveNote();
}
