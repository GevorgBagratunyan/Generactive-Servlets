package com.generactive.model;


import java.util.Objects;

public class Item {
    private int id;
    private String name;
    private String url;
    private double basePrice;
    private int groupID;

    public Item() {
    }

    public Item(int id, String name, String url, double basePrice, int groupID) {
        this.name = name;
        this.url = url;
        this.basePrice = basePrice;
        this.groupID = groupID;
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

    public void setGroupID(int groupID) {
        this.groupID = groupID;
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

    public int getGroupId() {
        return groupID;
    }


    public void printContent() {
        System.out.println("    Item group id: " + this.groupID);
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
                Objects.equals(groupID, item.groupID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, url);
    }
}
