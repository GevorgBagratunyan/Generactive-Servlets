package com.generactive.service;

import com.generactive.model.Item;
import com.generactive.repository.ItemRepository;
import com.generactive.service.crud.CRUD;
import com.generactive.service.dto.ItemDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ItemService implements CRUD<ItemDTO, Long> {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public ItemDTO create(ItemDTO itemDTO) {

        Item item = new Item();
        BeanUtils.copyProperties(itemDTO, item);
        return itemDTO;
    }

    @Override
    public ItemDTO get(Long id) {

        Item item = itemRepository.read(id)
                .orElseThrow(NoSuchElementException::new);
        ItemDTO itemDTO = new ItemDTO();
        BeanUtils.copyProperties(item, itemDTO);
        if (item.getGroup() != null) {
            itemDTO.setGroupName(item.getGroup().getName());
        }
        return itemDTO;
    }

    @Override
    public void update(ItemDTO itemDTO) {

        Item item = new Item();
        BeanUtils.copyProperties(itemDTO, item);
        itemRepository.update(item);
    }

    @Override
    public void delete(Long id) {
        itemRepository.delete(id);
    }

    public List<ItemDTO> getAll() {
        List<Item> items = itemRepository.getAll();
        return mapToItemDTOs(items);
    }


    public List<ItemDTO> allByPriceRange(double from, double to) {

        List<Item> items = itemRepository.allByPriceRange(from, to);
        return mapToItemDTOs(items);
    }

    public ItemDTO getByName(String name) {

        Item item = itemRepository.getByName(name);
        ItemDTO itemDTO = new ItemDTO();
        BeanUtils.copyProperties(item, itemDTO);
        if (item.getGroup() != null) {
            itemDTO.setGroupName(item.getGroup().getName());
        }

        return itemDTO;
    }

    public void setGroup(Long itemID, Long groupID) {
        itemRepository.setGroup(itemID, groupID);
    }

    private List<ItemDTO> mapToItemDTOs(List<Item> items) {
        List<ItemDTO> itemDTOs = new ArrayList<>();

        for (Item item : items) {
            ItemDTO itemDTO = new ItemDTO();
            BeanUtils.copyProperties(item, itemDTO);
            itemDTOs.add(itemDTO);
        }

        return itemDTOs;
    }
}
