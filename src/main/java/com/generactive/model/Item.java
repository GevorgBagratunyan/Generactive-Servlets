package com.generactive.model;


import java.util.Objects;

public class Item {
    private int id;
    private String name;
    private String url;
    private double basePrice;
    private Group group;

    public Item() {
    }

    public Item(int id, String name, String url, double basePrice, Group group) {
        this.name = name;
        this.url = url;
        this.basePrice = basePrice;
        this.group = group;
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public Group getGroup() {
        return group;
    }


    public void printContent() {
        System.out.println("    Item group : " + this.group.getName());
        System.out.println("    Item name : " + this.name);
        System.out.println("    Item price : " + this.basePrice);
        System.out.println("    Item ID : " + this.id);
    }

    public double calculatePrice(Configuration configuration) {
        double price = this.getBasePrice();
        double resolutionCoefficient = configuration.getResolution().getResolutionCoefficient();
        return price * resolutionCoefficient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id &&
                Objects.equals(name, item.name) &&
                Objects.equals(url, item.url) &&
                Objects.equals(group, item.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, url);
    }
}
