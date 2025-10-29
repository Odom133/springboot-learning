package com.balancika.model.dto;

import com.balancika.entity.Province;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProvinceDTO {
    private Long id;
    private String name;
    private String description;

    public ProvinceDTO(Province province) {
        this.id = province.getId();
        this.name = province.getName();
        this.description = province.getDescription();
    }
}
