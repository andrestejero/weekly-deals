package com.andrestejero.weeklydeals.views.presenters;

import android.support.annotation.NonNull;
import android.util.Log;

import com.andrestejero.weeklydeals.repositories.AppRepository;

import java.lang.ref.WeakReference;

public class SearchPresenter {

    private static final String LOG_TAG = SearchPresenter.class.getSimpleName();

    @NonNull
    private final WeakReference<View> weakView;
    @NonNull
    private final AppRepository mAppRepository;

    public SearchPresenter(@NonNull View view, @NonNull AppRepository mAppRepository) {
        this.weakView = new WeakReference<>(view);
        this.mAppRepository = mAppRepository;
    }

    public void getProductSearch(@NonNull String searchString) {
        Log.d(LOG_TAG, searchString);
    }

    public interface View {

    }
}
