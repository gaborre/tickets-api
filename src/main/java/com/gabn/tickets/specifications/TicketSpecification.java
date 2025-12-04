package com.gabn.tickets.specifications;

import com.gabn.tickets.models.Ticket;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import static com.gabn.tickets.constants.FieldConstants.ID;
import static com.gabn.tickets.constants.FieldConstants.STATUS;
import static com.gabn.tickets.constants.FieldConstants.USER;

public final class TicketSpecification {

    private TicketSpecification() {}

    public static Specification<Ticket> getFilterByStatus(String status) {
        return getFilterByString(STATUS, status)
            .and(getOrderByIdDesc());
    }

    public static Specification<Ticket> getFilterByUserId(Long userId) {
        return getFilterByLong(USER, ID, userId)
            .and(getOrderByIdDesc());
    }

    public static Specification<Ticket> getFilterByUserIdAndStatus(Long userId, String status) {
        return getFilterByLong(USER, ID, userId)
            .and(getFilterByString(STATUS, status))
            .and(getOrderByIdDesc());
    }

    private static Specification<Ticket> getFilterByString(
        String fieldName, String search
    ) {
        return (Root<Ticket> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (search == null) {
                return null;
            }
            return criteriaBuilder.equal(
                root.get(fieldName).as(String.class), search
            );
        };
    }

    private static Specification<Ticket> getFilterByLong(
        final String entityName,
        final String fieldName,
        final Long search
    ) {
        if (search == null) {
            return null;
        }

        return (Root<Ticket> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) ->
            criteriaBuilder.equal(root.get(entityName).get(fieldName), search);
    }

    public static Specification<Ticket> getOrderByIdDesc() {
        return (Root<Ticket> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.desc(root.get("id")));
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        };
    }
}
