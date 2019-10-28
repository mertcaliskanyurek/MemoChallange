package mh.myapplication.com.Model;

import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

public class Table implements View.OnClickListener {

    private List<Card> cards;
    private GridLayout layout;
    private CardClickListener listener = null;

    private int imageSize;

    public Table(GridLayout layout, Display display,int rowCount,int colmCount) {
        this.layout = layout;
        prepareTable(display,rowCount,colmCount);
    }

    private void prepareTable(Display display,int rowCount,int colmCount)
    {
        this.layout.setRowCount(rowCount);
        this.layout.setColumnCount(colmCount);
        //calculate image size
        Point size = new Point();
        display.getSize(size);
        this.imageSize = (size.x/colmCount)-20;
        //this.imageSize = 100;
        cards = new ArrayList<>();
    }

    public void addCard(Card card,int row,int col)
    {
        if(row>getRowCount() || col>getColCount())
            throw new IndexOutOfBoundsException("Card index out of bounds");

        GridLayout.LayoutParams parms = new GridLayout.LayoutParams(
                GridLayout.spec(row, GridLayout.CENTER),
                GridLayout.spec(col, GridLayout.CENTER));
        parms.width = this.imageSize;
        parms.height = this.imageSize;
        parms.setMargins(10,10,10,10);

        card.setLayoutParams(parms);
        card.setOnClickListener(this);

        cards.add(card);

        layout.addView(card, parms);
    }

    public int getRowCount()
    {
        return this.layout.getRowCount();
    }

    public int getColCount()
    {
        return this.layout.getColumnCount();
    }

    public void setListener(CardClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Card card=null;
        //findClickedCard
        for(Card c:cards)
            if(c.getId()==id)
                card = c;
        if(this.listener != null && card!=null)
            listener.onCardClick(card);
    }

    public static class TableIndex implements Comparable<TableIndex>{

        private int row;
        private int col;

        public TableIndex(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }


        @Override
        public int compareTo(TableIndex o) {
            return (this.row-o.row)+(this.col-o.col);
        }
    }

    interface CardClickListener {
        void onCardClick(Card card);
    }
}
