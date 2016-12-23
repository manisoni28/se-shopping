package com.srgiovine.seshopping.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.srgiovine.seshopping.model.Category;
import com.srgiovine.seshopping.model.Gender;
import com.srgiovine.seshopping.model.Item;
import com.srgiovine.seshopping.task.BackgroundTask;
import com.srgiovine.seshopping.task.Callback;

import java.util.List;
import java.util.Set;

import static com.srgiovine.seshopping.util.StringUtils.createCSVPlaceholders;
import static com.srgiovine.seshopping.util.StringUtils.toStringArray;

class DBItemRepository extends SQLiteOpenHelper implements ItemRepository {

    private static final String DB_NAME = "SEShopping.Items";
    private static final int DB_VERSION = 1;

    DBItemRepository(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            db.execSQL(ItemContract.CREATE_TABLE);
            db.execSQL(ItemContract.INITIAL_INSERT);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        onUpgrade(db, 1, 2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade will delete old db and create a new one
        db.beginTransaction();
        try {
            db.execSQL(ItemContract.DROP_TABLE);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        onCreate(db);
    }

    @Override
    public BackgroundTask getItemById(long itemId, Callback<Item> callback) {
        GetItemByIdTask task = new GetItemByIdTask(itemId, callback, getReadableDatabase());
        task.execute();
        return task;
    }

    @Override
    public BackgroundTask getItemsWithIds(Set<Long> itemIds, Callback<List<Item>> callback) {
        GetItemsWithIdsTask task = new GetItemsWithIdsTask(itemIds, callback, getReadableDatabase());
        task.execute();
        return task;
    }

    @Override
    public BackgroundTask getItemsWithName(String name, Callback<List<Item>> callback) {
        GetItemsWithNameTask task = new GetItemsWithNameTask(name, callback, getReadableDatabase());
        task.execute();
        return task;
    }

    @Override
    public BackgroundTask getItemsWithGendersAndCategories(Set<Gender> genders, Set<Category> categories,
                                                           Callback<List<Item>> callback) {
        GetItemsWithGendersAndCategoriesTask task
                = new GetItemsWithGendersAndCategoriesTask(categories, genders, callback, getReadableDatabase());
        task.execute();
        return task;
    }

    private static class GetItemsWithGendersAndCategoriesTask extends DBBackgroundTask<List<Item>> {

        private final Set<Gender> genders;
        private final Set<Category> categories;

        private GetItemsWithGendersAndCategoriesTask(Set<Category> categories, Set<Gender> genders,
                                                     Callback<List<Item>> callback, SQLiteDatabase db) {
            super(callback, db);
            this.categories = categories;
            this.genders = genders;
        }

        @Override
        List<Item> doInBackground(SQLiteDatabase db) {
            if (genders.isEmpty() && categories.isEmpty()) {
                return allItems(db);
            }

            if (categories.isEmpty()) {
                return itemsInGenders(db);
            }

            if (genders.isEmpty()) {
                return itemsInCategories(db);
            }

            return itemsInCategoriesAndGenders(db);
        }

        private List<Item> allItems(SQLiteDatabase db) {
            List<Item> items = null;
            Cursor cursor = db.query(ItemContract.TABLE_NAME, null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                items = ItemContentValues.toItems(cursor);
            }
            cursor.close();
            return items;
        }

        private List<Item> itemsInCategoriesAndGenders(SQLiteDatabase db) {
            List<Item> items = null;
            Cursor cursor = db.query(ItemContract.TABLE_NAME, null,
                    ItemContract.CATEGORY + " IN (" + createCSVPlaceholders("?", categories.size()) + ") " +
                            "AND " + ItemContract.GENDER + " IN (" + createCSVPlaceholders("?", genders.size()) + ") ",
                    toStringArray(categories, genders),
                    null, null, null);
            if (cursor.moveToFirst()) {
                items = ItemContentValues.toItems(cursor);
            }
            cursor.close();
            return items;
        }

        private List<Item> itemsInCategories(SQLiteDatabase db) {
            List<Item> items = null;
            Cursor cursor = db.query(ItemContract.TABLE_NAME, null,
                    ItemContract.CATEGORY + " IN (" + createCSVPlaceholders("?", categories.size()) + ")",
                    toStringArray(categories),
                    null, null, null);
            if (cursor.moveToFirst()) {
                items = ItemContentValues.toItems(cursor);
            }
            cursor.close();
            return items;
        }

        private List<Item> itemsInGenders(SQLiteDatabase db) {
            List<Item> items = null;
            Cursor cursor = db.query(ItemContract.TABLE_NAME, null,
                    ItemContract.GENDER + " IN (" + createCSVPlaceholders("?", genders.size()) + ")",
                    toStringArray(genders),
                    null, null, null);
            if (cursor.moveToFirst()) {
                items = ItemContentValues.toItems(cursor);
            }
            cursor.close();
            return items;
        }
    }

    private static class GetItemsWithNameTask extends DBBackgroundTask<List<Item>> {

        private final String name;

        private GetItemsWithNameTask(String name, Callback<List<Item>> callback, SQLiteDatabase db) {
            super(callback, db);
            this.name = name;
        }

        @Override
        List<Item> doInBackground(SQLiteDatabase db) {
            List<Item> items = null;
            Cursor cursor = db.query(ItemContract.TABLE_NAME, null,
                    ItemContract.NAME + " LIKE ?",
                    new String[]{"%" + name + "%"},
                    null, null, null);
            if (cursor.moveToFirst()) {
                items = ItemContentValues.toItems(cursor);
            }
            cursor.close();
            return items;
        }
    }

    private static class GetItemsWithIdsTask extends DBBackgroundTask<List<Item>> {

        private final Set<Long> itemIds;

        private GetItemsWithIdsTask(Set<Long> itemIds, Callback<List<Item>> callback, SQLiteDatabase db) {
            super(callback, db);
            this.itemIds = itemIds;
        }

        @Override
        List<Item> doInBackground(SQLiteDatabase db) {
            List<Item> items = null;
            Cursor cursor = db.query(ItemContract.TABLE_NAME, null,
                    ItemContract.ITEM_ID + " IN (" + createCSVPlaceholders("?", itemIds.size()) + ")",
                    toStringArray(itemIds),
                    null, null, null);
            if (cursor.moveToFirst()) {
                items = ItemContentValues.toItems(cursor);
            }
            cursor.close();
            return items;
        }
    }

    private static class GetItemByIdTask extends DBBackgroundTask<Item> {

        private final long itemId;

        private GetItemByIdTask(long itemId, Callback<Item> callback, SQLiteDatabase db) {
            super(callback, db);
            this.itemId = itemId;
        }

        @Override
        Item doInBackground(SQLiteDatabase db) {
            Item item = null;
            Cursor cursor = db.query(ItemContract.TABLE_NAME, null, ItemContract.ITEM_ID + " = ?",
                    new String[]{String.valueOf(itemId)},
                    null, null, null);
            if (cursor.moveToFirst()) {
                item = ItemContentValues.toItem(cursor);
            }
            cursor.close();
            return item;
        }
    }
}
