package com.chenghan.keepaccounts.common;


import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.chenghan.keepaccounts.bean.User;

public class SharedPreferencesSaveData {
    private static SharedPreferences sp;

    public static void saveData(Context context, User user) {
        sp = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt("id", user.getId());
        edit.putString("nickName", user.getNickName());
        edit.putString("username", user.getUsername());
        edit.putString("password", user.getPassword());
        edit.commit();
    }


    public static User getData(Context context) {
        sp = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        Integer id = sp.getInt("id", -1);
        String nickName = sp.getString("nickName", "");
        String username = sp.getString("username", "");
        String password = sp.getString("password", "");

        if (id == -1 || TextUtils.isEmpty(nickName) || TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            return null;
        }

        User user = new User();
        user.setId(id);
        user.setNickName(nickName);
        user.setUsername(username);
        user.setPassword(password);

        return user;
    }

    public static void clear(Context context) {
        sp = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.clear();
        edit.commit();
    }
}
