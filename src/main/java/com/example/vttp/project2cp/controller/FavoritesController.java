package com.example.vttp.project2cp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.vttp.project2cp.model.CarparkResponse;
import com.example.vttp.project2cp.model.Favorites;
import com.example.vttp.project2cp.model.User;
import com.example.vttp.project2cp.service.EmailService;
import com.example.vttp.project2cp.service.FavoritesService;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class FavoritesController {

    @Autowired
    private FavoritesService favService;

    @Autowired
    private EmailService emailService;

    @PostMapping(path = "/addToFavs", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> insertFavItems(@RequestBody String payload) {

        Favorites favItem = Favorites.create(payload);

        favService.insertFavorites(favItem);

        JsonObject data = Json.createObjectBuilder()
                    .add("car_park_no", favItem.getCarparkNum())
                    .add("address", favItem.getAddress())
                    .add("email", favItem.getEmail())
                    .build();


        return ResponseEntity.status(HttpStatus.CREATED).body(data.toString());
    }

    @GetMapping(path = "/favorites", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getFavItems(@RequestParam String email) {
        List<Favorites> favCarparks = favService.getFavoritesOfUser(email);
        
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Favorites favCarpark: favCarparks)
            arrayBuilder.add(favCarpark.favToJson());
            
            return ResponseEntity.ok(arrayBuilder.build().toString());
    }

    // to do:  USE @REQUESTBODY String payload , NOT REQUESTPARAM
    @PostMapping(path="/emailFavorites", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sendFavItems(@RequestBody User user) {
        List<Favorites> favCarparks = favService.getFavoritesOfUser(user.getEmail());
        // StringBuilder builder = new StringBuilder();
        // for (Favorites favCarpark : favCarparks) {
        //     builder.append(favCarpark);
        // }
        // String content = builder.toString();
        // System.out.println(content);
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Favorites favCarpark: favCarparks)
            arrayBuilder.add(favCarpark.favToJson());

        emailService.sendEmail(user.getEmail(), "Your favorite carparks::", arrayBuilder.build().toString());

        CarparkResponse r = new CarparkResponse();
        r.setMessage(">>Email success");
        r.setStatus(200);

        return ResponseEntity.status(HttpStatus.OK).body(r.toJson().toString());
    }

    @DeleteMapping(path = "/deleteFavorites")
    public ResponseEntity<String> deleteCartItemByUser(@RequestParam String email, @RequestParam String carparkNum) {
        try {
            favService.deleteFavByUser(email, carparkNum);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
        }

        CarparkResponse r = new CarparkResponse();
        r.setMessage("Fav item delete success.");
        r.setStatus(200);

        return ResponseEntity.status(HttpStatus.OK).body(r.toJson().toString());

    }


    
}
