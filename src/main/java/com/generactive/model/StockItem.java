package com.generactive.model;


import com.generactive.storage.Storage;

public class StockItem extends Item {

    public StockItem(StockItemBuilder builder) {
        super(builder.id, builder.name,builder.url, builder.price, builder.group);
    }

    public static class StockItemBuilder {
        private int id;
        private String name;
        private String url;
        private double price;
        private Group group;
        private Configuration configuration;

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

        public StockItemBuilder group(Group group) {
            this.group = group;
            return this;
        }

        public StockItemBuilder configuration(Configuration configuration) {
            this.configuration = configuration;
            return this;
        }

        public StockItem build() {
            return new StockItem(this);
        }
    }
}
