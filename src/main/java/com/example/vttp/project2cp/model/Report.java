package com.example.vttp.project2cp.model;

import java.io.InputStream;

public class Report {
    private String email;
    private String carparkNum;
    private String description;
    private String mediaType;
    private InputStream pic;
    
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getCarparkNum() {
        return carparkNum;
    }
    public void setCarparkNum(String carparkNum) {
        this.carparkNum = carparkNum;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getMediaType() {
        return mediaType;
    }
    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }
    public InputStream getPic() {
        return pic;
    }
    public void setPic(InputStream pic) {
        this.pic = pic;
    }

    
}
