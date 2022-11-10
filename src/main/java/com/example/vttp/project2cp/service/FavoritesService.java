package com.example.vttp.project2cp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vttp.project2cp.model.Favorites;
import com.example.vttp.project2cp.repo.FavoritesRepo;

@Service
public class FavoritesService {

    @Autowired
    private FavoritesRepo favRepo;

    public void insertFavorites (Favorites favorites) {
        favRepo.insertFavorites(favorites);
    }

    public List<Favorites> getFavoritesOfUser(String email) {
        return favRepo.getUserFavoritesItems(email);
    }

    public void deleteFavByUser(String email, String carparkNum) {
        favRepo.deleteFavoriteItemByUser(email,carparkNum);
    }


}
