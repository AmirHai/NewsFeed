package com.example.gson;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class Post {
    private String author;
    private ArrayList<Object> comments;
    private String image;
    private double rating;
    private String text;
    private String title;

    public Post(String author, ArrayList<Object> comments, String image, double rating, String text, String title){
        this.author = author;
        this.comments = comments;
        this.image = image;
        this.rating = rating;
        this.text = text;
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public ArrayList<Object> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Object> comments) {
        this.comments = comments;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    Post(){
    }
}
