package com.chenghan.keepaccounts.base;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BaseModel<T> {
    protected SQLiteOpenHelper helper;
    protected SQLiteDatabase db;
    protected Cursor cursor;

    private Class entityClass;

    public BaseModel(SQLiteOpenHelper helper) {
        this.helper = helper;
        //getClass() 获取Class对象，当前执行的是new FruitDAOImpl() , 创建的是FruitDAOImpl的实例
        Type genericType = getClass().getGenericSuperclass();
        Type[] actualTypeArguments = ((ParameterizedType) genericType).getActualTypeArguments();
        //获取到的<T>中的T的真实的类型
        Type actualType = actualTypeArguments[0];
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                entityClass = Class.forName(actualType.getTypeName());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("BaseDAO 构造方法出错了，可能的原因是没有指定<>中的类型");
        }
    }

    protected void update(String sql, Object... params) {
        db = helper.getWritableDatabase();
        db.execSQL(sql, params);
    }

    protected T querySingle(String sql, Object... params) {
        db = helper.getReadableDatabase();
        String[] strParams = new String[params.length];

        for (int i = 0; i < params.length; i++) {
            strParams[i] = params[i].toString();
        }
        Cursor cursor = db.rawQuery(sql, strParams);

        int columnCount = cursor.getColumnCount();
        String[] columnNames = cursor.getColumnNames();

        if (cursor.moveToFirst()) {
            try {
                T entity = (T) entityClass.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    String columnName = columnNames[i];
                    int type = cursor.getType(i);
                    Object columnValue = getObject(cursor, i);
                    setValue(entity, columnName, columnValue);
                }
                return entity;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    protected List<T> queryMulti(String sql, Object...params) {
        ArrayList<T> list = new ArrayList<>();
        db = helper.getReadableDatabase();
        String[] strParams = new String[params.length];
        for (int i = 0; i < params.length; i++) {
            if (params[i] == null) {
                continue;
            }
            strParams[i] = params[i].toString();
        }
        Cursor cursor = db.rawQuery(sql, strParams);
        int count = cursor.getCount();

        String[] columnNames = cursor.getColumnNames();
        int columnCount = cursor.getColumnCount();

        while (cursor.moveToNext()) {
            try {
                T entity = (T)entityClass.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    String columnName = columnNames[i];
                    Object columValue = getObject(cursor, i);
                    setValue(entity, columnName, columValue);
                }
                list.add(entity);
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        return list;
    }

    private void setValue(Object o, String property, Object propertyValue) throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        Class<?> clazz = o.getClass();
        //根据property获取对应的属性字段
        Field field = clazz.getDeclaredField(property);

        if (field != null) {
            String typeName = field.getType().getName();

            //判断是不是自定义类型，则需要调用这个自定义类的带一个参数的构造方法，创建出这个自定义的实例对象，然后将实例对象赋值给这个属性
            if (isMyType(typeName)) {
                Class<?> typeNameClass = Class.forName(typeName);
                Constructor<?> constructor = typeNameClass.getDeclaredConstructor(Integer.class);
                propertyValue = constructor.newInstance(propertyValue);
            }

            field.setAccessible(true);
            field.set(o, propertyValue);
        }

    }

    private static boolean isNotMyType(String typeName) {
        return "java.lang.Integer".equals(typeName)
                || "java.lang.String".equals(typeName)
                || "java.util.Date".equals(typeName)
                || "java.sql.Date".equals(typeName)
                || "java.lang.Double".equals(typeName)
                || "java.lang.Long".equals(typeName);
    }

    private static boolean isMyType(String typeName) {
        return !isNotMyType(typeName);
    }

    private Object getObject(Cursor cursor, int i) {
        Object columnValue = null;

        String columnName = cursor.getColumnName(i);
        if ("writeTime".equals(columnName)) {
            return cursor.getLong(i);
        }

        int type = cursor.getType(i);
        switch (type) {
            case Cursor.FIELD_TYPE_STRING:
                columnValue = cursor.getString(i);
                break;
            case Cursor.FIELD_TYPE_INTEGER:
                columnValue = cursor.getInt(i);
                break;
            case Cursor.FIELD_TYPE_FLOAT:
                columnValue = cursor.getDouble(i);
                break;
            case Cursor.FIELD_TYPE_BLOB:
                columnValue = cursor.getBlob(i);
                break;
            case Cursor.FIELD_TYPE_NULL:
                columnValue = null;
                break;
            default:
                columnValue = cursor.getFloat(i);
        }
        return columnValue;
    }

}
