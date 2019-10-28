package mh.myapplication.com;

import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import mh.myapplication.com.Model.Card;
import mh.myapplication.com.Model.Game;
import mh.myapplication.com.Model.Table;

public class MainActivity extends AppCompatActivity implements Game.GameListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Game game;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prepareGame();
    }

    private void prepareGame()
    {
        game = new Game(Game.GAME_MODE_HARD);

        GridLayout gl = findViewById(R.id.gridLayout_cards);
        Display display = getWindowManager().getDefaultDisplay();

        //prepareResources
        int[] resources = new int[20];
        int resID;

        for(int imgnum=0;imgnum<21;imgnum++) {
            resID = getResources().getIdentifier("img_" + imgnum, "drawable", "mh.myapplication.com");
            resources[imgnum] = resID;
        }

        game.initTable(gl,display,resources);
        game.setListener(this);
        game.startGame();
    }

    @Override
    public void onGameFinished(int score) {
        Toast.makeText(this,"Skorunuz: "+String.valueOf(score),Toast.LENGTH_SHORT).show();
    }
}
