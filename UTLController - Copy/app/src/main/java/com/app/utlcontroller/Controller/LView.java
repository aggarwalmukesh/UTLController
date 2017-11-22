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

public class LView extends View {

    float width = 0, height = 0;
    float firstHeight = 0, switch_switchHeight = 0;
    float anotherHeight = 0;
    boolean byPass_STS;
    boolean byPass_Switch;
    boolean noflow=false;
    boolean mbs1_mbs2=false;
    boolean sts_Load=false;
    boolean bypass_sts=false;
    boolean b;
    Paint p = new Paint();
    Paint newPaint = new Paint();

    public LView(Context context) {
        super(context);
        p.setColor(Color.WHITE);
        p.setStrokeWidth(3);
        newPaint.setColor(Color.BLUE);
    }

    public LView(Context context, AttributeSet attrs) {
        super(context, attrs);
        p.setColor(Color.WHITE);
        p.setStrokeWidth(3);
        newPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawLine(canvas.getWidth() / 4, 0, canvas.getWidth() / 4, canvas.getHeight()-55, p);
        canvas.drawLine(canvas.getWidth() / 4, canvas.getHeight()-57, 0, canvas.getHeight()-57, p);

        canvas.drawLine(canvas.getWidth() * 3 / 4, 0, canvas.getWidth() * 3 / 4, canvas.getHeight() / 2 - 51, p);
        canvas.drawLine(canvas.getWidth() * 3 / 4, canvas.getHeight() / 2 - 53, canvas.getWidth(), canvas.getHeight() / 2 - 53, p);

        canvas.drawLine(canvas.getWidth() *3/4, canvas.getHeight() / 2 - 23, canvas.getWidth(), canvas.getHeight() / 2 - 23, p);
        canvas.drawLine(canvas.getWidth() *3/4, canvas.getHeight() / 2 - 24, canvas.getWidth() *3/4, canvas.getHeight()-25, p);
        canvas.drawLine(0, canvas.getHeight()-27, canvas.getWidth() *3/4, canvas.getHeight()-27, p);

        if (width == 0)
            width = canvas.getWidth() *3/4;
        if (height == 0)
            height = canvas.getHeight() / 4;

        if (width < canvas.getWidth()) {
            width = width + 5;
        } else {
            width = 1;
        }

        if(!noflow) {

            if(sts_Load){
                if (anotherHeight <= (canvas.getHeight() / 2 - 60)) {
                    anotherHeight = anotherHeight + 2.9f;
                } else {
                    anotherHeight = 0;
                }
                canvas.drawCircle(canvas.getWidth() * 3 / 4, anotherHeight, Config.CircleRadius, newPaint);
            }

            if (byPass_STS) {
                if (firstHeight > 0) {
                    firstHeight = firstHeight - 4;
                } else {
                    firstHeight = canvas.getHeight() - 60;
                }
                canvas.drawCircle(canvas.getWidth() / 4, firstHeight, Config.CircleRadius, newPaint);
            }

            if(mbs1_mbs2){
                if (switch_switchHeight > canvas.getHeight() / 2-15) {
                    switch_switchHeight = switch_switchHeight - 3.3f;
                } else {
                    switch_switchHeight = canvas.getHeight() - 19;
                }
                canvas.drawCircle(canvas.getWidth() *3/4, switch_switchHeight, Config.CircleRadius, newPaint);
            }

            /*if (byPass_STS || !byPass_Switch) {
                if (anotherHeight <= (canvas.getHeight() / 2 - 60)) {
                    anotherHeight = anotherHeight + 5;
                } else {
                    anotherHeight = 0;
                }
                canvas.drawCircle(canvas.getWidth() * 3 / 4, anotherHeight, Config.CircleRadius, newPaint);
            }

            if (byPass_STS) {
                if (firstHeight > 0) {
                    firstHeight = firstHeight - 5;
                } else {
                    firstHeight = canvas.getHeight() - 60;
                }
                canvas.drawCircle(canvas.getWidth() / 4, firstHeight, Config.CircleRadius, newPaint);
            }

            if (byPass_Switch) {
                if (switch_switchHeight > canvas.getHeight() / 2) {
                    switch_switchHeight = switch_switchHeight - 5;
                } else {
                    switch_switchHeight = canvas.getHeight() - 19;
                }
                canvas.drawCircle(canvas.getWidth() / 2, switch_switchHeight, Config.CircleRadius, newPaint);
            }
*/
            invalidate();
        }

        super.onDraw(canvas);
    }

    public void byPassToSTS_Load() {
        byPass_STS=true;
        byPass_Switch=false;
        noflow=false;
        invalidate();
    }
    public void byPassSwitch_Switch() {
        byPass_STS=false;
        byPass_Switch=true;
        noflow=false;
        invalidate();
    }
    public void blockByPass() {
        byPass_STS=false;
        byPass_Switch=false;
        noflow=false;
        invalidate();
    }
    public void noFlow() {
        noflow=true;
        sts_Load=false;
        mbs1_mbs2=false;
        byPass_STS=false;
        byPass_Switch=false;
        invalidate();
    }


    public void startSts_Load(){
        sts_Load=true;
        noflow=false;
        invalidate();
    }

    public void disableSts_Load(){
        sts_Load=false;
        invalidate();
    }

    public void startMBS1_MBS2(){
        mbs1_mbs2=true;
        noflow=false;
        invalidate();
    }

    public void startByPass_Sts(){
        byPass_STS=true;
        noflow=false;
        invalidate();
    }



}
