package com.generactive.controller;

import com.generactive.service.ItemService;
import com.generactive.service.PageableImp;
import com.generactive.service.dto.ItemDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/by-offset-limit-and-sorted")
    public List<ItemDTO> getByPagination(@RequestParam Integer limit,
                                         @RequestParam Integer offset,
                                         @RequestParam String sort) {

        Sort srt;
        if(sort.equalsIgnoreCase("ASC")) {
            srt = Sort.by(Sort.Direction.ASC, "name");
        } else if(sort.equalsIgnoreCase("DESC")) {
            srt = Sort.by(Sort.Direction.DESC, "name");
        } else throw  new IllegalArgumentException("Invalid type of sorting, please input ASC or DESC");


        Pageable pageable = new PageableImp(limit, offset, srt);
        return itemService.getAll(pageable);
    }


    @GetMapping("/{id}")
    public ItemDTO get(@PathVariable long id) {
            return itemService.get(id);
    }

    @GetMapping
    public List<ItemDTO> getAll() {
        return itemService.getAll();
    }

    @GetMapping("/by-price")
    public List<ItemDTO> allByPriceRange(@RequestParam double from,
                                      @RequestParam double to) {
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

    @PutMapping
    public void update(@RequestBody ItemDTO item) {
        itemService.update(item);
    }

    @PutMapping("/set-group")
    public void setGroup(@RequestParam Long itemId,
                         @RequestParam Long groupId) {
        itemService.setGroup(itemId, groupId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        itemService.delete(id);
    }
}
