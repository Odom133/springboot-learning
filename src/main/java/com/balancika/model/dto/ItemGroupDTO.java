package com.balancika.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ItemGroupDTO {
    private Long id;
    private String groupId;
    private String name;
    private String type;
    private String description;
    private Boolean inactive;
}

