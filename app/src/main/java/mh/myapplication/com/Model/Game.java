package mh.myapplication.com.Model;

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

    private Card lastCard = null;
    private int knowCards = 0;

    private int startTime;
    private boolean secoundCard =false;

    public Game(int gameMode) {
        this.gameMode = gameMode;
    }

    public void initTable(GridLayout layout, Display display, int[] resources)
    {
        List<Integer> usedResources = new ArrayList<>();
       // List<Table.TableIndex> usedIndexes = new ArrayList<>();
        /*List<Integer> usedRows = new ArrayList<>();
        List<Integer> usedCols = new ArrayList<>();*/
        switch (gameMode)
        {
            case GAME_MODE_EASY:
                gameTable = new Table(layout,display,2,3);
                cardsSize = 3;
                break;

            case GAME_MODE_MEDIUM:
                gameTable = new Table(layout,display,3,4);
                cardsSize = 6;
                break;

            case GAME_MODE_HARD:
                gameTable = new Table(layout,display,3,6);
                cardsSize = 9;
                break;

                default:
                    return;
        }

        for(int i=0;i<gameTable.getRowCount();i++)
        {
            for(int j=0;j<gameTable.getColCount()-1;j+=2) {

                int rand;

                //decide image resource
                do {
                    rand = (int) (Math.random() * resources.length);
                } while (usedResources.contains(resources[rand]));

                Card card = new Card(layout.getContext(), resources[rand]);
                Card sameCard = new Card(layout.getContext(), resources[rand]);

                gameTable.addCard(card,i,j);
                gameTable.addCard(sameCard,i,j+1);

                usedResources.add(resources[rand]);
            }
        }

        shuffleCards();
        gameTable.setListener(this);
    }

    public void startGame()
    {
        startTime = (int) (System.currentTimeMillis());
    }

    ///TODO shuffle cards on table
    private void shuffleCards()
    {

    }

    public void setListener(GameListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCardClick(final Card card) {
        card.toggle();
        if(!secoundCard) {
            lastCard = card;
            secoundCard = true;
        }
        else
        {
            if(card.compareTo(lastCard) == 0) {
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                card.delete();
                                lastCard.delete();
                                knowCards++;
                            }
                        }, 1000);
            }
            else {
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                card.toggle();
                                lastCard.toggle();
                            }
                        }, 1000);

            }
            secoundCard = false;

        }

        //check game is finished
        //calculate score depend on game mode and time
        if(knowCards == cardsSize && listener != null)
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
