package com.generactive.storage;


import com.generactive.model.Item;

import java.util.List;
import java.util.Optional;

public class ItemRepository implements CRUD<Item> {

    @Override
    public Item create(Item item) {
        return Storage.addItem(item);
    }

    @Override
    public Optional<Item> read(int id) {
        return Storage.findItemById(id);
    }

    @Override
    public Optional<Item> update(int id, Item item) {
       return Storage.updateItem(id, item);
    }

    @Override
    public Optional<Item> delete(int id) {
        return Storage.removeItemById(id);
    }

    public List<Item> all() {
        return Storage.getAllItems();
    }

    public List<Item> allByPriceRange(double from, double to) {
        return Storage.findItemsByPriceRange(from,to);
    }

    public static void printAll() {
        Storage.printAllItems();
    }

    public static Optional<Item> getByName(String name) {
        return Storage.findItemByName(name);
    }

    public static List<Item> getHighestPricedItems() {
        return Storage.findHighestPricedItems();
    }
}
