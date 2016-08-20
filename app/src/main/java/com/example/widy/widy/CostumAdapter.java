package com.example.widy.widy;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.widy.widy.MyApplication;
import com.example.widy.widy.R;
import com.example.widy.widy.RowItem;

import java.util.List;

/**
 * Created by Widy on 7/15/2016.
 */
public class CostumAdapter  extends BaseAdapter{
    Context context;
    List<RowItem> rowItems;
    ImageLoader imageLoader = MyApplication.getInstance().getImageLoader();

    public CostumAdapter(Context context, List<RowItem> rowItems) {

        this.context = context;
        this.rowItems = rowItems;

    }

    @Override
    public int getCount() {

        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {

        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {

        return rowItems.indexOf(getItem(position));
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);
        }

        TextView temp_tv, minmax_tv, desc_tv, date_tv;

        NetworkImageView niv;

        temp_tv = (TextView) convertView.findViewById(R.id.ftemp);
        minmax_tv = (TextView) convertView.findViewById(R.id.fmin_max);
        desc_tv = (TextView) convertView.findViewById(R.id.fdesc);
        date_tv = (TextView) convertView.findViewById(R.id.fdate);
        niv = (NetworkImageView) convertView.findViewById(R.id.ficon);

        RowItem item = rowItems.get(position);

        temp_tv.setText(item.getTemp() + "\u2103");
        minmax_tv.setText(item.getTemp_min() + "\u2103" + "\\"
                + item.getTemp_min() + "\u2103");
        desc_tv.setText(item.getDescription() + "");
        date_tv.setText(item.getMdate() + "");

        String icon_url = "http://openweathermap.org/img/w/" + item.getIcon()
                + ".png";

        niv.setImageUrl(icon_url, imageLoader);

        return convertView;
    }


}
