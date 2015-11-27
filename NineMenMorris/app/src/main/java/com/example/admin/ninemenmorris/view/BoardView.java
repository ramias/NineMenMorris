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
            int w = this.getWidth(), h = this.getHeight();
            int size = (w + h) / 70;
            int x = (int) event.getX();
            int y = (int) event.getY();
            Log.i("coords", "x = " + x + "   y = " + y);
            Log.i("width", "h =" + h + "  h/5 = " + h / 6);
            if (x < w / 28 + size && x > w / 28 - size && y < h / 7 + size && y > h / 7 - size) {
                piecebounds = new Rect(w / 28 - size, h / 7 - size, w / 28 + size, h / 7 + size);
                invalidate();
            } else if (x < w / 28 + size && x > w / 28 - size && y < h / 2 + size && y > h / 2 - size) {
                piecebounds = new Rect(w / 28 - size, h / 2 - size, w / 28 + size, h / 2 + size);
                invalidate();
            } else if (x < w / 28 + size && x > w / 28 - size && y < h * 6 / 7 + size && y > h * 6 / 7 - size) {
                piecebounds = new Rect(w / 28 - size, h * 6 / 7 - size, w / 28 + size, h * 6 / 7 + size);
                invalidate();
            } else if (x < w / 2 + size && x > w / 2 - size && y < h / 7 + size && y > h / 7 - size) {
                piecebounds = new Rect(w / 2 - size, h / 7 - size, w / 2 + size, h / 7 + size);
                invalidate();
            } else if (x < w * 27 / 28 + size && x > w * 27 / 28 - size && y < h / 7 + size && y > h / 7 - size) {
                piecebounds = new Rect(w * 27 / 28 - size, h / 7 - size, w * 27 / 28 + size, h / 7 + size);
                invalidate();
            }


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
}
