package com.andrestejero.weeklydeals.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.andrestejero.weeklydeals.AppBaseActivity;
import com.andrestejero.weeklydeals.R;

public class MainActivity extends AppBaseActivity {

    @Nullable
    private ViewHolder mViewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewHolder = new ViewHolder();
        showGameList();
    }

    private void showGameList() {
        if (mViewHolder != null) {
            mViewHolder.gameList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, PsnListActivity.class);
                    intent.putExtra(PsnListActivity.EXTRA_PSN_LIST_ID, "STORE-MSF77008-SAVE");
                    startActivity(intent);
                }
            });
        }
    }

    private class ViewHolder {
        private final Button gameList;

        private ViewHolder() {
            gameList = (Button) findViewById(R.id.btGameList);
        }
    }
}
