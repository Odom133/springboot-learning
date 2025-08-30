package com.balancika.controller;

import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/role")
public class RoleController {

    private List<Map<String, Object>> data = new ArrayList<>();

    /*
    * e.g: /role
    * payload: {
    *   "id": 1,
    *   "name": "Admin"
    * }
    * */
    // Create role
    @PostMapping("/create")
    ResponseEntity<List<Map<String, Object>>> create(@RequestBody Map<String, Object> payload){
        data.add(payload);
        return ResponseEntity.ok(data);
    }
    /*
     * e.g: /role/:id
     * payload: {
     *   "name": "SuperAdmin"
     * }
     * */
    // Edit role
    @PatchMapping("/{id}")
    ResponseEntity<Object> edit(@PathVariable("id") String id, @RequestBody Object payload){
        return ResponseEntity.ok(id + payload);
    }
    /*
     * e.g: /role/:id
     * */
    // Delete role by id
    @DeleteMapping("/{id}")
    ResponseEntity<Object> delete(@PathVariable("id") String id){
        return ResponseEntity.ok(id);
    }
    /*
     * e.g: /role/:id
     * */
    // Get role by id
    @GetMapping("/{id}")
    ResponseEntity<Object> get(@PathVariable("id") String id){
        return ResponseEntity.ok(id);
    }
    /*
     * e.g: /role
     * */
    // List all role
    @GetMapping
    ResponseEntity<Object> listAll(@RequestParam("listAll") String listAll){
        return ResponseEntity.ok(listAll);
    }

}
