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
    private boolean pieceSelected; // flag to indicate if a piece has been selected.
    private int selectedPiecePosition; // If user selects a piece the position is stored here so that the position can be marked as empty later on.

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        pieceList = new LinkedList<>();
        placer = new PiecePlacer();
        pieceColor = "RED";
        pieceSelected = false;
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

                if(pieceSelected){
                    if(selectedPieceAction(action, position, event)){
                        return true;
                    }else{
                        return false;
                    }
                }
                // if-blocket kollar om man har markerat en bricka och valt att placera den på en ny tom position
                if (action == placer.NEW_PIECE) { // Places a new brick on the board
                    pieceList.add(new PieceView(this.getContext(), piecebounds, position, pieceColor));
                    // Check if player has a mill
                    toggleColor();
                    // Selects a excisting brick from the bord if all bricks have been played out
                } else if (action == placer.MOVE_PIECE) {
                    selectedPiecePosition = position;
                    selectPiece(event, position);
                    pieceSelected = true;
                }
            }
            invalidate();
            return true;
        }
        return false;
    }

    // Du får gärna döpa om den här metoden :P
    // Method for moving a piece around the board
    private boolean selectedPieceAction(int action, int position, MotionEvent event) {
        if (action == 0) {
            //metod som flyttar den markerade brickan
            moviePiece(event, position, piecebounds);
            // Markerar den tidigare positionen som tom och den nya som upptagen.
            placer.switchBoardPositions(selectedPiecePosition, position);
            // Check if player has a mill (3 in a row)
            boolean haveMill = placer.has3InRow(position);
            if (haveMill) {
                //Kommer vi in här så har vi 3 i rad och ska ges möjligheten att ta en bricka av motståndaren
                // Måste ta reda på vems tur det är.
                Log.i("ttt", " 3 In a row: " + haveMill);
            }
            pieceSelected = false;
            invalidate();
            return true;
            // else if-blocket kollar om man har markerat en bricka och sen väljer att av markera samma bricka.
        } else if (position == selectedPiecePosition) {
            pieceSelected = false;
            selectPiece(event, position);
            invalidate();
            return true;
        } else{
            return false;
        }
    }
    
    //Unfinished code
    private void moviePiece(MotionEvent event, int position, Rect piecebounds) {
        Log.i("mm","moviePiece pos: "+position);
        for (PieceView p : pieceList) {
            if (p.getPosition() == selectedPiecePosition) {
                p.setPosition(position);
                p.setPiecebounds(piecebounds);
                p.dispatchTouchEvent(event);
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
