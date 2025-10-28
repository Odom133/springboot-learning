package com.balancika.controller.DEMO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/role")
public class RoleController {

    private List<Map<String, Object>> data = new ArrayList<>();

    // Create role
    @PostMapping("/create")
    ResponseEntity<List<Map<String, Object>>> create(@RequestBody Map<String, Object> payload){
        data.add(payload);
        return ResponseEntity.ok(data);
    }

    // Get role
    @GetMapping("/getAll")
    ResponseEntity<List<Map<String, Object>>> getAll(){
        return ResponseEntity.ok(data);
    }

    // Get role by id
    @GetMapping("/{id}")
    ResponseEntity<Map<String, Object>> getById(@PathVariable("id") Long id){
        Map<String, Object> result = null;
        for (int i = 0; i < data.size(); i++) {
            if(data.get(i).get("id").equals(id.toString())) {
                result = data.get(i);
            }
        }
        return ResponseEntity.ok(result);
    }


    // edit role
    @PatchMapping("/{id}")
    ResponseEntity<Map<String, Object>> update(@PathVariable("id") Long id, @RequestParam("name") String name){
        Map<String, Object> result = null;
        for (int i = 0; i < data.size(); i++) {
            if(data.get(i).get("id").equals(id.toString())) {
                data.get(i).put("name", name);
                result = data.get(i);
            }
        }
        return ResponseEntity.ok(result);
    }

    // Delete
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        for(int i = 0 ; i< data.size(); i++){
            if(data.get(i).get("id").equals(id.toString())){
                data.remove(i);
            }
        }
        return ResponseEntity.noContent().build();
    }


}
