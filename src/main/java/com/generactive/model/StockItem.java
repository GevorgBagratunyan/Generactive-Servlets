package com.generactive.model;


import com.generactive.storage.Storage;

public class StockItem extends Item {

    public StockItem() {
    }

    public StockItem(int id, String name, String url, double basePrice, int groupID) {
        super(id, name, url, basePrice, groupID);
    }

    public StockItem(StockItemBuilder builder) {
        super(builder.id, builder.name,builder.url, builder.price, builder.groupID);
    }

    public static class StockItemBuilder {
        private int id;
        private String name;
        private String url;
        private double price;
        private int groupID;

        public StockItemBuilder id() {
            this.id = Storage.getNextItemID();
            return this;
        }

        public StockItemBuilder name(String name) {
            this.name = name;
            return this;
        }

        public StockItemBuilder url(String url) {
            this.url = url;
            return this;
        }

        public StockItemBuilder price(double price) {
            this.price = price;
            return this;
        }

        public StockItemBuilder group(int groupID) {
            this.groupID = groupID;
            return this;
        }

        public StockItem build() {
            return new StockItem(this);
        }
    }
}
