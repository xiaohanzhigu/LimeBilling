package com.chenghan.keepaccounts.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chenghan.keepaccounts.R;

import java.util.List;
import java.util.Map;

public class MonthAdapter extends BaseAdapter {
    private List< Map<String,Object>> list;
    private Context context;

    public MonthAdapter(Context context, List<Map<String, Object>> list) {
        this.list = list;
        this.context = context;
    }

    public void changeList(List<Map<String, Object>> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i("Main", "getView: " + position);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_month, null);
            viewHolder = new ViewHolder();
            viewHolder.countMonthTv = convertView.findViewById(R.id.tv_count_month);
            viewHolder.countPriceTv = convertView.findViewById(R.id.tv_count_price);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Map<String, Object> map = list.get(position);
        viewHolder.countMonthTv.setText(map.get("month").toString());
        Double price = (Double) map.get("price");
        viewHolder.countPriceTv.setText("￥" + price.toString());
        return convertView;
    }

    class ViewHolder{
        TextView countMonthTv, countPriceTv;
    }
}
