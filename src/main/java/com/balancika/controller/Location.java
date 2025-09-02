package com.balancika.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/location")
public class Location {

    private List<Map<String, Object>> data  = new ArrayList<>();
    // create location
    @PostMapping("/create")
    ResponseEntity<List<Map<String, Object>>> create(@RequestBody Map<String, Object> payload){
        data.add(payload);
        return ResponseEntity.ok(data);
    }
    // list location
    @GetMapping("/list")
    ResponseEntity<List<Map<String, Object>>> list(){
        return ResponseEntity.ok(data);
    }
    // search by id
    @GetMapping("/{id}")
    ResponseEntity<Map<String, Object>> searchById(@PathVariable("id") int id){
        Map<String, Object> result = null;
        for (int i = 0; i < data.size(); i++) {
            if (Integer.parseInt(data.get(i).get("id").toString()) == id){
                result = data.get(i);
            }
        }
       return ResponseEntity.ok(result);
    }

    // edit location
    @PatchMapping("/{id}")
    ResponseEntity<Map<String, Object>> edit(@PathVariable("id") int id, @RequestParam("l_code") String l_code, @RequestParam("l_name") String l_name ){
       Map<String, Object> result = null;
        for (int i = 0; i < data.size(); i++) {
            if(Integer.parseInt(data.get(i).get("id").toString()) == id){
                data.get(i).put("l_code", l_code);
                data.get(i).put("l_name", l_name);
                result = data.get(i);
            }
        }
        return ResponseEntity.ok(result);
    }
    // delete location
    @DeleteMapping("/{id}")
    ResponseEntity<String> delete(@PathVariable("id") int id){
        for (int i = 0; i < data.size(); i++) {
            if (Integer.parseInt(data.get(i).get("id").toString()) == id){
                data.remove(i);
            }
        }
        return ResponseEntity.noContent().build();
    }


}
