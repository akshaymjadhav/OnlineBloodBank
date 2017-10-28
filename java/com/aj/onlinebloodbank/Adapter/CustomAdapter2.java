package com.aj.onlinebloodbank.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aj.onlinebloodbank.Pojo.Receiver;
import com.aj.onlinebloodbank.R;

import java.util.ArrayList;

/**
 * Created by jadhave on 08-Jun-17.
 */

public class CustomAdapter2 extends BaseAdapter {

    Context context;
    ArrayList<Receiver> arrayList=new ArrayList<>();

    public CustomAdapter2(Context context, ArrayList<Receiver> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomAdapter2.ViewHolder viewHolder;
        Receiver receiver;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listrow2,
                    parent, false);
            viewHolder = new CustomAdapter2.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CustomAdapter2.ViewHolder) convertView.getTag();
        }
        receiver = (Receiver) getItem(position);

        viewHolder.t1.setText(receiver.getName());
        viewHolder.t2.setText(receiver.getPhone());
        viewHolder.t3.setText(receiver.getBlood());


        return convertView;
    }
    public class ViewHolder{
        TextView t1,t2,t3;


        public ViewHolder(View view) {
            t1=(TextView) view.findViewById(R.id.t1);
            t2=(TextView) view.findViewById(R.id.t2);
            t3=(TextView) view.findViewById(R.id.t3);
        }
    }
}
