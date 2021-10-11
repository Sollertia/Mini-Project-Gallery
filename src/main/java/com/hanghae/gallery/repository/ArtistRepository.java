package com.hanghae.gallery.repository;

import com.hanghae.gallery.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
}
