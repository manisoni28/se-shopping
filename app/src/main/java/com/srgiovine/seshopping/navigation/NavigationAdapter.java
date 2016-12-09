package com.srgiovine.seshopping.navigation;

import android.support.annotation.CallSuper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import srgiovine.com.seshopping.R;

public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.ViewHolder> {

    private final EventListener eventListener;

    private List<NavigationItem> items;

    public NavigationAdapter(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void setItems(NavigationItem... items) {
        this.items = Arrays.asList(items);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        NavigationItem.Type itemType = NavigationItem.Type.fromOrdinal(viewType);
        switch (itemType) {
            case LOGOUT:
                viewHolder = new ViewHolder(inflater.inflate(R.layout.navigation_item, parent, false));
                break;
            case FILTER:
                viewHolder = new FilterItemViewHolder(inflater.inflate(R.layout.navigation_filter_item, parent, false));
                break;
            default:
                throw new IllegalArgumentException("Unhandled item type " + itemType);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        NavigationItem item = items.get(position);
        return item.type().ordinal();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView icon;
        private final TextView name;

        protected NavigationItem item;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            name = (TextView) itemView.findViewById(R.id.name);
        }

        @CallSuper
        void bind(NavigationItem item) {
            this.item = item;
            icon.setImageResource(item.iconRes());
            name.setText(item.name());
        }

        @Override
        public void onClick(View view) {
            eventListener.onItemClicked(item);
        }
    }

    class FilterItemViewHolder extends ViewHolder {

        private final CheckBox checkBox;

        FilterItemViewHolder(View itemView) {
            super(itemView);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
        }

        @Override
        void bind(NavigationItem item) {
            super.bind(item);
            checkBox.setChecked(item.isChecked());
        }

        @Override
        public void onClick(View view) {
            item.setChecked(!item.isChecked());
            checkBox.setChecked(item.isChecked());
            super.onClick(view);
        }
    }

    public interface EventListener {
        void onItemClicked(NavigationItem item);
    }
}
