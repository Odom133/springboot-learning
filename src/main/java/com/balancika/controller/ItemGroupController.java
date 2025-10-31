package com.balancika.controller;

import com.balancika.model.dto.ItemGroupDTO;
import com.balancika.model.request.ItemGroupRequest;
import com.balancika.service.ItemGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/itemgroups")
@RequiredArgsConstructor
public class ItemGroupController {

    private final ItemGroupService itemGroupService;

    // ------------------------------
    // Create new ItemGroup
    // ------------------------------
    @PostMapping
    public ResponseEntity<ItemGroupDTO> create(@RequestBody ItemGroupRequest request) {
        ItemGroupDTO dto = itemGroupService.create(request);
        return ResponseEntity.ok(dto);
    }

    // ------------------------------
    // Update existing ItemGroup
    // ------------------------------
    @PutMapping("/{id}")
    public ResponseEntity<ItemGroupDTO> update(@PathVariable Long id, @RequestBody ItemGroupRequest request) {
        ItemGroupDTO dto = itemGroupService.update(id, request);
        return ResponseEntity.ok(dto);
    }

    // ------------------------------
    // List all ItemGroups
    // ------------------------------
    @GetMapping
    public ResponseEntity<List<ItemGroupDTO>> listAll() {
        List<ItemGroupDTO> list = itemGroupService.listAll();
        return ResponseEntity.ok(list);
    }
}

