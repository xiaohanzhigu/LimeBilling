package com.chenghan.keepaccounts.fragment;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.chenghan.keepaccounts.R;
import com.chenghan.keepaccounts.adapter.LabelTypeAdapter;
import com.chenghan.keepaccounts.base.BaseFragment;
import com.chenghan.keepaccounts.bean.DisburseIncome;
import com.chenghan.keepaccounts.bean.LabelType;
import com.chenghan.keepaccounts.presenter.AddPresenter;
import com.chenghan.keepaccounts.view.IBaseAddFragView;

import java.util.List;

public abstract class BaseAddFragment extends BaseFragment<AddPresenter, IBaseAddFragView> implements IBaseAddFragView {
    private View view;
    private GridView gridView;
    private LabelTypeAdapter adapter;
    public List<LabelType> labelTypeList;
    public LabelType selectLabelType;
    private OnChangeListener onChangeListener;
    private Integer selectPos;
    private LabelType label;
    private boolean isData = false;


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_expend, container, false);
        initView();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected AddPresenter createPresenter() {
        return new AddPresenter(getContext());
    }

    private void initView() {
        gridView = view.findViewById(R.id.gv_expend_label);
        adapter = new LabelTypeAdapter(getContext(), null);
        //编辑页面功能
        if (isData) {
            Integer id = label.getId();
            adapter.setLabelId(id);
        }
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectPos = position;
                adapter.setLabelId(null);
                if (onChangeListener != null) {
                    onChangeListener.onChangeListener(labelTypeList.get(selectPos));
                }
                adapter.selectPos = position;
                adapter.notifyDataSetInvalidated();//提示内容发生改变
            }
        });
        initData();
        gridView.setAdapter(adapter);
    }

    public abstract void initData();

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public void refresh(List<LabelType> list) {
        labelTypeList = list;
        adapter.changeList(list);
    }

    public interface OnChangeListener {
        void onChangeListener(LabelType selectLabelType);
        LabelType sentMsgToFrag();
    }

    public void setOnChangeListener(OnChangeListener onChangeListener) {
        this.onChangeListener = onChangeListener;
        label = onChangeListener.sentMsgToFrag();
        if (label != null) {
            isData = true;
        }
    }
}