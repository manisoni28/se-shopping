package com.srgiovine.seshopping.data;

import android.database.sqlite.SQLiteDatabase;

import com.srgiovine.seshopping.task.BackgroundAsyncTask;
import com.srgiovine.seshopping.task.Callback;

abstract class DBBackgroundTask<T> extends BackgroundAsyncTask<T> {

    private final SQLiteDatabase db;

    DBBackgroundTask(Callback<T> callback, SQLiteDatabase db) {
        super(callback);
        this.db = db;
    }

    @Override
    protected final T doInBackground() {
        return doInBackground(db);
    }

    abstract T doInBackground(SQLiteDatabase db);
}
