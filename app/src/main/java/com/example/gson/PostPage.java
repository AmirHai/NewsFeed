package com.example.gson;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class PostPage extends AppCompatActivity{
    TextView title,text, likes, author, comments;
    EditText com;
    ImageView image;
    DatabaseReference reference;
    String text_comments = "";
    String text_title, full_text, image_link, path;
    String txt_author;
    double text_likes;

    ArrayList<String> opinion = new ArrayList<>();
    Button send;
    String message = "";
    int i=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_page);
        Intent intent = getIntent();
        text_title = intent.getStringExtra("title");
        image_link = intent.getStringExtra("image link");
        full_text = intent.getStringExtra("text");
        opinion = intent.getStringArrayListExtra("comments");

        text_likes = intent.getDoubleExtra("likes", 0.0);
        final String text_author = intent.getStringExtra("author");
        txt_author = text_author;
        path = intent.getStringExtra("database child");

        title = (TextView) findViewById(R.id.title);
        text = (TextView) findViewById(R.id.text);
        likes = (TextView) findViewById(R.id.likes);
        author = (TextView) findViewById(R.id.author);
        comments = (TextView) findViewById(R.id.comments);
        image = (ImageView) findViewById(R.id.image);
        com = (EditText) findViewById(R.id.com);
        send = (Button) findViewById(R.id.send_comment);
        reference = FirebaseDatabase.getInstance().getReference().child("News").child(path).child("comments");

        Button close = (Button) findViewById(R.id.close);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                text_comments = "";

                for(DataSnapshot ds :dataSnapshot.getChildren()){
                    String s = (ds.getValue()).toString();

                    text_comments = text_comments + s + "\n";
                }
                comments.setText(text_comments);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        title.setText(text_title);
        Picasso.get().load(image_link).into(image);
        text.setText(full_text);
        likes.setText(text_likes + "");
        author.setText(text_author);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!message.equals("")) {
                    reference.child(opinion.size() + "").setValue(message);
                    com.setText("");
                    Toast.makeText(getApplicationContext(), "Comment added", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getApplicationContext(), "Comment can't be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
                message = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        View.OnClickListener OpenPic = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostPage.this, PictureActivity.class);
                intent.putExtra("title", text_title);
                intent.putExtra("text", full_text);
                intent.putExtra("likes", text_likes);
                intent.putExtra("author", txt_author);
                intent.putExtra("comments", opinion);
                intent.putExtra("image link", image_link);
                intent.putExtra("database child", path);

                startActivity(intent);
            }
        };
        image.setOnClickListener(OpenPic);
    }
}
