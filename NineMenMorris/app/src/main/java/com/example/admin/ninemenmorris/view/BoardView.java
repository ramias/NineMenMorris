package com.example.admin.ninemenmorris.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.admin.ninemenmorris.R;

/**
 * Created by Rami on 2015-11-25.
 */
public class BoardView extends View {
    private Drawable redpiece, bluepiece;
    private Rect boardBounds = null;

    public BoardView(Context context) {
        super(context);
        Resources resources = context.getResources();
        redpiece = (Drawable)
                resources.getDrawable(R.drawable.red_piece, null);
        bluepiece = (Drawable)
                resources.getDrawable(R.drawable.blue_piece, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("TouchView.onTouchEvent", "event = " + event);

        if (event.getAction() == MotionEvent.ACTION_DOWN ||
                event.getAction() == MotionEvent.ACTION_UP) {

            int w = redpiece.getIntrinsicWidth();
            int h = redpiece.getIntrinsicHeight();
            int x = (int) event.getX();
            int y = (int) event.getY();
            boardBounds = new Rect(x - w / 2, y - w / 2, x + w / 2, y + h / 2);

            int w2 = bluepiece.getIntrinsicWidth();
            int h2 = bluepiece.getIntrinsicHeight();
            int x2 = ((int) event.getX()) + 20;
            int y2 = ((int) event.getY()) + 20;
            boardBounds = new Rect(x - w / 2, y - w / 2, x + w / 2, y + h / 2);
            boardBounds = new Rect(x2 - w2 / 2, y2 - w2 / 2, x2 + w2 / 2, y2 + h2 / 2);
            // Request the system to redraw the view (call onDraw at
            // some point in the future)
            // From a non-UI thread, call postInvalidate instead
            invalidate();

            return true;
        }

        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        // Background
        Paint bgPaint = new Paint();
        bgPaint.setColor(Color.WHITE);
        canvas.drawPaint(bgPaint);

        if (boardBounds != null) {
            redpiece.setBounds(boardBounds);
            redpiece.draw(canvas);
            bluepiece.setBounds(boardBounds);
            bluepiece.draw(canvas);
        }
    }
}
