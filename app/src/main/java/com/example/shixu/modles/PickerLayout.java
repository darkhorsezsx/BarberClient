package com.example.shixu.modles;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shixu.barberclient.R;

/**
 * Created by Jiaqi Ning on 2014/8/22.
 */
public class PickerLayout extends RelativeLayout {
    Clockview amView;
    ClockViewPM pmView;
    TextView confirmBtn;
    TextView cancelBtn;
    ImageButton addBtn;
    EditText etTime;
    Button btnNext;
    ViewGroup grid;
    ViewGroup editPanel;
    StringBuilder time = new StringBuilder();

    boolean isAm = true;
    boolean isPm = false;
    boolean isStartTimeSet = false;
    String startTime = "";
    int startTimeMark = -1;
    int endTimeMark = -1;

    boolean[] amTimeTable = new boolean[18];
    boolean[] pmTimeTable = new boolean[27];
    public PickerLayout(Context context, AttributeSet attrs){
        super(context, attrs);
    }
    public PickerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    public PickerLayout(Context context) {
        super(context);


        LayoutInflater layoutInflater = LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.clock_layout,this);

        amView = (Clockview)findViewById(R.id.amView);
        pmView = (ClockViewPM)findViewById(R.id.pmView);

        confirmBtn = (TextView)findViewById(R.id.btn_confirm);
        cancelBtn = (TextView)findViewById(R.id.btn_cancel);
        addBtn = (ImageButton)findViewById(R.id.btn_add);
        etTime = (EditText)findViewById(R.id.et_time);

        grid = (ViewGroup)findViewById(R.id.grid_time);
        editPanel = (RelativeLayout)findViewById(R.id.et_panel);
        TextView btnPM = (TextView)findViewById(R.id.tv_pm);
        TextView btnAM = (TextView)findViewById(R.id.tv_am);
        btnNext = (Button)findViewById(R.id.btn_nxt);

        // AM button
        btnAM.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                amView.setVisibility(View.VISIBLE);
                pmView.setVisibility(View.GONE);
                confirmBtn.setVisibility(VISIBLE);
                addBtn.setVisibility(GONE);
                isAm = true;
                isPm = false;

                LayoutParams paramET = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                paramET.addRule(RelativeLayout.BELOW, R.id.amView);
                paramET.setMargins(0,20,0,0);
                editPanel.setLayoutParams(paramET);
                LayoutParams paramBT = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                paramBT.addRule(RelativeLayout.ALIGN_BOTTOM,R.id.amView);
                paramBT.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                confirmBtn.setLayoutParams(paramBT);
                LayoutParams paramsC = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                paramsC.addRule(ALIGN_PARENT_LEFT);
                paramsC.addRule(RelativeLayout.ALIGN_BOTTOM,R.id.amView);
                cancelBtn.setLayoutParams(paramsC);


                isStartTimeSet = false;
                etTime.setText("");
                startTimeMark = -1;
                endTimeMark = -1;
            }
        });


        // PM button
        btnPM.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                amView.setVisibility(View.GONE);
                pmView.setVisibility(View.VISIBLE);
                confirmBtn.setVisibility(VISIBLE);
                addBtn.setVisibility(GONE);
                isAm = false;
                isPm = true;

                LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.BELOW,R.id.pmView);
                params.setMargins(0,20,0,0);
                editPanel.setLayoutParams(params);
                LayoutParams paramBT = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                paramBT.addRule(RelativeLayout.ALIGN_BOTTOM,R.id.pmView);
                paramBT.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                confirmBtn.setLayoutParams(paramBT);
                LayoutParams paramsC = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                paramsC.addRule(RelativeLayout.ALIGN_BOTTOM,R.id.pmView);
                paramsC.addRule(ALIGN_PARENT_LEFT);
                cancelBtn.setLayoutParams(paramsC);

                isStartTimeSet = false;
                etTime.setText("");
                startTimeMark = -1;
                endTimeMark = -1;
            }
        });


        // Confirm time Button
        confirmBtn.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view) {

                if (isAm){
                    if (!isStartTimeSet) {
                        startTimeMark = amView.getTimeMark();
                        if (startTimeMark == 17)
                            Toast.makeText(getContext(),"不能选最末时间做为起点",Toast.LENGTH_SHORT).show();
                        else if (amTimeTable[startTimeMark])
                            Toast.makeText(getContext(),"所选时间冲突",Toast.LENGTH_LONG).show();
                        else {
                            startTime = amView.getSelectedValue();
                            etTime.setText("Start Time is " + startTime);
                            isStartTimeSet = true;
                            amTimeTable[startTimeMark] = true;
                        }

                    }else {
                        String endTime = amView.getSelectedValue();
                        endTimeMark = amView.getTimeMark();
                        if (isSelectedTimeValid(endTime,amTimeTable)) {
                            etTime.setText("The time period is: " + startTime + "-" + endTime);
                            addBtn.setVisibility(VISIBLE);
                            confirmBtn.setVisibility(GONE);
                            amTimeTable[startTimeMark] = true;
                        }
                        else
                            Toast.makeText(getContext(),"这不是一个合法的时间段!",Toast.LENGTH_LONG).show();
                    }
                } else if (isPm){
                    if (!isStartTimeSet) {
                        startTimeMark = pmView.getTimeMark();
                        if (startTimeMark == 26)
                            Toast.makeText(getContext(),"不能选最末时间做为起点",Toast.LENGTH_SHORT).show();
                        if (pmTimeTable[startTimeMark])
                            Toast.makeText(getContext(),"所选时间冲突",Toast.LENGTH_LONG).show();
                        else {
                            startTime = pmView.getSelectedValue();
                            etTime.setText("Start Time is " + startTime);
                            isStartTimeSet = true;
                            pmTimeTable[startTimeMark] = true;
                        }

                    } else {
                        String endTime = pmView.getSelectedValue();
                        if (isSelectedTimeValid(endTime,pmTimeTable)) {
                            etTime.setText("The time period is: " + startTime + "-" + endTime);
                            addBtn.setVisibility(VISIBLE);
                            confirmBtn.setVisibility(GONE);
                            endTimeMark = amView.getTimeMark();
                        }
                        else
                            Toast.makeText(getContext(),"这不是一个合法的时间段!",Toast.LENGTH_LONG).show();
                    }
                }


            }
        });


        // Add time button
        addBtn.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view) {
                isStartTimeSet = false;
                addBtn.setVisibility(GONE);
                confirmBtn.setVisibility(VISIBLE);

                String val = etTime.getText().toString().substring(20);
                time.append(val+"-");

                TextView text = new TextView(new ContextThemeWrapper(getContext(),R.style.timeText));
                text.setText(val);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.setMargins(0,0,10,10);
                text.setLayoutParams(params);
                grid.addView(text);
                etTime.setText("");
                if (isAm)
                    selectedArea(startTimeMark,endTimeMark,amTimeTable);
                else
                    selectedArea(startTimeMark,endTimeMark,pmTimeTable);

            }
        });

        cancelBtn.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view) {
                etTime.setText("");
                isStartTimeSet = false;
                confirmBtn.setVisibility(VISIBLE);
                addBtn.setVisibility(GONE);
                if (startTimeMark != -1)
                amTimeTable[startTimeMark] = false;
            }
        });



    }

    public String getTime() {
        return time.deleteCharAt(time.length()-1).toString();
    }

    private void selectedArea(int start,int end,boolean[] time){
        if (start == -1 && end == -1)
            return;
        for (int i = start;i <= end;i++){
            time[i] = true;
        }
    }

    private boolean isTimeCollision(int start,int end,boolean[] time){
        if (start == -1 && end == -1)
            return false;
        for (int i = start+1;i <= end;i++){
            if (time[i])
                return true;
        }
        return false;
    }

    private boolean isSelectedTimeValid(String val,boolean[] time) {
        String[] start = startTime.split(":");
        String[] end = val.split(":");
        if (Integer.parseInt(start[0]) < Integer.parseInt(end[0])) {
            if (!isTimeCollision(startTimeMark, endTimeMark, time))
                return true;
            else
                return false;
        }

        else if (Integer.parseInt(start[0]) == Integer.parseInt(end[0])){
            if (Integer.parseInt(start[0]) < Integer.parseInt(end[1])){
                if (!isTimeCollision(startTimeMark, endTimeMark, time))
                    return true;
                else
                    return false;
            }
            else
                return false;
        }else
            return false;
    }

}
