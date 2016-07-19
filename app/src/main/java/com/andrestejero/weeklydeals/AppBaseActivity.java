package com.andrestejero.weeklydeals;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.andrestejero.weeklydeals.repositories.AppRepository;
import com.andrestejero.weeklydeals.utils.StringUtils;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public abstract class AppBaseActivity extends AppCompatActivity {

    private AppRepository mAppRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        //Picasso.with(this).setIndicatorsEnabled(BuildConfig.DEBUG);
        //Picasso.with(this).setLoggingEnabled(BuildConfig.DEBUG);
        //mAppRepository = new AppRepository(new UserRepository(this), new FilterRepository(this), BuildConfig.DEBUG);

        mAppRepository = new AppRepository();
    }

    public AppRepository getAppRepository() {
        return mAppRepository;
    }

    public String getAppName() {
        return getString(R.string.app_name);
    }

    @Override
    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        super.setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            configureChildActionBar(actionBar);
        }
    }

    private void configureChildActionBar(@NonNull ActionBar actionBar) {
        actionBar.setDisplayHomeAsUpEnabled(true);
        String subtitle = getSubtitle();
        if (StringUtils.isNotEmpty(subtitle)) {
            actionBar.setSubtitle(subtitle);
        }
    }

    /**
     * Sobreescribir si se quiere setear un subtítulo
     *
     * @return subtítulo
     */
    @Nullable
    protected String getSubtitle() {
        return null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
