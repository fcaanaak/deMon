package com.modesec.server.controllers;

import com.modesec.server.controllers.constants.CoreConstants;
import com.modesec.server.models.Report;
import com.modesec.server.services.ReportServiceImpl;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
     *
     * @return All the reports in the database sorted in chronological order
     */
    @GetMapping(CoreConstants.REPORTS_ENDPOINT)
    @CrossOrigin(origins = CoreConstants.FRONTEND_URL)
    public List<Report> getReports() {

        List<Report> reportsList = reportService.getReports();
        reportsList.sort(Collections.reverseOrder());
        return reportsList;

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
    @ResponseStatus(value = HttpStatus.CREATED)
    public Report postReports(@Valid @RequestBody Report report) {
        reportService.addReport(report);
        return report;
    }

}

