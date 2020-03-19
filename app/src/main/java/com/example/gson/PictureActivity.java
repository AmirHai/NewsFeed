package com.example.gson;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PictureActivity extends AppCompatActivity implements View.OnTouchListener {

    String text_title, full_text, image_link, path;
    String txt_author;
    double text_likes;
    ArrayList<String> opinion = new ArrayList<>();

    public Button exit;

    ImageView image;

    private static final String TAG = "Touch" ;


    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();
    PointF start = new  PointF();
    public static PointF mid = new PointF();


    public static final int NONE = 0;
    public static final int DRAG = 1;
    public static final int ZOOM = 2;
    public static int mode = NONE;

    float oldDist;

    private float[] matrixValues = new float[9];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        image = (ImageView)findViewById(R.id.imageView);

        Intent intent = getIntent();
        text_title = intent.getStringExtra("title");
        image_link = intent.getStringExtra("image link");
        full_text = intent.getStringExtra("text");
        opinion = intent.getStringArrayListExtra("comments");

        text_likes = intent.getDoubleExtra("likes", 0.0);
        final String text_author = intent.getStringExtra("author");
        txt_author = text_author;
        path = intent.getStringExtra("database child");
        Picasso.get().load(image_link).into(image);

        exit = (Button) findViewById(R.id.button);

        View.OnClickListener exitButton = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(PictureActivity.this, PostPage.class);
                intent.putExtra("title", text_title);
                intent.putExtra("text", full_text);
                intent.putExtra("likes", text_likes);
                intent.putExtra("author", txt_author);
                intent.putExtra("comments", opinion);
                intent.putExtra("image link", image_link);
                intent.putExtra("database child", path);

                startActivity(intent);*/
                finish();
            }
        };
        exit.setOnClickListener(exitButton);

        image.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        ImageView view = (ImageView) v;

        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:

                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());
                Log.d(TAG, "mode=DRAG" );
                mode = DRAG;
                break;

            case MotionEvent.ACTION_POINTER_DOWN:

                oldDist = spacing(event);
                Log.d(TAG, "oldDist=" + oldDist);
                if (oldDist > 10f) {

                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = ZOOM;
                    Log.d(TAG, "mode=ZOOM" );
                }
                break;

            case MotionEvent.ACTION_MOVE:

                if (mode == DRAG) {

                    matrix.set(savedMatrix);
                    matrix.postTranslate(event.getX() - start.x, event.getY() - start.y);
                }
                else if (mode == ZOOM) {

                    float newDist = spacing(event);
                    Log.d(TAG, "newDist=" + newDist);
                    if (newDist > 10f) {

                        matrix.set(savedMatrix);
                        float scale = newDist / oldDist;
                        matrix.postScale(scale, scale, mid.x, mid.y);
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:

                mode = NONE;
                Log.d(TAG, "mode=NONE" );
                break;
        }


        view.setImageMatrix(matrix);

        return true;
    }

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float)Math.sqrt(x * x + y * y);
    }

    private void midPoint(PointF point, MotionEvent event) {

        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }
}
