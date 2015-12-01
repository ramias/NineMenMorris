package com.example.admin.ninemenmorris.controller;

import com.example.admin.ninemenmorris.model.NineMenMorrisRules;

/**
 * Created by Rami on 2015-11-28.
 */
public class PiecePlacer {
    private NineMenMorrisRules game;

    public static final int NO_ACTION = 0, NEW_PIECE = 1, SELECT_PIECE = 2, DESELECT_PIECE = 3, MOVE_PIECE = 4;

    public PiecePlacer() {
        game = new NineMenMorrisRules();
    }

    public int touchOn(int position, int selectedPiecePosition) {
        if (game.getTotalMarks() > 0) { // 1st stage: cant move only place.
            if (game.legalPlacement(position)) {
                return NEW_PIECE;
            }
        } else {  //2nd stage: can move pieces
            if (selectedPiecePosition != position) {
                if (selectedPiecePosition > 0) {
                    if (game.legalMove(position, selectedPiecePosition)) {
                        return MOVE_PIECE;
                    } else {
                        return NO_ACTION;
                    }
                } else {
                    if (game.board(position) == game.getTurnMarker()) {
                        return SELECT_PIECE;
                    }
                    return NO_ACTION;
                }
            } else {
                return DESELECT_PIECE;
            }
        }

        return NO_ACTION;
    }

    public boolean hasMill(int position){
        return game.hasMill(position);
    }

    // Är det tex. röd spelare som ska ta bort en bricka sätts color alltså till 4 = blå
    public boolean remove(int position) {
        return game.remove(position);
    }


    public String win() {
        return game.win();
    }
}
