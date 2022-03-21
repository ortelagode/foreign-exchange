package com.example.persistence;

import com.example.persistence.Conversion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversionRepository extends PagingAndSortingRepository<Conversion, Long>, JpaSpecificationExecutor<Conversion> {

    @Override
    Page<Conversion> findAll(Specification<Conversion> spec, Pageable pageable);

    List<Conversion> findAll();
}
