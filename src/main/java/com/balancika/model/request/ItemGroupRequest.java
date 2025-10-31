package com.balancika.model.request;

import com.balancika.model.dto.AccountSetDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemGroupRequest {
    private String groupId;
    private String name;
    private String type; // "Inventory part" or "Non-inventory part"
    private String description;
    private Boolean inactive;
    private Collection<AccountSetDTO> accountSets;

}
