package com.example.admin.ninemenmorris.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.admin.ninemenmorris.R;

public class MainActivity extends AppCompatActivity {
    private final int NEW_GAME = 3300;
    private BoardView board;

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.removeItem(0);
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
            recreate();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
