package com.srgiovine.seshopping.browse;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.srgiovine.seshopping.model.Item;

import java.util.ArrayList;
import java.util.List;

import srgiovine.com.seshopping.R;

public class BrowseItemsAdapter extends RecyclerView.Adapter<BrowseItemsAdapter.ViewHolder> {

    private final EventListener eventListener;

    private final List<Item> items = new ArrayList<>();

    public BrowseItemsAdapter(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    void setItems(List<Item> items) {
        this.items.clear();
        this.items.addAll(items);
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

        private final ImageView icon;
        private final TextView name;
        private final TextView description;
        private final ViewGroup tags;

        private Item item;

        private ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            icon = (ImageView) itemView.findViewById(R.id.image);
            name = (TextView) itemView.findViewById(R.id.name);
            description = (TextView) itemView.findViewById(R.id.description);
            tags = (ViewGroup) itemView.findViewById(R.id.tags);
        }

        private void bind(Item item) {
            this.item = item;
            icon.setImageResource(item.icon());
            name.setText(item.name());
            description.setText(item.description());

            tags.removeAllViews();
            addTag(item.gender().name());
            addTag(item.category().name());
            addTag("$" + item.price());
        }

        @Override
        public void onClick(View view) {
            eventListener.onItemClicked(item);
        }

        private void addTag(String tag) {
            TextView textView = createTagView();
            textView.setText(tag);
            tags.addView(textView);
        }

        private TextView createTagView() {
            LayoutInflater layoutInflater = LayoutInflater.from(itemView.getContext());
            return (TextView) layoutInflater.inflate(R.layout.browse_item_tag, tags, false);
        }
    }

    public interface EventListener {
        void onItemClicked(Item item);
    }
}
