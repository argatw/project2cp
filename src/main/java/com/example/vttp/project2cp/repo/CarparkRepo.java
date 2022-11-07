package com.example.vttp.project2cp.repo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.example.vttp.project2cp.model.Carpark;

@Repository
public class CarparkRepo {

    @Autowired
    private JdbcTemplate temp;

    public SqlRowSet queryCpInfoByAddress(String searchAddress) {
        final SqlRowSet rs = temp.queryForRowSet("select * from cp_info where address like ?", "%" + searchAddress + "%");
        return rs;
    }

    public SqlRowSet queryCpInfoByCpNum(String carparkNum) {
        final SqlRowSet rs = temp.queryForRowSet("select * from cp_info where car_park_no = ?", carparkNum);     
        return rs;
    }

    public Optional<Carpark> findCpInfoByCpNum(String carparkNum) {
        final SqlRowSet rs = queryCpInfoByCpNum(carparkNum);
        if (!rs.isBeforeFirst())
            return Optional.empty();
        return Optional.of(Carpark.convertCpToRs(rs));
    }
    
}
