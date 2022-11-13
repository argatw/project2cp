package com.example.vttp.project2cp.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.vttp.project2cp.model.Report;
import com.example.vttp.project2cp.service.ReportService;

import jakarta.json.Json;
import jakarta.json.JsonObject;

@RestController
public class ReportController {
    
    @Autowired
    private ReportService rService;

    @PostMapping(path="/report" ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> newRegistration(@RequestPart MultipartFile myFile, @RequestPart String email, @RequestPart String carparkNum, @RequestPart String description) throws IOException {
        
        Report r = new Report();

        r.setEmail(email);
        r.setCarparkNum(carparkNum);
        r.setDescription(description);
        r.setMediaType(myFile.getContentType());
        r.setPic(myFile.getInputStream());
        

        try {
            rService.reportCp(r);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
        }

        JsonObject data = Json.createObjectBuilder()
                .add("name", myFile.getName())
                .add("content-type", myFile.getContentType())
                .add("size", myFile.getSize())
                .add("email", r.getEmail())
                .add("carparkNum", r.getCarparkNum())
                .add("description", r.getDescription())
                .build();
        
        return ResponseEntity.ok(data.toString());
    }
}
