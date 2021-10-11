package com.hanghae.gallery.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


public class Work {


    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(unique = true)
    private String workTitle="무제";

    @Column(nullable = false)
    private String workSize;

    @Column(nullable = false)
    private String workMaterial;

    @Column(nullable = false)
    private String workMade;

    @Column(nullable = false)
    private String workDesc;

    @Column(nullable = false)
    private Long artistId;

}
