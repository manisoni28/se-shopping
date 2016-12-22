package com.srgiovine.seshopping.task;

public interface Callback<T> {

    void onSuccess(T result);

    void onFailed();

    void onCancelled();
}
