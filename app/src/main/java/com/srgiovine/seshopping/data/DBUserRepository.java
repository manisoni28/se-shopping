package com.srgiovine.seshopping.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.srgiovine.seshopping.model.User;
import com.srgiovine.seshopping.task.BackgroundTask;
import com.srgiovine.seshopping.task.Callback;

class DBUserRepository extends SQLiteOpenHelper implements UserRepository {

    private static final String DB_NAME = "SEShopping.Users";
    private static final int DB_VERSION = 1;

    DBUserRepository(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            db.execSQL(UserContract.CREATE_TABLE);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade will delete old db and create a new one
        db.beginTransaction();
        try {
            db.execSQL(UserContract.DROP_TABLE);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        onCreate(db);
    }

    @Override
    public BackgroundTask createUser(User user, Callback<User> callback) {
        CreateUserTask task = new CreateUserTask(user, callback, getWritableDatabase());
        task.execute();
        return task;
    }

    @Override
    public BackgroundTask updateUser(User user, Callback<User> callback) {
        UpdateUserTask task = new UpdateUserTask(user, callback, getWritableDatabase());
        task.execute();
        return task;
    }

    @Override
    public BackgroundTask getUser(String email, String password, Callback<User> callback) {
        GetUserTask task = new GetUserTask(email, password, callback, getReadableDatabase());
        task.execute();
        return task;
    }

    private static class GetUserTask extends DBBackgroundTask<User> {

        private final String email;
        private final String password;

        private GetUserTask(String email, String password, Callback<User> callback, SQLiteDatabase db) {
            super(callback, db);
            this.email = email;
            this.password = password;
        }

        @Override
        User doInBackground(SQLiteDatabase db) {
            User user = null;
            Cursor cursor = db.query(UserContract.TABLE_NAME, null,
                    UserContract.EMAIL + " = ? COLLATE NOCASE AND " + UserContract.PASSWORD + " = ?",
                    new String[]{email, password},
                    null, null, null);
            if (cursor.moveToFirst()) {
                user = UserContentValues.toUser(cursor);
            }
            cursor.close();
            return user;
        }
    }

    private static class UpdateUserTask extends DBBackgroundTask<User> {

        private final User user;

        private UpdateUserTask(User user, Callback<User> callback, SQLiteDatabase db) {
            super(callback, db);
            this.user = user;
        }

        @Override
        User doInBackground(SQLiteDatabase db) {
            ContentValues contentValues = UserContentValues.toContentValues(user);
            int affectedRows = db.update(UserContract.TABLE_NAME, contentValues,
                    UserContract.EMAIL + " = ? COLLATE NOCASE AND " + UserContract.PASSWORD + " = ?",
                    new String[]{user.email(), user.password()});
            return affectedRows > 0 ? user : null;
        }
    }

    private static class CreateUserTask extends DBBackgroundTask<User> {

        private final User user;

        private CreateUserTask(User user, Callback<User> callback, SQLiteDatabase db) {
            super(callback, db);
            this.user = user;
        }

        @Override
        User doInBackground(SQLiteDatabase db) {
            ContentValues contentValues = UserContentValues.toContentValues(user);
            long newCustomerId = db.insert(UserContract.TABLE_NAME, null, contentValues);
            return newCustomerId != -1L ? user : null;
        }
    }
}
