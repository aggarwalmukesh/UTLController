package com.app.utlcontroller.Controller;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Mints on 5/13/2017.
 */

public class SmallVerticalSeperatorView extends View {

    float width = 0, height = 0;
    boolean stopAnimation=false;
    boolean topToBottom=true;
    float firstHeight=0;
    float anotherHeight=0;

    boolean b;

    Paint p = new Paint();
    Paint newPaint = new Paint();

    public SmallVerticalSeperatorView(Context context) {
        super(context);
        p.setColor(Color.WHITE);
        p.setStrokeWidth(3);
        newPaint.setColor(Color.BLUE);
    }

    public SmallVerticalSeperatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        p.setColor(Color.WHITE);
        p.setStrokeWidth(3);
        newPaint.setColor(Color.BLUE);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawLine(canvas.getWidth()/2, 0 , canvas.getWidth()/2, canvas.getHeight() , p);
        if(!stopAnimation) {
            if(topToBottom) {
                if (height == 0)
                    height = canvas.getHeight() / 2;

                if (height < canvas.getHeight()) {
                    height = height + 1.5f;
                } else {
                    height = 1;
                }

            }else{
                if (height == 0)
                    height = canvas.getHeight() / 2;

                if (height > 0) {
                    height = height - 1.5f;
                } else {
                    height = canvas.getHeight();
                }
            }
            canvas.drawCircle(canvas.getWidth() / 2, height, Config.CircleRadius, newPaint);
            invalidate();
        }
        super.onDraw(canvas);
    }

    public void stopAnimation() {
        stopAnimation=true;
        invalidate();
    }
    public void startBatteryToLoadAnimation() {
        stopAnimation=false;
        topToBottom=false;
        invalidate();
    }
    public void startGridToBatteryAnimation() {
        stopAnimation=false;
        topToBottom=true;
        invalidate();
    }public void gridToLoad() {
        stopAnimation=false;
        topToBottom=true;
        invalidate();
    }

}
