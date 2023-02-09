package com.chenghan.keepaccounts.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chenghan.keepaccounts.R;
import com.chenghan.keepaccounts.base.BaseActivity;
import com.chenghan.keepaccounts.bean.DisburseIncome;
import com.chenghan.keepaccounts.common.DateUtils;
import com.chenghan.keepaccounts.dialog.HintDialog;
import com.chenghan.keepaccounts.presenter.EditPresenter;
import com.chenghan.keepaccounts.view.IEditView;

public class EditActivity extends BaseActivity<EditPresenter, IEditView> implements IEditView, View.OnClickListener {
    private TextView isDisburseTv, titleTv, remarkTv, priceTv, dateTv;
    private Button deleteBtn, editBtn;
    private ImageButton cancelBtn;
    private DisburseIncome dI;
    private HintDialog isDeleteDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initView();
        initDate();
    }


    private void initView() {
        isDisburseTv = findViewById(R.id.tv_edit_is_disburse);
        titleTv = findViewById(R.id.tv_edit_title);
        remarkTv = findViewById(R.id.tv_edit_remark);
        priceTv = findViewById(R.id.tv_edit_price);
        dateTv = findViewById(R.id.tv_edit_date);

        editBtn = findViewById(R.id.btn_edit_edit);
        deleteBtn = findViewById(R.id.btn_edit_delete);

        cancelBtn = findViewById(R.id.btn_edit_cancel);
        editBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }

    private void initDate() {
        Intent intent = getIntent();
        this.dI = (DisburseIncome) intent.getSerializableExtra("disburseIncome");
        showDate();

        isDeleteDialog = new HintDialog(EditActivity.this, "确定删除当前账单?");
        isDeleteDialog.setOnEnsureListener(new HintDialog.OnEnsureListener() {
            @Override
            public void onEnsure() {
                presenter.deleteAccount(dI);
                finish();
            }
        });
        showDate();
    }

    private void showDate() {
        presenter.showDate(dI.getId());
    }

    @Override
    protected void onStart() {
        super.onStart();
        showDate();
    }

    @Override
    public void refresh(DisburseIncome disburseIncome) {
        Integer isDisburse = disburseIncome.getIsDisburse();
        if (isDisburse == 1) {
            isDisburseTv.setText("支出");
        } else {
            isDisburseTv.setText("收入");
        }

        titleTv.setText(disburseIncome.getTitle());
        remarkTv.setText(disburseIncome.getRemark());
        priceTv.setText(disburseIncome.getPrice().toString());
        String date = DateUtils.toStr(disburseIncome.getWriteTime());
        dateTv.setText(date);
    }


    @Override
    protected EditPresenter createPresenter() {
        return new EditPresenter(this);
    }


    @Override
    public void showMessage(String msg) {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_edit_cancel:
                finish();
                break;
            case R.id.btn_edit_edit:
                Intent intent = new Intent(this, AddActivity.class);
                intent.putExtra("dI", dI);
                startActivity(intent);
                break;
            case R.id.btn_edit_delete:
                isDeleteDialog.show();
                break;
        }
    }
}