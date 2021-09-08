package com.generactive.model;


public class StockItem extends Item {

    public StockItem() {
    }

    public StockItem(int id, String name, String url, double basePrice, int groupID) {
        super(id, name, url, basePrice, groupID);
    }
}
