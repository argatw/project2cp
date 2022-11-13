package com.example.vttp.project2cp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vttp.project2cp.model.Report;
import com.example.vttp.project2cp.repo.ReportRepo;

@Service
public class ReportService {

    @Autowired
    private ReportRepo rRepo;

    public void reportCp (Report report) {
        rRepo.newReport(report);
    }


}
