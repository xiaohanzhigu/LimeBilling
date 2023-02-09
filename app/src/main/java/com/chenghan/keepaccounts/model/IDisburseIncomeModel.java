package com.chenghan.keepaccounts.model;

import com.chenghan.keepaccounts.bean.DisburseIncome;

import java.util.List;

public interface IDisburseIncomeModel {
    List<DisburseIncome> getListByDate(long startTime, long endTime);

    List<DisburseIncome> getListByMonth(long minMonthDate, long maxMonthDate);

    void addAccount(DisburseIncome disburseIncome);

    void deleteAccount(DisburseIncome disburseIncome);

    void updateAccount(DisburseIncome disburseIncome);

    DisburseIncome getDisburseIncomeById(int id);
}
