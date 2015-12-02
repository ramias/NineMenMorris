package com.example.admin.ninemenmorris.controller;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.admin.ninemenmorris.R;
import com.example.admin.ninemenmorris.model.GameState;
import com.example.admin.ninemenmorris.model.NineMenMorrisRules;
import com.example.admin.ninemenmorris.view.BoardView;
import com.example.admin.ninemenmorris.view.PieceView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    private final int NEW_GAME = 3300;
    private BoardView board;
    private GameState gameState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(
                R.id.toolbar);
        setSupportActionBar(toolbar);
        board = (BoardView) findViewById(R.id.boardview);

        board.setRedTurnView((TextView) findViewById(R.id.tvRED));
        board.setBlueTurnView((TextView) findViewById(R.id.tvBLUE));
        board.setStatusView((TextView) findViewById(R.id.tvSTATUS));
        board.updateView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (stateFileExists()) {
            gameState = loadState();
            LinkedList<PieceView> pieceList = new LinkedList<>();
            for (HashMap hm : gameState.getPiecePlacements()) {
                pieceList.add(new PieceView(this, new Rect((int) hm.get("left"), (int) hm.get("top"), (int) hm.get("right"), (int) hm.get("bottom")), (int) hm.get("position"), (String) hm.get("color")));
            }
            board.setPieceList(pieceList);
            board.setPieceColor(gameState.getPieceColor());
            board.setEnded(gameState.isEnded());
            board.setHasMill(gameState.isHasMill());

            NineMenMorrisRules.setGameplan(gameState.getGameplan());
            NineMenMorrisRules.setTurn(gameState.getTurn());
            NineMenMorrisRules.setRedmarker(gameState.getRedmarker());
            NineMenMorrisRules.setBluemarker(gameState.getBluemarker());
            NineMenMorrisRules.setRedMarkersLeft(gameState.getRedMarkersLeft());
            NineMenMorrisRules.setBlueMarkersLeft(gameState.getBlueMarkersLeft());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, NEW_GAME, Menu.NONE, "New Game");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == NEW_GAME) {
            newGame();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        saveState();
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private boolean stateFileExists() {
        File file = getBaseContext().getFileStreamPath("gamestate");
        return file.exists();
    }

    private GameState loadState() {
        GameState gs = new GameState();
        InputStream inputStream = null;
        ObjectInputStream ois = null;
        try {
            inputStream = getApplicationContext().openFileInput("gamestate");
            if (inputStream != null) {
                ois = new ObjectInputStream(inputStream);
                gs = (GameState) ois.readObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return gs;
    }

    private void saveState() {
        GameState gs = new GameState();
        gs.setPiecePlacements(board.getPiecePlacements());
        gs.setEnded(board.isEnded());
        gs.setHasMill(board.isHasMill());
        gs.setPieceColor(board.getPieceColor());
        gs.setTurn(NineMenMorrisRules.getTurn());
        gs.setRedmarker(NineMenMorrisRules.getRedmarker());
        gs.setBluemarker(NineMenMorrisRules.getBluemarker());
        gs.setRedMarkersLeft(NineMenMorrisRules.getRedMarkersLeft());
        gs.setBlueMarkersLeft(NineMenMorrisRules.getBlueMarkersLeft());
        Log.i("gameplan","savestate " +  Arrays.toString(gs.getGameplan()));
        gs.setGameplan(NineMenMorrisRules.getGameplan());

        FileOutputStream outputStream = null;
        ObjectOutputStream oos = null;
        try {
            outputStream = getApplicationContext().openFileOutput("gamestate", Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(outputStream);
            oos.writeObject(gs);
        } catch (Exception e) {
            Log.e("Exception", "File write failed: " + e.toString());
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    private void newGame() {
        GameState gs = new GameState();
        gs.setPiecePlacements(null);
        gs.setEnded(false);
        gs.setHasMill(false);
        gs.setPieceColor("RED");
        gs.setTurn(NineMenMorrisRules.RED_MOVES);
        gs.setRedmarker(9);
        gs.setBluemarker(9);
        gs.setRedMarkersLeft(9);
        gs.setGameplan(new int[25]);
        gs.setBlueMarkersLeft(9);
        Log.i("gameplan", "new game " + Arrays.toString(gs.getGameplan()));
        FileOutputStream outputStream = null;
        ObjectOutputStream oos = null;
        try {
            outputStream = getApplicationContext().openFileOutput("gamestate", Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(outputStream);
            oos.writeObject(gs);
        } catch (Exception e) {
            Log.e("Exception", "File write failed: " + e.toString());
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
