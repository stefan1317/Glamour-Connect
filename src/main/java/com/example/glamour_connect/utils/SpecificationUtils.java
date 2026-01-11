package com.example.glamour_connect.utils;

import com.example.glamour_connect.domain.Makeup;
import com.example.glamour_connect.dto.MakeupSearchDto;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class SpecificationUtils {

    public static Specification<Makeup> buildMakeupSpecification(MakeupSearchDto makeupSearchDto) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            String name = makeupSearchDto.name();

            if (name != null && !name.isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        "%" + name.toLowerCase() + "%"
                ));
            }

            String duration = makeupSearchDto.durationInHours();

            if (duration != null && !duration.isEmpty()) {
                if (duration.contains(">")) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("durationInHours"), tokenize(duration, ">").getFirst()));
                } else if (duration.contains("<")) {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("durationInHours"), tokenize(duration, "<").getFirst()));
                } else {
                    predicates.add(criteriaBuilder.equal(root.get("durationInHours"), duration));
                }
            }

            String price = makeupSearchDto.price();

            if (price != null && !price.isEmpty()) {
                if (price.contains(">")) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), tokenize(price, ">").getFirst()));
                } else if (price.contains("<")) {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), tokenize(price, "<").getFirst()));
                } else {
                    predicates.add(criteriaBuilder.equal(root.get("price"), price));
                }
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static List<Double> tokenize(String expression, String delimitator) {
        List<String> strings = Collections.list(new StringTokenizer(expression, delimitator)).stream()
                .map(token -> (String) token)
                .toList();

        return strings.stream().map(Double::parseDouble).collect(Collectors.toList());
    }
}
