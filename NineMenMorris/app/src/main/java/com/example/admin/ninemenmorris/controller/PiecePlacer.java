package com.example.admin.ninemenmorris.controller;

import android.util.Log;

import com.example.admin.ninemenmorris.model.NineMenMorrisRules;

/**
 * Created by Rami on 2015-11-28.
 */
public class PiecePlacer {
    private NineMenMorrisRules game;
    public static final int NO_ACTION = 0, NEW_PIECE = 1, MOVE_PIECE = 2;

    public PiecePlacer() {
        game = new NineMenMorrisRules();
    }

    public int touchOn(int position) {
        int from;
        //Checks if the player has selected a piece or is placing a new piece on the board
        // All pieces from a player has to be played out before a player can select a piece.
        if (game.board(position) != game.EMPTY_SPACE) {
            if(game.isSelectable()){
                return MOVE_PIECE;
            }
        } else {
            from = 0;
            if (game.legalMove(position, from)) {
                return NEW_PIECE;
            }
        }
        return NO_ACTION;
    }

    public void switchBoardPositions(int from, int to) {
        game.switchBoardPositions(from, to);
    }

    public boolean has3InRow(int position) {
        return game.remove(position); // konstigt namn, remove?
    }
}
