package com.example.admin.ninemenmorris.view;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;

import com.example.admin.ninemenmorris.R;

/**
 * Created by Rami on 2015-11-26.
 */
public class PieceView extends View {
    private Drawable piece, selectedPiece;
    private int position;
    private boolean isSelected;
    private Rect piecebounds = null;

    public PieceView(Context context, Rect piecebounds, int position, String color) {
        super(context);
        this.position = position;
        Resources resources = context.getResources();
        if (color.equals("RED")) {
            piece = (Drawable)
                    resources.getDrawable(R.drawable.red_piece);
            selectedPiece = (Drawable)
                    resources.getDrawable(R.drawable.red_piece_selected);
        } else if (color.equals("BLUE")) {
            piece = (Drawable)
                    resources.getDrawable(R.drawable.blue_piece);
            selectedPiece = (Drawable)
                    resources.getDrawable(R.drawable.blue_piece_selected);
        }
        this.piecebounds = piecebounds;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (isSelected)
                isSelected = false;
            else
                isSelected = true;
            invalidate();
            return super.onTouchEvent(event);
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (isSelected) {
            selectedPiece.setBounds(piecebounds);
            selectedPiece.draw(canvas);
        } else {
            piece.setBounds(piecebounds);
            piece.draw(canvas);
        }
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
