package com.chenghan.keepaccounts.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.chenghan.keepaccounts.R;

import java.util.List;

public class YearSelectAdapter extends BaseAdapter {
    private List<Integer> list;
    private Context context;
    public Integer selectPos = 0;

    public YearSelectAdapter(Context context, List<Integer> list) {
        this.context = context;
        this.list = list;
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
        View view = View.inflate(context, R.layout.item_year_button, null);
        TextView yearTv = view.findViewById(R.id.tv_year);
        yearTv.setText((list.get(position)).toString());

        Drawable green = context.getResources().getDrawable(R.drawable.shape_15dp_green_corners);
        Drawable gray = context.getResources().getDrawable(R.drawable.shape_15dp_ligh_gray_corners);

        if (position == selectPos) {
            yearTv.setBackground(green);
        } else {
            yearTv.setBackground(gray);
        }
        return view;
    }
}
