package com.chenghan.keepaccounts.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chenghan.keepaccounts.R;
import com.chenghan.keepaccounts.activity.LoginActivity;
import com.chenghan.keepaccounts.base.BaseFragment;
import com.chenghan.keepaccounts.bean.User;
import com.chenghan.keepaccounts.common.Data;
import com.chenghan.keepaccounts.common.SharedPreferencesSaveData;
import com.chenghan.keepaccounts.databinding.FragmentNotificationsBinding;
import com.chenghan.keepaccounts.dialog.BaseEditDialog;
import com.chenghan.keepaccounts.dialog.EditNameDialog;
import com.chenghan.keepaccounts.dialog.EditPasswordDialog;
import com.chenghan.keepaccounts.dialog.EditPhoneDialog;
import com.chenghan.keepaccounts.dialog.EditWechatDialog;
import com.chenghan.keepaccounts.dialog.HintDialog;
import com.chenghan.keepaccounts.presenter.MyPresenter;
import com.chenghan.keepaccounts.view.IMyView;


public class SettingFragment extends BaseFragment<MyPresenter, IMyView> implements IMyView, View.OnClickListener {
    private View root;
    private TextView nicknameTv;
    private User user;
    private Button editNicknameBtn, editPwdBtn, editWXBtn, editPhoneBtn, exitBtn;
    private FragmentNotificationsBinding binding;
    private BaseEditDialog baseEditDialog;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        initView();
        refresh();
        return root;
    }

    private void initView() {
        nicknameTv = root.findViewById(R.id.tv_my_nickname);
        editNicknameBtn = root.findViewById(R.id.btn_my_edit_nickname);
        editPwdBtn = root.findViewById(R.id.btn_my_edit_pwd);
        editWXBtn = root.findViewById(R.id.btn_my_edit_wx);
        editPhoneBtn = root.findViewById(R.id.btn_my_edit_phone);
        exitBtn = root.findViewById(R.id.btn_my_exit);
        exitBtn.setOnClickListener(this);
        editNicknameBtn.setOnClickListener(this);
        editPwdBtn.setOnClickListener(this);
        editWXBtn.setOnClickListener(this);
        editPhoneBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_my_edit_nickname:
                editNickname();
                break;
            case R.id.btn_my_edit_wx:
                editWechat();
                break;
            case R.id.btn_my_edit_phone:
                editPhone();
                break;
            case R.id.btn_my_edit_pwd:
                editPwd();
                break;
            case R.id.btn_my_exit:
                logout();
                break;
        }
    }

    public void editPwd() {
        EditPasswordDialog dialog = new EditPasswordDialog(getContext());
        dialog.setOnEnsureListener(new EditPasswordDialog.OnEnsureListener() {
            @Override
            public void onEnsureListener(String password) {
                presenter.editPwd(password);
                logout();
            }
        });
        dialog.show();
    }

    public void logout() {
        HintDialog dialog = new HintDialog(getContext(), "是否退出重新登录?");
        dialog.setOnEnsureListener(new HintDialog.OnEnsureListener() {
            @Override
            public void onEnsure() {
                Data.putData("user", null);
                SharedPreferencesSaveData.clear(getContext());
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
        dialog.show();
    }

    private void editNickname() {
        EditNameDialog editNameDialog = new EditNameDialog(getContext());
        editNameDialog.setOnEnsureListener(new BaseEditDialog.OnEnsureListener() {
            @Override
            public void onEnsureListener(String editContent) {
                Log.i("Main", "onEnsureListener: " + editContent);
                presenter.editNickname(editContent);
            }
        });
        editNameDialog.show();
    }

    private void editWechat() {
        EditWechatDialog editWechatDialog = new EditWechatDialog(getContext());
        editWechatDialog.setOnEnsureListener(new BaseEditDialog.OnEnsureListener() {
            @Override
            public void onEnsureListener(String editContent) {
                Log.i("Main", "onEnsureListener: " + editContent);
                presenter.editWX(editContent);
            }
        });
        editWechatDialog.show();
    }

    private void editPhone() {
        EditPhoneDialog editPhoneDialog = new EditPhoneDialog(getContext());
        editPhoneDialog.setOnEnsureListener(new BaseEditDialog.OnEnsureListener() {
            @Override
            public void onEnsureListener(String editContent) {
                Log.i("Main", "onEnsureListener: " + editContent);
                presenter.editPhone(editContent);
            }
        });
        editPhoneDialog.show();
    }

    @Override
    public void refresh() {
        user = (User) Data.getData("user");
        String nickName = user.getNickName();
        nicknameTv.setText(nickName);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    protected MyPresenter createPresenter() {
        MyPresenter myPresenter = new MyPresenter(getContext());
        return myPresenter;
    }

    @Override
    public void showMessage(String msg) {

    }

}