package com.chenghan.keepaccounts.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chenghan.keepaccounts.R;
import com.chenghan.keepaccounts.bean.DisburseIncome;
import com.chenghan.keepaccounts.bean.User;
import com.chenghan.keepaccounts.common.Data;
import com.chenghan.keepaccounts.common.DateType;
import com.chenghan.keepaccounts.common.DateUtils;
import com.chenghan.keepaccounts.common.StringUtils;

import java.text.ParseException;

public class KeyBoardDialog extends Dialog implements View.OnClickListener {
    private Button inputBtn0, inputBtn1, inputBtn2, inputBtn3, inputBtn4, inputBtn5, inputBtn6, inputBtn7, inputBtn8, inputBtn9;
    private Button inputDateBtn, inputConfirmBtn, inputDotBtn, inputBackBtn, inputSumBtn, inputSubBtn;
    private TextView moneyTv;
    private EditText remarkEt;
    private StringBuffer moneyStr;
    private DisburseIncome disburseIncome, dI;
    private OnEnsureListener onEnsureListener;
    private DataDialog dataDialog;
    private long writeTime;
    private boolean isFixed;
    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_key);
        initView();
        initData();
    }

    public interface OnEnsureListener {
        void onEnsure(DisburseIncome disburseIncome);
        DisburseIncome sentMsgToDialog();
    }

    public void showContent(String content) {
        remarkEt.setHint(content);
    }


    private void initData() {
        try {
            String todayTime = DateUtils.getTodayTime(DateType.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);
            //?????????????????????
            String[] split = todayTime.split("-");
            String month = split[1];
            String day = split[2].substring(0, 2);
            inputDateBtn.setText(month + "???" + day + "???");
            //?????????????????????
            writeTime = DateUtils.parse(todayTime, DateType.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        dataDialog.setOnSelectDateListener(new DataDialog.OnSelectDateListener() {
            @Override
            public void onSelectDate(int year, int month, int day) {
                try {
                    inputDateBtn.setText(month + "???" + day + "???");
                    String date = year + "-" + month + "-" + day;
                    String nowTime = DateUtils.getNowTime();
                    date = date + " " + nowTime;
                    writeTime = DateUtils.parse(date, DateType.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void cancel() {
        moneyStr = new StringBuffer("???0.00");
        moneyTv.setText(moneyStr.toString());
        super.cancel();
    }

    private void showDate() {
        dataDialog.show();
    }

    private void getViewDate() {
        //????????????
        String moneyS = moneyStr.substring(1, moneyStr.length());
        double money = StringUtils.calculator(moneyS);
        Log.i("Main", "getViewDate: ?????????: " + money);

        //????????????
        String remark = remarkEt.getText().toString();
        if (TextUtils.isEmpty(remark)) {
            //????????????????????????????????????????????????
            remark = remarkEt.getHint().toString();
        }
        String substring = remark.substring(1, 2);
        if (!"#".equals(substring)) {   //??????????????????#??????,???????????????
            remark = remark.substring(1);
        }
        disburseIncome.setRemark(remark);
        disburseIncome.setPrice(money);
        disburseIncome.setWriteTime(writeTime);
    }

    //??????DialogSize??????????????????????????????
    public void setDialogSize() {
        Window window = getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        if (isFixed) {
            wlp.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
            //wlp.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            setCancelable(false);
            window.setDimAmount(0);
        }

        Display d = window.getWindowManager().getDefaultDisplay();
        wlp.width = (int) (d.getWidth());
        wlp.gravity = Gravity.BOTTOM;
        window.setBackgroundDrawableResource(R.color.transparent);
        window.setAttributes(wlp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_input_1:
                append("1");
                break;
            case R.id.btn_input_2:
                append("2");
                break;
            case R.id.btn_input_3:
                append("3");
                break;
            case R.id.btn_input_4:
                append("4");
                break;
            case R.id.btn_input_5:
                append("5");
                break;
            case R.id.btn_input_6:
                append("6");
                break;
            case R.id.btn_input_7:
                append("7");
                break;
            case R.id.btn_input_8:
                append("8");
                break;
            case R.id.btn_input_9:
                append("9");
                break;
            case R.id.btn_input_0:
                append("0");
                break;
            case R.id.btn_input_sum:
                append("+");
                break;
            case R.id.btn_input_sub:
                append("-");
                break;
            case R.id.btn_input_dot:
                append(".");
                break;
            case R.id.btn_input_back:
                backspace();
                break;
            case R.id.btn_input_confirm:
                ensure();
                break;
            case R.id.btn_input_date:
                showDate();
                break;
        }

    }

    private void ensure() {
        String msg;
        if (dI != null) {
            msg = "??????????????????????";
        } else {
            msg = "???????????????????";
        }

        HintDialog hintDialog = new HintDialog(getContext(), msg);
        hintDialog.setOnEnsureListener(new HintDialog.OnEnsureListener() {
            @Override
            public void onEnsure() {
                getViewDate();
                onEnsureListener.onEnsure(disburseIncome);
            }
        });
        hintDialog.show();
    }

    public void append(String s) {
        if (isEdit) {
            moneyStr = new StringBuffer("???0.00");
            isEdit = true;
        }

        String t = moneyStr.toString();
        if (("???0.00").equals(t)) {
            moneyStr = new StringBuffer("???");
        }

        if (".".equals(s)) {
            if (t.contains(".")) {
                return;
            }
        }
        moneyStr.append(s);
        moneyTv.setText(moneyStr.toString());
    }

    //??????
    public void backspace() {
        String t = moneyStr.toString();
        if (("???0.00").equals(t)) {
            return;
        }
        String str = moneyStr.substring(0, moneyStr.length() - 1);
        if ("???".equals(str)) {
            str = "???0.00";
        }
        moneyStr = new StringBuffer(str);
        moneyTv.setText(moneyStr.toString());
    }

    private void initView() {
        disburseIncome = new DisburseIncome();
        moneyStr = new StringBuffer("???0.00");
        inputDateBtn = findViewById(R.id.btn_input_date);
        inputConfirmBtn = findViewById(R.id.btn_input_confirm);
        inputDotBtn = findViewById(R.id.btn_input_dot);
        inputSumBtn = findViewById(R.id.btn_input_sum);
        inputSubBtn = findViewById(R.id.btn_input_sub);
        inputBackBtn = findViewById(R.id.btn_input_back);
        moneyTv = findViewById(R.id.tv_money);
        inputBtn0 = findViewById(R.id.btn_input_0);
        inputBtn1 = findViewById(R.id.btn_input_1);
        inputBtn2 = findViewById(R.id.btn_input_2);
        inputBtn3 = findViewById(R.id.btn_input_3);
        inputBtn4 = findViewById(R.id.btn_input_4);
        inputBtn5 = findViewById(R.id.btn_input_5);
        inputBtn6 = findViewById(R.id.btn_input_6);
        inputBtn7 = findViewById(R.id.btn_input_7);
        inputBtn8 = findViewById(R.id.btn_input_8);
        inputBtn9 = findViewById(R.id.btn_input_9);
        remarkEt = findViewById(R.id.et_remark);
        moneyTv.setText(moneyStr.toString());
        inputConfirmBtn.setOnClickListener(this);
        inputDotBtn.setOnClickListener(this);
        inputSumBtn.setOnClickListener(this);
        inputSubBtn.setOnClickListener(this);
        inputBackBtn.setOnClickListener(this);
        inputDateBtn.setOnClickListener(this);
        inputBtn0.setOnClickListener(this);
        inputBtn1.setOnClickListener(this);
        inputBtn2.setOnClickListener(this);
        inputBtn3.setOnClickListener(this);
        inputBtn4.setOnClickListener(this);
        inputBtn5.setOnClickListener(this);
        inputBtn6.setOnClickListener(this);
        inputBtn7.setOnClickListener(this);
        inputBtn8.setOnClickListener(this);
        inputBtn9.setOnClickListener(this);

        dataDialog = new DataDialog(getContext());
        dataDialog.setDialogSize(false);

        if (isEdit) {
            dI = onEnsureListener.sentMsgToDialog();
            moneyStr = new StringBuffer("???" + dI.getPrice());
            moneyTv.setText(moneyStr);
            writeTime = dI.getWriteTime();
            String date = DateUtils.toStr(writeTime);//?????????????????????????????????
            date = date.substring(5, 11);//????????????
            inputDateBtn.setText(date);
        }
    }


    public KeyBoardDialog(@NonNull Context context) {
        super(context);
    }

    public KeyBoardDialog(Context context, boolean isFixed) {
        super(context);
        this.isFixed = isFixed;
    }
    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
        //??????activity????????????????????????????????????
        if (onEnsureListener.sentMsgToDialog() != null) {
           isEdit = true;
        }
    }
}
