package com.aj.onlinebloodbank.Adapter;

/**
 * Created by jadhave on 08-Jun-17.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aj.onlinebloodbank.List;
import com.aj.onlinebloodbank.Pojo.Donor;
import com.aj.onlinebloodbank.R;

import java.util.ArrayList;

/**
 * Created by jadhave on 01-Jun-17.
 */

public class CustomAdapter extends BaseAdapter {

    Context context;
    ArrayList<Donor> arrayList=new ArrayList<>();

    public CustomAdapter(Context context, ArrayList<Donor> arrayList) {

        this.context = context;
        this.arrayList = arrayList;
    }

    public CustomAdapter(ArrayList<Donor> arrayList, List list) {
        this.arrayList=arrayList;
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

        ViewHolder viewHolder;
        Donor donor;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listrow,
                    parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        donor = (Donor) getItem(position);

        viewHolder.t1.setText(donor.getName());
        viewHolder.t2.setText(donor.getAddress());
        viewHolder.t3.setText(donor.getPhone());
        viewHolder.t4.setText(donor.getBlood());
        viewHolder.t5.setText(donor.getAge());

        return convertView;
    }

    public class ViewHolder{
        TextView t1,t2,t3,t4,t5;

        public ViewHolder(View view) {
            t1=(TextView) view.findViewById(R.id.t1);
            t2=(TextView) view.findViewById(R.id.t2);
            t3=(TextView) view.findViewById(R.id.t3);
            t4=(TextView) view.findViewById(R.id.t4);
            t5=(TextView) view.findViewById(R.id.t5);
        }
    }
}
