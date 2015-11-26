package com.example.admin.ninemenmorris.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.example.admin.ninemenmorris.R;

/**
 * Created by Rami on 2015-11-25.
 */
public class BoardView extends View {
    private Drawable board;
    private Rect piecebounds = null;

    public BoardView(Context context) {
        super(context);
        Resources resources = context.getResources();
        board = (Drawable)
                resources.getDrawable(R.drawable.board);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        int w = this.getWidth(), h = this.getHeight();
        Rect bounds = new Rect(0+h/20, 0+h/5, w-h/20, h-h/20);
        board.setBounds(bounds);
        board.draw(canvas);
    }
}
