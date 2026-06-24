package com.modesec.server.services;

import com.modesec.server.models.Report;
import com.modesec.server.repositories.ReportRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.modesec.server.websocket.handler.ReportUpdatesHandler;

import java.io.IOException;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ReportUpdatesHandler reportUpdatesHandler;

    /**
     * Send a report to all clients
     *
     * @param report the report to broadcast
     */
    private void broadcastReport(Report report) {
        try {
            reportUpdatesHandler.broadcastReportToClients(report);
        } catch (IOException e) {
            // Do nothing for now
        }
    }

    /**
     * Save a passed in report to the DB and broadcast it to all connected clients
     *
     * @param report The report to add
     */
    @Override
    public void addReport(Report report) {
        reportRepository.save(report);
        broadcastReport(report);
    }

    @Override
    public List<Report> getReports() {
        return reportRepository.findAll();
    }

}
