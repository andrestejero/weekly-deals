package com.andrestejero.weeklydeals;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.andrestejero.weeklydeals.repositories.AppRepository;

public abstract class AppBaseActivity extends AppCompatActivity {

    private AppRepository mAppRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
}
