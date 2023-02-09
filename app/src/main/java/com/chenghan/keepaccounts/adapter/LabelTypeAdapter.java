package com.chenghan.keepaccounts.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenghan.keepaccounts.R;
import com.chenghan.keepaccounts.bean.LabelType;
import com.chenghan.keepaccounts.common.StringUtils;

import java.util.List;

public class LabelTypeAdapter extends BaseAdapter {
    private Context context;
    private List<LabelType> list;
    public int selectPos;
    public Integer labelId = null;//从编辑页面来的标签id

    public LabelTypeAdapter(Context context, List<LabelType> list) {
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

    public void changeList(List<LabelType> list) {
        this.list = list;
        super.notifyDataSetChanged();
    }

    //此适配器不考虑复用问题,因为所有的item都显示在一个页面
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(context, R.layout.item_add_label, null);
        LinearLayout layout = convertView.findViewById(R.id.ll_inner_background);
        ImageView imageView = convertView.findViewById(R.id.iv_add_label_image);
        TextView textView = convertView.findViewById(R.id.tv_add_label_title);
        LabelType labelType = list.get(position);
        int resourceId = StringUtils.getResourceId(context, labelType.getImage());
        imageView.setImageResource(resourceId);

        Drawable green = context.getResources().getDrawable(R.drawable.shape_40dp_green_corners);
        Drawable gray = context.getResources().getDrawable(R.drawable.shape_40dp_grey_corners);
        textView.setText(labelType.getTypeName());

        //如果labelType有数据说明是从编辑页面来的，则默认选中传来的数据
        if (labelId != null) {
            if (labelType.getId() == labelId) {
                layout.setBackground(green);
            } else {
                layout.setBackground(gray);
            }
        } else {
            //判断当前位置是否为选中位置,如果是选中位置,就设置背景为绿色,否则为灰色
            if (selectPos == position) {
                layout.setBackground(green);
            } else {
                layout.setBackground(gray);
            }
        }

        return convertView;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }
}
