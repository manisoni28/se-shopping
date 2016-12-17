package com.srgiovine.seshopping.browse;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.srgiovine.seshopping.model.Category;
import com.srgiovine.seshopping.model.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import srgiovine.com.seshopping.R;

public class BrowseItemsAdapter extends RecyclerView.Adapter<BrowseItemsAdapter.ViewHolder> {

    private static final List<Category> GENDER_CATEGORIES = Arrays.asList(Category.Mens, Category.Womens);

    private final EventListener eventListener;

    private final Set<Category> visibleCategories = new HashSet<>();

    private final Set<Category> visibleGenderCategories = new HashSet<>();

    private final List<Item> items = new ArrayList<>();

    private final List<Item> visibleItems = new ArrayList<>();

    public BrowseItemsAdapter(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void addItems(Item[] items) {
        this.items.addAll(Arrays.asList(items));
        invalidateVisibleItems();
    }

    public void setCategoryVisible(Category category, boolean isVisible) {
        if (isVisible) {
            if (GENDER_CATEGORIES.contains(category)) {
                visibleGenderCategories.add(category);
            } else {
                visibleCategories.add(category);
            }
        } else {
            if (GENDER_CATEGORIES.contains(category)) {
                visibleGenderCategories.remove(category);
            } else {
                visibleCategories.remove(category);
            }
        }
        invalidateVisibleItems();
    }

    private void invalidateVisibleItems() {
        visibleItems.clear();

        if (visibleCategories.isEmpty() && visibleGenderCategories.isEmpty()) {
            visibleItems.addAll(items);
            notifyDataSetChanged();
            return;
        }

        if (visibleCategories.isEmpty()) {
            addItemsWithVisibleGender();
            notifyDataSetChanged();
            return;
        }

        for (Item item : items) {
            if (!Collections.disjoint(item.categories(), visibleGenderCategories)
                    && !Collections.disjoint(item.categories(), visibleCategories)) {
                visibleItems.add(item);
            }
        }

        notifyDataSetChanged();
    }

    private void addItemsWithVisibleGender() {
        for (Item item : items) {
            if (!Collections.disjoint(item.categories(), visibleGenderCategories)) {
                visibleItems.add(item);
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.browse_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(visibleItems.get(position));
    }

    @Override
    public int getItemCount() {
        return visibleItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView icon;
        private final TextView name;
        private final TextView description;
        private final TextView price;
        private final ViewGroup tags;

        private Item item;

        private ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            icon = (ImageView) itemView.findViewById(R.id.image);
            name = (TextView) itemView.findViewById(R.id.name);
            description = (TextView) itemView.findViewById(R.id.description);
            price = (TextView) itemView.findViewById(R.id.price);
            tags = (ViewGroup) itemView.findViewById(R.id.tags);
        }

        private void bind(Item item) {
            this.item = item;
            icon.setImageResource(item.icon());
            name.setText(item.name());
            description.setText(item.description());
            price.setText("$" + item.price());

            tags.removeAllViews();
            for (Category category : item.categories()) {
                addCategoryAsTag(category);
            }
            tags.addView(price);
        }

        @Override
        public void onClick(View view) {
            eventListener.onItemClicked(item);
        }

        private void addCategoryAsTag(Category category) {
            LayoutInflater layoutInflater = LayoutInflater.from(itemView.getContext());
            TextView tag = (TextView) layoutInflater.inflate(R.layout.browse_item_tag, tags, false);
            tag.setText(category.name());
            tags.addView(tag);
        }
    }

    public interface EventListener {
        void onItemClicked(Item item);
    }
}
