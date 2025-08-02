package com.dbmanager.dbmanager.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.dbmanager.dbmanager.Service.GeminiService;
import com.dbmanager.dbmanager.Service.QueryExecutionService;

@RestController
@RequestMapping("/api/query")
public class QueryController {

    @Autowired
    private GeminiService geminiService;

    @Autowired
    private QueryExecutionService queryExecutionService;

    @PostMapping
    public List<Map<String, Object>> generateAndRunSQL(@RequestBody Map<String, String> request) {
        String prompt = request.get("prompt");

        // Step 1: Convert to SQL using Gemini
        String sql = geminiService.convertToSQL(prompt);
        System.out.println("Generated SQL: " + sql);

        // Step 2: Execute SQL
        return queryExecutionService.executeSQL(sql);
    }
}