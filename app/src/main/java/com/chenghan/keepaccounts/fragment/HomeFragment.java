package com.chenghan.keepaccounts.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chenghan.keepaccounts.R;
import com.chenghan.keepaccounts.activity.AddActivity;
import com.chenghan.keepaccounts.activity.EditActivity;
import com.chenghan.keepaccounts.adapter.DetailAdapter;
import com.chenghan.keepaccounts.base.BaseFragment;
import com.chenghan.keepaccounts.bean.DisburseIncome;
import com.chenghan.keepaccounts.bean.LabelType;
import com.chenghan.keepaccounts.common.DateType;
import com.chenghan.keepaccounts.common.DateUtils;
import com.chenghan.keepaccounts.common.MyScrollView;
import com.chenghan.keepaccounts.common.UnScrollListView;
import com.chenghan.keepaccounts.databinding.FragmentHomeBinding;
import com.chenghan.keepaccounts.dialog.DataDialog;
import com.chenghan.keepaccounts.dialog.KeyBoardDialog;
import com.chenghan.keepaccounts.presenter.HomePresenter;
import com.chenghan.keepaccounts.view.IHomeView;

import java.util.List;
import java.util.Map;


public class HomeFragment extends BaseFragment<HomePresenter, IHomeView> implements IHomeView, View.OnClickListener {
    private DetailAdapter detailAdapter;
    private TextView monthIncomeTv, monthExpendTv, monthSurplusTv, homeDataTv, dayTotalExpendTv;
    private ImageButton addBtn;
    private View root;
    private Context context;
    private Button labelBreakfastBtn, labelLunchBth, labelDinnerBth, labelBusBtn, labelTeaBtn, labelPhoneBtn, monthBtn;
    private Integer labelId;
    private KeyBoardDialog keyBoardDialog;  //添加功能的键盘
    private FragmentHomeBinding binding;
    private List<DisburseIncome> list;//listview的数据
    private DataDialog dialog;  //日期选中
    private String date;

    private UnScrollListView disburseLv;
    private MyScrollView mScrollView;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        this.root = root;
        initView();
        initDate();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initDate() {
        detailAdapter = new DetailAdapter(getContext(), null);
        showListView();//查询列表数据
        disburseLv.setFocusable(false);
        disburseLv.setAdapter(detailAdapter);

        disburseLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), EditActivity.class);
                DisburseIncome disburseIncome = list.get(position);
                intent.putExtra("disburseIncome", disburseIncome);
                startActivity(intent);
            }
        });

        dialog = new DataDialog(getContext());
        dialog.setOnSelectDateListener(new DataDialog.OnSelectDateListener() {
            @Override
            public void onSelectDate(int year, int month, int day) {
                Log.i("Main", year + "年" + month + "月" + day + "日");
                date = year + "-" + month + "-" + day;
                updateView(year, month, day);
                showListView();
            }
        });
        dialog.setDialogSize(false);
    }

    private void updateView(int year, int month, int day) {
        monthBtn.setText(month + "月");
        String showDate = year + "年" + month + "月" + day + "日";
        String todayTime = DateUtils.getTodayTime(DateType.NYR);
        //如果选中的是今天
        if (todayTime.equals(showDate)) {
            homeDataTv.setText("今天");
        } else {
            homeDataTv.setText(showDate);
        }
    }

    private void initView() {
        disburseLv = (UnScrollListView) root.findViewById(R.id.lv_detail);
        mScrollView = (MyScrollView) root.findViewById(R.id.scrollView);
        monthExpendTv = root.findViewById(R.id.tv_month_expenses);
        monthIncomeTv = root.findViewById(R.id.tv_month_income);
        monthSurplusTv = root.findViewById(R.id.tv_month_surplus);
        labelTeaBtn = root.findViewById(R.id.but_label_tea);
        labelDinnerBth = root.findViewById(R.id.but_label_dinner);
        labelBusBtn = root.findViewById(R.id.but_label_bus);
        labelPhoneBtn = root.findViewById(R.id.but_label_phone);
        labelBreakfastBtn = root.findViewById(R.id.but_label_breakfast);
        labelLunchBth = root.findViewById(R.id.but_label_lunch);
        monthBtn = root.findViewById(R.id.btn_month);
        homeDataTv = root.findViewById(R.id.tv_home_data);
        addBtn = root.findViewById(R.id.btn_add);
        dayTotalExpendTv = root.findViewById(R.id.tv_day_total_disburse);

        monthBtn.setOnClickListener(this);
        labelPhoneBtn.setOnClickListener(this);
        labelBusBtn.setOnClickListener(this);
        labelDinnerBth.setOnClickListener(this);
        labelTeaBtn.setOnClickListener(this);
        labelBreakfastBtn.setOnClickListener(this);
        labelLunchBth.setOnClickListener(this);
        addBtn.setOnClickListener(this);

        String month = DateUtils.getNowMonth();
        monthBtn.setText(month + "月");
    }

    @Override
    public void onStart() {
        super.onStart();
        showListView();
    }

    private void showListView() {
        if (date == null) { //判断用户有没有选则要显示的日期
            //如果date没有数据，则默认为今天
            presenter.showDetailView();
        } else {
            presenter.showDetailView(date);
        }
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(getContext());
    }

    @Override
    public void refresh(List<DisburseIncome> list) {
        this.list = list;   //记录找到的数据
        detailAdapter.changeList(list);
        Map map = presenter.showMonthSpend(date); //查询页面的统计信息
        monthExpendTv.setText("￥" + map.get("expend").toString());
        monthIncomeTv.setText(map.get("income").toString());
        monthSurplusTv.setText(map.get("surplus").toString());
        dayTotalExpendTv.setText("支" + map.get("dayExpend").toString());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_month:
                dialog.show();
                break;
            case R.id.btn_add:
                startActivity(new Intent(getActivity(), AddActivity.class));
                break;
            case R.id.but_label_breakfast:
                labelId = 1;
                showKeyBoard("#早餐");
                break;
            case R.id.but_label_lunch:
                labelId = 1;
                showKeyBoard("#午餐");
                break;
            case R.id.but_label_dinner:
                labelId = 1;
                showKeyBoard("#晚餐");
                break;
            case R.id.but_label_tea:
                labelId = 8;
                showKeyBoard("#奶茶");
                break;
            case R.id.but_label_bus:
                labelId = 3;
                showKeyBoard("#交通");
                break;
            case R.id.but_label_phone:
                labelId = 6;
                showKeyBoard("#通讯");
                break;
        }
    }

    //显示软键盘
    private void showKeyBoard(String content) {
        if (keyBoardDialog == null) {
            keyBoardDialog = new KeyBoardDialog(getContext(), false);
            keyBoardDialog.setDialogSize();
            keyBoardDialog.setOnEnsureListener(new KeyBoardDialog.OnEnsureListener() {
                @Override
                public void onEnsure(DisburseIncome disburseIncome) {
                    LabelType labelType = presenter.getLabelTypeById(labelId);
                    disburseIncome.setIsDisburse(1);
                    disburseIncome.setImage(labelType.getImage());
                    disburseIncome.setTitle(labelType.getTypeName());
                    presenter.addAccount(disburseIncome);
                    keyBoardDialog.cancel();
                    showListView();
                }

                @Override
                public DisburseIncome sentMsgToDialog() {
                    return null;
                }
            });
        }
        keyBoardDialog.show();
        keyBoardDialog.showContent(content);
    }

    @Override
    public void showMessage(String msg) {

    }
}