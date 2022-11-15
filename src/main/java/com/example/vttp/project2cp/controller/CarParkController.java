package com.example.vttp.project2cp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.example.vttp.project2cp.model.Carpark;
import com.example.vttp.project2cp.model.CarparkResponse;
import com.example.vttp.project2cp.service.CarparkService;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;

@RestController
@RequestMapping(path="/carpark" , produces = MediaType.APPLICATION_JSON_VALUE)
// @CrossOrigin
public class CarParkController {

    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    // @PreAuthorize
    public String getEmployees() {
        return "Favorites!";
    }


    @Autowired
    private CarparkService cService;

    @GetMapping(path="/search")
    public ResponseEntity<String> getCarparks(@RequestParam String location
    ) {

        List<Carpark> cpList = cService.findCpInfoByAddress(location);

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        for(Carpark cp: cpList)
            arrBuilder.add(cp.cpListtoJson()); //list of cp (cpListToJson), do another toJson on aft click on THAT ONE cp (cpToJson)
        // arrBuilder.add(cpList);

        return ResponseEntity.ok(arrBuilder.build().toString());
    }

    @GetMapping(path="{carparkNum}")
    public ResponseEntity<String> getCarpark(@PathVariable String carparkNum) {
        Optional<Carpark> o = cService.getCpInfo(carparkNum);

        if(o.isEmpty()) {
            CarparkResponse r =  new CarparkResponse();
            r.setStatus(404);
            r.setMessage("HDB Carpark %s not found".formatted(carparkNum));
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(r.toJson().toString());
        }

        Carpark cp = o.get();

        return ResponseEntity.ok(cp.cptoJson().toString());
    }

    
}
