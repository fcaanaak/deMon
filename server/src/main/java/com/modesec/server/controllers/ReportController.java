package com.modesec.server.controllers;

import com.modesec.server.controllers.constants.CoreConstants;
import com.modesec.server.models.Report;
import com.modesec.server.services.ReportServiceImpl;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller meant to be used by devices and NOT clients
 * to upload reports
 */
@RestController
public class ReportController {

    @Autowired
    ReportServiceImpl reportService;

    /**
     * GET Mapping for reports endpoint
     * will send back either all the reports or some of the reports, with options to get more
     *
     * @return Placeholder for now, but later either some or all of reports
     */
    @GetMapping(CoreConstants.REPORTS_ENDPOINT)// Temp, update later to
    public String getReports() {
        return "<h1>HELLO WORLD</h1>";
    }

    /**
     * POST Mapping for reports endpoint
     *
     * Stores the report in the database and then broadcasts the latest
     * report to all clients
     *
     * @param report The submitted report
     * @return a response containing the report just sent
     */
    @PostMapping(CoreConstants.REPORTS_ENDPOINT)
    public Report postReports(@Valid @RequestBody Report report) {
        reportService.addReport(report);
        return report;
    }

}
