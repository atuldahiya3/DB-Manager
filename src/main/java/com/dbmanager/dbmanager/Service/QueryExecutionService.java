package com.dbmanager.dbmanager.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class QueryExecutionService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> executeSQL(String sqlQuery) {
        // Basic validation: only allow SELECT
        if (!sqlQuery.trim().toLowerCase().startsWith("select")) {
            throw new IllegalArgumentException("Only SELECT queries are allowed.");
        }

        // Execute query and return result
        return jdbcTemplate.queryForList(sqlQuery);
    }
}