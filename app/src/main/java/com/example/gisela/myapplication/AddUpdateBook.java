package com.example.gisela.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * Created by Michael Townsend on 4/13/2016.
 */
public class AddUpdateBook extends AppCompatActivity {
    private static final String EXTRA_BOOK_ID = "com.example.bookId";
    private static final String EXTRA_ADD_UPDATE = "com.example.add_update";
    private EditText bookTitleEditText;
    private EditText bookAuthorEditText;
    private EditText ISBNEditText;
    private Button addUpdateButton;
    private Book newBook;
    private Book oldBook;
    private String mode;
    private long bookId;
    private InventoryOperations bookData;
    private boolean validInput = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_book);
        newBook = new Book();
        oldBook = new Book();
        bookTitleEditText= (EditText)findViewById(R.id.edit_text_book_title);
        bookAuthorEditText = (EditText)findViewById(R.id.edit_text_book_author);
        ISBNEditText = (EditText)findViewById(R.id.edit_text_book_isbn);
        addUpdateButton = (Button)findViewById(R.id.button_add_update_book);
        bookData = new InventoryOperations(this);
        bookData.open();

        mode = getIntent().getStringExtra(EXTRA_ADD_UPDATE);

        if(mode.equals("Update")){
            try {
                addUpdateButton.setText("Update Book");
                bookId = getIntent().getLongExtra(EXTRA_BOOK_ID, 0);

                initializeBook(bookId);
            }
            catch (Exception ex){
                Toast t = Toast.makeText(AddUpdateBook.this, "Improper Book Entry! Try again.", Toast.LENGTH_SHORT);
                t.show();
                Intent i = new Intent(AddUpdateBook.this, ProfilePage.class);
                startActivity(i);
            }


        }

        addUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (mode.equals("Add")) {
                    try {
                        newBook.setBookAuthor(bookAuthorEditText.getText().toString());
                        newBook.setBookName(bookTitleEditText.getText().toString());
                        newBook.setISBN(Long.parseLong(ISBNEditText.getText().toString()));
                        bookData.addBook(newBook);
                        Toast t = Toast.makeText(AddUpdateBook.this, newBook.getBookName() + " has been added successfully !", Toast.LENGTH_SHORT);
                        t.show();
                        Intent i = new Intent(AddUpdateBook.this, ProfilePage.class);
                        startActivity(i);
                    }
                    catch(Exception ex) {

                        Toast t = Toast.makeText(AddUpdateBook.this, "Improper Book Entry!", Toast.LENGTH_SHORT);
                        t.show();
                        Intent i = new Intent(AddUpdateBook.this, ProfilePage.class);
                        startActivity(i);
                    }

                } else {

                    oldBook.setBookAuthor(bookAuthorEditText.getText().toString());
                    oldBook.setBookName(bookTitleEditText.getText().toString());
                    oldBook.setISBN(Long.parseLong(ISBNEditText.getText().toString()));
                    bookData.updateBook(oldBook);
                    Toast t = Toast.makeText(AddUpdateBook.this, oldBook.getBookName() + " has been updated successfully !", Toast.LENGTH_SHORT);
                    t.show();
                    Intent i = new Intent(AddUpdateBook.this, ProfilePage.class);
                    startActivity(i);
                }
            }

        });


    }

    private void initializeBook (long bookId) {
        oldBook = bookData.getBook(bookId);
        bookAuthorEditText.setText(oldBook.getBookAuthor());
        bookTitleEditText.setText(oldBook.getBookName());
        ISBNEditText.setText(String.valueOf(oldBook.getISBN()));
    }






}
