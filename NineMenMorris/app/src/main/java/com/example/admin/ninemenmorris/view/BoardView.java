package com.example.admin.ninemenmorris.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.admin.ninemenmorris.R;

/**
 * Created by Rami on 2015-11-25.
 */
public class BoardView extends View {
    private Drawable board;
    private Drawable redpiece, bluepiece;
    private Rect piecebounds = null;

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Resources resources = context.getResources();
        board = (Drawable)
                resources.getDrawable(R.drawable.board);
        redpiece = (Drawable)
                resources.getDrawable(R.drawable.red_piece);
        bluepiece = (Drawable)
                resources.getDrawable(R.drawable.blue_piece);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            Log.i("coords", "x = " + x + "   y = " + y);
            placeOnCorrectCoords(x, y);

            // Rect(int left, int top, int right, int bottom)
            return true;
        }

        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int w = this.getWidth(), h = this.getHeight();
        int size = (w + h) / 2, offset = (w + h) / 14;
        Rect bounds = new Rect(offset + w - size, h - size, size - offset, size);
        board.setBounds(bounds);
        board.draw(canvas);
        if (piecebounds != null) {
            redpiece.setBounds(piecebounds);
            redpiece.draw(canvas);
        }
    }

    private void placeOnCorrectCoords(int x, int y) {
        int w = this.getWidth(), h = this.getHeight();
        int size = (w + h) / 65;
        // Col, row
        // 1,1
        if (x < w / 28 + size && x > w / 28 - size && y < h / 7 + size && y > h / 7 - size) {
            piecebounds = new Rect(w / 28 - size, h / 7 - size, w / 28 + size, h / 7 + size);
            invalidate();
            // 1,4
        } else if (x < w / 28 + size && x > w / 28 - size && y < h / 2 + size && y > h / 2 - size) {
            piecebounds = new Rect(w / 28 - size, h / 2 - size, w / 28 + size, h / 2 + size);
            invalidate();
            // 1,7
        } else if (x < w / 28 + size && x > w / 28 - size && y < h * 6 / 7 + size && y > h * 6 / 7 - size) {
            piecebounds = new Rect(w / 28 - size, h * 6 / 7 - size, w / 28 + size, h * 6 / 7 + size);
            invalidate();
            // 4,1
        } else if (x < w / 2 + size && x > w / 2 - size && y < h / 7 + size && y > h / 7 - size) {
            piecebounds = new Rect(w / 2 - size, h / 7 - size, w / 2 + size, h / 7 + size);
            invalidate();
            // 7,1
        } else if (x < w * 27 / 28 + size && x > w * 27 / 28 - size && y < h / 7 + size && y > h / 7 - size) {
            piecebounds = new Rect(w * 27 / 28 - size, h / 7 - size, w * 27 / 28 + size, h / 7 + size);
            invalidate();
            // 7,4
        } else if (x < w * 27 / 28 + size && x > w * 27 / 28 - size && y < h / 2 + size && y > h / 2 - size) {
            piecebounds = new Rect(w * 27 / 28 - size, h / 2 - size, (w * 27) / 28 + size, h / 2 + size);
            invalidate();
            // 7,7
        } else if (x < w * 27 / 28 + size && x > w * 27 / 28 - size && y < h * 6 / 7 + size && y > h * 6 / 7 - size) {
            piecebounds = new Rect(w * 27 / 28 - size, h * 6 / 7 - size, (w * 27) / 28 + size, h * 6 / 7 + size);
            invalidate();
            // 4, 7
        } else if (x < w / 2 + size && x > w / 2 - size && y < h * 6 / 7 + size && y > h * 6 / 7 - size) {
            piecebounds = new Rect(w / 2 - size, h * 6 / 7 - size, w / 2 + size, h * 6 / 7 + size);
            invalidate();
            //2,2
        } else if (x < w * 3 / 16 + size && x > w * 3 / 16 - size && y < h * 13 / 50 + size && y > h * 13 / 50 - size) {
            piecebounds = new Rect(w * 3 / 16 - size, h * 13 / 50 - size, w * 3 / 16 + size, h * 13 / 50 + size);
            invalidate();
            //4,2
        } else if (x < w / 2 + size && x > w / 2 - size && y < h * 4 / 15 + size && y > h * 4 / 15 - size) {
            piecebounds = new Rect(w / 2 - size, h * 4 / 15 - size, w / 2 + size, h * 4 / 15 + size);
            invalidate();
            //6,2
        } else if (x < w * 13 / 16 + size && x > w * 13 / 16 - size && y < h * 13 / 50 + size && y > h * 13 / 50 - size) {
            piecebounds = new Rect(w * 13 / 16 - size, h * 13 / 50 - size, w * 13 / 16 + size, h * 13 / 50 + size);
            invalidate();
            //6,4
        } else if (x < w * 13 / 16 + size && x > w * 13 / 16 - size && y < h / 2 + size && y > h / 2 - size) {
            piecebounds = new Rect(w * 13 / 16 - size, h / 2 - size, w * 13 / 16 + size, h / 2 + size);
            invalidate();
            //6,6
        } else if (x < w * 13 / 16 + size && x > w * 13 / 16 - size && y < h * 37 / 50 + size && y > h * 37 / 50 - size) {
            piecebounds = new Rect(w * 13 / 16 - size, h * 37 / 50 - size, w * 13 / 16 + size, h * 37 / 50 + size);
            invalidate();
            //4,6
        } else if (x < w / 2 + size && x > w / 2 - size && y < h * 37 / 50 + size && y > h * 37 / 50 - size) {
            piecebounds = new Rect(w / 2 - size, h * 37 / 50 - size, w / 2 + size, h * 37 / 50 + size);
            invalidate();
            //2,6
        } else if (x < w * 3 / 16 + size && x > w * 3 / 16 - size && y < h * 37 / 50 + size && y > h * 37 / 50 - size) {
            piecebounds = new Rect(w * 3 / 16 - size, h * 37 / 50 - size, w * 3 / 16 + size, h * 37 / 50 + size);
            invalidate();
            //2,4
        } else if (x < w * 3 / 16 + size && x > w * 3 / 16 - size && y < h / 2 + size && y > h / 2 - size) {
            piecebounds = new Rect(w * 3 / 16 - size, h / 2 - size, w * 3 / 16 + size, h / 2 + size);
            invalidate();
            //3,3
        } else if (x < w * 17 / 49 + size && x > w * 17 / 49 - size && y < h * 19 / 50 + size && y > h * 19 / 50 - size) {
            piecebounds = new Rect(w * 17 / 49 - size, h * 19 / 50 - size, w * 17 / 49 + size, h * 19 / 50 + size);
            invalidate();
            //4,3
        } else if (x < w / 2 + size && x > w / 2 - size && y < h * 19 / 50 + size && y > h * 19 / 50 - size) {
            piecebounds = new Rect(w / 2 - size, h * 19 / 50 - size, w / 2 + size, h * 19 / 50 + size);
            invalidate();
            //5,3
        } else if (x < w * 32 / 49 + size && x > w * 32 / 49 - size && y < h * 19 / 50 + size && y > h * 19 / 50 - size) {
            piecebounds = new Rect(w * 32 / 49 - size, h * 19 / 50 - size, w * 32 / 49 + size, h * 19 / 50 + size);
            invalidate();
            //5,4
        } else if (x < w * 32 / 49 + size && x > w * 32 / 49 - size && y < h / 2 + size && y > h / 2 - size) {
            piecebounds = new Rect(w * 32 / 49 - size, h / 2 - size, w * 32 / 49 + size, h / 2 + size);
            invalidate();
            //5,5
        } else if (x < w * 32 / 49 + size && x > w * 32 / 49 - size && y < h * 31 / 50 + size && y > h * 31 / 50 - size) {
            piecebounds = new Rect(w * 32 / 49 - size, h * 31 / 50 - size, w * 32 / 49 + size, h * 31 / 50 + size);
            invalidate();
            //4,5
        } else if (x < w / 2 + size && x > w / 2 - size && y < h * 31 / 50 + size && y > h * 31 / 50 - size) {
            piecebounds = new Rect(w / 2 - size, h * 31 / 50 - size, w / 2 + size, h * 31 / 50 + size);
            invalidate();
            //3,5
        } else if (x < w * 17 / 49 + size && x > w * 17 / 49 - size && y < h * 31 / 50 + size && y > h * 31 / 50 - size) {
            piecebounds = new Rect(w * 17 / 49 - size, h * 31 / 50 - size, w * 17 / 49 + size, h * 31 / 50 + size);
            invalidate();
            //3,4
        } else if (x < w * 17 / 49 + size && x > w * 17 / 49 - size && y < h / 2 + size && y > h / 2 - size) {
            piecebounds = new Rect(w * 17 / 49 - size, h / 2 - size, w * 17 / 49 + size, h / 2 + size);
            invalidate();
        } else {
            //do nothing
        }
    }
}
