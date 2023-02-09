package com.chenghan.keepaccounts.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chenghan.keepaccounts.R;
import com.chenghan.keepaccounts.base.BaseActivity;
import com.chenghan.keepaccounts.bean.User;
import com.chenghan.keepaccounts.presenter.RegisterPresenter;
import com.chenghan.keepaccounts.view.IRegisterView;

public class RegisterActivity extends BaseActivity<RegisterPresenter, IRegisterView> implements IRegisterView, View.OnClickListener {
    private Button registerBtn, toLoginBtn;
    private EditText usernameEt, passwordEt, confirmPasswordEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void init() {
        registerBtn = findViewById(R.id.btn_register);
        toLoginBtn = findViewById(R.id.btn_to_register);
        usernameEt = findViewById(R.id.et_username);
        passwordEt = findViewById(R.id.et_password);
        confirmPasswordEt = findViewById(R.id.et_confirm_password);

        registerBtn.setOnClickListener(this);
        toLoginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                String username = usernameEt.getText().toString();
                String password = passwordEt.getText().toString();
                String confirmPassword = confirmPasswordEt.getText().toString();
                presenter.registerUser(username, password, confirmPassword);
                break;
            case R.id.btn_to_register:
                toLoginPage(null);
                break;
        }
    }

    @Override
    public void toLoginPage(User user) {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        if (user != null) {
            intent.putExtra("username", user.getUsername());
            intent.putExtra("password", user.getPassword());
        }
        startActivity(intent);
    }
}