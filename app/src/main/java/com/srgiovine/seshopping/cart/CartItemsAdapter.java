package com.srgiovine.seshopping.cart;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.srgiovine.seshopping.util.CounterViewPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import srgiovine.com.seshopping.R;

class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.ViewHolder> {

    private final EventListener eventListener;

    private final List<CartItem> items = new ArrayList<>();

    CartItemsAdapter(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    void initialize() {
        notifyTotalPriceChanged();
    }

    void setItems(List<CartItem> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
        notifyTotalPriceChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.cart_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private void onRemoveItemClicked(CartItem item) {
        int position = items.indexOf(item);
        items.remove(position);
        notifyItemRemoved(position);
        notifyTotalPriceChanged();

        eventListener.onItemRemoved(item);
    }

    private void notifyTotalPriceChanged() {
        int total = 0;
        for (CartItem item : items) {
            total += item.count() * item.item().price();
        }
        eventListener.onTotalPriceChanged(total);
    }

    class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, CounterViewPresenter.EventListener {

        private final ImageView icon;
        private final TextView name;
        private final TextView price;
        private final TextView totalPrice;

        private final CounterViewPresenter counterViewPresenter;

        private CartItem item;

        private ViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            name = (TextView) itemView.findViewById(R.id.name);
            price = (TextView) itemView.findViewById(R.id.price);
            totalPrice = (TextView) itemView.findViewById(R.id.total_price);

            itemView.setOnClickListener(this);
            itemView.findViewById(R.id.remove).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onRemoveItemClicked(item);
                }
            });

            counterViewPresenter = new CounterViewPresenter(itemView, this);
        }

        @Override
        public void onClick(View view) {
            eventListener.onItemClicked(item);
        }

        @Override
        public void onCountUpdated(int newCount) {
            item.setCount(newCount);
            updateTotalPrice();

            eventListener.onItemCountUpdated(item, newCount);
            notifyTotalPriceChanged();
        }

        private void bind(CartItem item) {
            this.item = item;

            icon.setImageResource(item.item().icon());
            name.setText(item.item().name());
            price.setText(String.format(Locale.US, "Price: $%s", item.item().price()));
            updateTotalPrice();

            counterViewPresenter.setCount(item.count());
        }

        private void updateTotalPrice() {
            totalPrice.setText(String.format(Locale.US, "Total: $%s",
                    item.item().price() * item.count()));
        }
    }

    interface EventListener {
        void onTotalPriceChanged(int totalPrice);

        void onItemCountUpdated(CartItem item, int newCount);

        void onItemRemoved(CartItem item);

        void onItemClicked(CartItem item);
    }
}
