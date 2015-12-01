package com.example.admin.ninemenmorris.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.admin.ninemenmorris.R;

/**
 * Created by Thomas on 2015-12-01.
 */
public class txtView extends View {

    private Drawable txtDrawable;
    private String imgToDisplay;
    private Rect txtBounds = null;

    public txtView(Context context,int w, int h, String imgToDisplay) {
        super(context);
       // Log.i("wh","W: "+w+" H: "+h); // landscape: 1216 x 326 // portrait: 656 x 870
        Resources resources = context.getResources();
        this.imgToDisplay = imgToDisplay;
        if(imgToDisplay.equals("RED")) {
            txtDrawable = (Drawable) resources.getDrawable(R.drawable.red_playertxt);
        }else if(imgToDisplay.equals("BLUE")){
            txtDrawable = (Drawable) resources.getDrawable(R.drawable.blue_playertxt);
        }else if(imgToDisplay.equals("MILL")){
            txtDrawable = (Drawable) resources.getDrawable(R.drawable.have_mill);
            txtBounds = new Rect(220,55,420,75); // hårdkodat än så länge
            txtDrawable.setBounds(txtBounds);
            return;
        }
        txtBounds = new Rect(0,0,180,25); // Rect(int left, int top, int right, int bottom)
        txtDrawable.setBounds(txtBounds);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        invalidate();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        txtDrawable.draw(canvas);
    }
}
