package com.hanghae.gallery.model;
import com.hanghae.gallery.dto.WorkRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Setter // 테스트 데이터 용
@NoArgsConstructor
@Getter
@Entity
public class Work{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column()
    private String workTitle;

    @Column(nullable = false)
    private String workSize;

    @Column(nullable = false)
    private String workMaterial;

    @Column(nullable = false)
    private String workMade;

    @Column(nullable = false)
    private String workDesc;

    //@Column(nullable = false, unique = true)
    @Column(nullable = false) // 테스터 데이터 용
    private String image;

    @Column(nullable = false)
    private Long artistId=12L;

    //클라이언트 -> 서버 (작가id없음)
    public void workSaveInfo(WorkRequestDto workRequestDto){
        this.workDesc = workRequestDto.getWorkDesc();
        this.workMade = workRequestDto.getWorkMade();
        this.workSize = workRequestDto.getWorkSize();
        this.image = workRequestDto.getImage();
        this.workMaterial = workRequestDto.getWorkMaterial();
        if (workRequestDto.getWorkTitle().equals(""))
        this.workTitle = "무제";
    }

}
