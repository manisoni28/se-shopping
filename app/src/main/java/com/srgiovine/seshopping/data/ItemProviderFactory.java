package com.srgiovine.seshopping.data;

public final class ItemProviderFactory {

    private ItemProviderFactory() {
    }

    public static ItemProvider create() {
        return new FakeItemProvider();
    }

}
