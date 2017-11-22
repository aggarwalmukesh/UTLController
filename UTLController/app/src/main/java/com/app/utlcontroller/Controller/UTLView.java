package com.app.utlcontroller.Controller;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.app.utlcontroller.R;

import java.util.ArrayList;

/**
 * Created by Mints on 4/9/2017.
 */

public class UTLView extends View {

    float startPointX=80;
    float startPointY=50;

    ArrayList<Float> startPointsX=new ArrayList<>();


    float sPoint1,sPoint2,sPoint3,sPoint4,sPoint5,sPoint6,sPoint7,sPoint8,sPoint9,sPoint10;

    float yPoint1,yPoint2,yPoint3,yPoint4,yPoint5,yPoint6,yPoint7,yPoint8;

    float yStart1,yCenter,yBottom;

    Paint background,forground;
    float width,height;
    private float smallboxWidth=180;
    private float smallboxHeight=180;

    public UTLView(Context context) {
        super(context);
        background=new Paint();
        background.setColor(Color.RED);
        forground=new Paint();
        forground.setColor(Color.YELLOW);

    }

    public UTLView(Context context, AttributeSet attrs) {
        super(context, attrs);
        background=new Paint();
        background.setColor(Color.LTGRAY);
        forground=new Paint();
        forground.setColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        height=canvas.getHeight();
        width=canvas.getWidth();
        /*for(int i=1;i<width;){
            startPointsX.add(0,width/i);
        }*/

        sPoint10=width*10/10;
        sPoint9=width*9/10;
        sPoint8=width*8/10;
        sPoint7=width*7/10;
        sPoint6=width*6/10;
        sPoint5=width*5/10;
        sPoint4=width*4/10;
        sPoint3=width*3/10;
        sPoint2=width*2/10;
        sPoint1=width*1/10;

        yPoint1=height*1/10;
        yPoint2=height*2/10;
        yPoint3=height*3/10;
        yPoint4=height*4/10;
        yPoint5=height*5/10;
        yPoint6=height*6/10;
        yPoint7=height*7/10;
        yPoint8=height*8/10;



        yStart1=50;
        yCenter=height/2-50;
        yBottom=height-100;

        Log.v("StartPoint1 ",sPoint1+" ");
        Log.v("StartPoint2 ",sPoint2+" ");
        Log.v("StartPoint3 ",sPoint3+" ");
        Log.v("StartPoint4 ",sPoint4+" ");
        Log.v("StartPoint5 ",sPoint5+" ");
        Log.v("StartPoint6 ",sPoint6+" ");
        Log.v("width ",width+" ");
        Log.v("yBottom ",yBottom+" ");
        Log.v("Height ",height+" ");


        canvas.drawRect(0,0,width,height,background);
        //drawRect(startPointX,startPointY,smallboxWidth,smallboxHeight,forground,canvas);
        /*drawRect(250,startPointY,350,smallboxHeight,forground,canvas);
        drawRect(400,startPointY,500,smallboxHeight,forground,canvas);
        drawRect(550,startPointY,650,smallboxHeight,forground,canvas);
        drawRect(700,startPointY,800,smallboxHeight,forground,canvas);
*/
        /*drawRect(sPoint1,yPoint1,sPoint2-30,yPoint3,forground,canvas);
        drawRect(sPoint2,yPoint1,sPoint3-30,yPoint3,forground,canvas);
        drawRect(sPoint3,yPoint1,sPoint4-30,yPoint3,forground,canvas);
        drawRect(sPoint4,yPoint1,sPoint5-30,yPoint3,forground,canvas);
        drawRect(sPoint5,yPoint1,sPoint6-30,yPoint3,forground,canvas);

        drawRect(sPoint1,yPoint6,sPoint2-30,yPoint8,forground,canvas);
        drawRect(sPoint2,yPoint6,sPoint3-30,yPoint8,forground,canvas);
        drawRect(sPoint3,yPoint6,sPoint4-30,yPoint8,forground,canvas);
        drawRect(sPoint4,yPoint6,sPoint5-30,yPoint8,forground,canvas);
        drawRect(sPoint5,yPoint6,sPoint6-30,yPoint8,forground,canvas);
*/

        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.power,null),sPoint1,yPoint1,forground);
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.connector,null),sPoint2,yPoint1,forground);
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.rectifier,null),sPoint3,yPoint1,forground);
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.invertor,null),sPoint4,yPoint1,forground);
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.sts,null),sPoint5,yPoint1,forground);
        //drawRect(sPoint1,yPoint1,sPoint2-30,yPoint2,forground,canvas);
       /* drawRect(sPoint2,yPoint1,sPoint3-30,yPoint2,forground,canvas);
        drawRect(sPoint3,yPoint1,sPoint4-30,yPoint2,forground,canvas);
        drawRect(sPoint4,yPoint1,sPoint5-30,yPoint2,forground,canvas);
        drawRect(sPoint5,yPoint1,sPoint6-30,yPoint2,forground,canvas);
*/
        drawRect(sPoint1,yPoint7,sPoint2-30,yPoint8,forground,canvas);
        drawRect(sPoint2,yPoint7,sPoint3-30,yPoint8,forground,canvas);
        drawRect(sPoint3,yPoint7,sPoint4-30,yPoint8,forground,canvas);
        drawRect(sPoint4,yPoint7,sPoint5-30,yPoint8,forground,canvas);
        drawRect(sPoint5,yPoint7,sPoint6-30,yPoint8,forground,canvas);


        drawRect(sPoint4-30,yPoint2+30,sPoint4,yPoint3,forground,canvas);
        drawRect(sPoint4-60,yPoint4,sPoint4+30,yPoint6-50,forground,canvas);

        drawRect(sPoint6,yPoint3+30,sPoint8-50,yPoint5+50,forground,canvas);
        drawRect(sPoint8,yPoint3+30,sPoint10-50,yPoint5+50,forground,canvas);

       // drawRect(sPoint6,yPoint5,sPoint8,yPoint3,forground,canvas);
       // drawRect(sPoint8,yPoint5,sPoint10,yPoint3,forground,canvas);

        super.onDraw(canvas);
    }

    public void drawRect(float sX, float sY,float eX,float eY,Paint p,Canvas canvas){
        canvas.drawRect(sX,sY,eX,eY,p);
    }
}
