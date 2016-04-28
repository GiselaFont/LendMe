package com.example.gisela.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Michael Townsend on 4/13/2016.
 */
public class InventoryDBHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "BookInventory.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_BOOKS= "books";
    public static final String COLUMN_BOOK_ID = "bookId";
    public static final String COLUMN_BOOK_AUTHOR = "bookAuthor";
    public static final String COLUMN_BOOK_NAME = "bookName";
    public static final String COLUMN_ISBN = "isbnNumber";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_BOOKS + " (bookId INTEGER PRIMARY KEY AUTOINCREMENT, bookAuthor TEXT,bookName TEXT, isbnNumber INTEGER )";

    public InventoryDBHandler(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_BOOKS);
        db.execSQL(TABLE_CREATE);
    }
}
