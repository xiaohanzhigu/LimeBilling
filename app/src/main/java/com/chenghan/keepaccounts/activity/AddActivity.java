package com.chenghan.keepaccounts.activity;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.chenghan.keepaccounts.R;
import com.chenghan.keepaccounts.adapter.TableLayoutFragmentPagerAdapter;
import com.chenghan.keepaccounts.base.BaseActivity;
import com.chenghan.keepaccounts.bean.DisburseIncome;
import com.chenghan.keepaccounts.bean.LabelType;
import com.chenghan.keepaccounts.dialog.KeyBoardDialog;
import com.chenghan.keepaccounts.fragment.BaseAddFragment;
import com.chenghan.keepaccounts.fragment.ExpendFragment;
import com.chenghan.keepaccounts.fragment.IncomeFragment;
import com.chenghan.keepaccounts.presenter.AddPresenter;
import com.chenghan.keepaccounts.view.IAddView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class AddActivity extends BaseActivity<AddPresenter, IAddView> implements IAddView, BaseAddFragment.OnChangeListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private KeyBoardDialog keyBoardDialog;
    private DisburseIncome disburseIncome, dI;
    private LabelType selectLabelType;
    private Button cancelBtn;
    private LabelType label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        //获取dI如果获取到了就是从编辑页面来的
        Intent intent = getIntent();
        dI = (DisburseIncome) intent.getSerializableExtra("dI");
        if (dI != null) {
            label = presenter.getLabelByName(dI.getTitle());
        }
        initView();
        initData();
    }

    private void initData() {
        keyBoardDialog.setOnEnsureListener(new KeyBoardDialog.OnEnsureListener() {
            @Override
            public void onEnsure(DisburseIncome disburseIncome) {
                AddActivity.this.disburseIncome = disburseIncome;
                if (selectLabelType == null) {
                    //说明用户没有选则标签，根据当前的viewPager是那一页判断默认标签
                    int selectedTabPosition = tabLayout.getSelectedTabPosition();
                    if (selectedTabPosition == 0) {
                        selectLabelType = new LabelType("餐饮", 1, "bj_label_cy");
                    } else {
                        selectLabelType = new LabelType("工资", 0, "bj_label_gz");
                    }
                }
                Log.i("Main", disburseIncome.toString() + ":" + selectLabelType.toString());
                disburseIncome.setIsDisburse(selectLabelType.getKind());
                disburseIncome.setTitle(selectLabelType.getTypeName());
                disburseIncome.setImage(selectLabelType.getImage());

                //编辑页面功能
                if (dI != null) {
                    disburseIncome.setId(dI.getId());
                }
                presenter.addOrUpdateAccount(disburseIncome);
                keyBoardDialog.cancel();
                finish();
            }

            @Override
            public DisburseIncome sentMsgToDialog() {
                return dI;
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected AddPresenter createPresenter() {
        return new AddPresenter(this);
    }

    private void initView() {
        tabLayout = findViewById(R.id.tl_tabs);
        viewPager = findViewById(R.id.viewPage);
        cancelBtn = findViewById(R.id.btn_add_cancel);

        ArrayList<Fragment> fragList = new ArrayList();
        ExpendFragment expendFragment = new ExpendFragment();
        IncomeFragment incomeFragment = new IncomeFragment();

        expendFragment.setOnChangeListener(this);
        incomeFragment.setOnChangeListener(this);

        fragList.add(expendFragment);
        fragList.add(incomeFragment);

        //设置适配器
        TableLayoutFragmentPagerAdapter pagerAdapter =
                new TableLayoutFragmentPagerAdapter(getSupportFragmentManager(), fragList, new String[]{"支出", "收入"});
        viewPager.setAdapter(pagerAdapter);
        //设置page
        tabLayout.setupWithViewPager(viewPager);

        keyBoardDialog = new KeyBoardDialog(this, true);
        keyBoardDialog.setDialogSize();
        keyBoardDialog.show();
    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public void onChangeListener(LabelType selectLabelType) {
        this.selectLabelType = selectLabelType;
    }

    @Override
    public LabelType sentMsgToFrag() {
        return label;
    }
}