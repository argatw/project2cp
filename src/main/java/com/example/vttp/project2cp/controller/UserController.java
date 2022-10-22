package com.example.vttp.project2cp.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;

import com.example.vttp.project2cp.model.User;
import com.example.vttp.project2cp.service.UserService;

import jakarta.json.Json;
import jakarta.json.JsonObject;

@Controller
@RequestMapping
public class UserController {

    @Autowired
    private UserService uSvc;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    
    // @PostMapping(path = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    // public ResponseEntity<String> addUser(@RequestPart String email, @RequestPart String password) {
    //     // userRepository.save(user);
    //     User user = new User();
    //     user.setEmail(email);
    //     user.setPassword(password);

    //     Boolean result = uSvc.insertUser(user);
    //     if (result) {
            
    //     } else {
            
    //     }

    //     return new ResponseEntity<String>(HttpStatus.CREATED);

    // }
    
    // REGISTER
    @PostMapping(path = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addUser(@RequestPart String email, @RequestPart String password) {
        // userRepository.save(user);
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        try {
            uSvc.insertUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
        }

        // JsonObject data = Json.createObjectBuilder()
        //     .build();

        // return ResponseEntity.ok(data.toString());

        return ResponseEntity.ok(user.toJson().toString());

    }

    // LOGIN
    @PostMapping(path = "/auth", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> userLogin(@RequestPart String email, @RequestPart String password, HttpSession session) {
        // String email = payload.getFirst("email");
        // String password = payload.getFirst("password");
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        try {
            uSvc.authLogin(email, password);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
        }


        // if (!uSvc.authLogin(email, password)) {
        //     return ResponseEntity<String>(HttpStatus.BAD_REQUEST)
        // } else {
        //     // mvc.addObject("email", email);
        //     // session.setAttribute("email", email);
        //     // mvc = new ModelAndView("redirect:/protected/favourites");
            
        // }
        return ResponseEntity.ok(user.toJson().toString());
    }
}
