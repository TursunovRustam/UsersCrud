package com.example.backend.Controller;

import com.example.backend.Entity.Till;
import com.example.backend.Service.Till.TillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/till")
public class TillController {
    @Autowired
    public TillService tillService;

    @GetMapping
    public ResponseEntity<?> getAllTills(){
        return ResponseEntity.ok(tillService.getAllTills());
    }

    @PostMapping
    public ResponseEntity<?> saveTill(@RequestBody Till till){
        return new ResponseEntity<>(tillService.saveTill(till), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getTillById(@PathVariable("id") String id){
        return ResponseEntity.ok(tillService.getTillById(UUID.  fromString(id)));
    }
}