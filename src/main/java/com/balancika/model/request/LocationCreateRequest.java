package com.balancika.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationCreateRequest {
    private String name;
    private Long warehouseId;
    private String warehouseName;
}
