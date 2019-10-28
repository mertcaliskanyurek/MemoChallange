package mh.myapplication.com.Model;

import android.content.Context;
import android.view.View;

import mh.myapplication.com.R;

public class Card extends android.support.v7.widget.AppCompatImageView implements Comparable<Card> {

    private static final int CARD_BACK_RES_ID = R.drawable.question;

    private boolean isOpened=false;
    private int imgResID;

    public Card(Context context,int imgResID) {
        super(context);
        this.imgResID = imgResID;
        this.setImageResource(CARD_BACK_RES_ID);
    }

    public boolean toggle()
    {
        isOpened = !isOpened;

        if(isOpened)
            this.setImageResource(imgResID);
        else
            this.setImageResource(CARD_BACK_RES_ID);

        return isOpened;
    }

    public void delete()
    {
        this.setVisibility(View.INVISIBLE);
    }

    public int getImgResID() {
        return imgResID;
    }

    @Override
    public int compareTo(Card o) {
        return getImgResID() - o.getImgResID();
    }
}
