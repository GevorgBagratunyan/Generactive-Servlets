package com.generactive.storage;


import com.generactive.model.Group;
import com.generactive.model.Item;

import java.util.*;
import java.util.stream.Collectors;

public class Storage {
    private static int groupID = 0;
    private static int itemID = 0;
    private static final Map<Integer, Group> GROUPS = new HashMap<>();
    private static final Map<Integer, Item> ITEMS = new HashMap<>();

    private Storage() {

    }

    //ITEM methods_____________________________________________________________

    public static Item addItem(Item item) {
        ITEMS.put(item.getId(), item);
        return ITEMS.get(item.getId());
    }

    public static int getNextItemID() {
        return ++itemID;
    }

    public static Optional<Item> updateItem(int id, Item item) {
        ITEMS.put(id, item);
        return ITEMS.values().stream()
                .filter(i -> i.getId()==id)
                .findFirst();
    }

    public static Optional<Item> findItemByName(String name) {
        return ITEMS.values().stream()
                .filter(item -> item.getName().equals(name))
                .findFirst();
    }

    public static Optional<Item> findItemById(int id) {
        return ITEMS.values().stream()
                .filter(e -> e.getId() == id)
                .findFirst();
    }

    public static Optional<Item> removeItemById(int id) {
        Optional<Item> item =  ITEMS.values().stream()
                .filter(i -> i.getId()==id)
                .findFirst();
        if(item.isPresent()){
            ITEMS.remove(id);
        }
        return item;
    }

    public static List<Item> findHighestPricedItems() {

        double max = ITEMS.entrySet().stream()
                .max(Comparator.comparingDouble(el -> el.getValue().getBasePrice()))
                .get()
                .getValue()
                .getBasePrice();

        return ITEMS.values().stream()
                .filter(item -> item.getBasePrice() == max)
                .collect(Collectors.toList());
    }

    public static void printAllItems() {
        ITEMS.entrySet().stream()
                .forEach(e -> e.getValue().printContent());
    }

    public static List<Item> findItemsByPriceRange(double from, double to) {
        return ITEMS.values().stream()
                .filter(item -> item.getBasePrice()>=from && item.getBasePrice()<=to)
                .collect(Collectors.toList());
    }

    //GROUP methods_____________________________________________________________


    public static int getNextGroupID() {
        return ++groupID;
    }

    public static Group addGroup(Group group) {
        GROUPS.put(group.getId(), group);
        return GROUPS.get(group.getId());

    }

    public static Group findGroupByName(String name) {
        return GROUPS.values().stream()
                .filter(v -> v.getName().equals(name))
                .findFirst()
                .orElseThrow(()-> new NoSuchElementException("There is no such Group"));
    }

    public static Optional<Group> findGroupByID(int id) {
        return GROUPS.entrySet().stream()
                .filter(e -> e.getKey() == id)
                .map(Map.Entry::getValue)
                .findFirst();
    }

    public static Optional<Group> removeGroupByID(int id) {
        return GROUPS.values().stream()
                .filter(group -> group.getId()==id)
                .findFirst();
    }

    public static List<Group> findSubGroupsByParent(Group parent) {
        return GROUPS.values().stream()
                .filter(group -> group.equals(parent))
                .collect(Collectors.toList());
    }

    public static void printAllGroups() {
        GROUPS.values().stream()
                .forEach(Group::printContent);
    }

    public static void clear() {
        GROUPS.clear();
        ITEMS.clear();
    }

    public static List<Item> getAllItems() {
        return new ArrayList<>(ITEMS.values());
    }

}
