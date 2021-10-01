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
    public ItemDTO get(@PathVariable long id) {
            return itemService.get(id);
    }

    @GetMapping
    public List<ItemDTO> getAll() {
        return itemService.getAll();
    }

    @PostMapping
    public ItemDTO save(@RequestBody ItemDTO itemDTO) {
        return itemService.create(itemDTO);
    }

    @PutMapping
    public void update(@RequestBody ItemDTO item) {
        itemService.update(item);
    }

    @DeleteMapping
    public void delete(@PathVariable long id) {
        itemService.delete(id);
    }

    @PutMapping("/{itemId}/{groupId}")
    public void setGroup(@PathVariable long itemId,
                         @PathVariable long groupId) {
    }

    @GetMapping("/{from}/{to}")
    public List<ItemDTO> allByPriceRange(@PathVariable double from,
                                      @PathVariable double to) {
        return itemService.allByPriceRange(from, to);
    }

    @GetMapping("/item/{name}")
    public ItemDTO getByName(@PathVariable String name) {
        return itemService.getByName(name);
    }
}
