package com.andrestejero.weeklydeals.views;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.andrestejero.weeklydeals.AppBaseActivity;
import com.andrestejero.weeklydeals.R;
import com.andrestejero.weeklydeals.models.Category;
import com.andrestejero.weeklydeals.utils.StringUtils;
import com.andrestejero.weeklydeals.views.adapters.SearchAdapter;
import com.andrestejero.weeklydeals.views.presenters.SearchPresenter;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SearchActivity extends AppBaseActivity implements
        SearchView.OnQueryTextListener,
        SearchPresenter.View,
        SearchAdapter.OnItemClickListener {

    private static final String LOG_TAG = SearchActivity.class.getSimpleName();
    private final static long AUTOCOMPLETE_TIMER_DELAY = 1000;

    @Nullable
    private ViewHolder mViewHolder;

    @Nullable
    private Timer mAutocompleteTimer;

    @Nullable
    private SearchPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mViewHolder = new ViewHolder();
        mPresenter = new SearchPresenter(this, getAppRepository());
        setupSearchView();
        setupViews(View.VISIBLE, View.GONE, View.GONE, View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mViewHolder != null) {
            mViewHolder.searchView.requestFocus();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getAppRepository().stopAutocomplete();
    }

    private void setupSearchView() {
        if (mViewHolder != null) {
            mViewHolder.searchView.setOnQueryTextListener(this);
            mViewHolder.searchView.setQueryHint(getString(R.string.search));
            mViewHolder.searchView.setFocusable(true);
            mViewHolder.searchView.setIconifiedByDefault(false);
            mViewHolder.searchView.requestFocusFromTouch();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String searchString) {
        // FIXME: 14/6/17 (Andres) continuar
        Log.d(LOG_TAG, "onQueryTextSubmit: " + StringUtils.replaceSpaces(searchString));
        return false;
    }

    @Override
    public boolean onQueryTextChange(String searchString) {
        cancelPreviousScheduledGetAutocompleteItems();
        if (StringUtils.isEmpty(searchString)) {
            setupViews(View.VISIBLE, View.GONE, View.GONE, View.GONE);
            if (mViewHolder != null) {
                mViewHolder.searchAdapter.emptySearchList();
            }
        } else {
            scheduleGetAutocompleteItems(StringUtils.replaceSpaces(searchString));
        }
        return true;
    }

    private void cancelPreviousScheduledGetAutocompleteItems() {
        if (mAutocompleteTimer != null) {
            mAutocompleteTimer.cancel();
        }
    }

    private void scheduleGetAutocompleteItems(final String searchString) {
        if (mViewHolder != null) {
            setupViews(View.GONE, View.VISIBLE, View.GONE, View.GONE);
        }
        mAutocompleteTimer = new Timer();
        mAutocompleteTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                getAutocompleteItems(searchString);
            }
        }, AUTOCOMPLETE_TIMER_DELAY);
    }

    private void getAutocompleteItems(final String searchString) {
        if (mPresenter != null) {
            mPresenter.getProductSearch(searchString);
        }
    }

    private void setupViews(int hintContainer, int loading, int emptyAutocomplete, int errorMessage) {
        if (mViewHolder != null) {
            mViewHolder.hintContainer.setVisibility(hintContainer);
            mViewHolder.searchAutocompleteLoading.setVisibility(loading);
            mViewHolder.emptyAutocomplete.setVisibility(emptyAutocomplete);
            mViewHolder.errorMessage.setVisibility(errorMessage);
        }
    }

    @Override
    public void updateList(@NonNull List<Category> products) {
        setupViews(View.GONE, View.GONE, View.GONE, View.GONE);
        if (mViewHolder != null) {
            mViewHolder.searchAdapter.updateSearchList(products);
        }
    }

    @Override
    public void showEmptyList() {
        setupViews(View.GONE, View.GONE, View.VISIBLE, View.GONE);
        if (mViewHolder != null) {
            mViewHolder.searchAdapter.emptySearchList();
        }
    }

    @Override
    public void showError() {
        setupViews(View.GONE, View.GONE, View.GONE, View.VISIBLE);
        if (mViewHolder != null) {
            mViewHolder.searchAdapter.emptySearchList();
        }
    }

    @Override
    public void onItemClick(@NonNull String productId) {
        Intent intent = ProductDetailActivity.newIntent(this, productId);
        startActivity(intent);
    }

    private class ViewHolder {
        private final View hintContainer;
        private final TextView emptyAutocomplete;
        private final TextView errorMessage;
        private final SearchView searchView;
        private final RecyclerView searchResult;
        private SearchAdapter searchAdapter;
        private ProgressBar searchAutocompleteLoading;

        private ViewHolder() {
            hintContainer = findViewById(R.id.hintContainer);
            emptyAutocomplete = (TextView) findViewById(R.id.tvEmptyAutocomplete);
            errorMessage = (TextView) findViewById(R.id.tvErrorMessage);
            searchView = (SearchView) findViewById(R.id.searchView);
            searchResult = (RecyclerView) findViewById(R.id.searchResult);
            searchAutocompleteLoading = (ProgressBar) findViewById(R.id.pbLoading);
            searchAdapter = new SearchAdapter(SearchActivity.this);
            searchAdapter.setOnItemClickListener(SearchActivity.this);
            searchResult.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
            searchResult.setAdapter(searchAdapter);
        }
    }
}
