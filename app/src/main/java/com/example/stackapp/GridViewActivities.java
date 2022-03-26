package com.example.stackapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class GridViewActivities extends BaseAdapter {
    private Context mContext;
    private List<String> names;

    public GridViewActivities(Context c, List<String> names) {
        this.mContext = c;
        this.names = names;
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.gridviewlayout, null);
            TextView textView = (TextView) grid.findViewById(R.id.gridview_text);
            textView.setText(names.get(i));
            //System.out.println("Test");
        } else {
            grid = view;
        }

        return grid;
    }
}
