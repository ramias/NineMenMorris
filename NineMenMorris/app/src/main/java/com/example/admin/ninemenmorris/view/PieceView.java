package com.example.admin.ninemenmorris.view;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.admin.ninemenmorris.R;

/**
 * Created by Rami on 2015-11-26.
 */
public class PieceView extends View{
    private Drawable redpiece,bluepiece;
    private Rect piecebounds = null;

    public PieceView(Context context) {
        super(context);
        Resources resources = context.getResources();
        redpiece = (Drawable)
                resources.getDrawable(R.drawable.red_piece);
        bluepiece = (Drawable)
                resources.getDrawable(R.drawable.blue_piece);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN ||
                event.getAction() == MotionEvent.ACTION_UP) {
            int w = this.getWidth()/15, h = this.getHeight()/15;
            int x = (int) event.getX();
            int y = (int) event.getY();
            piecebounds = new Rect(x - w / 2, y - w / 2, x + w / 2, y + h / 2);
            invalidate();
            return true;
        }

        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Button theButton = (Button)findViewById(R.id.theButton);
        theButton.setVisibility(View.VISIBLE);
        theButton.setBackgroundColor(Color.TRANSPARENT);

        if(piecebounds != null) {
            redpiece.setBounds(piecebounds);
            redpiece.draw(canvas);
        }
    }
}
