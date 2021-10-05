package com.generactive.service.criteria;

import com.generactive.service.dto.DTO;

import java.util.Objects;

public class ItemFindAllCriteria implements DTO {
    private Integer limit;
    private Integer offset;
    private String sort;
    private String orderByFieldName;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrderByFieldName() {
        return orderByFieldName;
    }

    public void setOrderByFieldName(String orderByFieldName) {
        this.orderByFieldName = orderByFieldName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemFindAllCriteria that = (ItemFindAllCriteria) o;
        return Objects.equals(limit, that.limit) &&
                Objects.equals(offset, that.offset) &&
                Objects.equals(sort, that.sort) &&
                Objects.equals(orderByFieldName, that.orderByFieldName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(limit, offset);
    }

    @Override
    public String toString() {
        return "ItemSearchCriteria{" +
                "limit=" + limit +
                ", offset=" + offset +
                ", sort='" + sort + '\'' +
                ", orderByFieldName='" + orderByFieldName + '\'' +
                '}';
    }
}
