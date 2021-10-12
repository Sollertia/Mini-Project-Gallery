package com.hanghae.gallery.service;

import com.hanghae.gallery.model.Work;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InfinityRepository extends JpaRepository<Work,Long> {
}
