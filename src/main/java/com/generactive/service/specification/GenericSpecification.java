package com.generactive.service.specification;

import com.generactive.service.criteria.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class GenericSpecification<T> implements Specification<T> {

    private SearchCriteria searchCriteria;

    public GenericSpecification(SearchCriteria searchCriteria) {
        super();
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        String argument = searchCriteria.getArgument();

        switch (searchCriteria.getSearchOperation()) {
            case EQUALITY:
                return criteriaBuilder.equal(root.get(searchCriteria.getKey()), argument);
            case GREATER_THAN:
                return criteriaBuilder.greaterThan(root.get(searchCriteria.getKey()), (Comparable) argument);
            case IN:
                return root.get(searchCriteria.getKey()).in(argument);
            case LIKE:
                return criteriaBuilder.like(root.get(searchCriteria.getKey()), argument);
            case LESS_THAN:
                return criteriaBuilder.lessThan(root.get(searchCriteria.getKey()), (Comparable) argument);
            case NEGATION:
                return criteriaBuilder.notEqual(root.get(searchCriteria.getKey()), argument);
        }
        return null;
    }
}
