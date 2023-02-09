package com.chenghan.keepaccounts.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenghan.keepaccounts.R;
import com.chenghan.keepaccounts.bean.DisburseIncome;
import com.chenghan.keepaccounts.common.StringUtils;

import java.util.List;

public class DetailAdapter extends BaseAdapter {
    private Context context;
    private List<DisburseIncome> list;

    public DetailAdapter(Context context, List<DisburseIncome> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_detail, null);
            holder = new ViewHolder();
            holder.imageIv = convertView.findViewById(R.id.iv_image);
            holder.detailTitleTv = convertView.findViewById(R.id.tv_detail_title);
            holder.detailRemark = convertView.findViewById(R.id.tv_detail_remark);
            holder.detailPrice = convertView.findViewById(R.id.tv_detail_price);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        DisburseIncome disburseIncome = list.get(position);
        int imageId = StringUtils.getResourceId(context, disburseIncome.getImage());
        holder.imageIv.setBackgroundResource(imageId);
        holder.detailTitleTv.setText(disburseIncome.getTitle());
        holder.detailRemark.setText(disburseIncome.getRemark());
        holder.detailPrice.setText(disburseIncome.getPrice().toString());
        return convertView;
    }


    public void changeList(List<DisburseIncome> list) {
        this.list = list;
        super.notifyDataSetChanged();
    }

    class ViewHolder{
        ImageView imageIv;
        TextView detailTitleTv, detailRemark, detailPrice;
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
}
