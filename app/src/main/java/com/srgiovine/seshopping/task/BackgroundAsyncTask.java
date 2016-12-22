package com.srgiovine.seshopping.task;

import android.os.AsyncTask;

public abstract class BackgroundAsyncTask<T> extends AsyncTask<Void, Void, T> implements BackgroundTask {

    @Override
    protected final T doInBackground(Void... voids) {
        try {
            Thread.sleep(1_500L);
        } catch (InterruptedException ie) {
        }

        return doInBackground();
    }
    
    @Override
    public void cancel() {
        cancel(true);
    }

    protected abstract T doInBackground();
}
