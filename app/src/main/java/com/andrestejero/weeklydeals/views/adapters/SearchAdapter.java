package com.andrestejero.weeklydeals.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andrestejero.weeklydeals.R;
import com.andrestejero.weeklydeals.models.Category;
import com.andrestejero.weeklydeals.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @NonNull
    private Context mContext;

    @Nullable
    private OnItemClickListener mListener;

    @Nullable
    private List<Category> mSearchList;

    public SearchAdapter(@NonNull Context context) {
        this.mContext = context;
        this.mSearchList = new ArrayList<>();
    }

    public void updateSearchList(@Nullable List<Category> searchList) {
        this.mSearchList = searchList;
        notifyDataSetChanged();
    }

    public void emptySearchList() {
        this.mSearchList = new ArrayList<>();
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.search_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        if (CollectionUtils.isNotEmpty(mSearchList)) {
            final Category category = mSearchList.get(position);
            viewHolder.productName.setText(category.getName());
        }
    }

    @Override
    public int getItemCount() {
        return CollectionUtils.safeSize(mSearchList);
    }

    public void setOnItemClickListener(@Nullable OnItemClickListener itemClickListener) {
        this.mListener = itemClickListener;
    }

    public interface OnItemClickListener {
        // FIXME: 14/6/17 void onItemClick(@NonNull SearchItem searchItem);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView productName;

        public ViewHolder(View itemView) {
            super(itemView);
            productName = (TextView) itemView.findViewById(R.id.productName);
        }
    }
}
