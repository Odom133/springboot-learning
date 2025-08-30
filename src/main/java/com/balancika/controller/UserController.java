package com.balancika.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

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

}
