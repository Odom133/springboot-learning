package com.balancika.controller;


import jakarta.websocket.OnClose;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    private List<Map<String, Object>> data1 = new ArrayList<>();



    @PostMapping("/create")
    ResponseEntity<List<Map<String, Object>>> create(@RequestBody Map<String, Object> payload){
        data1.add(payload);
        return ResponseEntity.ok(data1);
    }
    @GetMapping("/getAll")
    ResponseEntity<List<Map<String, Object>>> getAll(){
        return  ResponseEntity.ok(data1);
    }
    @GetMapping("/{id}")
    ResponseEntity<Map<String, Object>> getById(@PathVariable("id") Long id){
        Map<String, Object> result = null;
        for (int i = 0; i < data1.size(); i++) {
            if(Long.parseLong(data1.get(i).get("id").toString()) == id){
                result = data1.get(i);
            }
        }
        return ResponseEntity.ok(result);
    }
    @PatchMapping("/{id}")
    ResponseEntity<Map<String, Object>> edit(@PathVariable("id") Long id, @RequestParam("name") String name, @RequestParam("tel") String tel){
        Map<String, Object> result = null;
        for (int i = 0 ; i < data1.size(); i++){
            if (Long.parseLong((data1.get(i).get("id").toString())) == id){
                data1.get(i).put("name", name);
                data1.get(i).put("tel", tel);
                result = data1.get(i);
            }

        }
        return ResponseEntity.ok(result);
    }
    @DeleteMapping("/{id}")
    ResponseEntity<Map<String, Object>> delete(@PathVariable("id") Long id){
        for (int i = 0; i < data1.size(); i++) {
            if (Long.parseLong(data1.get(i).get("id").toString()) == id){
                data1.remove(i);
            }
        }
        return ResponseEntity.noContent().build();
    }

    /*
    // Search user
    // /user/search?query=...
    // /user?query=...
    @GetMapping
    ResponseEntity<Object> search(@RequestParam("query") String query) {
        return ResponseEntity.ok(query);
    }

    // Get User by ID
    @GetMapping("/{id}")
    ResponseEntity<Object> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(id);
    }
    // Create User
    @PostMapping
    ResponseEntity<Object> create(@RequestBody Object payload) {
        return ResponseEntity.ok(payload);
    }
    // Update User
    // /user/1?name=...&email=...
    @PatchMapping("/{id}")
    ResponseEntity<Object> updatePartially(@PathVariable("id") Long id, @RequestParam("name") String name, @RequestParam("email") String email) {
        return ResponseEntity.ok(id);
    }

    // Delete User by ID
    // List all users

     */

}
