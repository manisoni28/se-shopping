package com.srgiovine.seshopping.data;

import android.database.Cursor;

import com.srgiovine.seshopping.model.Category;
import com.srgiovine.seshopping.model.Gender;
import com.srgiovine.seshopping.model.Item;

import java.util.ArrayList;
import java.util.List;

final class ItemContentValues {

    private ItemContentValues() {
    }

    static Item toItem(Cursor cursor) {
        return Item.builder()
                .setId(cursor.getInt(cursor.getColumnIndexOrThrow(ItemContract.ID)))
                .setName(cursor.getString(cursor.getColumnIndexOrThrow(ItemContract.NAME)))
                .setDescription(cursor.getString(cursor.getColumnIndexOrThrow(ItemContract.DESCRIPTION)))
                .setGender(Gender.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(ItemContract.GENDER))))
                .setCategory(Category.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(ItemContract.CATEGORY))))
                .setPrice(cursor.getInt(cursor.getColumnIndexOrThrow(ItemContract.PRICE)))
                .setImage(cursor.getInt(cursor.getColumnIndexOrThrow(ItemContract.IMAGE)))
                .setIcon(cursor.getInt(cursor.getColumnIndexOrThrow(ItemContract.ICON)))
                .build();
    }

    static List<Item> toItems(Cursor cursor) {
        List<Item> items = new ArrayList<>();
        do {
            items.add(toItem(cursor));
            cursor.moveToNext();
        } while (!cursor.isAfterLast());
        return items;
    }
}
