package com.example.vttp.project2cp.repo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.vttp.project2cp.model.User;

@Repository
public class UserRepo {
    @Autowired
    private JdbcTemplate temp;

    // query
    public User getUserByEmail(String email) {
        final SqlRowSet q = temp.queryForRowSet(
            "select * from user where email like ?", email
        );
        if(!q.next())
            return null;

        return User.create(q);
    }

    // insert "insert into user (userId,email,password) values (?,?,sha1(?))"
    public boolean saveNewUser(User user) {
        final int added = temp.update(
            "insert into user (userId,email,password) values (?,?,?)"
            ,user.getId(),user.getEmail(),new BCryptPasswordEncoder().encode(user.getPassword())
        );
        return added > 0;
    }

    // query
    public int getUserByEmailAndPassword(String email, String password) {
        final SqlRowSet q = temp.queryForRowSet(
            "select count(*) as user_count from user where email = ? and password = sha1(?)"
                , email, password
        );
        if(!q.next())
            return 0;
        return q.getInt("user_count");
    }

    // update
    public int updatePw(User user) {
        int count = temp.update("update user set password = sha1(?) where email = ? and password = sha1(?)",
            user.getNewPassword(), user.getEmail(), user.getPassword());
        return count;
    }
    
    public void deleteUser(String email) {
        temp.update("delete from user where email = ?", email);
    }



}
