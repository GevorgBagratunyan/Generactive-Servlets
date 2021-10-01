package com.generactive.controller;

import com.generactive.service.ItemService;
import com.generactive.service.dto.ItemDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }


    @GetMapping("/{id}")
    public ItemDTO get(@PathVariable Long id) {
        return itemService.get(id);
    }

    @GetMapping
    public List<ItemDTO> getAll() {
        return itemService.getAll();
    }

    @GetMapping("/by-price")
    public List<ItemDTO> allByPriceRange(@RequestParam Double from,
                                         @RequestParam Double to) {
        return itemService.allByPriceRange(from, to);
    }

    @GetMapping("/item/{name}")
    public ItemDTO getByName(@PathVariable String name) {
        return itemService.getByName(name);
    }

    @PostMapping
    public ItemDTO save(@RequestBody ItemDTO itemDTO) {
        return itemService.create(itemDTO);
    }

    @PutMapping("/item")
    public void update(@RequestBody ItemDTO itemDTO) {
        itemService.update(itemDTO);
    }

    @PutMapping
    public void setGroup(@RequestParam Long itemId,
                         @RequestParam Long groupId) {
        itemService.setGroup(itemId, groupId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        itemService.delete(id);
    }


}
