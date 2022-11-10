package com.example.vttp.project2cp.repo;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.example.vttp.project2cp.model.Favorites;

@Repository
public class FavoritesRepo {

    @Autowired
    private JdbcTemplate temp;

    public boolean insertFavorites(Favorites favorite) {
        int updated = temp.update("insert into favourite(email, car_park_no, address) values(?,?,?)", favorite.getEmail(), favorite.getCarparkNum(), favorite.getAddress());
        return 1 == updated;
    }

    public List<Favorites> getUserFavoritesItems (String email) {

        List<Favorites> favItems = new LinkedList<>();

        SqlRowSet rs = temp.queryForRowSet("select * from favourite where email = ?", email);
        while (rs.next()) {
            Favorites favItem = Favorites.createRs(rs);
            favItems.add(favItem);
        }
        
        return favItems;
    }

    public Optional<Favorites> getFavoriteItemByUser (String email) {
        SqlRowSet rs = temp.queryForRowSet("select * from favourite where email = ?", email);
        if (!rs.next()) {
            return Optional.empty();
        }
        
        Favorites f = new Favorites();
        f = Favorites.createRs(rs);

        return Optional.of(f);
    }

    public boolean deleteFavoriteItemByUser(String email, String carparkNum) {
        int updated = temp.update("delete from favourite where email = ? and car_park_no = ?", email, carparkNum);
        return 1 == updated;
    }

}
