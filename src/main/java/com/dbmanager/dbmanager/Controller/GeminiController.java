package com.dbmanager.dbmanager.Controller;

import com.dbmanager.dbmanager.Service.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sql")
public class GeminiController {

    @Autowired
    private GeminiService geminiService;

    @PostMapping
    public String convertToSql(@RequestBody String prompt) {
        return geminiService.convertToSQL(prompt);
    }
}