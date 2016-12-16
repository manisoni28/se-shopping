package com.srgiovine.seshopping.browse;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.srgiovine.seshopping.model.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import srgiovine.com.seshopping.R;

public class BrowseItemsAdapter extends RecyclerView.Adapter<BrowseItemsAdapter.ViewHolder> {

    private final EventListener eventListener;

    private final List<Item> items = new ArrayList<>();

    public BrowseItemsAdapter(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void addItems(Item[] items) {
        this.items.addAll(Arrays.asList(items));
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.browse_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Item item;

        private ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        private void bind(Item item) {
            this.item = item;
        }

        @Override
        public void onClick(View view) {
            eventListener.onItemClicked(item);
        }
    }

    public interface EventListener {
        void onItemClicked(Item item);
    }
}
