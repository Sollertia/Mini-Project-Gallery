package com.hanghae.gallery.repository;


import com.hanghae.gallery.model.Work;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkRepository extends JpaRepository<Work, Long> {
}
