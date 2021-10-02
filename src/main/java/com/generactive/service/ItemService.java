package com.generactive.service;

import com.generactive.model.Group;
import com.generactive.model.Item;
import com.generactive.repository.GroupRepository;
import com.generactive.repository.ItemRepository;
import com.generactive.service.crud.CRUD;
import com.generactive.service.dto.ItemDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ItemService implements CRUD<ItemDTO, Long> {

    private final ItemRepository itemRepository;
    private final GroupRepository groupRepository;

    public ItemService(ItemRepository itemRepository, GroupRepository groupRepository) {
        this.itemRepository = itemRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public ItemDTO create(ItemDTO itemDTO) {

        Item item = new Item();
        BeanUtils.copyProperties(itemDTO, item);
        Item saved = itemRepository.save(item);
        BeanUtils.copyProperties(saved, itemDTO);
        return itemDTO;
    }

    @Override
    public ItemDTO get(Long id) {

        Item item = itemRepository.findById(id)
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
        itemRepository.save(item);
    }

    @Override
    public void delete(Long id) {
        itemRepository.deleteById(id);
    }

    public List<ItemDTO> getAll() {
        List<Item> items = itemRepository.findAll();
        return mapToItemDTOs(items);
    }

    public List<ItemDTO> getAll(Pageable pageable) {
        List<Item> items = itemRepository.findAll(pageable).getContent();
        return mapToItemDTOs(items);
    }


    public List<ItemDTO> allByPriceRange(double from, double to) {

        List<Item> items = itemRepository.findByBasePriceGreaterThanAndBasePriceLessThan(from, to);
        return mapToItemDTOs(items);
    }

    public ItemDTO getByName(String name) {

        Item item = itemRepository.findByName(name)
                .orElseThrow(NoSuchElementException::new);
        ItemDTO itemDTO = new ItemDTO();
        BeanUtils.copyProperties(item, itemDTO);
        if (item.getGroup() != null) {
            itemDTO.setGroupName(item.getGroup().getName());
        }

        return itemDTO;
    }

    public void setGroup(Long itemID, Long groupID) {
        Item item = itemRepository.findById(itemID)
                .orElseThrow(NoSuchElementException::new);
        Group group = groupRepository.findById(groupID)
                .orElseThrow(NoSuchElementException::new);
        item.setGroup(group);
        itemRepository.save(item);
    }

    private List<ItemDTO> mapToItemDTOs(List<Item> items) {
        List<ItemDTO> itemDTOs = new ArrayList<>();

        for (Item item : items) {
            ItemDTO itemDTO = new ItemDTO();
            BeanUtils.copyProperties(item, itemDTO);
            itemDTO.setGroupName(item.getGroup().getName());
            itemDTOs.add(itemDTO);
        }

        return itemDTOs;
    }
}
