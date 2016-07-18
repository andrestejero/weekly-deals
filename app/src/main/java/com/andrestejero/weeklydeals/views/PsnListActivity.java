package com.andrestejero.weeklydeals.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
        PsnListAdapter.OnItemClickListener,
        PsnListAdapter.OnPageLoadingListener {

    private static final String LOG_TAG = PsnListActivity.class.getSimpleName();

    public static final String EXTRA_PSN_LIST_ID = "EXTRA_PSN_LIST_ID";

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
        setTitle(R.string.title_activity_game_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        mViewHolder = new ViewHolder();
        mPresenter = new PsnPresenter(this, getAppRepository());

        loadPsnList(false);
    }

    private void loadPsnList(boolean nextPage) {
        Bundle extras = getIntent().getExtras();
        String id = extras.getString(EXTRA_PSN_LIST_ID);

        if (mPresenter != null && StringUtils.isNotEmpty(id)) {
            mPresenter.getPsnContainer(id, nextPage);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getAppRepository().stopGetPsnContainer();
    }

    @Override
    public void showLoading() {
        updateVisibilities(View.VISIBLE, View.GONE, View.GONE, View.GONE);
    }

    @Override
    public void showErrorGameList() {
        updateVisibilities(View.GONE, View.VISIBLE, View.GONE, View.GONE);
    }

    @Override
    public void refreshPsnContainer(@NonNull PsnContainer psnContainer, int positionStart, int itemCount) {
        mPsnContainer = psnContainer;
        if (mViewHolder != null) {
            mViewHolder.psnListAdapter.updatePsnList(mPsnContainer, mPsnContainer.getPagingTotal());
            mViewHolder.psnListAdapter.notifyItemRangeChanged(positionStart, itemCount);
        }
    }

    @Override
    public void showEmptyList() {
        updateVisibilities(View.GONE, View.GONE, View.VISIBLE, View.GONE);
    }

    @Override
    public void showPsnContainer(@NonNull PsnContainer psnContainer) {
        updateVisibilities(View.GONE, View.GONE, View.GONE, View.VISIBLE);
        mPsnContainer = psnContainer;
        if (mViewHolder != null) {
            mViewHolder.psnListAdapter.updatePsnList(mPsnContainer, mPsnContainer.getPagingTotal());
        }
    }

    private void updateVisibilities(int loadingVisibility, int errorVisibility, int emptyVisibility, int contentVisibility) {
        if (mViewHolder != null) {
            mViewHolder.loadingView.setVisibility(loadingVisibility);
            mViewHolder.errorView.setVisibility(errorVisibility);
            mViewHolder.emptyView.setVisibility(emptyVisibility);
            mViewHolder.psnListView.setVisibility(contentVisibility);
        }
    }

    @Override
    public void onCategoryClick(int position) {
        if (mPsnContainer != null && CollectionUtils.isNotEmpty(mPsnContainer.getCategories())) {
            Category category = mPsnContainer.getCategories().get(position);
            if (StringUtils.isNotEmpty(category.getId())) {
                Intent intent = new Intent(PsnListActivity.this, PsnListActivity.class);
                intent.putExtra(EXTRA_PSN_LIST_ID, category.getId());
                startActivity(intent);
            }
        }
    }

    @Override
    public void onItemClick(int position) {
        if (mPsnContainer != null && CollectionUtils.isNotEmpty(mPsnContainer.getProducts())) {
            Product product = mPsnContainer.getProducts().get(position);
            if (StringUtils.isNotEmpty(product.getId())) {
                Intent intent = new Intent(PsnListActivity.this, ProductDetailActivity.class);
                intent.putExtra(ProductDetailActivity.EXTRA_PRODUCT_ID, product.getId());
                startActivity(intent);
            }
        }
    }

    @Override
    public void onPageLoading() {
        loadPsnList(true);
    }

    private class ViewHolder {
        private View loadingView;
        private View errorView;
        private View emptyView;
        private RecyclerView psnListView;
        private final PsnListAdapter psnListAdapter;
        private final RecyclerView.LayoutManager mLayoutManager;

        private ViewHolder() {
            loadingView = findViewById(R.id.loadingView);
            errorView = findViewById(R.id.errorView);
            emptyView = findViewById(R.id.emptyView);
            psnListView = (RecyclerView) findViewById(R.id.rvPsnList);
            psnListAdapter = new PsnListAdapter(PsnListActivity.this);
            psnListView.setAdapter(psnListAdapter);
            mLayoutManager = new LinearLayoutManager(PsnListActivity.this);
            psnListView.setLayoutManager(mLayoutManager);
            psnListAdapter.setOnItemClickListener(PsnListActivity.this);
            psnListAdapter.setOnPageLoadingListener(PsnListActivity.this);
        }
    }

}
