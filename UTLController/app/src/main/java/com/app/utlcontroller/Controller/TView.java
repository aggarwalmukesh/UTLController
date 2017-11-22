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

public class TView extends View {

    float width = 0, height = 0;

    float anotherHeight = 0;
    boolean batteryToLoad = false;
    boolean rectifierToInverter = false;
    boolean rectifierToBattery = false;
    boolean bottomflow = false;
    boolean showAnimation = true;


    Paint p = new Paint();
    Paint newPaint = new Paint();

    public TView(Context context) {
        super(context);
        p.setColor(Color.WHITE);
        p.setStrokeWidth(3);
        newPaint.setColor(Color.BLUE);
    }

    public TView(Context context, AttributeSet attrs) {
        super(context, attrs);
        p.setColor(Color.WHITE);
        p.setStrokeWidth(3);
        newPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawLine(0, canvas.getHeight() / 4, canvas.getWidth(), canvas.getHeight() / 4, p);
        canvas.drawLine(canvas.getWidth() / 2, canvas.getHeight() / 4, canvas.getWidth() / 2, canvas.getHeight(), p);


        //Log.v("ShowAnimation", showAnimation + "  rectifierToInverter " + rectifierToInverter + "  rectifierToBattery=" + rectifierToBattery + "   BottomAnimation" + bottomflow);
        if (showAnimation) {

            if (rectifierToInverter && rectifierToBattery) {
                if (width == 0)
                    width = canvas.getWidth();
                if (height == 0)
                    height = canvas.getHeight() / 4;

                if (width < canvas.getWidth()) {
                    width = width + 3;
                } else {
                    width = 1;
                }

                canvas.drawCircle(width, height, Config.CircleRadius, newPaint);

                if (anotherHeight == canvas.getHeight())
                    anotherHeight = canvas.getHeight() / 4;

                if (anotherHeight < (canvas.getHeight())) {
                    anotherHeight = anotherHeight + 3;
                } else {
                    anotherHeight = canvas.getHeight() / 4;
                }

                // Log.v("Another height=",anotherHeight+"  aa");

                canvas.drawCircle(canvas.getWidth() / 2, anotherHeight, Config.CircleRadius, newPaint);

            } else if (rectifierToBattery) {

                if (width == 0)
                    width = canvas.getWidth() / 2;
                if (height == 0)
                    height = canvas.getHeight() / 4;

                // straight first half flow from rectifier
                if (width < canvas.getWidth()/2) {
                    width = width + 3;
                } else {
                    width = 1;
                }

                canvas.drawCircle(width, height, Config.CircleRadius, newPaint);

                if (anotherHeight == canvas.getHeight())
                    anotherHeight = canvas.getHeight() / 4;

                // bottom flow from rectifier to battery

                if (anotherHeight < (canvas.getHeight())) {
                    anotherHeight = anotherHeight + 3;
                } else {
                    anotherHeight = canvas.getHeight() / 4;
                }

                // Log.v("Another height=",anotherHeight+"  aa");

                canvas.drawCircle(canvas.getWidth() / 2, anotherHeight, Config.CircleRadius, newPaint);

            } else if (rectifierToInverter) {
                {
                    if (width == 0)
                        width = canvas.getWidth() / 2;
                    if (height == 0)
                        height = canvas.getHeight() / 4;

                    if (width < canvas.getWidth()) {
                        width = width + 3;
                    } else {
                        width = 1;
                    }
                    canvas.drawCircle(width, height, Config.CircleRadius, newPaint);
                }
            } else if (batteryToLoad) {

                if (width == 0)
                    width = canvas.getWidth() / 2;
                if (height == 0)
                    height = canvas.getHeight() / 4;

                if (width < canvas.getWidth()) {
                    width = width + 3;
                } else {
                    width = canvas.getWidth() / 2;
                }

                canvas.drawCircle(width, height, Config.CircleRadius, newPaint);


                if (anotherHeight == 0)
                    anotherHeight = canvas.getHeight() - 1;

                if (anotherHeight > (canvas.getHeight() / 4)) {
                    anotherHeight = anotherHeight - 3;
                } else {
                    anotherHeight = canvas.getHeight() - 1;
                }

                canvas.drawCircle(canvas.getWidth() / 2, anotherHeight, Config.CircleRadius, newPaint);
            } else if (!batteryToLoad && !bottomflow) {
                if (width == 0)
                    width = canvas.getWidth() / 2;
                if (height == 0)
                    height = canvas.getHeight() / 4;

                if (width < canvas.getWidth()) {
                    width = width + 3;
                } else {
                    width = 1;
                }

                canvas.drawCircle(width, height, Config.CircleRadius, newPaint);

            } else {
                if (width == 0)
                    width = canvas.getWidth() / 2;
                if (height == 0)
                    height = canvas.getHeight() / 4;

                if (width < canvas.getWidth()) {
                    width = width + 3;
                } else {
                    width = 1;
                }

                canvas.drawCircle(width, height, Config.CircleRadius, newPaint);

                if (bottomflow) {
                    if (anotherHeight == canvas.getHeight())
                        anotherHeight = canvas.getHeight() / 4;


                    if (anotherHeight < (canvas.getHeight())) {
                        anotherHeight = anotherHeight + 3;
                    } else {
                        anotherHeight = canvas.getHeight() / 4;
                    }

                    // Log.v("Another height=",anotherHeight+"  aa");

                    canvas.drawCircle(canvas.getWidth() / 2, anotherHeight, Config.CircleRadius, newPaint);
                }
            }
            invalidate();
        }
        super.onDraw(canvas);
    }

    public void flowGridToBattery() {
        batteryToLoad = false;
        showAnimation = true;
        bottomflow = true;
        invalidate();
    }

    public void onlyTopFlow() {
        batteryToLoad = false;
        bottomflow = false;
        showAnimation = true;
        invalidate();
    }

    public void flowBatteryToLoad() {
        batteryToLoad = true;
        showAnimation = true;
        rectifierToBattery = false;
        rectifierToInverter = false;
        bottomflow = true;
        invalidate();
    }

    public void stopAnimation() {
        showAnimation = false;
        rectifierToBattery = false;
        rectifierToInverter = false;
        batteryToLoad = false;
        bottomflow = false;
        invalidate();
    }

    public void rectifierToInverterFlow() {
        rectifierToInverter = true;
        showAnimation = true;
    }

    public void rectifierToBattery() {
        rectifierToBattery = true;
        showAnimation = true;
    }

    public void stoprectifierToInverterFlow() {
        rectifierToInverter = false;
    }

    public void stoprectifierToBattery() {
        rectifierToBattery = false;
    }


}
