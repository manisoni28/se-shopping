package com.srgiovine.seshopping.navigation;

import android.support.annotation.CallSuper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import srgiovine.com.seshopping.R;

public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.ViewHolder<? extends NavigationItem>> {

    private static final int ITEM_VIEW_TYPE_GENDER = 0;
    private static final int ITEM_VIEW_TYPE_CATEGORY = 1;
    private static final int ITEM_VIEW_TYPE_SETTINGS = 2;
    private static final int ITEM_VIEW_TYPE_SEPARATOR = 3;

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
    public ViewHolder<? extends NavigationItem> onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder<? extends NavigationItem> viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM_VIEW_TYPE_GENDER:
                viewHolder = new GenderFilterItemViewHolder(inflater.inflate(R.layout.navigation_item_filter, parent, false));
                break;
            case ITEM_VIEW_TYPE_CATEGORY:
                viewHolder = new CategoryFilterItemViewHolder(inflater.inflate(R.layout.navigation_item_filter, parent, false));
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
    public void onBindViewHolder(ViewHolder<? extends NavigationItem> holder, int position) {
        NavigationItem item = items.get(position);

        int itemViewType = getItemViewType(position);
        switch (itemViewType) {
            case ITEM_VIEW_TYPE_GENDER:
                ((GenderFilterItemViewHolder) holder).bind((GenderNavigationItem) item);
                break;
            case ITEM_VIEW_TYPE_CATEGORY:
                ((CategoryFilterItemViewHolder) holder).bind((CategoryNavigationItem) item);
                break;
            case ITEM_VIEW_TYPE_SETTINGS:
                ((SettingsItemViewHolder) holder).bind((SettingsNavigationItem) item);
                break;
            case ITEM_VIEW_TYPE_SEPARATOR:
                ((SeparatorItemViewHolder) holder).bind((SeparatorNavigationItem) item);
                break;
            default:
                throw new IllegalArgumentException("Unhandled item view type " + itemViewType);
        }
    }

    @Override
    public int getItemViewType(int position) {
        NavigationItem item = items.get(position);

        if (item instanceof GenderNavigationItem) {
            return ITEM_VIEW_TYPE_GENDER;
        } else if (item instanceof CategoryNavigationItem) {
            return ITEM_VIEW_TYPE_CATEGORY;
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

    private abstract class ViewHolderWithIcon<T extends NavigationItemWithIcon> extends ViewHolder<T> {

        private final ImageView icon;

        private ViewHolderWithIcon(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.icon);
        }

        @Override
        void bind(T item) {
            super.bind(item);
            icon.setImageResource(item.iconRes());
        }
    }

    private abstract class ViewHolderWithIconAndCheckbox<T extends NavigationItemWithIcon> extends ViewHolderWithIcon<T>
            implements CompoundButton.OnCheckedChangeListener {

        private final CheckBox checkBox;

        private ViewHolderWithIconAndCheckbox(View itemView) {
            super(itemView);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
            checkBox.setOnCheckedChangeListener(this);
        }

        @Override
        void bind(T item) {
            super.bind(item);
            checkBox.setChecked(isChecked());
        }

        @Override
        public void onClick(View view) {
            checkBox.setChecked(!isChecked());
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            checkedItems.put(item, isChecked);
            onItemCheckedChanged(item, isChecked);
        }

        private boolean isChecked() {
            return checkedItems.containsKey(item) && checkedItems.get(item);
        }

        abstract void onItemCheckedChanged(T item, boolean isChecked);
    }

    private class GenderFilterItemViewHolder extends ViewHolderWithIconAndCheckbox<GenderNavigationItem> {
        private GenderFilterItemViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        void onItemCheckedChanged(GenderNavigationItem item, boolean isChecked) {
            eventListener.onGenderItemClicked(item, isChecked);
        }
    }

    private class CategoryFilterItemViewHolder extends ViewHolderWithIconAndCheckbox<CategoryNavigationItem> {
        private CategoryFilterItemViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        void onItemCheckedChanged(CategoryNavigationItem item, boolean isChecked) {
            eventListener.onCategoryItemClicked(item, isChecked);
        }
    }

    private class SettingsItemViewHolder extends ViewHolderWithIcon<SettingsNavigationItem> {

        private SettingsItemViewHolder(View itemView) {
            super(itemView);
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
        void onGenderItemClicked(GenderNavigationItem item, boolean isChecked);

        void onCategoryItemClicked(CategoryNavigationItem item, boolean isChecked);

        void onSettingsItemClicked(SettingsNavigationItem item);
    }
}
