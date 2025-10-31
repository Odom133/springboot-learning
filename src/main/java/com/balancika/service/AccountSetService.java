package com.balancika.service;


import com.balancika.entity.AccountSet;
import com.balancika.model.dto.AccountSetDTO;
import com.balancika.repository.AccountSetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountSetService {

    private final AccountSetRepository accountSetRepository;

    // ------------------------------
    // Templates for Inventory Part
    // ------------------------------
    private static final List<AccountSet> INVENTORY_TEMPLATES = List.of(
            new AccountSet(null, "IC-GRO","", "NA", null),
            new AccountSet(null, "REVENUE-GRO", "","NA", null),
            new AccountSet(null, "REVENUE_RETURN-GRO", "","NA", null),
            new AccountSet(null, "COGS-GRO", "","NA", null),
            new AccountSet(null, "COGS-GRO", "","AC1", null),
            new AccountSet(null, "COGS-GRO", "","AC2", null),
            new AccountSet(null, "COGS-GRO", "","AC3", null),
            new AccountSet(null, "COGS-GRO", "","AC4", null),
            new AccountSet(null, "COGS-GRO", "","AC5", null),
            new AccountSet(null, "IC-IU-GRO", "","NA", null),
            new AccountSet(null, "IC-IA-GRO","","NA", null),
            new AccountSet(null, "UNEARNED_REVENUE-GRO","", "NA", null),
            new AccountSet(null, "DEFERRED_DISCOUNT-GRO", "","NA", null)
    );

    // ------------------------------
    // Templates for Non-inventory Part
    // ------------------------------
    private static final List<AccountSet> NON_INVENTORY_TEMPLATES = List.of(
            new AccountSet(null, "IC-GRO", "","NA", null),
            new AccountSet(null, "REVENUE-GRO", "","NA", null),
            new AccountSet(null, "REVENUE_RETURN-GRO", "","NA", null)
    );

    // ------------------------------
    // Generate account sets for ItemGroup
    // ------------------------------
    @Transactional(readOnly = true)
    public List<AccountSetDTO> listAccountSetByType(String id, String type) {
        List<AccountSet> result = Optional.ofNullable(id).map(accountSetRepository::findAllByGroupId).orElse(getAllTemplates(type));
        return result.stream().map(AccountSetDTO::new).toList();
    }

    // ------------------------------
    // Return templates as DTOs
    // ------------------------------
    public List<AccountSetDTO> getAllTemplatesAsDTO(String itemType) {
        return getAllTemplates(itemType)
                .stream()
                .map(t -> AccountSetDTO.builder()
                        .type(t.getType())
                        .groupId(t.getGroupId())
                        .subGroupId(t.getSubGroupId())
                        .accountId(t.getAccountId())
                        .build())
                .collect(Collectors.toList());
    }

    // ------------------------------
    // Helper: get templates list
    // ------------------------------
    public List<AccountSet> getAllTemplates(String itemType) {
        if (itemType == null) {
            throw new IllegalArgumentException("Item type must not be null");
        }
        if ("Non-inventory part".equalsIgnoreCase(itemType)) {
            return NON_INVENTORY_TEMPLATES;
        } else if ("Inventory part".equalsIgnoreCase(itemType)) {
            return INVENTORY_TEMPLATES;
        }
        throw new IllegalArgumentException("Unknown item group type: " + itemType);
    }

}

