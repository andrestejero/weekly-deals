package com.andrestejero.weeklydeals.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.andrestejero.weeklydeals.R;
import com.andrestejero.weeklydeals.models.Filter;
import com.andrestejero.weeklydeals.utils.CollectionUtils;
import com.andrestejero.weeklydeals.utils.StringUtils;

import java.util.List;

public class PsnListFilterAdapter extends BaseAdapter {

    @NonNull
    private final Context mContext;

    @NonNull
    private List<Filter> mFilters;

    public PsnListFilterAdapter(@NonNull Context context, @NonNull List<Filter> filters) {
        this.mContext = context;
        this.mFilters = filters;
    }

    @Override
    public int getCount() {
        return CollectionUtils.safeSize(mFilters);
    }

    @Override
    public Object getItem(int position) {
        return mFilters.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.psn_filter, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.filterName = (TextView) convertView.findViewById(R.id.tvFilterName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Filter filter = mFilters.get(position);
        if (StringUtils.isNotEmpty(filter.getName())) {
            viewHolder.filterName.setText(filter.getName());
        }
        return convertView;
    }

    public class ViewHolder {
        private TextView filterName;
    }
}
