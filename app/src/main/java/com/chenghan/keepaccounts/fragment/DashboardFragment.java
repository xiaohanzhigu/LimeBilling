package com.chenghan.keepaccounts.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chenghan.keepaccounts.R;
import com.chenghan.keepaccounts.adapter.MonthAdapter;
import com.chenghan.keepaccounts.base.BaseFragment;
import com.chenghan.keepaccounts.common.DateUtils;
import com.chenghan.keepaccounts.databinding.FragmentDashboardBinding;
import com.chenghan.keepaccounts.dialog.DataDialog;
import com.chenghan.keepaccounts.dialog.YearSelectDialog;
import com.chenghan.keepaccounts.presenter.CountPresenter;
import com.chenghan.keepaccounts.view.ICountView;

import java.util.List;
import java.util.Map;


public class DashboardFragment extends BaseFragment<CountPresenter, ICountView> implements ICountView{
    private View root;
    private TextView yearExpendTv, yearIncomeTv, yearSurplusTv, yearCountTv;
    private Button yearBtn;
    private ListView monthLv;
    private MonthAdapter monthAdapter;
    //private DataDialog dialog;
    private YearSelectDialog dialog;
    public int year;//当前页面显示的年份
    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        initView();
        initDate();
        showYearSpend();
        return root;
    }


    private void initView() {
        yearExpendTv = root.findViewById(R.id.tv_count_jnzc);
        yearIncomeTv = root.findViewById(R.id.tv_count_jnsr);
        yearSurplusTv = root.findViewById(R.id.tv_count_jnjy);
        yearCountTv = root.findViewById(R.id.tv_count_jzbs);
        yearBtn = root.findViewById(R.id.tv_year);
        monthLv = root.findViewById(R.id.lv_month);
        String year = DateUtils.getNowYear();
        yearBtn.setText(year + "年");
    }

    private void initDate() {
        monthAdapter = new MonthAdapter(getContext(), null);
        //dialog = new DataDialog(getContext());
        //dialog.setDialogSize(false);
        //dialog.setOnSelectDateListener(new DataDialog.OnSelectDateListener() {
        //    @Override
        //    public void onSelectDate(int year, int month, int day) {
        //        yearBtn.setText(year + "年");
        //        DashboardFragment.this.year = year;
        //        showYearSpend();
        //    }
        //});
        dialog = new YearSelectDialog(getContext());
        dialog.setOnSelectDateListener(new YearSelectDialog.OnSelectDateListener() {
            @Override
            public void onSelectDate(Integer year) {
                yearBtn.setText(year + "年");
                DashboardFragment.this.year = year;
                showYearSpend();
            }
        });

        yearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
    }

    private void showYearSpend() {
        String s = yearBtn.getText().toString();
        String year = s.substring(0, 4);
        presenter.showYearSpend(year);
    }

    @Override
    public void refresh(Map<String, Object> map) {
        yearExpendTv.setText("￥" + map.get("yearExpend").toString());
        yearIncomeTv.setText("￥" + map.get("yearIncome").toString());
        yearSurplusTv.setText("￥" + map.get("yearSurplus").toString());
        yearCountTv.setText(map.get("yearCount").toString());
        List<Map<String, Object>> monthExpendList = (List<Map<String, Object>>) map.get("monthExpendList");
        List<Map<String, Object>> monthIncomeList = (List<Map<String, Object>>) map.get("monthIncomeList");
        monthAdapter.changeList(monthExpendList);
        monthLv.setAdapter(monthAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    protected CountPresenter createPresenter() {
        return new CountPresenter(getContext());
    }

    @Override
    public void showMessage(String msg) {

    }
}