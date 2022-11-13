package com.example.vttp.project2cp.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.vttp.project2cp.model.Report;

@Repository
public class ReportRepo {

    @Autowired
    private JdbcTemplate temp;

    public boolean newReport(Report r) {

        int updated = temp.update("insert into report(email, description, media_type, pic, car_park_no) values(?,?,?,?,?)", r.getDescription(), r.getEmail(), r.getMediaType(), r.getPic(), r.getCarparkNum());
        return updated == 1;
    }


}
