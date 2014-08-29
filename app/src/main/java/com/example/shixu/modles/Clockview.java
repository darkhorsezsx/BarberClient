package com.example.shixu.modles;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.shixu.barberclient.R;

/**
 * Created by Jiaqi Ning on 2014/8/18.
 */
public class Clockview extends View {
    private Paint markerPaint;
    private Paint circlePaint;
    private Paint largetextPaint;
    private Paint smalltextPaint;
    private Paint needlePaint;
    private int textHeight;
    private int tempDegree;
    private String selectedValue = "07:00";
    int timeMark = 0;



    boolean isFirst = true;
    float xPos = 0;
    float yPos = 0;


    public Clockview(Context context) {
        super(context);
        initCompassView();
    }

    public Clockview(Context context, AttributeSet attrs) {
        super(context, attrs);
        initCompassView();
    }

    public Clockview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCompassView();
    }

    protected void initCompassView(){
        setFocusable(true);
        Resources r = this.getResources();

        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(r.getColor(R.color.background_color));
        circlePaint.setStrokeWidth(2);
        circlePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        largetextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        largetextPaint.setColor(r.getColor(R.color.text_color));

        smalltextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        smalltextPaint.setColor(r.getColor(R.color.text_color));

        textHeight = (int)largetextPaint.measureText("12");

        markerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        markerPaint.setColor(r.getColor(R.color.marker_color));

        needlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        needlePaint.setColor(r.getColor(R.color.light_blue));

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredWidth = measure(widthMeasureSpec);
        int measureHeight = measure(heightMeasureSpec);

        int d = Math.min(measuredWidth,measureHeight);

        setMeasuredDimension(d,d);
    }

    private int measure(int measureSpec){
        int result = 0;

        // decode the size
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.UNSPECIFIED)
            result = 200;
        else
            result = specSize;

        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int mMeasuredWidth = getMeasuredWidth();
        int mMeasureHeight = getMeasuredHeight();

        int px = mMeasuredWidth / 2;
        int py = mMeasureHeight / 2;



        int radius = Math.min(px,py);
        float needleOffset = radius * 0.65f;
        smalltextPaint.setTextSize(0.0508f*radius);
        largetextPaint.setTextSize(0.1016f*radius);

        canvas.drawCircle(px,py,radius,circlePaint);
        // Draw the middle dot
        canvas.drawCircle(px,py,5,needlePaint);
        canvas.save();


        int doubletextWidth = (int)largetextPaint.measureText("12");
        int singletexWidth = (int)largetextPaint.measureText("8");
        int timeTextWidth = (int)needlePaint.measureText("7:00");

        int cardinalX = 0;
        int cardinalY = py - radius + textHeight+(int)(radius*0.08);

        // draw a mark every per 15°, draw a text every per 45°
        for (int i = 0; i < 18;i++){
            // Draw long marker and short marker
            if (i % 3 == 1 || i % 3 == 2)
                canvas.drawLine(px,py-radius,px,py-radius +  radius*0.02f,markerPaint);
            else
                canvas.drawLine(px,py-radius,px,py-radius + radius*0.045f,markerPaint);

            canvas.save();
            canvas.translate(0,textHeight); //move the canvas down to draw the number

            // Set proper x for double number value or single number
            if (i <= 6) {
                cardinalX = px - singletexWidth / 2;
            }else {
                cardinalX = px - doubletextWidth / 2;
            }

            // Draw the number around the circle
            String val  = "";
            if (i % 3 == 1){
                val = "20";
                canvas.drawText(val, cardinalX, cardinalY, smalltextPaint);
            } else if (i % 3 == 2) {
                val = "40";
                canvas.drawText(val, cardinalX, cardinalY, smalltextPaint);
            } else {
                switch (i) {
                    case 0:
                        val = "7";
                        break;
                    case 3:
                        val = "8";
                        break;
                    case 6:
                        val = "9";
                        break;
                    case 9:
                        val = "10";
                        break;
                    case 12:
                        val = "11";
                        break;
                    case 15:
                        val = "12";
                        break;

                }
                canvas.drawText(val, cardinalX, cardinalY, largetextPaint);
            }
            canvas.restore();       // reset the canvas position
            canvas.rotate(20,px,py);
        }
        if (isFirst){
            needlePaint.setStrokeWidth(4f);
            canvas.drawLine(px,py,px,radius - needleOffset,needlePaint);
            isFirst = false;
        }

        // change the needle position
        if (isInCircle(px,py,radius)) {
            canvas.save();
            double tan = getTan(xPos,yPos,px,py);

            if (xPos > px && yPos < py) {    // the first region
                tan *= -1;
               if (tan > Math.tan(Math.PI * 4/9)) {
                   canvas.drawLine(px, py, px, radius - needleOffset, needlePaint);
                   tempDegree = 0;
                   selectedValue = "07:00";
               }
               else if (tan <= Math.tan(Math.PI * 4/9) && tan > Math.tan(Math.PI / 3)) {
                   canvas.rotate(20,px,py);
                   canvas.drawLine(px, py, px, radius - needleOffset, needlePaint);
                   canvas.restore();
                   tempDegree = 20;
                   selectedValue = "07:20";
                   timeMark = 1;
               }
               else if (tan <= Math.tan(Math.PI / 3) && tan > Math.tan(Math.PI * 2/9)){
                   canvas.rotate(40,px,py);
                   canvas.drawLine(px, py, px, radius - needleOffset, needlePaint);
                   canvas.restore();
                   tempDegree = 40;
                   selectedValue = "07:40";
                   timeMark = 2;
               }
               else if (tan <= Math.tan(Math.PI * 2/9) && tan > Math.tan(Math.PI / 9)){
                   canvas.rotate(60,px,py);
                   canvas.drawLine(px, py, px, radius - needleOffset, needlePaint);
                   canvas.restore();
                   tempDegree = 60;
                   selectedValue = "08:00";
                   timeMark = 3;
               } else {
                   canvas.rotate(80,px,py);
                   canvas.drawLine(px, py, px, radius - needleOffset, needlePaint);
                   canvas.restore();
                   tempDegree = 80;
                   selectedValue = "08:20";
                   timeMark = 4;
               }


            }
            else if (xPos > px && yPos > py) {                              // The second section
                if (tan > Math.tan(Math.PI * 4/9)){
                    canvas.rotate(180,px,py);
                    canvas.drawLine(px,py,px,radius - needleOffset,needlePaint);
                    canvas.restore();
                    tempDegree = 180;
                    selectedValue = "10:00";
                    timeMark = 9;
                }

                else if (tan <= Math.tan(Math.PI * 4/9) && tan > Math.tan(Math.PI / 3)) {
                    canvas.rotate(160,px,py);
                    canvas.drawLine(px, py, px, radius - needleOffset, needlePaint);
                    canvas.restore();
                    tempDegree = 160;
                    selectedValue = "09:40";
                    timeMark = 8;
                }
                else if (tan <= Math.tan(Math.PI / 3) && tan > Math.tan(Math.PI * 2/9)){
                    canvas.rotate(140,px,py);
                    canvas.drawLine(px, py, px, radius - needleOffset, needlePaint);
                    canvas.restore();
                    tempDegree = 140;
                    selectedValue = "09:20";
                    timeMark = 7;
                }
                else if (tan <= Math.tan(Math.PI * 2/9) && tan > Math.tan(Math.PI / 9)){
                    canvas.rotate(120,px,py);
                    canvas.drawLine(px, py, px, radius - needleOffset, needlePaint);
                    canvas.restore();
                    tempDegree = 120;
                    selectedValue = "09:00";
                    timeMark = 6;
                } else {
                    canvas.rotate(100,px,py);
                    canvas.drawLine(px, py, px, radius - needleOffset, needlePaint);
                    canvas.restore();
                    tempDegree = 100;
                    selectedValue = "08:40";
                    timeMark = 5;
                }
            }
            else if (xPos < px && yPos > py){       // The third section
                tan *= -1;
                if (tan > Math.tan(Math.PI * 4/9)){
                    canvas.rotate(180,px,py);
                    canvas.drawLine(px,py,px,radius - needleOffset,needlePaint);
                    canvas.restore();
                    tempDegree = 180;
                    selectedValue = "10:00";
                    timeMark = 9 ;
                }

                else if (tan <= Math.tan(Math.PI * 4/9) && tan > Math.tan(Math.PI / 3)) {
                    canvas.rotate(200,px,py);
                    canvas.drawLine(px, py, px, radius - needleOffset, needlePaint);
                    canvas.restore();
                    tempDegree = 200;
                    selectedValue = "10:20";
                    timeMark = 10;
                }
                else if (tan <= Math.tan(Math.PI / 3) && tan > Math.tan(Math.PI * 2/9)){
                    canvas.rotate(220,px,py);
                    canvas.drawLine(px, py, px, radius - needleOffset, needlePaint);
                    canvas.restore();
                    tempDegree = 220;
                    selectedValue = "10:40";
                    timeMark = 11;
                }
                else if (tan <= Math.tan(Math.PI * 2/9) && tan > Math.tan(Math.PI / 9)){
                    canvas.rotate(240,px,py);
                    canvas.drawLine(px, py, px, radius - needleOffset, needlePaint);
                    canvas.restore();
                    tempDegree = 240;
                    selectedValue = "11:00";
                    timeMark = 12;
                } else {
                    canvas.rotate(260,px,py);
                    canvas.drawLine(px, py, px, radius - needleOffset, needlePaint);
                    canvas.restore();
                    tempDegree = 260;
                    selectedValue = "11:20";
                    timeMark = 13;
                }
            }
            else {
                if (tan > Math.tan(Math.PI * 4/9)){
                    canvas.drawLine(px,py,px,radius - needleOffset,needlePaint);
                    tempDegree = 0;
                    selectedValue = "07:00";
                    timeMark = 0;
                }
                else if (tan <= Math.tan(Math.PI * 4/9) && tan > Math.tan(Math.PI / 3)) {
                    canvas.rotate(-20,px,py);
                    canvas.drawLine(px, py, px, radius - needleOffset, needlePaint);
                    canvas.restore();
                    tempDegree = -20;
                    selectedValue = "12:40";
                    timeMark = 17;
                }
                else if (tan <= Math.tan(Math.PI / 3) && tan > Math.tan(Math.PI * 2/9)){
                    canvas.rotate(-40,px,py);
                    canvas.drawLine(px, py, px, radius - needleOffset, needlePaint);
                    canvas.restore();
                    tempDegree = -40;
                    selectedValue = "12:20";
                    timeMark = 16;
                }
                else if (tan <= Math.tan(Math.PI * 2/9) && tan > Math.tan(Math.PI / 9)){
                    canvas.rotate(-60,px,py);
                    canvas.drawLine(px, py, px, radius - needleOffset, needlePaint);
                    canvas.restore();
                    tempDegree = -60;
                    selectedValue = "12:00";
                    timeMark = 15;
                } else {
                    canvas.rotate(-80,px,py);
                    canvas.drawLine(px, py, px, radius - needleOffset, needlePaint);
                    canvas.restore();
                    tempDegree = -80;
                    selectedValue = "11:40";
                    timeMark = 14;
                }

            }
            canvas.restore();

            // draw the selection time
            canvas.drawText(selectedValue,px,py-radius*0.2f,needlePaint);
        } else {
            canvas.rotate(tempDegree,px,py);
            canvas.drawLine(px, py, px, radius - needleOffset, needlePaint);
            canvas.restore();
            needlePaint.setTextSize(0.2f*radius);
            needlePaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(selectedValue,px,py-radius*0.2f,needlePaint);

        }


    }
    public String getSelectedValue() {
        return selectedValue;
    }

    // 判断用户的点击是否在圆内
    private boolean isInCircle(int px,int py,int r){
        float distance = Math.abs(px - xPos)*Math.abs(px - xPos) + Math.abs(py - yPos)*Math.abs(py - yPos);
        if (distance < r*r)
            return true;
        else
            return false;
    }

    // 计算点在圆内的圆心角tan
    private double getTan(double x,double y,int centerX,int centerY){
        double tan = (y - centerY) / (x - centerX);
        return tan;
    }


    @Override
    public boolean onTouchEvent (MotionEvent event) {
        if ((event.getAction()==MotionEvent.ACTION_DOWN) || (event.getAction() == MotionEvent.ACTION_MOVE)) {
            xPos = event.getX();
            yPos = event.getY();
            invalidate();
        }
        return true;
    }

    public int getTimeMark() {
        return timeMark;
    }
}
