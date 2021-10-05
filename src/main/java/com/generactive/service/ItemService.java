package com.generactive.service;

import com.generactive.model.Group;
import com.generactive.model.Item;
import com.generactive.repository.GroupRepository;
import com.generactive.repository.ItemRepository;
import com.generactive.service.criteria.ItemFindAllCriteria;
import com.generactive.service.criteria.ItemSearchCriteria;
import com.generactive.service.crud.CRUD;
import com.generactive.service.dto.ItemDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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

    public List<ItemDTO> getAll(ItemFindAllCriteria criteria) {
        Integer limit = criteria.getLimit();
        Integer offset = criteria.getOffset();
        String sort = criteria.getSort();
        String orderByFieldName = criteria.getOrderByFieldName();

        Sort srt;
        if(sort==null) {
            srt = Sort.by(Sort.Direction.ASC, "id");
        } else if (sort.equalsIgnoreCase("ASC")) {
            srt = Sort.by(Sort.Direction.ASC, orderByFieldName);
        } else if (sort.equalsIgnoreCase("DESC")) {
            srt = Sort.by(Sort.Direction.DESC, orderByFieldName);
        } else throw new IllegalArgumentException("Invalid type of sorting, please input ASC or DESC");

        Pageable pageable = new PageableImp(limit, offset, srt);
//        Specification<Item> specification ?????????????????????
//        List<Item> filteredItems = itemRepository.findAll(specification, pageable).getContent();

        List<Item> items = itemRepository.findAll(pageable).getContent();
        return mapToItemDTOs(items);
    }


    public List<ItemDTO> allByPriceRange(double from, double to) {

        List<Item> items = itemRepository.findByBasePriceGreaterThanAndBasePriceLessThan(from, to);
        return mapToItemDTOs(items);
    }

    public ItemDTO getByName(ItemSearchCriteria criteria) {
        String name = criteria.getName();
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
