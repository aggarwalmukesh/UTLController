package com.app.utlcontroller.Controller;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Mints on 5/13/2017.
 */

public class SeperatorView extends View {

    float width = 0, height = 0;

    float firstHeight=0;
    float anotherHeight=0;
    boolean stopAnimation=false;

    boolean b;

    Paint p = new Paint();
    Paint newPaint = new Paint();

    public SeperatorView(Context context) {
        super(context);
        p.setColor(Color.WHITE);
        p.setStrokeWidth(3);
        newPaint.setColor(Color.BLUE);
    }

    public SeperatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        p.setColor(Color.WHITE);
        p.setStrokeWidth(3);
        newPaint.setColor(Color.BLUE);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawLine(0, canvas.getHeight()/2 , canvas.getWidth(), canvas.getHeight()/2 , p);

        if(!stopAnimation) {
            if (width == 0)
                width = canvas.getWidth() / 2;

            if (width < canvas.getWidth()) {
                width = width + 3.5f;
            } else {
                width = 1;
            }

            canvas.drawCircle(width, canvas.getHeight() / 2, Config.CircleRadius, newPaint);
            invalidate();
        }
        super.onDraw(canvas);
    }

    public void stopAnimation() {
        stopAnimation=true;
        invalidate();
    }public void startAnimation() {
        stopAnimation=false;
        invalidate();
    }
}
