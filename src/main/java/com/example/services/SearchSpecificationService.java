package com.example.services;

import org.springframework.data.jpa.domain.Specification;

public interface SearchSpecificationService<T, S> {

    public Specification<T> search(S searchBean);

}
