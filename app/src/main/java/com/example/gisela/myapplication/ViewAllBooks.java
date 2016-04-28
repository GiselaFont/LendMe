package com.example.gisela.myapplication;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.example.gisela.myapplication.Book;
import com.example.gisela.myapplication.InventoryOperations;

import java.util.List;

public class ViewAllBooks extends ListActivity{
    private InventoryOperations bookOps;
    List<Book> books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_books);
        bookOps = new InventoryOperations(this);
        bookOps.open();
        books = bookOps.getAllBooks();
        bookOps.close();
        ArrayAdapter<Book> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, books);
        setListAdapter(adapter);
    }
}
