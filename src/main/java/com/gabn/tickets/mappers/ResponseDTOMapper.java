package com.gabn.tickets.mappers;

import com.gabn.tickets.dtos.BaseResponseDTO;
import com.gabn.tickets.dtos.BaseResponseDTO.ErrorDTO;
import com.gabn.tickets.dtos.CriteriaDTO;
import com.gabn.tickets.dtos.PageResponseDTO;
import com.gabn.tickets.dtos.PaginationDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

public final class ResponseDTOMapper {

    private ResponseDTOMapper() {}

    public static <T> BaseResponseDTO<T> toBaseResponseDTO(HttpStatus httpStatus, T data) {
        return BaseResponseDTO.<T>builder()
            .code(httpStatus.value())
            .message(httpStatus.getReasonPhrase())
            .data(data)
            .build();
    }

    public static <T> PageResponseDTO<T> toPageResponseDTO(
        HttpStatus httpStatus, T data, PaginationDTO paginationDTO
    ) {
        return PageResponseDTO.<T>builder()
            .code(httpStatus.value())
            .message(httpStatus.getReasonPhrase())
            .data(data)
            .pagination(paginationDTO)
            .build();
    }

    public static <T> PaginationDTO buildPaginationDTO(
        CriteriaDTO criteriaDTO, Page<T> page
    ) {
        return PaginationDTO.builder()
            .page(criteriaDTO.getPage())
            .perPage(criteriaDTO.getLimit())
            .lastPage(page.getTotalPages())
            .total(page.getTotalElements())
            .build();
    }

    public static ErrorDTO buildErrorDTO(String type, String description) {
        return ErrorDTO.builder()
            .type(type)
            .description(description)
            .build();
    }
}
