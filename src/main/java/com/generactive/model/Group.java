package com.generactive.model;

import com.generactive.storage.Storage;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private final int id;
    private final String name;
    private final Group parent;
    private final List<Group> subGroups;
    private final List<Item> items;

    public Group(GroupBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.parent = builder.parent;
        this.subGroups = builder.subGroups;
        this.items = builder.items;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Group getParent() {
        return parent;
    }

    public List<Group> getSubGroups() {
        return subGroups;
    }

    public void addGroup(Group group) {
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

    public static class GroupBuilder {

        private int id;
        private String name;
        private Group parent;
        private final List<Group> subGroups;
        private final List<Item> items;

        public GroupBuilder() {
            this.subGroups = new ArrayList<>();
            this.items = new ArrayList<>();
        }

        public GroupBuilder id() {
            this.id = Storage.getNextGroupID();
            return this;
        }

        public GroupBuilder groupName(String name) {
            this.name = name;
            return this;
        }

        public GroupBuilder parentGroup(Group parent) {
            this.parent = parent;
            return this;
        }

        public GroupBuilder addSubgroup(Group group) {
            this.subGroups.add(group);
            return this;
        }

        public Group build() {
            return new Group(this);
        }
    }

}
