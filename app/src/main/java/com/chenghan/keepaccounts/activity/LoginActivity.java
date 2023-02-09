package com.chenghan.keepaccounts.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.chenghan.keepaccounts.R;
import com.chenghan.keepaccounts.base.BaseActivity;
import com.chenghan.keepaccounts.common.Data;
import com.chenghan.keepaccounts.common.SharedPreferencesSaveData;
import com.chenghan.keepaccounts.bean.User;
import com.chenghan.keepaccounts.presenter.LoginPresenter;
import com.chenghan.keepaccounts.view.ILoginView;

public class LoginActivity extends BaseActivity<LoginPresenter, ILoginView> implements ILoginView, View.OnClickListener {
    private Button loginBtn, toRegisterBtn;
    private EditText usernameEt, passwordEt;
    private CheckBox cbAutoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        //如果为false说明已经登录过一次
        if ((boolean) Data.getData("isFirstOpenApplication")) {
            autoLogin();
        }
        Data.putData("isFirstOpenApplication", false);

        //注册页面的回填
        backfillDate();
    }

    private void backfillDate() {
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String password = intent.getStringExtra("password");
        if (!(TextUtils.isEmpty(username) && TextUtils.isEmpty(password))) {
            usernameEt.setText(username);
            passwordEt.setText(password);
        }
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                String username = usernameEt.getText().toString();
                String password = passwordEt.getText().toString();
                presenter.login(username, password, false);
                break;
            case R.id.btn_to_register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
        }
    }

    @Override
    public void login() {
        if (cbAutoLogin.isChecked()) {
            User user = (User) Data.getData("user");
            SharedPreferencesSaveData.saveData(this, user);
        }
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    public void autoLogin() {
        User user = SharedPreferencesSaveData.getData(this);
        if (user == null) {
            return;
        }
        presenter.login(user.getUsername(), user.getPassword(), true);
    }

    private void initView() {
        loginBtn = findViewById(R.id.btn_register);
        toRegisterBtn = findViewById(R.id.btn_to_register);
        usernameEt = findViewById(R.id.et_username);
        passwordEt = findViewById(R.id.et_password);
        cbAutoLogin = findViewById(R.id.cb_auto_login);
        toRegisterBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


}