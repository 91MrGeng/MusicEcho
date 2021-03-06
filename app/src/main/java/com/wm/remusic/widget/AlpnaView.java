package com.wm.remusic.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageButton;

public class AlpnaView extends ImageButton {
    private AlpnaViewSelecterListener listener;
    private float screenHeight;
    private String[] letters = {"#", "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z"};

    public AlpnaView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setHeight(float Height) {
        this.screenHeight = Height;
    }

    public void setOnAlpnaViewSelecterListener(
            AlpnaViewSelecterListener listener) {
        this.listener = listener;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        String key = null;
        int action = event.getAction();
        float y = event.getY();
        if (y <= screenHeight) {
            int index = (int) ((y / screenHeight) * 27.0f);
            key = letters[index];
        } else {
            listener.up();
        }
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                listener.down(key);
                break;
            case MotionEvent.ACTION_MOVE:
                listener.move(key);
                break;
            case MotionEvent.ACTION_UP:
                listener.up();
                break;
        }
        return true;
    }
}
