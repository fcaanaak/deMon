package com.modesec.server.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {

    @GetMapping("/reports")
    public String getReports(){
        return "<h1>HELLO WORLD</h1>";
    }

}
