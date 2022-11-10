package com.example.vttp.project2cp.model;

import java.io.StringReader;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Favorites {
    private String carparkNum;
    private String address;
    private String email;
    public String getCarparkNum() {
        return carparkNum;
    }
    public void setCarparkNum(String carparkNum) {
        this.carparkNum = carparkNum;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public static Favorites createRs(SqlRowSet rs) {
        Favorites c = new Favorites();
        c.setCarparkNum(rs.getString("car_park_no"));
        c.setAddress(rs.getString("address"));
        c.setEmail(rs.getString("email"));
        return c;
    }

    public static Favorites create(String json) {
        JsonReader r = Json.createReader(new StringReader(json));
        JsonObject o = r.readObject();

        Favorites favItem = new Favorites();
        favItem.setEmail(o.getString("email"));
        favItem.setCarparkNum(o.getString("carparkNum"));
        favItem.setAddress(o.getString("address"));
        return favItem;

    }

    public JsonObject favToJson() {
        return Json.createObjectBuilder()
            .add("carparkNum", carparkNum)
            .add("address", address)
            .add("email", email)
            .build();
    }
}
