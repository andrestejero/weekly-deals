package com.andrestejero.weeklydeals.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.andrestejero.weeklydeals.AppBaseActivity;
import com.andrestejero.weeklydeals.R;
import com.andrestejero.weeklydeals.models.Category;
import com.andrestejero.weeklydeals.models.Product;
import com.andrestejero.weeklydeals.models.PsnContainer;
import com.andrestejero.weeklydeals.utils.CollectionUtils;
import com.andrestejero.weeklydeals.utils.StringUtils;
import com.andrestejero.weeklydeals.views.adapters.PsnListAdapter;
import com.andrestejero.weeklydeals.views.presenters.PsnPresenter;

import java.util.ArrayList;
import java.util.List;

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
        setupRefreshHandler(mViewHolder);
        loadPsnList(false);
    }

    private void loadPsnList(boolean nextPage) {
        Bundle extras = getIntent().getExtras();
        String id = extras.getString(EXTRA_PSN_LIST_ID);
        if (mPresenter != null && StringUtils.isNotEmpty(id)) {
            mPresenter.getPsnContainer(id, nextPage);
        }
    }

    private void setupRefreshHandler(@NonNull ViewHolder holder) {
        holder.refreshControl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadPsnList(false);
            }
        });
    }

    private void stopRefreshIndicator() {
        if (mViewHolder != null) {
            mViewHolder.refreshControl.setRefreshing(false);
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
        stopRefreshIndicator();
        updateVisibilities(View.GONE, View.VISIBLE, View.GONE, View.GONE);
    }

    @Override
    public void showEmptyList() {
        stopRefreshIndicator();
        updateVisibilities(View.GONE, View.GONE, View.VISIBLE, View.GONE);
    }

    @Override
    public void showPsnContainer(@NonNull PsnContainer psnContainer) {
        mPsnContainer = psnContainer;
        stopRefreshIndicator();
        invalidateOptionsMenu();
        updateVisibilities(View.GONE, View.GONE, View.GONE, View.VISIBLE);
        if (mViewHolder != null) {
            updateHeaderTitleView(mViewHolder, mPsnContainer.getName(), mPsnContainer.getPagingTotal());
            mViewHolder.psnListAdapter.updatePsnList(mPsnContainer, mPsnContainer.getPagingTotal());
            mViewHolder.psnListAdapter.notifyDataSetChanged();
        }
    }

    private void updateHeaderTitleView(@NonNull ViewHolder viewHolder, @Nullable String title, int total) {
        if (StringUtils.isNotEmpty(title)) {
            setTitle(title);
            if (mPsnContainer != null && CollectionUtils.isNotEmpty(mPsnContainer.getProducts())) {
                String text = getString(R.string.results_separator, title, total);
                if (total == 1) {
                    text += getString(R.string.result);
                } else {
                    text += getString(R.string.results);
                }
                viewHolder.psnListCount.setVisibility(View.VISIBLE);
                viewHolder.psnListCount.setText(text);
            } else {
                viewHolder.psnListCount.setVisibility(View.GONE);
            }
        } else {
            viewHolder.psnListCount.setVisibility(View.GONE);
        }
    }

    @Override
    public void refreshPsnContainer(@NonNull PsnContainer psnContainer, int positionStart, int itemCount) {
        mPsnContainer = psnContainer;
        if (mViewHolder != null) {
            mViewHolder.psnListAdapter.updatePsnList(mPsnContainer, mPsnContainer.getPagingTotal());
            mViewHolder.psnListAdapter.notifyItemRangeChanged(positionStart, itemCount);
        }
    }

    private void updateVisibilities(int loadingVisibility, int errorVisibility, int emptyVisibility, int contentVisibility) {
        if (mViewHolder != null) {
            mViewHolder.loadingView.setVisibility(loadingVisibility);
            mViewHolder.errorView.setVisibility(errorVisibility);
            mViewHolder.emptyView.setVisibility(emptyVisibility);
            mViewHolder.psnListContent.setVisibility(contentVisibility);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_psn_list, menu);
        MenuItem filterItem = menu.findItem(R.id.action_filter);
        filterItem.setVisible(shouldShowFilterMenuItem());
        return true;
    }

    private boolean shouldShowFilterMenuItem() {
        return mPsnContainer != null && CollectionUtils.isNotEmpty(mPsnContainer.getProducts());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_filter) {
            showFiltersByAlertDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // FIXME test filtros
    private void showFiltersByAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PsnListActivity.this);
        builder.setTitle("title");

        List<CharSequence> optionsNames = new ArrayList<>();
        optionsNames.add("test1");
        optionsNames.add("test2");
        optionsNames.add("test3");
        CharSequence[] array = new CharSequence[optionsNames.size()];
        
        builder.setSingleChoiceItems(optionsNames.toArray(array), 2, null);
        AlertDialog alert = builder.create();
        alert.show();
    }

    private class ViewHolder {
        private SwipeRefreshLayout refreshControl;
        private View loadingView;
        private View errorView;
        private View emptyView;
        private View psnListContent;
        private TextView psnListCount;
        private RecyclerView psnListView;
        private final PsnListAdapter psnListAdapter;
        private final RecyclerView.LayoutManager mLayoutManager;

        private ViewHolder() {
            refreshControl = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
            loadingView = findViewById(R.id.loadingView);
            errorView = findViewById(R.id.errorView);
            emptyView = findViewById(R.id.emptyView);
            psnListContent = findViewById(R.id.llPsnListContent);
            psnListCount = (TextView) findViewById(R.id.tvPsnListCount);
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
