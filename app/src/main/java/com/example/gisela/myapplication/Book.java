package com.example.gisela.myapplication;

public class Book {
    private String BookName;
    private String BookAuthor;
    private long ISBN;
    private long bookId;

    public Book(long bookId, String bookName, String bookAuthor, long ISBN) {
        this.BookAuthor = bookAuthor;
        this.BookName = bookName;
        this.ISBN = ISBN;
        this.bookId = bookId;
    }

    public Book() {

    }

    public String getBookName(){
        return BookName;
    }

    public String getBookAuthor(){
        return BookAuthor;
    }

    public long getISBN(){
        return ISBN;
    }

    public long getBookId(){
        return bookId;
    }

    public void setBookName(String bookName){
        this.BookName = bookName;
    }

    public void setBookAuthor(String bookAuthor){
        this.BookAuthor = bookAuthor;
    }

    public void setISBN(long ISBN){
        this.ISBN = ISBN;
    }

    public void setBookId(long bookId){
        this.bookId = bookId;
    }

    public String toString() {
        return "Book ID: " + getBookId() + "\n" +
                "Book Name: " + getBookName() + "\n" +
                "Book Author: " + getBookAuthor() + "\n" +
                "ISBN: " + getISBN();


    }

}
