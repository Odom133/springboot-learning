package com.balancika.model.dto;

import com.balancika.entity.AccountSet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AccountSetDTO {
    private Long id;
    private String type;
    private String groupId;
    private String subGroupId;
    private Long accountId;

    public AccountSetDTO(AccountSet set) {
        this.id = set.getId();
        this.type = set.getType();
        this.groupId = set.getGroupId();
        this.subGroupId = set.getSubGroupId();
        this.accountId = set.getAccountId();
    }
}

