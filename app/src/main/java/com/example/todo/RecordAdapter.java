package com.example.todo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.List;

public class RecordAdapter extends BaseAdapter implements Filterable {
    private Context mContext;
    private List<Record> backList;//备份数据
    private List<Record> recordList;//新的数据
    public RecordAdapter(Context mContext,List<Record> recordList){
        this.mContext=mContext;
        this.recordList=recordList;
        backList=recordList;
    }



    @Override
    public int getCount() {
        return recordList.size();
    }

    @Override
    public Object getItem(int position) {
        return recordList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(mContext,R.layout.record_layout,null);
        TextView tv_title=(TextView)view.findViewById(R.id.tv_title);
        TextView tv_time=(TextView)view.findViewById(R.id.tv_time);
        TextView tv_content=(TextView)view.findViewById(R.id.tv_content);
        TextView tv_tag =(TextView)view.findViewById(R.id.tv_tag);
        String allText = recordList.get(position).getContent();
        tv_content.setText(allText);
        tv_time.setText(recordList.get(position).getTime());
        tv_title.setText(recordList.get(position).getTitle());
        tv_tag.setText(recordList.get(position).getTag());
        view.setTag(recordList.get(position).getId());
        return view;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}
