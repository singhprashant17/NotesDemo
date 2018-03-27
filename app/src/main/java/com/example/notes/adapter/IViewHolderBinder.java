package com.example.notes.adapter;

/**
 * Created by webonise on 24/3/18.
 */

public interface IViewHolderBinder<T> {
    void bind(T data, int position);
}
