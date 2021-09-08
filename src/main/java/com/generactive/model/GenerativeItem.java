package com.generactive.model;


import com.generactive.model.enums.Complexity;
import com.generactive.storage.Storage;

import java.util.Objects;

public class GenerativeItem extends Item {

    private Complexity complexity;

    public GenerativeItem() {
    }

    public GenerativeItem(int id, String name, String url, double basePrice, int groupID, String complexity) {
        super(id, name, url, basePrice, groupID);
        this.complexity = Complexity.valueOf(complexity);
    }

    public GenerativeItem(GenerativeItemBuilder builder) {
        super(builder.id, builder.name, builder.url, builder.price, builder.groupID);
        complexity = builder.complexity;
    }

    public Complexity getComplexity() {
        return complexity;
    }

    public void setComplexity(Complexity complexity) {
        this.complexity = complexity;
    }

    @Override
    public double calculatePrice(Configuration configuration) {
        double price = this.getBasePrice();
        double resolutionCoefficient = configuration.getResolution().getResolutionCoefficient();
        double comp = complexity.getValue();
        return price * resolutionCoefficient * comp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenerativeItem that = (GenerativeItem) o;
        if (!super.equals(o)) {
            return false;
        } else {
            return complexity == that.complexity;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), complexity);
    }

    public static class GenerativeItemBuilder {
        private int id;
        private String name;
        private String url;
        private double price;
        private int groupID;
        private Complexity complexity;

        public GenerativeItemBuilder id() {
            this.id = Storage.getNextItemID();
            return this;
        }

        public GenerativeItemBuilder name(String name) {
            this.name = name;
            return this;
        }

        public GenerativeItemBuilder url(String url) {
            this.url = url;
            return this;
        }

        public GenerativeItemBuilder price(double price) {
            this.price = price;
            return this;
        }

        public GenerativeItemBuilder group(int groupID) {
            this.groupID = groupID;
            return this;
        }


        public GenerativeItemBuilder complexity(Complexity complexity) {
            this.complexity = complexity;
            return this;
        }

        public GenerativeItem build() {
            return new GenerativeItem(this);
        }
    }
}
