package com.modesec.server.services;

import com.modesec.server.models.Report;
import com.modesec.server.repositories.ReportRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.modesec.server.websocket.handler.ReportUpdatesHandler;

import java.io.IOException;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ReportUpdatesHandler reportUpdatesHandler;

    private void broadcastReport(Report report) {
        try {
            reportUpdatesHandler.broadcastReportToClients(report);
        } catch (IOException e) {
            // Do nothing for now
        }
    }

    @Override
    public void addReport(Report report) {
        reportRepository.save(report);
        broadcastReport(report);

    }

}
