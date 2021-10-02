//package com.generactive.repository;
//
//import com.generactive.model.GenerativeItem;
//import com.generactive.model.Group;
//import com.generactive.model.Item;
//import com.generactive.model.enums.Complexity;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//
//public class ItemRepositoryTest {
//
//    @Autowired
//    private static ItemRepository itemRepository;
//    @Autowired
//    private static GroupRepository groupRepository;
//
//    @BeforeAll
//    static void initDatabase() {
//        Group parent = new Group();
//        parent.setName("Parent");
//        groupRepository.create(parent);
//
//        Group child = new Group();
//        child.setName("Child");
//        child.setParent(parent);
//        groupRepository.create(child);
//
//        Item item = new GenerativeItem(Complexity.TWO);
//        item.setName("Start_Item");
//        item.setUrl("Start_URL");
//        item.setBasePrice(25.0);
//        itemRepository.save(item);
//    }
//
//    @Test
//    @DisplayName("Create Item")
//    public void create() {
//        Item item = new GenerativeItem(Complexity.TWO);
//        item.setName("Test_Item");
//        item.setUrl("Test_URL");
//        item.setBasePrice(22.5);
//
//        Item fetched = itemRepository.save(item);
//        assertNotNull(fetched);
//        assertEquals(2, fetched.getId());
//        assertTrue(fetched instanceof GenerativeItem);
//    }
//
//    @Test
//    @DisplayName("Get Item By id")
//    public void getById() {
//        Optional<Item> optionalItem = itemRepository.findById(1L);
//        assertTrue(optionalItem.isPresent());
//        assertEquals(1, optionalItem.get().getId());
//        assertEquals("Start_Item", optionalItem.get().getName());
//    }
//
//    @Test
//    @DisplayName("Delete Item By id")
//    public void deleteById() {
//        assertDoesNotThrow(() -> itemRepository.deleteById(1L));
//    }
//
//    @Test
//    @DisplayName("Update Item")
//    public void update() {
//        Item item = new GenerativeItem(Complexity.ONE);
//        item.setName("Updated");
//        assertDoesNotThrow(() -> itemRepository.save(item));
//
//    }
//
//    @Test
//    @DisplayName("Get All Items")
//    public void getAll() {
//        List<Item> list = itemRepository.findAll();
//        assertTrue(list.size() > 0);
//        assertEquals("Start_Item", list.get(0).getName());
//        assertTrue(list.get(0) instanceof GenerativeItem);
//    }
//
//    @Test
//    @DisplayName("Get Items in Given Price Range")
//    public void getItemsListByGivenPriceRange() {
//        List<Item> list = itemRepository.findAllByBasePriceGreaterThanAndSmallerThan(0.0, 1200.0);
//        assertTrue(list.size() > 0);
//        assertEquals("Start_Item", list.get(0).getName());
//        assertTrue(list.get(0) instanceof GenerativeItem);
//        assertTrue(list.get(0).getBasePrice() < 1200.0 && list.get(0).getBasePrice() > 0.0);
//    }
//
//}
