package com.srgiovine.seshopping.navigation;

import android.support.annotation.CallSuper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import srgiovine.com.seshopping.R;

public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.ViewHolder> {

    private static final int ITEM_VIEW_TYPE_FILTER = 0;
    private static final int ITEM_VIEW_TYPE_SETTINGS = 1;
    private static final int ITEM_VIEW_TYPE_SEPARATOR = 2;

    private final EventListener eventListener;

    private final Map<NavigationItem, Boolean> checkedItems = new HashMap<>();

    private final List<NavigationItem> items = new ArrayList<>();

    public NavigationAdapter(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void addItems(NavigationItem[] items) {
        this.items.addAll(Arrays.asList(items));
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM_VIEW_TYPE_FILTER:
                viewHolder = new FilterItemViewHolder(inflater.inflate(R.layout.navigation_item_filter, parent, false));
                break;
            case ITEM_VIEW_TYPE_SETTINGS:
                viewHolder = new SettingsItemViewHolder(inflater.inflate(R.layout.navigation_item_settings, parent, false));
                break;
            case ITEM_VIEW_TYPE_SEPARATOR:
                viewHolder = new SeparatorItemViewHolder(inflater.inflate(R.layout.navigation_item_separator, parent, false));
                break;
            default:
                throw new IllegalArgumentException("Unhandled item view type " + viewType);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NavigationItem item = items.get(position);

        if (item instanceof FilterNavigationItem) {
            ((FilterItemViewHolder) holder).bind((FilterNavigationItem) item);
        } else if (item instanceof SettingsNavigationItem) {
            ((SettingsItemViewHolder) holder).bind((SettingsNavigationItem) item);
        } else if (item instanceof SeparatorNavigationItem) {
            holder.bind(item);
        } else {
            throw new IllegalArgumentException("Unknown item type " + item.name());
        }
    }

    @Override
    public int getItemViewType(int position) {
        NavigationItem item = items.get(position);

        if (item instanceof FilterNavigationItem) {
            return ITEM_VIEW_TYPE_FILTER;
        } else if (item instanceof SettingsNavigationItem) {
            return ITEM_VIEW_TYPE_SETTINGS;
        } else if (item instanceof SeparatorNavigationItem) {
            return ITEM_VIEW_TYPE_SEPARATOR;
        }
        throw new IllegalArgumentException("Unknown item type " + item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    abstract class ViewHolder<T extends NavigationItem> extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView name;

        T item;

        private ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name = (TextView) itemView.findViewById(R.id.name);
        }

        @CallSuper
        void bind(T item) {
            this.item = item;
            name.setText(item.name());
        }

        @Override
        public void onClick(View view) {
        }
    }

    private class FilterItemViewHolder extends ViewHolder<FilterNavigationItem> {

        private final ImageView icon;
        private final CheckBox checkBox;

        private FilterItemViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
        }

        @Override
        void bind(FilterNavigationItem item) {
            super.bind(item);
            icon.setImageResource(item.iconRes());
            checkBox.setChecked(isChecked());
        }

        @Override
        public void onClick(View view) {
            checkedItems.put(item, !isChecked());
            checkBox.setChecked(isChecked());
            eventListener.onFilterItemClicked(item, isChecked());
        }

        private boolean isChecked() {
            return checkedItems.containsKey(item) && checkedItems.get(item);
        }
    }

    private class SettingsItemViewHolder extends ViewHolder<SettingsNavigationItem> {

        private final ImageView icon;

        private SettingsItemViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.icon);
        }

        @Override
        void bind(SettingsNavigationItem item) {
            super.bind(item);
            icon.setImageResource(item.iconRes());
        }

        @Override
        public void onClick(View view) {
            eventListener.onSettingsItemClicked(item);
        }
    }

    private class SeparatorItemViewHolder extends ViewHolder<SeparatorNavigationItem> {
        private SeparatorItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface EventListener {
        void onFilterItemClicked(FilterNavigationItem item, boolean isChecked);

        void onSettingsItemClicked(SettingsNavigationItem item);
    }
}
