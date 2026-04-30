package com.modesec.server.repositories;

import com.modesec.server.models.Report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

}
