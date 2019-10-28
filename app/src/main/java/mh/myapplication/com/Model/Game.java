package mh.myapplication.com.Model;

import android.content.Context;
import android.view.Display;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

public class Game implements Table.CardClickListener{

    public static final int GAME_MODE_EASY = 1;
    public static final int GAME_MODE_MEDIUM = 2;
    public static final int GAME_MODE_HARD = 3;

    private GameListener listener;

    private Table gameTable = null;
    private int gameMode;
    private int cardsSize;

    private Card openedCard = null;
    private int knowedCards = 0;

    private int startTime;

    public Game(int gameMode) {
        this.gameMode = gameMode;
    }

    public void initTable(GridLayout layout, Display display, int[] resources)
    {
        List<Integer> usedResources = new ArrayList<>();
        List<Integer> usedRows = new ArrayList<>();
        List<Integer> usedCols = new ArrayList<>();
        switch (gameMode)
        {
            case GAME_MODE_EASY:
                gameTable = new Table(layout,display,2,3);
                cardsSize = 3;
                break;

            case GAME_MODE_MEDIUM:
                gameTable = new Table(layout,display,4,3);
                cardsSize = 6;
                break;

            case GAME_MODE_HARD:
                gameTable = new Table(layout,display,6,3);
                cardsSize = 9;
                break;

                default:
                    return;
        }

        for(int i=0;i<cardsSize;i++)
        {
            int rand;
            int row;
            int col;

            //decide image resource
            do {
                rand = (int) (Math.random() * resources.length);
            }while (usedResources.contains(resources[rand]));

            Card card = new Card(layout.getContext(),resources[rand]);

            //put card different two places on table
            for(int j=0;j<2;j++) {
                //decide card row
                do {
                    rand = (int) (Math.random() * gameTable.getRowCount());
                } while (usedRows.contains(rand));

                row = rand;

                do {
                    rand = (int) (Math.random() * gameTable.getColCount());
                } while (usedCols.contains(rand));

                col = rand;
                gameTable.addCard(card,row,col);
                usedRows.add(row);
                usedCols.add(col);
            }

            usedResources.add(resources[rand]);
        }

        gameTable.setListener(this);
    }

    public void startGame()
    {
        startTime = (int) (System.currentTimeMillis());
    }

    public void setListener(GameListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCardClick(Card card) {
        card.toggle();
        if(openedCard == null)
            openedCard = card;
        else
        {
            if(card == openedCard) {
                card.delete();
                openedCard.delete();
                knowedCards++;
            }
            else
                openedCard.toggle();
        }

        //check game is finished
        //calculate score depend on game mode and time
        if(knowedCards == cardsSize && listener != null)
        {
            int finishTime = (int) (System.currentTimeMillis());
            int score = (finishTime-startTime)*gameMode;
            listener.onGameFinished(score);
        }

    }

    public interface GameListener {
        void onGameFinished(int score);
    }
}
