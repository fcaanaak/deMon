package com.modesec.server.controllers;

import com.modesec.server.controllers.constants.CoreConstants;
import com.modesec.server.models.Report;
import com.modesec.server.services.ReportServiceImpl;
import com.modesec.server.websocket.handler.ReportUpdatesHandler;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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


    private Logger logger = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    ReportServiceImpl reportService;

    @GetMapping(CoreConstants.REPORTS_ENDPOINT)// Temp, delete later
    public String getReports() {
        return "<h1>HELLO WORLD</h1>";
    }

    /**
     * POST Mapping for reports endpoint
     *
     *  Steps:
     *  1. Save the report to the server database
     *  2. Broadcast the report to all active clients using websockets
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
