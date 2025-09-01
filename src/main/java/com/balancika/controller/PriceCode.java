package com.balancika.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/price_code")
public class PriceCode {
    private List<Map<String, Object>> data = new ArrayList<>();

    //create
    @PostMapping("/create")
    ResponseEntity<List<Map<String, Object>>> create(@RequestBody Map<String, Object> payload){
        data.add(payload);
        return ResponseEntity.ok(data);
    }
    //List price code
    @GetMapping("/list")
    ResponseEntity<List<Map<String, Object>>> list(){
        return ResponseEntity.ok(data);
    }
    // Search by Id
    @GetMapping("/{id}")
    ResponseEntity<Map<String, Object>> searchById(@PathVariable("id") Integer id){
        Map<String, Object> result = null;
        for (int i = 0; i < data.size(); i++) {
                if (Integer.parseInt(data.get(i).get("id").toString()) == id){
                    result = data.get(i);
                }
        }
        return ResponseEntity.ok(result);
    }
    // edit price_code
    @PatchMapping("/{id}")
    ResponseEntity<Map<String, Object>> edit(@PathVariable("id") Integer id,@RequestParam("name") String name,@RequestParam("des")  String des){
        Map<String, Object> result = null;
        for (int i = 0 ; i < data.size(); i++){
            if (Integer.parseInt((data.get(i).get("id").toString())) == id){
                data.get(i).put("name", name);
                data.get(i).put("des", des);
                result = data.get(i);
            }
        }
        return ResponseEntity.ok(result);
    }

    // delete price_code
    @DeleteMapping("/{id}")
    ResponseEntity<String> delete(@PathVariable("id") Integer id){
        for (int i =0 ; i < data.size(); i++){
            if (Integer.parseInt(data.get(i).get("id").toString()) == id){
                data.remove(i);
            }
        }
        return ResponseEntity.status(201).body("Delete successfully!");
    }


}
