package com.example.admin.ninemenmorris.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

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
    private boolean ended;
    private TextView redTurnView, blueTurnView, statusView;
    private boolean hasMill;
    private int selectedPiecePosition; // If user selects a piece the position is stored here so that the position can be marked as empty later on.


    public BoardView(Context context, AttributeSet attr) {
        super(context, attr);
        pieceList = new LinkedList<>();
        selectedPiecePosition = 0;
        placer = new PiecePlacer();
        pieceColor = "RED";
        hasMill = false;
        ended = false;
        Resources resources = context.getResources();
        board = resources.getDrawable(R.drawable.board);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && !ended) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            int position = validateCoords(x, y);
            if (position > 0) {
                // Om tidigare klick på skärmen har gett 3 i rad, hasMill=true
                if (hasMill) {
                    if (placer.remove(position)) {
                        removePiece(event, position);
                        if (placer.win().equals(pieceColor)) {
                            endGame();
                        } else {
                            toggleTurn();
                        }
                    }
                } else {
                    int action = placer.touchOn(position, selectedPiecePosition);
                    if (action == placer.NEW_PIECE) { // Places a new brick on the board
                        pieceList.add(new PieceView(this.getContext(), piecebounds, position, pieceColor));
                        // Selects an existing brick from the bord if all bricks have been played out
                        hasMill(position);
                    } else if (action == placer.SELECT_PIECE) {
                        selectPiece(event, position);
                    } else if (action == placer.DESELECT_PIECE) {
                        deselectPiece(event, position);
                    } else if (action == placer.MOVE_PIECE) {
                        moviePiece(position, piecebounds);
                        deselectPiece(event, position);
                        hasMill(position);
                    } else {
                        return false;
                    }

                }
                invalidate();
                return true;
            }
        }
        return false;
    }

    private void endGame() {
        statusView.setText(pieceColor + " PLAYER WON");
        if (pieceColor.equals("RED")) {
            statusView.setTextColor(Color.parseColor("#ee1818"));
        } else {
            statusView.setTextColor(Color.parseColor("#2c7def"));
        }
        statusView.setVisibility(VISIBLE);
        ended = true;
    }

    private void hasMill(int position) {
        // Check if player has a mill
        hasMill = placer.hasMill(position);
        if (!hasMill) {
            toggleTurn();
        } else {
            statusView.setVisibility(VISIBLE);
        }
    }

    private void removePiece(MotionEvent event, int position) {
        hasMill = false;
        statusView.setVisibility(INVISIBLE);
        // Om de blir exception! Testa iterator istället.
        for (PieceView p : pieceList) {
            if (p.getPosition() == position) {
                pieceList.remove(p);
                break;
            }
        }
    }

    //Unfinished code
    private void moviePiece(int position, Rect piecebounds) {
        for (PieceView p : pieceList) {
            if (p.getPosition() == selectedPiecePosition) {
                p.setPosition(position);
                p.setPiecebounds(piecebounds);
                break;
            }
        }
    }

    private void toggleTurn() {
        if (pieceColor.equals("RED")) {
            pieceColor = "BLUE";
            redTurnView.setVisibility(INVISIBLE);
            blueTurnView.setVisibility(VISIBLE);
        } else {
            pieceColor = "RED";
            blueTurnView.setVisibility(INVISIBLE);
            redTurnView.setVisibility(VISIBLE);
        }

    }

    /*
        Dispatches the event to the specific piece
     */
    private void selectPiece(MotionEvent event, int pos) {
        selectedPiecePosition = pos;
        for (PieceView p : pieceList) {
            if (p.getPosition() == pos) {
                p.dispatchTouchEvent(event);
                break;
            }
        }
    }

    private void deselectPiece(MotionEvent event, int pos) {
        selectedPiecePosition = 0;
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

    public void setRedTurnView(TextView redTurnView) {
        this.redTurnView = redTurnView;
    }

    public void setBlueTurnView(TextView blueTurnView) {
        this.blueTurnView = blueTurnView;
    }

    public void setStatusView(TextView statusView) {
        this.statusView = statusView;
    }
}
