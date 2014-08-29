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
 * Created by Jiaqi Ning on 2014/8/21.
 */
public class ClockViewPM extends View{
    private Paint markerPaint;
    private Paint circlePaint;
    private Paint largetextPaint;
    private Paint smalltextPaint;
    private Paint needlePaint;
    private int textHeight;
    private int tempDegree;
    private int val = 13;
    boolean isFirst = true;
    float xPos = 0;
    float yPos = 0;
    float degree = 0;
    private String selectedValue = "13:00";
    private String[] allDegreeVal = new String[27];
    float[] allDegree = new float[27];
    int timeMark = 0;


    public ClockViewPM (Context context) {
        super(context);
        initCompassView();
    }

    public ClockViewPM (Context context, AttributeSet attrs) {
        super(context, attrs);
        initCompassView();
    }

    public ClockViewPM (Context context, AttributeSet attrs, int defStyleAttr) {
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

        float degree = 0;
        int val = 13;
        for (int i = 0;i < 25;i++){
            allDegree[i] = degree;
            degree += 40/3;
            if (i % 3 == 1) {
                allDegreeVal[i] = val + ":20";
            }else if (i % 3 == 2){
                allDegreeVal[i] = val + ":40";
                val++;
            }else {
                allDegreeVal[i] = val + ":00";
            }
        }
        allDegree[25] = allDegree[24] + 17;
        allDegree[26] = allDegree[25] + 16;
        allDegreeVal[25] = "21:20";
        allDegreeVal[26] = "21:40";

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
        largetextPaint.setTextSize(0.0816f*radius);
        smalltextPaint.setTextSize(0.0408f*radius);

        canvas.drawCircle(px,py,radius,circlePaint);
        // Draw the middle dot
        canvas.drawCircle(px,py,5,needlePaint);
        canvas.save();


        int textWidth = (int)largetextPaint.measureText("12");


        int cardinalX = px - textWidth / 2;
        int cardinalY = py - radius + textHeight+(int)(radius*0.095);
        canvas.save();

        // draw a mark every per 15°, draw a text every per 45°
        for (int i = 0; i < 27;i++){
            // Draw long marker and short marker
            if (i % 3 == 1 || i % 3 == 2)
                canvas.drawLine(px,py-radius,px,py-radius +radius*0.025f,markerPaint);
            else
                canvas.drawLine(px,py-radius,px,py-radius +radius*0.05f,markerPaint);

            canvas.save();
            canvas.translate(0,textHeight); //move the canvas down to draw the number

            // Draw the number around the circle

            if (i % 3 == 0){
                canvas.drawText(val+"",cardinalX,cardinalY,largetextPaint);
                val++;
            }else if (i % 3 == 1){
                canvas.drawText("20", cardinalX, cardinalY, smalltextPaint);
            } else if (i % 3 == 2) {
                canvas.drawText("40", cardinalX, cardinalY, smalltextPaint);
            }

            canvas.restore();       // reset the canvas position

            if (i == 24)
                canvas.rotate(17,px,py);
            else if (i == 25)
                canvas.rotate(16,px,py);
            else
                canvas.rotate(40/3,px,py);
        }

        canvas.restore();

        // first time draw the needle in default position
        if (isFirst){
            needlePaint.setStrokeWidth(4f);
            canvas.drawLine(px,py,px,radius - needleOffset,needlePaint);
            isFirst = false;
        }

        // change the needle position
        if (isInCircle(px,py,radius)) {
            canvas.save();
            degree = (float)getDegreeFromCood(xPos,yPos,px,py);
            if (xPos > px && yPos < py) {    // the first region
                degree = 90 - Math.abs(degree);
            }
            else if (xPos > px && yPos > py) {                              // The second section
                degree += 90;
            }
            else if (xPos < px && yPos > py){       // The third section
                degree = 90 - degree + 180;

            }
            else {
                degree += 270;
            }

            degree = getNeedlePosition(degree);
            canvas.rotate(degree,px,py);
            canvas.drawLine(px, py, px, radius - needleOffset, needlePaint);

            canvas.restore();

            // draw the selection time
            canvas.drawText(selectedValue,px,py-radius*0.2f,needlePaint);
        } else {
            canvas.rotate(degree,px,py);
            canvas.drawLine(px, py, px, radius - needleOffset, needlePaint);
            canvas.restore();
            needlePaint.setTextSize(0.2f*radius);
            needlePaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(selectedValue,px,py-radius*0.2f,needlePaint);
        }

        val = 13;
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

    private double getDegreeFromCood(double x,double y,int centerX,int centerY){
        double tan = Math.abs(y - centerY) / Math.abs(x - centerX);
        return Math.toDegrees(Math.atan(tan));
    }

    private float getNeedlePosition(float degree){
        int largePos= 0;
        int smallPos = 0;
        for (int i =0;i<27;i++){
            if (degree < allDegree[i]){
                largePos = i;

                break;
            }
        }
        if (largePos != 0)
            smallPos = largePos - 1;

        float deltaPrev = degree - allDegree[smallPos];
        float deltaAfter = allDegree[largePos] - degree;
        if (deltaAfter < deltaPrev) {
            selectedValue = allDegreeVal[largePos];
            timeMark = largePos;
            return allDegree[largePos];
        }
        else {
            selectedValue = allDegreeVal[smallPos];
            timeMark = smallPos;
            return allDegree[smallPos];
        }
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

    public String getSelectedValue() {
        return selectedValue;
    }
}
