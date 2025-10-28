package com.balancika.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginationDTO<L> {
    private long totalElements;
    private int pageNumber;
    private int totalPages;
    private int pageSize;
    private List<?> content;


}
