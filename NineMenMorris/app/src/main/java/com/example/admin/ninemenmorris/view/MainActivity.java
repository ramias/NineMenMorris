package com.example.admin.ninemenmorris.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.admin.ninemenmorris.R;

public class MainActivity extends AppCompatActivity {
    private final int NEW_GAME = 3300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(
                R.id.toolbar);
        setSupportActionBar(toolbar);

        FrameLayout rootLayout = (FrameLayout)findViewById(R.id.framelo);
        rootLayout.addView(new BoardView(this));
        rootLayout.addView(new PieceView(this));


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
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
        if (id == R.id.action_new_game) {
            //Start new game
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
