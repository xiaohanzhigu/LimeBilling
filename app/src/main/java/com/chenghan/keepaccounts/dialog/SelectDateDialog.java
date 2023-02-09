package com.chenghan.keepaccounts.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.CalendarView;

import androidx.annotation.NonNull;

import com.chenghan.keepaccounts.R;

public class SelectDateDialog extends Dialog {
    private CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_data);
        initView();
    }

    private void initView() {
        calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

            }
        });
    }

    public SelectDateDialog(@NonNull Context context) {
        super(context);
    }
}
