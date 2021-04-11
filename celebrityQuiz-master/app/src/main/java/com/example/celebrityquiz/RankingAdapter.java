package com.example.celebrityquiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RankingAdapter extends RecyclerView.Adapter {

    private List<Ranking> rankingList;
    private Context context;

    RankingAdapter(List<Ranking> rankingList, Context context) {
        this.rankingList = rankingList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutInflater = LayoutInflater.from(context).
                inflate(R.layout.ranking, parent, false);

        Collections.sort(rankingList, new cmp());

        return new RecyclerView.ViewHolder(layoutInflater) {};
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TextView index = holder.itemView.findViewById(R.id.index);
        TextView user = holder.itemView.findViewById(R.id.user_id);
        TextView collect = holder.itemView.findViewById(R.id.collect);
        TextView time = holder.itemView.findViewById(R.id.time);

        holder.itemView.findViewById(R.id.horizontalDivider);

        if(!rankingList.isEmpty()){
            Ranking rank = rankingList.get(position);

            index.setText(String.format("%s", position + 1));
            user.setText(String.format("%s",  rank.email));
            collect.setText(String.format("%s", rank.collect));
            time.setText(String.format("%s %s", rank.times, "초"));
        }
    }

    @Override
    public int getItemCount() {
        if (rankingList == null) return 0;
        return rankingList.size();
    }

    class cmp implements Comparator<Ranking> {
        @Override
        public int compare(Ranking first, Ranking second) {
            if(first.collect == second.collect){
                return first.times>second.times?1:-1;
            }
            else{
                return first.collect>second.collect?-1:1;
            }
        }
    }
}
