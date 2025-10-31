package com.balancika.controller;

import com.balancika.model.dto.AccountSetDTO;
import com.balancika.repository.ItemGroupRepository;
import com.balancika.service.AccountSetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accountsets")
@RequiredArgsConstructor
public class AccountSetController {

    private final AccountSetService accountSetService;
    private final ItemGroupRepository itemGroupRepository;

    @GetMapping("/templates")
    public ResponseEntity<List<AccountSetDTO>> listTemplates(@RequestParam("type") String itemType) {
        List<AccountSetDTO> templates = accountSetService.getAllTemplatesAsDTO(itemType);
        return ResponseEntity.ok(templates);
    }

    @GetMapping("/")
    public ResponseEntity<List<AccountSetDTO>> listAccountSetByType(@RequestParam("type") String type, @RequestParam(name = "id", required = false) String id) {
        return ResponseEntity.ok(accountSetService.listAccountSetByType(id, type));
    }


}
