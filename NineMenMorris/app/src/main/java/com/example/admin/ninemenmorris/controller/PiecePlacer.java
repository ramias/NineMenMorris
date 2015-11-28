package com.example.admin.ninemenmorris.controller;

import com.example.admin.ninemenmorris.model.NineMenMorrisRules;

/**
 * Created by Rami on 2015-11-28.
 */
public class PiecePlacer {
    private NineMenMorrisRules game;
    public static final int NEW_PIECE = 1;
    public static final int MOVE_PIECE = 2;

    public PiecePlacer() {
        game = new NineMenMorrisRules();
    }

    public int touchOn(int position) {
        int from;
        //Checks if the player has selected a piece
        if (game.board(position) != game.EMPTY_SPACE) {
            return MOVE_PIECE;
        } else {
            from = 0;
            if (game.legalMove(position, from)) {
                return NEW_PIECE;
            }
        }
        return 0;
    }
}
