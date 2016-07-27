package com.andrestejero.weeklydeals.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.andrestejero.weeklydeals.R;
import com.andrestejero.weeklydeals.models.Sort;
import com.andrestejero.weeklydeals.utils.CollectionUtils;
import com.andrestejero.weeklydeals.utils.StringUtils;

import java.util.List;

public class PsnListSortAdapter extends BaseAdapter {

    @NonNull
    private final Context mContext;

    @NonNull
    private List<Sort> mSorts;

    public PsnListSortAdapter(@NonNull Context context, @NonNull List<Sort> sorts) {
        this.mContext = context;
        this.mSorts = sorts;
    }

    @Override
    public int getCount() {
        return CollectionUtils.safeSize(mSorts);
    }

    @Override
    public Object getItem(int position) {
        return mSorts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.psn_sort, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.sortName = (TextView) convertView.findViewById(R.id.tvSortName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Sort sort = mSorts.get(position);
        if (sort.isSelected()) {
            viewHolder.sortName.setBackgroundColor(ContextCompat.getColor(mContext, R.color.green20));
        } else {
            viewHolder.sortName.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white00));
        }
        if (StringUtils.isNotEmpty(sort.getName())) {
            viewHolder.sortName.setText(sort.getName());
        }
        return convertView;
    }

    public class ViewHolder {
        private TextView sortName;
    }
}
