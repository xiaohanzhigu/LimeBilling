package com.chenghan.keepaccounts.presenter;

import android.content.Context;

import com.chenghan.keepaccounts.base.BasePresenter;
import com.chenghan.keepaccounts.bean.DisburseIncome;
import com.chenghan.keepaccounts.bean.LabelType;
import com.chenghan.keepaccounts.common.DateType;
import com.chenghan.keepaccounts.common.DateUtils;
import com.chenghan.keepaccounts.model.IDisburseIncomeModel;
import com.chenghan.keepaccounts.model.ILabelTypeModel;
import com.chenghan.keepaccounts.model.impl.DisburseIncomeModel;
import com.chenghan.keepaccounts.model.impl.LabelTypeModel;
import com.chenghan.keepaccounts.db.keepAccountsDatabaseHelper;
import com.chenghan.keepaccounts.view.IHomeView;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomePresenter<T extends IHomeView> extends BasePresenter {
    private IDisburseIncomeModel diModel;
    private ILabelTypeModel iLabelTypeModel;

    public void showDetailView() {
        String date = DateUtils.getTodayTime();
        List<DisburseIncome> list = acquireDataByDate(date);
        ((T)weakView.get()).refresh(list);
    }

    public void showDetailView(String date) {
        List<DisburseIncome> list = acquireDataByDate(date);
        ((T)weakView.get()).refresh(list);
    }

    public Map<String, Double> showMonthSpend(String date) {
        if (date == null) {
            date = DateUtils.getTodayTime();
        }

        //获取月开始时间和结束时间
        Long minMonthDate = DateUtils.getMinMonthDate(date);
        Long maxMonthDate = DateUtils.getMaxMonthDate(date);

        //查询月统计信息
        List<DisburseIncome> list = diModel.getListByMonth(minMonthDate, maxMonthDate);
        Map<String, Double> spendMap = new HashMap();
        Double income = 0.0, expend = 0.0, surplus = 0.0;

        for (DisburseIncome dI : list) {
            if (dI.getIsDisburse() == 1) {
                expend += dI.getPrice();
            } else {
                income += dI.getPrice();
            }
        }
        surplus = income - expend;
        spendMap.put("income", income);
        spendMap.put("expend", expend);
        spendMap.put("surplus", surplus);

        //查询日统计信息
        List<DisburseIncome> dayDateList = acquireDataByDate(date);
        expend = 0.0;
        for (DisburseIncome dI : dayDateList) {
            if (dI.getIsDisburse() == 1) {
                expend += dI.getPrice();
            }
        }
        spendMap.put("dayExpend", expend);
        return spendMap;
    }

    private List<DisburseIncome> acquireDataByDate(String date){
        try {
            long startTime = DateUtils.parse(date, DateType.YEAR_MONTH_DAY);
            long endTime = startTime + 24 * 3600 * 1000;
            List<DisburseIncome> list = diModel.getListByDate(startTime, endTime);
            return list;
        } catch (ParseException e) {
            e.printStackTrace();
            ((T) weakView.get()).showMessage("日期格式错误");
        }
        return null;
    }

    public LabelType getLabelTypeById(Integer id) {
        LabelType labelType = iLabelTypeModel.getLabelTypeById(id);
        return labelType;
    }

    public HomePresenter(Context context) {
        keepAccountsDatabaseHelper helper = new keepAccountsDatabaseHelper(context);
        diModel = new DisburseIncomeModel(helper);
        iLabelTypeModel = new LabelTypeModel(helper);
    }

    public void addAccount(DisburseIncome disburseIncome) {
        diModel.addAccount(disburseIncome);
    }
}
