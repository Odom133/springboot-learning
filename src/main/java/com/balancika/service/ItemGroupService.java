package com.balancika.service;


import com.balancika.entity.ItemGroup;
import com.balancika.model.dto.ItemGroupDTO;
import com.balancika.model.request.ItemGroupRequest;
import com.balancika.repository.ItemGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemGroupService {

    private final ItemGroupRepository itemGroupRepository;
    private final AccountSetService accountSetService;

    @Transactional
    public ItemGroupDTO create(ItemGroupRequest request) {
        ItemGroup itemGroup = new ItemGroup();
        itemGroup.setGroupId(request.getGroupId());
        itemGroup.setName(request.getName());
        itemGroup.setType(request.getType());
        itemGroup.setDescription(request.getDescription());
        itemGroup.setInactive(request.getInactive() != null ? request.getInactive() : false);

        ItemGroup saved = itemGroupRepository.save(itemGroup);

        return mapToDTO(saved);
    }

    @Transactional
    public ItemGroupDTO update(Long id, ItemGroupRequest request) {
        ItemGroup itemGroup = itemGroupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ItemGroup not found with ID: " + id));

        itemGroup.setGroupId(request.getGroupId());
        itemGroup.setName(request.getName());
        itemGroup.setType(request.getType());
        itemGroup.setDescription(request.getDescription());
        itemGroup.setInactive(request.getInactive() != null ? request.getInactive() : false);

        ItemGroup saved = itemGroupRepository.save(itemGroup);

        return mapToDTO(saved);
    }

    public List<ItemGroupDTO> listAll() {
        return itemGroupRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ------------------------------
    // Helper  entity convert to DTO
    // ------------------------------
    private ItemGroupDTO mapToDTO(ItemGroup itemGroup) {
        return ItemGroupDTO.builder()
                .id(itemGroup.getId())
                .groupId(itemGroup.getGroupId())
                .name(itemGroup.getName())
                .type(itemGroup.getType())
                .description(itemGroup.getDescription())
                .inactive(itemGroup.getInactive())
                .build();
    }
}

