package com.andrestejero.weeklydeals.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.andrestejero.weeklydeals.AppBaseActivity;
import com.andrestejero.weeklydeals.R;
import com.andrestejero.weeklydeals.models.HomeContainer;
import com.andrestejero.weeklydeals.views.adapters.HomeAdapter;
import com.andrestejero.weeklydeals.views.presenters.HomePresenter;

public class MainActivity extends AppBaseActivity implements HomePresenter.HomeView {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Nullable
    private ViewHolder mViewHolder;

    @Nullable
    private HomePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        mViewHolder = new ViewHolder();
        mPresenter = new HomePresenter(this, getAppRepository());

        loadHome();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getAppRepository().stopGetHomeContainer();
    }

    @Nullable
    @Override
    protected String getSubtitle() {
        return getString(R.string.home_subtitle);
    }

    private void loadHome() {
        if (mPresenter != null) {
            mPresenter.getHome();
        }
    }

    /*
    Intent intent = new Intent(MainActivity.this, PsnListActivity.class);
    intent.putExtra(PsnListActivity.EXTRA_PSN_LIST_ID, "STORE-MSF77008-SAVE");
     */

    @Override
    public void showLoading() {
        // FIXME: 17/3/17 
        Log.d(LOG_TAG, "---- LOADING ----");
    }

    @Override
    public void showHome(@NonNull HomeContainer homeContainer) {
        if (mViewHolder != null) {
            // TODO: 17/3/17 (Andres) mover a list
            //mViewHolder.bannersAdapter = new BannersAdapter(this, homeContainer.getBanners());
            //mViewHolder.bannersPager.setAdapter(mViewHolder.bannersAdapter);

            mViewHolder.homeAdapter = new HomeAdapter(this, homeContainer.getBanners(), homeContainer.getCategories());
            mViewHolder.homeList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            mViewHolder.homeList.setAdapter(mViewHolder.homeAdapter);
        }
    }

    @Override
    public void showError() {
        // FIXME: 17/3/17 
        Log.d(LOG_TAG, "---- ERROR ----");
    }

    private class ViewHolder {
        //private final ViewPager bannersPager;
        //private BannersAdapter bannersAdapter;
        private final RecyclerView homeList;
        private HomeAdapter homeAdapter;

        private ViewHolder() {
            homeList = (RecyclerView) findViewById(R.id.homeList);
            //bannersPager = (ViewPager) findViewById(R.id.banners);
        }
    }
}
