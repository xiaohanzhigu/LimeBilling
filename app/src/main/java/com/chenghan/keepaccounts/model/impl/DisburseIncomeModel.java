package com.chenghan.keepaccounts.model.impl;

import android.database.sqlite.SQLiteOpenHelper;

import com.chenghan.keepaccounts.bean.DisburseIncome;
import com.chenghan.keepaccounts.base.BaseModel;
import com.chenghan.keepaccounts.bean.LabelType;
import com.chenghan.keepaccounts.bean.User;
import com.chenghan.keepaccounts.common.Data;
import com.chenghan.keepaccounts.model.IDisburseIncomeModel;

import java.util.List;

public class DisburseIncomeModel extends BaseModel<DisburseIncome> implements IDisburseIncomeModel {
    public DisburseIncomeModel(SQLiteOpenHelper helper) {
        super(helper);
    }

    @Override
    public List<DisburseIncome> getListByDate(long startTime, long endTime) {
        Integer userId = ((User) Data.getData("user")).getId();
        String sql = "select * from t_disburse_income where user = ? and writeTime between ? and ? order by id desc";
        List<DisburseIncome> list = queryMulti(sql, userId, startTime, endTime);
        return list;
    }

    @Override
    public List<DisburseIncome> getListByMonth(long minMonthDate, long maxMonthDate) {
        Integer userId = ((User) Data.getData("user")).getId();
        String sql = "select * from t_disburse_income where user = ? and writeTime between ? and ?";
        List<DisburseIncome> list = queryMulti(sql, userId,minMonthDate, maxMonthDate);
        return list;
    }

    @Override
    public void addAccount(DisburseIncome dI) {
        Integer userId = ((User) Data.getData("user")).getId();
        String sql = "insert into t_disburse_income values(null, ?, ?, ?, ?, ?, ?, ?)";
        update(sql, dI.getIsDisburse(), dI.getTitle(), dI.getRemark(), dI.getPrice(), dI.getImage(), dI.getWriteTime(), userId);
    }

    @Override
    public void deleteAccount(DisburseIncome disburseIncome) {
        update("delete from t_disburse_income where id = ?", disburseIncome.getId());
    }

    @Override
    public void updateAccount(DisburseIncome dI) {
        String sql = "update t_disburse_income set isDisburse = ?, title = ?, remark = ?, price = ?, image = ?, writeTime = ? where id = ?";
        update(sql, dI.getIsDisburse(), dI.getTitle(), dI.getRemark(), dI.getPrice(), dI.getImage(), dI.getWriteTime(), dI.getId());
    }

    @Override
    public DisburseIncome getDisburseIncomeById(int id) {
        return querySingle("select * from t_disburse_income where id = ?", id);
    }


}
