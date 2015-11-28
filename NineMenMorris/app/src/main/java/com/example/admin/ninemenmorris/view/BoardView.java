package com.example.admin.ninemenmorris.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.admin.ninemenmorris.R;
import com.example.admin.ninemenmorris.controller.PiecePlacer;

import java.util.LinkedList;

/**
 * Created by Rami on 2015-11-25.
 */
public class BoardView extends View {
    private Drawable board;
    private PiecePlacer placer;
    private Rect piecebounds = null;
    private LinkedList<PieceView> pieceList;
    private String pieceColor;

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        pieceList = new LinkedList<>();
        placer = new PiecePlacer();
        pieceColor = "RED";
        Resources resources = context.getResources();
        board = (Drawable)
                resources.getDrawable(R.drawable.board);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            int position = validateCoords(x, y);
            if (position > 0) {
                int action = placer.touchOn(position);
                if (action == placer.NEW_PIECE) {
                    pieceList.add(new PieceView(this.getContext(), piecebounds, position, pieceColor));
                    toggleColor();
                    //unfinished code below (else if)
//                } else if (action == placer.MOVE_PIECE) {
//                    moviePiece(event,position,piecebounds);
//                    toggleColor();
                } else {
                    selectPiece(event, position);
                }
            }
            invalidate();
            return true;
        }

        return false;
    }
    //Unfinished code
    private void moviePiece(MotionEvent event, int position, Rect piecebounds) {
        for (PieceView p : pieceList) {
            if (p.getPosition() == position) {
                p.setPosition(position);
                p.setPiecebounds(piecebounds);
                break;
            }
        }
    }

    private void toggleColor() {
        if (pieceColor.equals("RED"))
            pieceColor = "BLUE";
        else
            pieceColor = "RED";
    }

    /*
        Dispatches the event to the specific piece
     */
    private void selectPiece(MotionEvent event, int pos) {
        for (PieceView p : pieceList) {
            if (p.getPosition() == pos) {
                p.dispatchTouchEvent(event);
                break;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int w = this.getWidth(), h = this.getHeight();
        int size = (w + h) / 2, offset = (w + h) / 14;
        Rect bounds = new Rect(offset + w - size, h - size, size - offset, size);
        board.setBounds(bounds);
        board.draw(canvas);

        //Draw all pieces
        for (PieceView p : pieceList) {
            p.draw(canvas);
        }

    }

    private int validateCoords(int x, int y) {
        int w = this.getWidth(), h = this.getHeight();
        int size = (w + h) / 65;
        // Col, row
        // 1,1
        if (x < w / 28 + size && x > w / 28 - size && y < h / 7 + size && y > h / 7 - size) {
            piecebounds = new Rect(w / 28 - size, h / 7 - size, w / 28 + size, h / 7 + size);
            return 3;
        }
        // 4,1
        if (x < w / 2 + size && x > w / 2 - size && y < h / 7 + size && y > h / 7 - size) {
            piecebounds = new Rect(w / 2 - size, h / 7 - size, w / 2 + size, h / 7 + size);
            return 6;
        }
        // 7,1
        if (x < w * 27 / 28 + size && x > w * 27 / 28 - size && y < h / 7 + size && y > h / 7 - size) {
            piecebounds = new Rect(w * 27 / 28 - size, h / 7 - size, w * 27 / 28 + size, h / 7 + size);
            return 9;
        }
        // 7,4
        if (x < w * 27 / 28 + size && x > w * 27 / 28 - size && y < h / 2 + size && y > h / 2 - size) {
            piecebounds = new Rect(w * 27 / 28 - size, h / 2 - size, (w * 27) / 28 + size, h / 2 + size);
            return 12;
        }
        // 7,7
        if (x < w * 27 / 28 + size && x > w * 27 / 28 - size && y < h * 6 / 7 + size && y > h * 6 / 7 - size) {
            piecebounds = new Rect(w * 27 / 28 - size, h * 6 / 7 - size, (w * 27) / 28 + size, h * 6 / 7 + size);
            return 15;
        }
        // 4, 7
        if (x < w / 2 + size && x > w / 2 - size && y < h * 6 / 7 + size && y > h * 6 / 7 - size) {
            piecebounds = new Rect(w / 2 - size, h * 6 / 7 - size, w / 2 + size, h * 6 / 7 + size);
            return 18;
        }
        // 1,7
        if (x < w / 28 + size && x > w / 28 - size && y < h * 6 / 7 + size && y > h * 6 / 7 - size) {
            piecebounds = new Rect(w / 28 - size, h * 6 / 7 - size, w / 28 + size, h * 6 / 7 + size);
            return 21;
        }
        // 1,4
        if (x < w / 28 + size && x > w / 28 - size && y < h / 2 + size && y > h / 2 - size) {
            piecebounds = new Rect(w / 28 - size, h / 2 - size, w / 28 + size, h / 2 + size);
            return 24;
        }
        //2,2
        if (x < w * 3 / 16 + size && x > w * 3 / 16 - size && y < h * 13 / 50 + size && y > h * 13 / 50 - size) {
            piecebounds = new Rect(w * 3 / 16 - size, h * 13 / 50 - size, w * 3 / 16 + size, h * 13 / 50 + size);
            return 2;
        }
        //4,2
        if (x < w / 2 + size && x > w / 2 - size && y < h * 4 / 15 + size && y > h * 4 / 15 - size) {
            piecebounds = new Rect(w / 2 - size, h * 4 / 15 - size, w / 2 + size, h * 4 / 15 + size);
            return 5;
        }
        //6,2
        if (x < w * 13 / 16 + size && x > w * 13 / 16 - size && y < h * 13 / 50 + size && y > h * 13 / 50 - size) {
            piecebounds = new Rect(w * 13 / 16 - size, h * 13 / 50 - size, w * 13 / 16 + size, h * 13 / 50 + size);
            return 8;
        }
        //6,4
        if (x < w * 13 / 16 + size && x > w * 13 / 16 - size && y < h / 2 + size && y > h / 2 - size) {
            piecebounds = new Rect(w * 13 / 16 - size, h / 2 - size, w * 13 / 16 + size, h / 2 + size);
            return 11;
        }
        //6,6
        if (x < w * 13 / 16 + size && x > w * 13 / 16 - size && y < h * 37 / 50 + size && y > h * 37 / 50 - size) {
            piecebounds = new Rect(w * 13 / 16 - size, h * 37 / 50 - size, w * 13 / 16 + size, h * 37 / 50 + size);
            return 14;
        }
        //4,6
        if (x < w / 2 + size && x > w / 2 - size && y < h * 37 / 50 + size && y > h * 37 / 50 - size) {
            piecebounds = new Rect(w / 2 - size, h * 37 / 50 - size, w / 2 + size, h * 37 / 50 + size);
            return 17;
        }
        //2,6
        if (x < w * 3 / 16 + size && x > w * 3 / 16 - size && y < h * 37 / 50 + size && y > h * 37 / 50 - size) {
            piecebounds = new Rect(w * 3 / 16 - size, h * 37 / 50 - size, w * 3 / 16 + size, h * 37 / 50 + size);
            return 20;
        }
        //2,4
        if (x < w * 3 / 16 + size && x > w * 3 / 16 - size && y < h / 2 + size && y > h / 2 - size) {
            piecebounds = new Rect(w * 3 / 16 - size, h / 2 - size, w * 3 / 16 + size, h / 2 + size);
            return 23;
        }
        //3,3
        if (x < w * 17 / 49 + size && x > w * 17 / 49 - size && y < h * 19 / 50 + size && y > h * 19 / 50 - size) {
            piecebounds = new Rect(w * 17 / 49 - size, h * 19 / 50 - size, w * 17 / 49 + size, h * 19 / 50 + size);
            return 1;
        }
        //4,3
        if (x < w / 2 + size && x > w / 2 - size && y < h * 19 / 50 + size && y > h * 19 / 50 - size) {
            piecebounds = new Rect(w / 2 - size, h * 19 / 50 - size, w / 2 + size, h * 19 / 50 + size);
            return 4;
        }
        //5,3
        if (x < w * 32 / 49 + size && x > w * 32 / 49 - size && y < h * 19 / 50 + size && y > h * 19 / 50 - size) {
            piecebounds = new Rect(w * 32 / 49 - size, h * 19 / 50 - size, w * 32 / 49 + size, h * 19 / 50 + size);
            return 7;
        }
        //5,4
        if (x < w * 32 / 49 + size && x > w * 32 / 49 - size && y < h / 2 + size && y > h / 2 - size) {
            piecebounds = new Rect(w * 32 / 49 - size, h / 2 - size, w * 32 / 49 + size, h / 2 + size);
            return 10;
        }
        //5,5
        if (x < w * 32 / 49 + size && x > w * 32 / 49 - size && y < h * 31 / 50 + size && y > h * 31 / 50 - size) {
            piecebounds = new Rect(w * 32 / 49 - size, h * 31 / 50 - size, w * 32 / 49 + size, h * 31 / 50 + size);
            return 13;
        }
        //4,5
        if (x < w / 2 + size && x > w / 2 - size && y < h * 31 / 50 + size && y > h * 31 / 50 - size) {
            piecebounds = new Rect(w / 2 - size, h * 31 / 50 - size, w / 2 + size, h * 31 / 50 + size);
            return 16;
        }
        //3,5
        if (x < w * 17 / 49 + size && x > w * 17 / 49 - size && y < h * 31 / 50 + size && y > h * 31 / 50 - size) {
            piecebounds = new Rect(w * 17 / 49 - size, h * 31 / 50 - size, w * 17 / 49 + size, h * 31 / 50 + size);
            return 19;
        }
        //3,4
        if (x < w * 17 / 49 + size && x > w * 17 / 49 - size && y < h / 2 + size && y > h / 2 - size) {
            piecebounds = new Rect(w * 17 / 49 - size, h / 2 - size, w * 17 / 49 + size, h / 2 + size);
            return 22;
        }
        return -1;

    }
}
