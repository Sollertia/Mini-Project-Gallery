package com.hanghae.gallery.repository;

import com.hanghae.gallery.model.Artist;
import com.hanghae.gallery.model.Follow;
import com.hanghae.gallery.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow,Long> {
    Follow findByArtistAndUser(Artist artist, User user);
}
