package com.generactive.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Group {
    private int id;
    private String name;
    private int parentId;
    private final List<Group> subGroups = new ArrayList<>();
    private final List<Item> items = new ArrayList<>();

    public Group() {
    }

    public Group(int id, String name, int parentGroupId) {
        this.id = id;
        this.name = name;
        this.parentId = parentGroupId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getParentId() {
        return parentId;
    }

    public List<Group> getSubGroups() {
        return subGroups;
    }

    public void addSubGroup(Group group) {
        subGroups.add(group);
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void printContent() {
        System.out.println("Group ID : " + id + "\nName : " + name);
        System.out.println("Items in this Group : ");

        if (!items.isEmpty()) {
            for (Item item : items) {
                item.printContent();
            }
        } else System.out.println();

        System.out.println();
        System.out.println("Subgroups in this Group : ");
        if (!subGroups.isEmpty()) {
            for (Group group : subGroups) {
                System.out.printf("  GROUP - id: {%d} Name: {%s}%n", group.getId(), group.getName());
                System.out.println("  Items in this group : ");
                group.printItems();
            }
        } else System.out.println();
        System.out.println("____________________");
    }

    public void printItems() {
        for (Item item : items) {
            item.printContent();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return id == group.id && parentId == group.parentId && name.equals(group.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, parentId);
    }
}
