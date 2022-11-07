package com.example.vttp.project2cp.model;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class CarparkResponse {
    private Integer status;
    private String message;
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
            .add("status", status)
            .add("message", message)
            .build();
    }
}
