package com.chenghan.keepaccounts.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chenghan.keepaccounts.R;
import com.chenghan.keepaccounts.bean.User;
import com.chenghan.keepaccounts.common.Data;
import com.chenghan.keepaccounts.common.SharedPreferencesSaveData;
import com.chenghan.keepaccounts.common.StringUtils;

public class EditPasswordDialog extends Dialog implements View.OnClickListener {
    private EditText oldPwdEt, newPwdEt;
    private Button ensureBtn, cancelBtn;
    private TextView noEmptyTv1, noEmptyTv2;
    private OnEnsureListener onEnsureListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_edit_password);
        //设置背景透明
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
        if (v.getId() == R.id.btn_edit_pwd_ensure) {
            String oldPwd = oldPwdEt.getText().toString();
            String newPwd = newPwdEt.getText().toString();
            if (TextUtils.isEmpty(oldPwd)) {
                noEmptyTv1.setText("不可以为空");
                noEmptyTv1.setVisibility(View.VISIBLE);
                return;
            }
            if (TextUtils.isEmpty(newPwd)) {
                noEmptyTv2.setText("不可以为空");
                noEmptyTv2.setVisibility(View.VISIBLE);
                return;
            }

            User user = (User) Data.getData("user");
            String password = user.getPassword();
            String oldPassword = StringUtils.MD5(oldPwd);

            if (!password.equals(oldPassword)) {
                noEmptyTv1.setText("旧密码错误");
                noEmptyTv1.setVisibility(View.VISIBLE);
                return;
            }
            String newPassword = StringUtils.MD5(newPwd);

            HintDialog dialog = new HintDialog(getContext(), "确定修改密码");
            dialog.setOnEnsureListener(new HintDialog.OnEnsureListener() {
                @Override
                public void onEnsure() {
                    onEnsureListener.onEnsureListener(newPassword);
                }
            });
            dialog.show();
        }
        cancel();
    }

    private void initView() {
        oldPwdEt = findViewById(R.id.et_edit_old_password);
        newPwdEt = findViewById(R.id.et_edit_new_password);
        ensureBtn = findViewById(R.id.btn_edit_pwd_ensure);
        cancelBtn = findViewById(R.id.btn_edit_pwd_cancel);
        noEmptyTv1 = findViewById(R.id.tv_edit_no_empty1);
        noEmptyTv2 = findViewById(R.id.tv_edit_no_empty2);
        noEmptyTv1.setVisibility(View.INVISIBLE);
        noEmptyTv2.setVisibility(View.INVISIBLE);
        ensureBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }

    public interface OnEnsureListener{
        void onEnsureListener(String password);
    }

    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    public EditPasswordDialog(@NonNull Context context) {
        super(context);
    }
}
