package com.example.bubbledrawapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.view.View;
import java.util.ArrayList;
import android.os.Handler;
import java.util.Random;

public class BubbleView extends ImageView implements View.OnTouchListener {
    private Random rand = new Random();
    private ArrayList<Bubble> bubbleList;
    private int size = 50;
    private int delay = 33;
    private Paint myPaint = new Paint();
    private Handler h = new Handler(); // Enables us to work with threading


    public BubbleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        bubbleList = new ArrayList<Bubble>();
        // testBubbles();
        setOnTouchListener(this);
    }

    private Runnable r = new Runnable() {
        @Override
        public void run() {
            for (Bubble b : bubbleList)
                b.update();
            invalidate();
        }
    };

    protected void onDraw(Canvas canvas) {
        for (Bubble b : bubbleList) {
            b.draw(canvas);
        }
        h.postDelayed(r, delay);
    }

    public void testBubbles() {
        for (int i = 0; i < 100; i++) {
            bubbleList.add(new Bubble(
                    rand.nextInt(600),
                    rand.nextInt(400),
                    rand.nextInt(50))
            );
        }
        invalidate();  // Fungerer som repaint();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        for (int i = 0; i < motionEvent.getPointerCount(); i++) {
            bubbleList.add(new Bubble((int)motionEvent.getX(), (int)motionEvent.getY(), (rand.nextInt(size) + size)));
        }
        return true;
    }


    private class Bubble {
        // Instance variable
        private int x, y;
        private int size;
        private int color;
        private int xspeed, yspeed;
        private final int MAX_SPEED = 15;

        // Constructor
        public Bubble(int x, int y, int size) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.color = Color.argb(rand.nextInt(256), // R
                    rand.nextInt(256), // G
                    rand.nextInt(256), // B
                    rand.nextInt(256)  // Opacity
            );
            do {
                xspeed = rand.nextInt(MAX_SPEED * 2 + 1) - MAX_SPEED;  // Max value will look like (5 * 2) - 5 = 5, since + 1 is to insure we get 10 as a possibility
                yspeed = rand.nextInt(MAX_SPEED * 2 + 1) - MAX_SPEED;  // Negative values moves the bubbles to the left
            } while (xspeed == 0 && yspeed == 0);
        }


        // Drawing the bubble to the canvas.
        public void draw(Canvas canvas) {
            myPaint.setColor(this.color);
            canvas.drawOval(
                    this.x - size / 2,
                    this.y - size / 2,
                    this.x + size / 2,
                    this.y + size / 2,
                    myPaint);
        }

        public void update() {
            x += xspeed;
            y += yspeed;
            /// Checks to see if obj reached the edge of the screen
            // (0,0) er i midten, så vi fjerner halvdelen af cirklen for at kigge på toppen, og tilføjer for at kigge på bunden.
            if (x - size / 2 <= 0 || x + size / 2 >= getWidth())
                xspeed = -xspeed;
            if (y - size / 2 <= 0 || y + size / 2 >= getHeight())
                yspeed = -yspeed;
        }
    }

}
