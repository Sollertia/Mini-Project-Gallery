package com.hanghae.gallery.repository;


import com.hanghae.gallery.model.Work;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkRepository extends JpaRepository<Work, Long> {
    List<Work> findAllByArtistId(Long id);

}
