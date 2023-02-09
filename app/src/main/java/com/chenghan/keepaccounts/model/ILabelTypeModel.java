package com.chenghan.keepaccounts.model;

import android.database.sqlite.SQLiteOpenHelper;

import com.chenghan.keepaccounts.adapter.LabelTypeAdapter;
import com.chenghan.keepaccounts.base.BaseModel;
import com.chenghan.keepaccounts.bean.LabelType;

import java.util.List;

public interface ILabelTypeModel {

    List<LabelType> getLabelTypeByKind(int king);

    LabelType getLabelTypeById(Integer id);

    LabelType getLabelByName(String name);
}
