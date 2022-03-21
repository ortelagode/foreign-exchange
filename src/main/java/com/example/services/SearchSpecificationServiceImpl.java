package com.example.services;

import com.example.persistence.Conversion;
import com.example.models.ConversionFilterBean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchSpecificationServiceImpl implements SearchSpecificationService<Conversion, ConversionFilterBean> {

    private static final String ID = "id";
    private static final String CREATED_AT = "createdAt";
    private static final String TRUNC = "TRUNC";
    private static final String TO_DATE = "TO_DATE";
    private static final String YYYY_MM_DD = "yyyy-mm-dd";

    @Override
    public Specification<Conversion> search(ConversionFilterBean searchBean) {

        return new Specification<Conversion>() {

            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Conversion> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<>();

                if (searchBean.getId() != null) {
                    Predicate idPredicate = criteriaBuilder.equal(root.get(ID), searchBean.getId());
                    predicates.add(idPredicate);
                }

                if (searchBean.getDate() != null) {
                    Predicate datePredicate = criteriaBuilder.equal(criteriaBuilder.function(TRUNC, java.sql.Date.class, root.get(CREATED_AT)),criteriaBuilder.function(TO_DATE, java.sql.Date.class, criteriaBuilder.literal(searchBean.getDate()), criteriaBuilder.literal(YYYY_MM_DD)));
                    predicates.add(datePredicate);
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };
    }
}
