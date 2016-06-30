package com.andrestejero.weeklydeals.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.andrestejero.weeklydeals.AppBaseActivity;
import com.andrestejero.weeklydeals.R;

public class GameDetailActivity extends AppBaseActivity {

    @Nullable
    private ViewHolder mViewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_detail);

        Bundle extras = getIntent().getExtras();
        String description = extras.getString("GAME_ID");

        mViewHolder = new ViewHolder();
        mViewHolder.title.setText(description);
    }

    private class ViewHolder {
        private final TextView title;

        private ViewHolder() {
            title = (TextView) findViewById(R.id.tvGameDetail);
        }
    }
}
