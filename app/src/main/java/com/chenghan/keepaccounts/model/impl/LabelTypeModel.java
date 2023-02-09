package com.chenghan.keepaccounts.model.impl;

import android.database.sqlite.SQLiteOpenHelper;

import com.chenghan.keepaccounts.base.BaseModel;
import com.chenghan.keepaccounts.bean.LabelType;
import com.chenghan.keepaccounts.model.ILabelTypeModel;

import java.util.List;

public class LabelTypeModel  extends BaseModel<LabelType> implements ILabelTypeModel {
    public LabelTypeModel(SQLiteOpenHelper helper) {
        super(helper);
    }

    @Override
    public List<LabelType> getLabelTypeByKind(int king) {
        String sql = "select * from t_label_type where kind = ? order by id";
        List<LabelType> list = queryMulti(sql, king);
        return list;
    }

    @Override
    public LabelType getLabelTypeById(Integer id) {
        String sql = "select * from t_label_type where id = ?";
        LabelType labelType = querySingle(sql, id);
        return labelType;
    }

    @Override
    public LabelType getLabelByName(String name) {
        return querySingle("select * from t_label_type where typeName = ?", name);
    }
}
