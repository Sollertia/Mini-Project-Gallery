package com.hanghae.gallery.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Follow {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Artist_id")//Follow 테이블에 저장되는 칼럼 이름
    private Artist artist;

    @ManyToOne
    @JoinColumn(name = "User_id")
    private User user;

}
