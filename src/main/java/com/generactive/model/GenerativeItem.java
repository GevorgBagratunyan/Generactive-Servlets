package com.generactive.model;


import com.generactive.model.enums.Complexity;

import java.util.Objects;

public class GenerativeItem extends Item {

    private Complexity complexity;

    public GenerativeItem() {
    }

    public GenerativeItem(int id, String name, String url, double basePrice, int groupID, String complexity) {
        super(id, name, url, basePrice, groupID);
        this.complexity = Complexity.valueOf(complexity);
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

}
