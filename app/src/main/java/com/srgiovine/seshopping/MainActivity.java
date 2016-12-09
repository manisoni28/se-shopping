package com.srgiovine.seshopping;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.srgiovine.seshopping.navigation.NavigationAdapter;
import com.srgiovine.seshopping.navigation.NavigationItem;
import com.srgiovine.seshopping.navigation.NavigationItemFactory;

import srgiovine.com.seshopping.R;

public class MainActivity extends Activity {

    private final NavigationAdapter.EventListener navigationEventListener = new NavigationAdapter.EventListener() {
        @Override
        public void onItemClicked(NavigationItem item) {
            switch (item.type()) {
                case LOGOUT:
                    onLogout();
                    break;
                case FILTER:
                    onFilterClicked(item);
                    break;
                default:
                    throw new IllegalArgumentException("Unhandled item type " + item.type());
            }
        }
    };

    private final NavigationAdapter navigationAdapter = new NavigationAdapter(navigationEventListener);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView navigationDrawer = (RecyclerView) findViewById(R.id.navigation_drawer);
        navigationDrawer.setLayoutManager(new LinearLayoutManager(this));
        navigationDrawer.setAdapter(navigationAdapter);

        navigationAdapter.setItems(NavigationItemFactory.creteNavigationItems());
    }

    private void onLogout() {
        // TODO remove fake code
        startActivity(new Intent(this, SplashActivity.class));
        finish();
    }

    private void onFilterClicked(NavigationItem item) {
        // TODO update the filtered items
    }
}
