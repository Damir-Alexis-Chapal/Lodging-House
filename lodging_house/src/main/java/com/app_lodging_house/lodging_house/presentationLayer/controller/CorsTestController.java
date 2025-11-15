package com.app_lodging_house.lodging_house.presentationLayer.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CorsTestController {

    @GetMapping("/cors")
    public String testCors() {
        System.out.println("GET /api/test/cors - EJECUTADO");
        return "CORS GET funciona!";
    }

    @PostMapping("/cors")
    public String testCorsPost(@RequestBody String data) {
        System.out.println("POST /api/test/cors - EJECUTADO: " + data);
        return "CORS POST funciona! con: " + data;
    }
}