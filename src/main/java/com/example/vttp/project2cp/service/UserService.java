package com.example.vttp.project2cp.service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.vttp.project2cp.model.User;
import com.example.vttp.project2cp.repo.UserRepo;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo uRepo;

    // @Autowired
	// private PasswordEncoder bcryptEncoder;

    public boolean insertUser(User user) {
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString().substring(0,8);
        user.setId(id);
        // String encodedPw = new BCryptPasswordEncoder().encode(user.getPassword());
        // user.setPassword(encodedPw);
        // user.setPassword(bcryptEncoder.encode(user.getPassword()));
        boolean added = uRepo.saveNewUser(user);
        if (added) {
            return true;
        } else {
            return false;
        }
    }

    public boolean authLogin(String email, String password) {
        return 1== uRepo.getUserByEmailAndPassword(email, password);
    }

    public boolean changeUserPw(User user) {
        return 1 == uRepo.updatePw(user);
    }

    // public Optional<User> findUserByEmail(String email) {
    //     User user = uRepo.getUserByEmail(email);
    //     if (user == null) {
    //         return Optional.empty();
    //     }
    //     return Optional.of(user);
    // }

    // public UserDetails findUserByEmail(String email) throws UsernameNotFoundException {
    //     User user = uRepo.getUserByEmail(email);
    //     if (user == null) {
	// 		throw new UsernameNotFoundException("User not found with username: " + email);
	// 	}
	// 	return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
	// 			new ArrayList<>());
    // }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = uRepo.getUserByEmail(email);
        
        if (user == null) {
			throw new UsernameNotFoundException("User not found with email: " + email);
		}
		// return new org.springframework.security.core.userdetails.User(user.getEmail(), new BCryptPasswordEncoder().encode(user.getPassword()), 
		// 		new ArrayList<>());
        
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
        			new ArrayList<>());
    }

    //deactivateUserAcc(String email) method
        //findFavByEmail 
            //removeFav(email)
        //deleteUser(email)
    
}
