package com.andrestejero.weeklydeals.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andrestejero.weeklydeals.R;
import com.andrestejero.weeklydeals.models.Game;
import com.andrestejero.weeklydeals.utils.CollectionUtils;

import java.util.Collections;
import java.util.List;

public class GameListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String LOG_TAG = GameListAdapter.class.getSimpleName();

    @NonNull
    private Context mContext;

    @NonNull
    private List<Game> mGames;

    public GameListAdapter(@NonNull Context context) {
        this.mContext = context;
        mGames = Collections.emptyList();
    }

    public void updateGames(@Nullable List<Game> games) {
        this.mGames = CollectionUtils.safeList(games);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.game_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        final Game game = mGames.get(position);
        viewHolder.gameTitle.setText(game.getDescription());
    }

    @Override
    public int getItemCount() {
        return CollectionUtils.safeSize(mGames);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView gameTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            View actionableCover = itemView.findViewById(R.id.actionableCover);
            gameTitle = (TextView) itemView.findViewById(R.id.tvGameTitle);
            actionableCover.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(LOG_TAG, "OnItemClick");
        }
    }

}
