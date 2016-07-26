package com.andrestejero.weeklydeals.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.andrestejero.weeklydeals.R;
import com.andrestejero.weeklydeals.models.Value;
import com.andrestejero.weeklydeals.utils.CollectionUtils;
import com.andrestejero.weeklydeals.utils.StringUtils;

import java.util.List;

public class PsnListFilterAdapter extends BaseAdapter {

    @NonNull
    private final Context mContext;

    @NonNull
    private List<Value> mValues;

    public PsnListFilterAdapter(@NonNull Context context, @NonNull List<Value> values) {
        this.mContext = context;
        this.mValues = values;
    }

    @Override
    public int getCount() {
        return CollectionUtils.safeSize(mValues);
    }

    @Override
    public Object getItem(int position) {
        return mValues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.psn_filter_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.filterName = (TextView) convertView.findViewById(R.id.tvFilterName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Value value = mValues.get(position);
        if (StringUtils.isNotEmpty(value.getName())) {
            viewHolder.filterName.setText(value.getName());
        }
        return convertView;
    }

    public class ViewHolder {
        private TextView filterName;
    }
}
