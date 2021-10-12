package com.hanghae.gallery.repository;

import com.hanghae.gallery.model.Work;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InfinityRepository extends JpaRepository<Work,Long> {

    Page<Work> findAllByIdLessThan(Long lastWorkId, Pageable pageable);
    Page<Work> findAllByArtistId(Long artist, Pageable pageable);
    Page<Work> findAllByArtistIdAndIdLessThan(Long artist,Long lastWorkId,Pageable pageable);
}
