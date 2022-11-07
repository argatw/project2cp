package com.example.vttp.project2cp.service;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.vttp.project2cp.model.Carpark;
import com.example.vttp.project2cp.repo.CarparkRepo;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class CarparkService {
    @Autowired
    private CarparkRepo cRepo;

    public Optional<Carpark> getCpInfo(String carparkNum) {
        Optional <Carpark> optional = cRepo.findCpInfoByCpNum(carparkNum);
        if (optional.isEmpty()) {
            return Optional.empty();
        }
        Carpark cp = optional.get();
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date timeNow = new Date();
        String cpUrl = UriComponentsBuilder
            .fromUriString("https://api.data.gov.sg/v1/transport/carpark-availability")
            .queryParam("date_time", sDateFormat.format(timeNow))
            .toUriString();
        RequestEntity request = RequestEntity
            .get(cpUrl)
            .accept(MediaType.APPLICATION_JSON)
            .build();

        RestTemplate rTemplate = new RestTemplate();
        ResponseEntity<String> resp = rTemplate.exchange(request, String.class);
        JsonReader r = Json.createReader(new StringReader(resp.getBody()));
        JsonObject o = r.readObject();
        JsonArray cpArray = o.getJsonArray("items");

        for (int i = 0; i < cpArray.size(); i++){
            JsonArray cpDataArr = cpArray
                .getJsonObject(i)
                .getJsonArray("carpark_data");
            for (int j = 0; j < cpDataArr.size(); j++){
                JsonObject index = cpDataArr.getJsonObject(j);
                if (carparkNum.equals(index.getString("carpark_number"))) {
                    JsonArray cpInfoArr = index.getJsonArray("carpark_info");
                    Integer totalLots = 0;
                    Integer availableLots = 0;
                    for (int k = 0; k < cpInfoArr.size(); k++){
                        Integer tempTotalLots = Integer.parseInt(cpInfoArr.getJsonObject(k).getString("total_lots"));
                        Integer tempAvailableLots = Integer.parseInt(cpInfoArr.getJsonObject(k).getString("lots_available"));
                        totalLots += tempTotalLots;
                        availableLots += tempAvailableLots;
                    }
                    cp.setAvailableLots(availableLots);
                    cp.setTotalLots(totalLots);
                }
            }
        }
        return Optional.of(cp);

        
        // try(InputStream is = new ByteArrayInputStream(json.getBytes())) {
        //     JsonReader r = Json.createReader(is);
        //     JsonObject o = r.readObject();
        //     JsonArray cpArray = o.getJsonArray("items");
        //     for (int i = 0; i < cpArray.size(); i++) {
        //         JsonArray cpDataArray = cpArray.getJsonObject(i).getJsonArray("carpark_data");
        //         for (int j = 0; j < cpDataArray.size(); j++) {
        //             JsonObject in = cpDataArray.getJsonObject(j);
        //         }
        //     }


        // }
    }

    public List<Carpark> findCpInfoByAddress(String searchAddress) {
        return Carpark.convertCpAsList(cRepo.queryCpInfoByAddress(searchAddress));
    }
    
}
