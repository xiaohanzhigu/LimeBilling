package com.chenghan.keepaccounts.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * t_user：id, nickName, username, password
 *
 * t_disburse_income: id, title, remark, price, date, user, isDisburse
 *
 * t_quick_label:id, content, image ,user
 */
public class keepAccountsDatabaseHelper extends SQLiteOpenHelper {

    public keepAccountsDatabaseHelper(Context context) {
        super(context, "keepAccounts.db", null, 17);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table t_user(" +
                "id integer primary key autoincrement," +
                "nickName varchar(20)," +
                "username varchar(20)," +
                "password varchar(20)," +
                "phone varchar(20)," +
                "wx varchar(20))");

        db.execSQL("create table t_disburse_income(" +
                "id integer primary key autoincrement," +
                "isDisburse integer," +
                "title varchar(255)," +
                "remark varchar(255)," +
                "price double," +
                "image varchar(50)," +
                "writeTime integer," +
                "user integer)");

        db.execSQL("create table t_label_type(" +
                "id integer primary key autoincrement," +
                "typeName varchar(20)," +
                "kind integer," +
                "image varchar(50))");

        db.execSQL( "Insert into t_label_type VALUES (1, '餐饮', 1, 'bj_label_cy')");
        db.execSQL( "Insert into t_label_type VALUES (2, '购物', 1, 'bj_label_gw')");
        db.execSQL( "Insert into t_label_type VALUES (3, '交通', 1, 'bj_label_jt')");
        db.execSQL( "Insert into t_label_type VALUES (4, '零食', 1, 'bj_label_ls')");
        db.execSQL( "Insert into t_label_type VALUES (5, '水果', 1, 'bj_label_sg')");
        db.execSQL( "Insert into t_label_type VALUES (6, '通讯', 1, 'bj_label_tx')");
        db.execSQL( "Insert into t_label_type VALUES (7, '娱乐', 1, 'bj_label_yl')");
        db.execSQL( "Insert into t_label_type VALUES (8, '饮品', 1, 'bj_label_yp')");
        db.execSQL( "Insert into t_label_type VALUES (9, '工资', 0, 'bj_label_gz')");
        db.execSQL( "Insert into t_label_type VALUES (10, '红包', 0, 'bj_label_hb')");
        db.execSQL( "Insert into t_label_type VALUES (11, '借款', 0, 'bj_label_jk')");
        db.execSQL( "Insert into t_label_type VALUES (12, '投资', 0, 'bj_label_tz')");
        db.execSQL( "Insert into t_label_type VALUES (13, '理财', 0, 'bj_label_lc')");
        db.execSQL( "Insert into t_label_type VALUES (14, '礼金', 0, 'bj_label_lj')");
        db.execSQL( "Insert into t_label_type VALUES (15, '收款', 0, 'bj_label_sk')");
        db.execSQL( "Insert into t_label_type VALUES (18, '服饰', 1, 'bj_label_fs')");
        db.execSQL( "Insert into t_label_type VALUES (996, '其他', 1, 'bj_label_qt')");
        db.execSQL( "Insert into t_label_type VALUES (998, '其他', 0, 'bj_label_qt')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }
}
