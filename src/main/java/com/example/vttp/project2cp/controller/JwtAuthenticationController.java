package com.example.vttp.project2cp.controller;

import com.example.vttp.project2cp.config.JwtTokenUtil;
import com.example.vttp.project2cp.model.JwtRequest;
import com.example.vttp.project2cp.model.JwtResponse;
import com.example.vttp.project2cp.model.User;
import com.example.vttp.project2cp.service.UserService;

import jakarta.json.Json;
import jakarta.json.JsonObject;

import org.apache.catalina.connector.Response;
// import com.techgeeknext.model.JwtRequest;
// import com.techgeeknext.model.JwtResponse;
// import com.techgeeknext.model.UserDto;
// import com.techgeeknext.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserService userDetailsService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}

	// @RequestMapping(value = "/register", method = RequestMethod.POST)
	// public ResponseEntity<?> saveUser(@RequestBody User user) throws Exception {
	// 	return ResponseEntity.ok(userDetailsService.insertUser(user));
	// }

    @RequestMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<String> saveUser(@RequestBody User user) throws Exception {
		// return ResponseEntity.ok(userDetailsService.insertUser(user));
        try {
            userDetailsService.insertUser(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
        }
        JsonObject data = Json.createObjectBuilder()
            .add("email", user.getEmail())
            .build();

        return ResponseEntity.ok(data.toString());
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}

