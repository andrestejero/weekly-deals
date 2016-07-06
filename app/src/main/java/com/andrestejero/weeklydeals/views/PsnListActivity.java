package com.andrestejero.weeklydeals.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.andrestejero.weeklydeals.AppBaseActivity;
import com.andrestejero.weeklydeals.R;
import com.andrestejero.weeklydeals.models.Category;
import com.andrestejero.weeklydeals.models.Product;
import com.andrestejero.weeklydeals.models.PsnContainer;
import com.andrestejero.weeklydeals.utils.CollectionUtils;
import com.andrestejero.weeklydeals.utils.StringUtils;
import com.andrestejero.weeklydeals.views.adapters.PsnListAdapter;
import com.andrestejero.weeklydeals.views.presenters.PsnPresenter;

public class PsnListActivity extends AppBaseActivity implements
        PsnPresenter.PsnListView,
        PsnListAdapter.OnItemClickListener {

    private static final String LOG_TAG = PsnListActivity.class.getSimpleName();

    @Nullable
    private ViewHolder mViewHolder;

    @Nullable
    private PsnPresenter mPresenter;

    @Nullable
    private PsnContainer mPsnContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.psn_list);

        mViewHolder = new ViewHolder();
        mPresenter = new PsnPresenter(this, getAppRepository());
        loadPsnList("STORE-MSF77008-SAVE");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getAppRepository().stopGetPsnContainer();
    }

    @Override
    public void showLoading() {
        if (mViewHolder != null) {
            updateLoadingView(mViewHolder.loadingView, View.VISIBLE);
        }
    }

    @Override
    public void showPsnContainer(@NonNull PsnContainer psnContainer) {
        mPsnContainer = psnContainer;
        if (mViewHolder != null) {
            updateLoadingView(mViewHolder.loadingView, View.GONE);
            mViewHolder.psnListAdapter.updatePsnList(mPsnContainer);
        }
    }

    @Override
    public void showEmptyList() {
        if (mViewHolder != null) {
            updateLoadingView(mViewHolder.loadingView, View.GONE);
        }
        Log.d(LOG_TAG, "El listado esta vacio");
    }

    @Override
    public void showErrorGameList() {
        if (mViewHolder != null) {
            updateLoadingView(mViewHolder.loadingView, View.GONE);
        }
        Log.d(LOG_TAG, "showErrorGameList");
    }

    @Override
    public void onCategoryClick(int position) {
        if (mPsnContainer != null && CollectionUtils.isNotEmpty(mPsnContainer.getCategories())) {
            Category category = mPsnContainer.getCategories().get(position);
            if (StringUtils.isNotEmpty(category.getId())) {
                loadPsnList(category.getId());
            }
        }
    }

    private void loadPsnList(@NonNull String id) {
        if (mPresenter != null) {
            mPresenter.getPsnContainer(id);
        }
    }

    @Override
    public void onItemClick(int position) {
        if (mPsnContainer != null && CollectionUtils.isNotEmpty(mPsnContainer.getProducts())) {
            Product product = mPsnContainer.getProducts().get(position);
            Intent intent = new Intent(PsnListActivity.this, ProductDetailActivity.class);
            intent.putExtra(ProductDetailActivity.EXTRA_PRODUCT_ID, product.getId());
            startActivity(intent);
        }
    }

    private class ViewHolder {
        private View loadingView;
        private RecyclerView psnListView;
        private final PsnListAdapter psnListAdapter;
        private final RecyclerView.LayoutManager mLayoutManager;

        private ViewHolder() {
            loadingView = findViewById(R.id.loadingView);
            psnListView = (RecyclerView) findViewById(R.id.rvPsnList);
            psnListAdapter = new PsnListAdapter(PsnListActivity.this);
            psnListView.setAdapter(psnListAdapter);
            mLayoutManager = new LinearLayoutManager(PsnListActivity.this);
            psnListView.setLayoutManager(mLayoutManager);
            psnListAdapter.setOnItemClickListener(PsnListActivity.this);
        }
    }

}
