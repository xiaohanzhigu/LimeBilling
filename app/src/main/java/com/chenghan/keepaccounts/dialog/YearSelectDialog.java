package com.chenghan.keepaccounts.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;

import androidx.annotation.NonNull;

import com.chenghan.keepaccounts.R;
import com.chenghan.keepaccounts.adapter.YearSelectAdapter;
import com.chenghan.keepaccounts.common.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class YearSelectDialog extends Dialog {
    private GridView gridView;
    private YearSelectAdapter adapter;
    private List<Integer> yearList;
    private OnSelectDateListener onSelectDateListener;
    private ImageButton cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_year_select);
        init();
    }

    public void init() {
        setDialogSize();
        cancelBtn = findViewById(R.id.btn_year_select);
        gridView = findViewById(R.id.gv_year_select);
        yearList = new ArrayList<>();
        for (int i = 2012; i < 2033; i++) {
            yearList.add(i);
        }

        //根据当前年份找到list所在的位置
        Integer yearPos = 0;
        String nowYear = DateUtils.getNowYear();
        for (int i = 0;i < yearList.size(); i++) {
            Integer year = yearList.get(i);
            if (nowYear.equals(year.toString())) {
                yearPos = i;
            }
        }

        adapter = new YearSelectAdapter(getContext(), yearList);
        adapter.selectPos = yearPos;
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Integer year = yearList.get(position);
                adapter.selectPos = position;
                onSelectDateListener.onSelectDate(year);
                cancel();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
    }

    //设置DialogSize的尺寸于屏幕尺寸一致
    public void setDialogSize() {
        Window window = getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        Display d = window.getWindowManager().getDefaultDisplay();
        wlp.width = (int) (d.getWidth());
        wlp.gravity = Gravity.BOTTOM;
        window.setBackgroundDrawableResource(R.color.transparent);
        wlp.height = (int) (d.getHeight() * 0.5); // 高度设置为屏幕的0.6，根据实际情况调整
        window.setAttributes(wlp);

    }

    public interface OnSelectDateListener{
        void onSelectDate(Integer year);
    }

    public void setOnSelectDateListener(OnSelectDateListener onSelectDateListener) {
        this.onSelectDateListener = onSelectDateListener;
    }

    public YearSelectDialog(@NonNull Context context) {
        super(context);
    }
}
