package com.modesec.server.controllers;

import com.modesec.server.models.Report;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReportController {

    @GetMapping("/reports")// Temp, delete later
    public String getReports(){
        return "<h1>HELLO WORLD</h1>";
    }

    @PostMapping("/reports")
    public Report postReports(@RequestBody Report report) {
        return new Report("Test");
    }

}
