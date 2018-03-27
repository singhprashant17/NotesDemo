package com.example.notes.adapter;

import android.support.v7.widget.RecyclerView;

/**
 * Interface to be implemented by those who wish to listen the click event of RecyclerView items
 */
public interface RecyclerViewItemClick {
    /**
     * This method will be invoked on a click event
     *
     * @param adapter    The {@link RecyclerView.Adapter} instance which
     *                   is set to the RecyclerView
     * @param viewHolder The {@link RecyclerView.ViewHolder} on which the
     *                   clicked is performed
     * @param position   The position in the adapter
     */
    void onItemClicked(RecyclerView.Adapter adapter, RecyclerView.ViewHolder viewHolder,
                       int position);
}
