package com.modesec.server.services;

import com.modesec.server.models.Report;

import java.util.List;

/**
 * Service concerned with report-based operations
 */
public interface ReportService {
    void addReport(Report report);
    List<Report> getReports();
}
