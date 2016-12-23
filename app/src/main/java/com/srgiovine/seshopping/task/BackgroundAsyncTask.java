package com.srgiovine.seshopping.task;

import android.os.AsyncTask;

public abstract class BackgroundAsyncTask<T> extends AsyncTask<Void, Void, T> implements BackgroundTask {

    protected final Callback<T> callback;

    public BackgroundAsyncTask(Callback<T> callback) {
        this.callback = callback;
    }

    @Override
    protected final T doInBackground(Void... voids) {
        try {
            // FIXME remove this artificial delay when replacing with network calls
            Thread.sleep(500L);
        } catch (InterruptedException ie) {
        }

        try {
            return doInBackground();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(T result) {
        super.onPostExecute(result);
        if (result == null) {
            callback.onFailed();
        } else {
            callback.onSuccess(result);
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        callback.onCancelled();
    }

    @Override
    public void cancel() {
        cancel(true);
    }

    protected abstract T doInBackground();
}
