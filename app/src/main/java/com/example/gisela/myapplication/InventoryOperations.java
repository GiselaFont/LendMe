package com.example.gisela.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

//import com.example.mg.inventorydatabase.BookInventory;


public class InventoryOperations {
    public static final String LOGTAG = "INV_BOOK_SYS";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase database;

    private static final String[] allColumns = {
            InventoryDBHandler.COLUMN_BOOK_ID,
            InventoryDBHandler.COLUMN_BOOK_NAME,
            InventoryDBHandler.COLUMN_BOOK_AUTHOR,
            InventoryDBHandler.COLUMN_ISBN

    };

    public InventoryOperations(Context context){
        dbhandler = new InventoryDBHandler(context);
    }

    public void open(){
        Log.i(LOGTAG,"Database Opened");
        database = dbhandler.getWritableDatabase();


    }
    public void close(){
        Log.i(LOGTAG, "Database Closed");
        dbhandler.close();

    }
    public Book addBook(Book Book){
        ContentValues values  = new ContentValues();
        values.put(InventoryDBHandler.COLUMN_BOOK_NAME, Book.getBookName());
        values.put(InventoryDBHandler.COLUMN_BOOK_AUTHOR,Book.getBookAuthor());
        values.put(InventoryDBHandler.COLUMN_ISBN, Book.getISBN());
        long insertid = database.insert(InventoryDBHandler.TABLE_BOOKS,null,values);
        Book.setBookId(insertid);
        return Book;

    }

    // Getting single Book
    public Book getBook(long id) {

        Cursor cursor = database.query(InventoryDBHandler.TABLE_BOOKS,allColumns,InventoryDBHandler.COLUMN_BOOK_ID + "=?", new String[]{String.valueOf(id)},null,null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Book e = new Book(Long.parseLong(cursor.getString(0)),cursor.getString(1),cursor.getString(2),Long.parseLong(cursor.getString(3)));
        // return Book
        return e;
    }

    public List<Book> getAllBooks() {

        Cursor cursor = database.query(InventoryDBHandler.TABLE_BOOKS,allColumns,null,null,null, null, null);

        List<Book> books = new ArrayList<>();
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                Book book = new Book();
                book.setBookId(cursor.getLong(cursor.getColumnIndex(InventoryDBHandler.COLUMN_BOOK_ID)));
                book.setISBN(cursor.getLong(cursor.getColumnIndex(InventoryDBHandler.COLUMN_ISBN)));
                book.setBookAuthor(cursor.getString(cursor.getColumnIndex(InventoryDBHandler.COLUMN_BOOK_AUTHOR)));
                book.setBookName(cursor.getString(cursor.getColumnIndex(InventoryDBHandler.COLUMN_BOOK_NAME)));
                books.add(book);
            }
        }
        // return All books
        return books;
    }



    //Updating Book
    public int updateBook(Book book) {

        ContentValues values = new ContentValues();
        values.put(InventoryDBHandler.COLUMN_BOOK_ID, book.getBookId());
        values.put(InventoryDBHandler.COLUMN_BOOK_NAME, book.getBookName());
        values.put(InventoryDBHandler.COLUMN_BOOK_AUTHOR, book.getBookAuthor());
        values.put(InventoryDBHandler.COLUMN_ISBN, book.getISBN());

        // updating row
        return database.update(InventoryDBHandler.TABLE_BOOKS, values,
                InventoryDBHandler.COLUMN_BOOK_ID + "=?",new String[] { String.valueOf(book.getBookId())});
    }

    // Deleting Book
    public void removeBook(Book book) {

        database.delete(InventoryDBHandler.TABLE_BOOKS, InventoryDBHandler.COLUMN_BOOK_ID + "=" + book.getBookId(), null);
    }



}
