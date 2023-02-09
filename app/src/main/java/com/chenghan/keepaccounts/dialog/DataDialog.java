package com.chenghan.keepaccounts.dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chenghan.keepaccounts.R;

public class DataDialog extends Dialog {
    private CalendarView calendarView;
    private OnSelectDateListener onSelectDateListener;

    public DataDialog(@NonNull Context context) {
        super(context);
    }


    public interface OnSelectDateListener {
        void onSelectDate(int year, int month, int day);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_data);
        init();
    }


    private void init() {
        calendarView = findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                if (month == 12) {
                    month = 1;
                } else {
                    month += 1;
                }
                Log.i("Main", "year:" + year + "month:" + "day:" + dayOfMonth);
                onSelectDateListener.onSelectDate(year, month, dayOfMonth);
                cancel();
            }
        });
    }

    @Override
    public void cancel() {
        super.cancel();
    }

    //设置DialogSize的尺寸于屏幕尺寸一致
    public void  setDialogSize(boolean isFixed) {
        Window window = getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        window.setWindowAnimations(R.style.NullAnimationDialog);
        if  (isFixed) {
            wlp.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            window.setDimAmount(0);
            setCancelable(false);
        }

        window.getDecorView().setPadding(0, 0, 0, 0); //消除边距
        Display d = window.getWindowManager().getDefaultDisplay();
        wlp.width = (int) (d.getWidth());
        wlp.gravity = Gravity.BOTTOM;
        window.setBackgroundDrawableResource(R.color.transparent);
        window.setAttributes(wlp);
    }

    public void setOnSelectDateListener(OnSelectDateListener onSelectDateListener) {
        this.onSelectDateListener = onSelectDateListener;
    }
}
