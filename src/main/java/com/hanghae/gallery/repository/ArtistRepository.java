package com.hanghae.gallery.repository;

import com.hanghae.gallery.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Optional<Artist> findByUsername(String username);
    Optional<Artist> findByNickname(String nickname);
}
