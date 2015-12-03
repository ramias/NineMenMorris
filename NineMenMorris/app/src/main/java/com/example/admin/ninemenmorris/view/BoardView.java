package com.example.admin.ninemenmorris.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.ninemenmorris.R;
import com.example.admin.ninemenmorris.controller.PiecePlacer;

import java.util.HashMap;
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
    private Resources resources;

    public BoardView(Context context, AttributeSet attr) {
        super(context, attr);
        pieceList = new LinkedList<>();
        selectedPiecePosition = 0;
        placer = new PiecePlacer();
        pieceColor = "RED";
        hasMill = false;
        ended = false;
        resources = context.getResources();
        board = resources.getDrawable(R.drawable.board);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.i("nn", "this: w/2: " + this.getWidth()/2 + " this: h/2: " + this.getHeight()/2);
        Log.i("nn", "Board: w: " + board.getBounds().width() + "Board: h: " + board.getBounds().height());

        if(event.getAction()== MotionEvent.ACTION_MOVE){
            Log.i("coord","x: "+event.getX()+" y: "+event.getY());
        }
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

                    Log.i("nn", "Piecebounds: w: " + piecebounds.width() + " h: " + piecebounds.height());
                    Log.i("nn", "boardbounds: w: " + board.getBounds().width() + " h: " + board.getBounds().height());

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

    public void updateView(){
        if (pieceColor.equals("RED")) {
            redTurnView.setVisibility(VISIBLE);
            blueTurnView.setVisibility(INVISIBLE);
        } else {
            blueTurnView.setVisibility(VISIBLE);
            redTurnView.setVisibility(INVISIBLE);
        }
        if(hasMill){
            statusView.setVisibility(VISIBLE);
        }else{
            statusView.setVisibility(INVISIBLE);
        }
        if(ended){
            endGame();
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
        int w =0, h=0;
        if(resources.getConfiguration().orientation == 1){ // PORTRAIT
            w = this.getWidth();
            h = this.getWidth();
        }else{
            w = this.getHeight();
            h = this.getHeight();
        }
       // w = (w+h)/(10);
       // h = (w+h)/10;
        int size = (w + h) / 2, offset = (w+h) / 14;
        Rect bounds = new Rect(0, 0, w, h);
        board.setBounds(bounds);
        board.draw(canvas);

        //Draw all pieces
        for (PieceView p : pieceList) {
            p.draw(canvas);
        }
    }


    // Nexus S: x: 230.98616 y: 223.0
    // Nexus 4: x: 375.9957 y: 358.00287
    // Nexus 5: x: 546.0 y: 495.18066

    private int validateCoords(int x, int y) {
        int w =0, h=0;
        if(resources.getConfiguration().orientation == 1){ // PORTRAIT
            w = this.getWidth();
            h = this.getWidth();
        }else{
            w = this.getHeight();
            h = this.getHeight();
        }

        Log.i("nn", "this: w: " + board.getBounds().width() + " h: " + board.getBounds().height());
        int size = (w + h) / 65;
        // Col, row
        // 1,1
        if (x < w / 31 + size && x > w / 31 - size && y < h / 31 + size && y > h / 31 - size) {
            piecebounds = new Rect(w / 31 - size, h / 31 - size, w / 31 + size, h / 31 + size);
            return 3;
        }
        // 4,1
        if (x < w / 2 + size && x > w / 2 - size && y < h / 31 + size && y > h / 31 - size) {
            piecebounds = new Rect(w / 2 - size, h / 31 - size, w / 2 + size, h / 31 + size);
            return 6;
        }
        // 7,1
        if (x < w * 30 / 31 + size && x > w * 30 / 31 - size && y < h / 31 + size && y > h / 31 - size) {
            piecebounds = new Rect(w * 30 / 31 - size, h / 31 - size, w * 30 / 31 + size, h / 31 + size);
            return 9;
        }
        // 7,4
        if (x < w * 30 / 31 + size && x > w * 30 / 31 - size && y < h / 2 + size && y > h / 2 - size) {
            piecebounds = new Rect(w * 30 / 31 - size, h / 2 - size, (w * 30) / 31 + size, h / 2 + size);
            return 12;
        }
        // 7,7
        if (x < w * 30 / 31 + size && x > w * 30 / 31 - size && y < h * 30 / 31 + size && y > h * 30 / 31 - size) {
            piecebounds = new Rect(w * 30 / 31 - size, h * 30 / 31 - size, (w * 30) / 31 + size, h * 30 / 31 + size);
            return 15;
        }
        // 4, 7
        if (x < w / 2 + size && x > w / 2 - size && y < h * 30 / 31 + size && y > h * 30 / 31 - size) {
            piecebounds = new Rect(w / 2 - size, h * 30 / 31 - size, w / 2 + size, h * 30 / 31 + size);
            return 18;
        }
        // 1,7
        if (x < w / 31 + size && x > w / 31 - size && y < h * 30 / 31 + size && y > h * 30 / 31 - size) {
            piecebounds = new Rect(w / 31 - size, h * 30 / 31 - size, w / 31 + size, h * 30 / 31 + size);
            return 21;
        }
        // 1,4
        if (x < w / 31 + size && x > w / 31 - size && y < h / 2 + size && y > h / 2 - size) {
            piecebounds = new Rect(w / 31 - size, h / 2 - size, w / 31 + size, h / 2 + size);
            return 24;
        }
        //2,2
        if (x < w * 3 / 16 + size && x > w * 3 / 16 - size && y < h *19/100 + size && y > h *19/100 - size) {
            piecebounds = new Rect(w * 3 / 16 - size, h * 19/ 100 - size, w * 3 / 16 + size, h *19/ 100 + size);
            return 2;
        }
        //4,2
        if (x < w / 2 + size && x > w / 2 - size && y < h * 19/ 100 + size && y > h * 19/ 100 - size) {
            piecebounds = new Rect(w / 2 - size, h * 19/ 100 - size, w / 2 + size, h * 19/ 100 + size);
            return 5;
        }
        //6,2
        if (x < w * 13 / 16 + size && x > w * 13 / 16 - size && y < h * 19/ 100 + size && y > h * 19/ 100 - size) {
            piecebounds = new Rect(w * 13 / 16 - size, h * 19/ 100 - size, w * 13 / 16 + size, h * 19/ 100 + size);
            return 8;
        }
        //6,4
        if (x < w * 13 / 16 + size && x > w * 13 / 16 - size && y < h / 2 + size && y > h / 2 - size) {
            piecebounds = new Rect(w * 13 / 16 - size, h / 2 - size, w * 13 / 16 + size, h / 2 + size);
            return 11;
        }
        //6,6
        if (x < w * 13 / 16 + size && x > w * 13 / 16 - size && y < h * 81/ 100 + size && y > h * 81/100 - size) {
            piecebounds = new Rect(w * 13 / 16 - size, h * 81/100 - size, w * 13 / 16 + size, h * 81/100 + size);
            return 14;
        }
        //4,6
        if (x < w / 2 + size && x > w / 2 - size && y < h * 81/100 + size && y > h * 81/100 - size) {
            piecebounds = new Rect(w / 2 - size, h * 81/100 - size, w / 2 + size, h * 81/100 + size);
            return 17;
        }
        //2,6
        if (x < w * 3 / 16 + size && x > w * 3 / 16 - size && y < h * 81/100 + size && y > h * 81/100 - size) {
            piecebounds = new Rect(w * 3 / 16 - size, h * 81/100 - size, w * 3 / 16 + size, h * 81/100 + size);
            return 20;
        }
        //2,4
        if (x < w * 3 / 16 + size && x > w * 3 / 16 - size && y < h / 2 + size && y > h / 2 - size) {
            piecebounds = new Rect(w * 3 / 16 - size, h / 2 - size, w * 3 / 16 + size, h / 2 + size);
            return 23;
        }
        //3,3
        if (x < w * 17 / 49 + size && x > w * 17 / 49 - size && y < h * 17 / 50 + size && y > h * 17 / 50 - size) {
            piecebounds = new Rect(w * 17 / 49 - size, h * 17 / 50 - size, w * 17 / 49 + size, h * 17 / 50 + size);
            return 1;
        }
        //4,3
        if (x < w / 2 + size && x > w / 2 - size && y < h * 17 / 50 + size && y > h * 17 / 50 - size) {
            piecebounds = new Rect(w / 2 - size, h * 17 / 50 - size, w / 2 + size, h * 17 / 50 + size);
            return 4;
        }
        //5,3
        if (x < w * 32 / 49 + size && x > w * 32 / 49 - size && y < h * 17 / 50 + size && y > h * 17 / 50 - size) {
            piecebounds = new Rect(w * 32 / 49 - size, h * 17 / 50 - size, w * 32 / 49 + size, h * 17 / 50 + size);
            return 7;
        }
        //5,4
        if (x < w * 32 / 49 + size && x > w * 32 / 49 - size && y < h / 2 + size && y > h / 2 - size) {
            piecebounds = new Rect(w * 32 / 49 - size, h / 2 - size, w * 32 / 49 + size, h / 2 + size);
            return 10;
        }
        //5,5
        if (x < w * 32 / 49 + size && x > w * 32 / 49 - size && y < h * 33 / 50 + size && y > h * 33 / 50 - size) {
            piecebounds = new Rect(w * 32 / 49 - size, h * 33 / 50 - size, w * 32 / 49 + size, h * 33 / 50 + size);
            return 13;
        }
        //4,5
        if (x < w / 2 + size && x > w / 2 - size && y < h * 33 / 50 + size && y > h * 33 / 50 - size) {
            piecebounds = new Rect(w / 2 - size, h * 33 / 50 - size, w / 2 + size, h * 33 / 50 + size);
            return 16;
        }
        //3,5
        if (x < w * 17 / 49 + size && x > w * 17 / 49 - size && y < h * 33 / 50 + size && y > h * 33 / 50 - size) {
            piecebounds = new Rect(w * 17 / 49 - size, h * 33 / 50 - size, w * 17 / 49 + size, h * 33 / 50 + size);
            return 19;
        }
        //3,4
        if (x < w * 17 / 49 + size && x > w * 17 / 49 - size && y < h / 2 + size && y > h / 2 - size) {
            piecebounds = new Rect(w * 17 / 49 - size, h / 2 - size, w * 17 / 49 + size, h / 2 + size);
            return 22;
        }
        return -1;

    }

    public LinkedList<HashMap<String,?>> getPiecePlacements() {
        LinkedList<HashMap<String,?>> placement = new LinkedList<>();
        for (PieceView p : pieceList) {
            HashMap hs = new HashMap();
            hs.put("left",p.getPiecebounds().left);
            hs.put("top",p.getPiecebounds().top);
            hs.put("right",p.getPiecebounds().right);
            hs.put("bottom",p.getPiecebounds().bottom);

            hs.put("position",p.getPosition());
            hs.put("color",p.getColor());
            placement.add(hs);
        }
        return placement;
    }

    public void setPieceList(LinkedList<PieceView> pieceList) {
        this.pieceList = pieceList;
    }

    public void setPieceColor(String pieceColor) {
        this.pieceColor = pieceColor;

    }

    public boolean isEnded() {
        return ended;
    }

    public boolean isHasMill() {
        return hasMill;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }

    public void setHasMill(boolean hasMill) {
        this.hasMill = hasMill;
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

    public String getPieceColor() {
        return pieceColor;
    }
}
