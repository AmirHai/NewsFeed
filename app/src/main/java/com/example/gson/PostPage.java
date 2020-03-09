package com.example.gson;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class PostPage extends AppCompatActivity {
    TextView title,text, likes, author, comments;
    EditText com;
    ImageView image;
    DatabaseReference reference;
    String text_comments;
    HashMap<Integer, String> opinion = new HashMap<>();
    int i=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_page);
        Intent intent = getIntent();
        String text_title = intent.getStringExtra("title");
        String image_link = intent.getStringExtra("image link");
        String full_text = intent.getStringExtra("text");
        double text_likes = intent.getDoubleExtra("likes", 0.0);
        final String text_author = intent.getStringExtra("author");
        String path = intent.getStringExtra("database child");

        title = (TextView) findViewById(R.id.title);
        text = (TextView) findViewById(R.id.text);
        likes = (TextView) findViewById(R.id.likes);
        author = (TextView) findViewById(R.id.author);
        comments = (TextView) findViewById(R.id.comments);
        image = (ImageView) findViewById(R.id.image);
        com = (EditText) findViewById(R.id.com);
        reference = FirebaseDatabase.getInstance().getReference().child("News").child(path).child("comments");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                text_comments = "";
                i=0;
                for(DataSnapshot ds :dataSnapshot.getChildren()){
                    String s = (ds.getValue()).toString();

                    Log.d("Logs", s);

                    opinion.put(i, s);
                    ++i;
                    text_comments = text_comments + s + "\n";
                }


                comments.setText(text_comments);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Button close = (Button) findViewById(R.id.close);

        title.setText(text_title);
        Picasso.get().load(image_link).into(image);
        text.setText(full_text);
        likes.setText(text_likes + "");
        author.setText(text_author);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        com.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //push в комментарии
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
