package com.chenghan.keepaccounts.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chenghan.keepaccounts.R;

public class HintDialog extends Dialog implements View.OnClickListener {
    private Button ensureBtn, cancelBtn;
    private TextView hintTv;
    private OnEnsureListener onEnsureListener;
    private String hintMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_hint);

        //设置背景透明，不然会出现白色直角问题
        Window window = getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCanceledOnTouchOutside(false);
        //初始化布局控件
        initView();
        //设置参数必须在show之后，不然没有效果
        WindowManager.LayoutParams params = getWindow().getAttributes();
        getWindow().setAttributes(params);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_dialog_ensure) {
            onEnsureListener.onEnsure();
        }
        cancel();
    }

    public interface OnEnsureListener {
        public void onEnsure();
    }

    private void initView() {
        cancelBtn = findViewById(R.id.btn_dialog_cancel);
        ensureBtn = findViewById(R.id.btn_dialog_ensure);
        hintTv = findViewById(R.id.tv_setting_dialog_hint);
        hintTv.setText(hintMsg);
        cancelBtn.setOnClickListener(this);
        ensureBtn.setOnClickListener(this);
    }


    public HintDialog(@NonNull Context context, String message) {
        super(context);
        this.hintMsg = message;
    }

    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }
}
