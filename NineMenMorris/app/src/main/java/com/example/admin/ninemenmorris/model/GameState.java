package com.example.admin.ninemenmorris.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;

public class GameState implements Serializable {
    private LinkedList<HashMap<String, ?>> piecePlacements;
    private String pieceColor;
    private boolean ended;
    private boolean hasMill;
    private static int[] gameplan;
    private static int bluemarker, redmarker;
    private static int blueMarkersLeft, redMarkersLeft;
    private static int turn;
    public GameState() {
    }

    public LinkedList<HashMap<String, ?>> getPiecePlacements() {
        return piecePlacements;
    }

    public void setPiecePlacements(LinkedList<HashMap<String, ?>> piecePlacements) {
        this.piecePlacements = piecePlacements;
    }

    public String getPieceColor() {
        return pieceColor;
    }

    public void setPieceColor(String pieceColor) {
        this.pieceColor = pieceColor;
    }

    public boolean isEnded() {
        return ended;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }

    public boolean isHasMill() {
        return hasMill;
    }

    public void setHasMill(boolean hasMill) {
        this.hasMill = hasMill;
    }

    public static int[] getGameplan() {
        return gameplan;
    }

    public static void setGameplan(int[] gameplan) {
        GameState.gameplan = gameplan;
    }

    public static int getBluemarker() {
        return bluemarker;
    }

    public static void setBluemarker(int bluemarker) {
        GameState.bluemarker = bluemarker;
    }

    public static int getRedmarker() {
        return redmarker;
    }

    public static void setRedmarker(int redmarker) {
        GameState.redmarker = redmarker;
    }

    public static int getBlueMarkersLeft() {
        return blueMarkersLeft;
    }

    public static void setBlueMarkersLeft(int blueMarkersLeft) {
        GameState.blueMarkersLeft = blueMarkersLeft;
    }

    public static int getRedMarkersLeft() {
        return redMarkersLeft;
    }

    public static void setRedMarkersLeft(int redMarkersLeft) {
        GameState.redMarkersLeft = redMarkersLeft;
    }

    public static int getTurn() {
        return turn;
    }

    public static void setTurn(int turn) {
        GameState.turn = turn;
    }
}