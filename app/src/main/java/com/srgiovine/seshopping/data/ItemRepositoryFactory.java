package com.srgiovine.seshopping.data;

public final class ItemRepositoryFactory {

    private ItemRepositoryFactory() {
    }

    public static ItemRepository create() {
        return new FakeItemRepository();
    }

}
