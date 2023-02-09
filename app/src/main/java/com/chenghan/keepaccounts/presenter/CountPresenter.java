package com.chenghan.keepaccounts.presenter;

import android.content.Context;
import android.util.Log;

import com.chenghan.keepaccounts.base.BasePresenter;
import com.chenghan.keepaccounts.bean.DisburseIncome;
import com.chenghan.keepaccounts.common.DateType;
import com.chenghan.keepaccounts.common.DateUtils;
import com.chenghan.keepaccounts.model.IDisburseIncomeModel;
import com.chenghan.keepaccounts.model.impl.DisburseIncomeModel;
import com.chenghan.keepaccounts.db.keepAccountsDatabaseHelper;
import com.chenghan.keepaccounts.view.ICountView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountPresenter<T extends ICountView> extends BasePresenter {
    private IDisburseIncomeModel diModel;

    public void showYearSpend(String year) {
        String yearStart = year + "-00-00";//得到年开始时间
        String yearEnd = year + "-12-31";//得到年结束时间
        try {
            long yearStartTime = DateUtils.parse(yearStart, DateType.YEAR_MONTH_DAY);
            long yearEndTime = DateUtils.parse(yearEnd, DateType.YEAR_MONTH_DAY);
            Map<String, Object> spendMap = new HashMap();

            //查询年统计信息
            List<DisburseIncome> list = diModel.getListByDate(yearStartTime, yearEndTime);
            Double income = 0.0, expend = 0.0, surplus = 0.0;
            for (DisburseIncome dI : list) {
                if (dI.getIsDisburse() == 1) {
                    expend += dI.getPrice();
                } else {
                    income += dI.getPrice();
                }
            }
            surplus = income - expend;
            spendMap.put("yearIncome", income);
            spendMap.put("yearExpend", expend);
            spendMap.put("yearSurplus", surplus);
            spendMap.put("yearCount", list.size());
            Log.i("Main", "income" + income + "expend" + expend + "surplus" + surplus);
            dataClassification(list, year, spendMap);
            ((T)weakView.get()).refresh(spendMap);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void dataClassification(List<DisburseIncome> list, String year, Map<String, Object> spendMap) {
        List<Map<String, Object>> monthExpendList = new ArrayList<>();
        List<Map<String, Object>> monthIncomeList = new ArrayList<>();
        Integer month;

        Integer currentYear = Integer.parseInt(DateUtils.getNowYear());
        //如果选择的是当前时间的年，则只显示1月到当前月的信息
        if (Integer.parseInt(year) == currentYear) {
            month = Integer.parseInt(DateUtils.getNowMonth());
        } else {
            month = 12;
        }

        for (; month > 0; month--) {
            String date = year + "-" + month.toString() + "-00 00:00:00";

            long yearStartTime = DateUtils.getMinMonthDate(date);
            long yearEndTime = DateUtils.getMaxMonthDate(date);

            Double monthIncome = 0.0, monthExpend = 0.0;

            for (DisburseIncome dI : list) {
                //是某月的账单
                Long writeTime = dI.getWriteTime();
                if (dI.getWriteTime() >= yearStartTime && dI.getWriteTime() <= yearEndTime) {
                    if (dI.getIsDisburse() == 1) {
                        monthExpend += dI.getPrice();
                    } else {
                        monthIncome += dI.getPrice();
                    }
                }
            }
            HashMap<String, Object> expendMap = new HashMap<>();
            expendMap.put("month", month + "月");
            expendMap.put("price", monthExpend);
            HashMap<String, Object> incomeMap = new HashMap<>();
            incomeMap.put("month", month + "月");
            incomeMap.put("price", monthIncome);

            monthExpendList.add(expendMap);
            monthIncomeList.add(incomeMap);
        }
        spendMap.put("monthExpendList", monthExpendList);
        spendMap.put("monthIncomeList", monthIncomeList);
    }

    public CountPresenter(Context context) {
        this.diModel = new DisburseIncomeModel(new keepAccountsDatabaseHelper(context));
    }


}
