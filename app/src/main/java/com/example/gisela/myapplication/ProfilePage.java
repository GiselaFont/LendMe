package com.example.gisela.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.login.LoginManager;

/**
 * Created by Gisela on 4/16/16.
 */
public class ProfilePage extends AppCompatActivity {

    private Button addBookButton;
    private Button editBookButton;
    private Button deleteBookButton;
    private Button viewAllBookButton;
    private Button logOutButton;
    private InventoryOperations bookOps;
    private static final String EXTRA_BOOK_ID = "com.example.bookId";
    private static final String EXTRA_ADD_UPDATE = "com.example.add_update";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        addBookButton = (Button) findViewById(R.id.button_add_book);
        editBookButton = (Button) findViewById(R.id.button_edit_book);
        deleteBookButton = (Button) findViewById(R.id.button_delete_book);
        viewAllBookButton = (Button)findViewById(R.id.button_view_books);
        logOutButton = (Button) findViewById(R.id.Blogout);

        bookOps = new InventoryOperations(ProfilePage.this);
        bookOps.open();

        logOutButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                LoginManager.getInstance().logOut();
                Intent i = new Intent(ProfilePage.this,MainActivity.class);
                startActivity(i);
            }
        });


        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfilePage.this,AddUpdateBook.class);
                i.putExtra(EXTRA_ADD_UPDATE, "Add");
                startActivity(i);
            }
        });
        editBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBookIDandUpdateBook();
            }
        });
        deleteBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBookIdAndRemoveBook();
            }
        });
        viewAllBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfilePage.this, ViewAllBooks.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getBookIDandUpdateBook(){

        LayoutInflater li = LayoutInflater.from(this);
        View getBookIDView = li.inflate(R.layout.dialog_get_book_id, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // set dialog_get_book_id.xml to alertdialog builder
        alertDialogBuilder.setView(getBookIDView);

        final EditText userInput = (EditText) getBookIDView.findViewById(R.id.editTextDialogUserInput);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // get user input and set it to result
                        // edit text
                        try {
                            Intent i = new Intent(ProfilePage.this, AddUpdateBook.class);
                            i.putExtra(EXTRA_ADD_UPDATE, "Update");
                            i.putExtra(EXTRA_BOOK_ID, Long.parseLong(userInput.getText().toString()));
                            startActivity(i);
                        }
                        catch(Exception ex){
                            Toast t = Toast.makeText(ProfilePage.this, "Invalid book entry!", Toast.LENGTH_SHORT);
                            t.show();
                        }
                    }
                }).create()
                .show();

    }


    public void getBookIdAndRemoveBook(){

        LayoutInflater li = LayoutInflater.from(this);
        View getBookIdView = li.inflate(R.layout.dialog_get_book_id, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // set dialog_get_book_id.xml to alertdialog builder
        alertDialogBuilder.setView(getBookIdView);

        final EditText userInput = (EditText) getBookIdView.findViewById(R.id.editTextDialogUserInput);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // get user input and set it to result
                        // edit text
                        //bookOps = new InventoryOperations(MainActivity.this);
                        try {
                            bookOps.removeBook(bookOps.getBook(Long.parseLong(userInput.getText().toString())));
                            Toast t = Toast.makeText(ProfilePage.this, "Book removed successfully!", Toast.LENGTH_SHORT);
                            t.show();
                        }
                        catch(Exception ex){
                            Toast t = Toast.makeText(ProfilePage.this, "Invalid book entry!", Toast.LENGTH_SHORT);
                            t.show();
                        }
                    }
                }).create()
                .show();

    }
    @Override
    protected void onResume() {
        super.onResume();

        bookOps.open();

    }

    @Override
    protected void onPause() {
        super.onPause();
        LoginManager.getInstance().logOut();
        bookOps.close();
        Intent i = new Intent(ProfilePage.this, MainActivity.class);
        startActivity(i);

    }

}

