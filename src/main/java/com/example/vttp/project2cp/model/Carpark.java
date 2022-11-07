package com.example.vttp.project2cp.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;

public class Carpark {
    private String carparkNum;
    private String address;
    private String carparkType;
    private String parkingSystem;
    private String parkingTerm;
    private String freeParking;
    private String nightParking;
    private String gantryHeight;
    private String decks;
    private Integer totalLots;
    private Integer availableLots;
    private String xCoord;
    private String yCoord;
    private String cpBasement;

    public String getCpBasement() {
        return cpBasement;
    }
    public void setCpBasement(String cpBasement) {
        this.cpBasement = cpBasement;
    }
    public String getxCoord() {
        return xCoord;
    }
    public void setxCoord(String xCoord) {
        this.xCoord = xCoord;
    }
    public String getyCoord() {
        return yCoord;
    }
    public void setyCoord(String yCoord) {
        this.yCoord = yCoord;
    }
    public String getcarparkNum() {
        return carparkNum;
    }
    public void setcarparkNum(String carparkNum) {
        this.carparkNum = carparkNum;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getcarparkType() {
        return carparkType;
    }
    public void setcarparkType(String carparkType) {
        this.carparkType = carparkType;
    }
    public String getParkingSystem() {
        return parkingSystem;
    }
    public void setParkingSystem(String parkingSystem) {
        this.parkingSystem = parkingSystem;
    }
    public String getParkingTerm() {
        return parkingTerm;
    }
    public void setParkingTerm(String parkingTerm) {
        this.parkingTerm = parkingTerm;
    }
    public String getFreeParking() {
        return freeParking;
    }
    public void setFreeParking(String freeParking) {
        this.freeParking = freeParking;
    }
    public String getNightParking() {
        return nightParking;
    }
    public void setNightParking(String nightParking) {
        this.nightParking = nightParking;
    }
    public String getGantryHeight() {
        return gantryHeight;
    }
    public void setGantryHeight(String gantryHeight) {
        this.gantryHeight = gantryHeight;
    }
    public String getDecks() {
        return decks;
    }
    public void setDecks(String decks) {
        this.decks = decks;
    }
    public Integer getTotalLots() {
        return totalLots;
    }
    public void setTotalLots(Integer totalLots) {
        this.totalLots = totalLots;
    }
    public Integer getAvailableLots() {
        return availableLots;
    }
    public void setAvailableLots(Integer availableLots) {
        this.availableLots = availableLots;
    }
    

    public static Carpark convertCpToRs(SqlRowSet rs) {
        Carpark cp = new Carpark();
        while (rs.next()) {
            cp.setcarparkNum(rs.getString("car_park_no"));
            cp.setAddress(rs.getString("address"));
            cp.setyCoord(rs.getString("y_coord"));
            cp.setxCoord(rs.getString("x_coord"));
            cp.setcarparkType(rs.getString("car_park_type"));
            cp.setParkingSystem(rs.getString("type_of_parking_system"));
            cp.setParkingTerm(rs.getString("short_term_parking"));
            cp.setFreeParking(rs.getString("free_parking"));
            cp.setNightParking(rs.getString("night_parking"));
            cp.setGantryHeight(rs.getString("gantry_height"));
            cp.setDecks(rs.getString("car_park_decks"));
            cp.setCpBasement(rs.getString("car_park_basement"));
        }
        return cp;
    }

    public static List<Carpark> convertCpAsList(SqlRowSet rs) {
        List<Carpark> carparks = new LinkedList<>();
        while (rs.next()) {
            Carpark cp = new Carpark();
            cp.setAddress(rs.getString("address"));
            cp.setcarparkNum(rs.getString("car_park_no"));
            carparks.add(cp);
        }
        return carparks;
    }

    public JsonObject cpListtoJson() {
        return Json.createObjectBuilder()
            .add("address", address)
            .add("carparkNum", carparkNum)
            .build();
    }
    
    public JsonObject cptoJson() {
        return Json.createObjectBuilder()
            .add("car_park_no", carparkNum) 
            .add("address", address) 
            .add("carparkType", carparkType) 
            .add("parkingSystem", parkingSystem) 
            .add("parkingTerm", parkingTerm)
            .add("freeParking", freeParking)
            .add("nightParking", nightParking)
            .add("gantryHeight", gantryHeight)
            .add("decks", decks)
            .add("totalLots", totalLots)
            .add("availableLots", availableLots)
            .add("xCoord", xCoord)
            .add("yCoord", yCoord)
            .add("cpBasement", cpBasement)
            .build();
    }

    // public static List<Carpark> create(String json) throws IOException {
    //     List <Carpark> carparks = new LinkedList<>();
    //     try(InputStream is = new ByteArrayInputStream(json.getBytes())) {
    //         JsonReader r = Json.createReader(is);
    //         JsonObject o = r.readObject();
    //         JsonArray cpArray = o.getJsonArray("items");
    //         for (int i = 0; i < cpArray.size(); i++) {
    //             JsonArray cpDataArray = cpArray.getJsonObject(i).getJsonArray("carpark_data");
    //             for (int j = 0; j < cpDataArray.size(); j++) {
    //                 JsonObject in = cpDataArray.getJsonObject(j);
    //             }
    //         }


    //     } 



    //     return carparks;
    // }
}

