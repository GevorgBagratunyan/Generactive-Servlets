package com.generactive.model;


import com.generactive.model.enums.Complexity;
import com.generactive.storage.Storage;

import java.util.Objects;

public class GenerativeItem extends Item {

    private final Complexity complexity;

    public GenerativeItem(GenerativeItemBuilder builder) {
        super(builder.id, builder.name, builder.url, builder.price, builder.group);
        complexity = builder.complexity;
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
        private Group group;
        private Configuration configuration;
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

        public GenerativeItemBuilder group(Group group) {
            this.group = group;
            return this;
        }

        public GenerativeItemBuilder configuration(Configuration configuration) {
            this.configuration = configuration;
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
