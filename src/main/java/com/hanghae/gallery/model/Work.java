package com.hanghae.gallery.model;
import com.hanghae.gallery.dto.WorkRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;


@NoArgsConstructor
@Getter
@Entity
public class Work{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(nullable = false,unique = true)
    private String image;

    @Column(nullable = false)
    private Long artistId;

    //클라이언트 -> 서버 (작가id없음)
    public void workSaveInfo(WorkRequestDto workRequestDto){
        this.workDesc = workRequestDto.getWorkDesc();
        this.workMade = workRequestDto.getWorkMade();
        this.workSize = workRequestDto.getWorkSize();
        this.image = workRequestDto.getImage();
        this.workMaterial = workRequestDto.getWorkMaterial();
        this.workTitle = workRequestDto.getWorkTitle();
    }

}
