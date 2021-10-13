package com.hanghae.gallery.controller;

import com.hanghae.gallery.dto.FollowDto;
import com.hanghae.gallery.dto.WorkRequestDto;
import com.hanghae.gallery.model.*;
import com.hanghae.gallery.repository.ArtistRepository;
import com.hanghae.gallery.repository.WorkRepository;
import com.hanghae.gallery.service.WorkService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class WorkRestController {

    private final WorkRepository workRepository;
    private final WorkService workService;
    private final ArtistRepository artistRepository;


    public WorkRestController(WorkRepository workRepository, WorkService workService, ArtistRepository artistRepository) {

        this.workRepository = workRepository;
        this.workService = workService;
        this.artistRepository = artistRepository;
    }


    //작품 상세
    @GetMapping("/work/detail")
    public FollowDto getWork(@RequestParam Long workId, User user) { // User user부분 나중에 Userdetails로 변경
        Work work = workRepository.findById(workId).orElseThrow(
                ()-> new IllegalArgumentException("해당 작품을 찾을 수 없습니다."));
        Long artistId =work.getArtistId();
        Artist artist = artistRepository.getById(artistId);

        Optional<Follow> follow = workService.getUserAndArtist(artist, user);

        FollowEnum responseCodeSet = workService.codeSetHeandler(follow, user);

        return new FollowDto(artist,work,responseCodeSet);
    }


    //작품 수정
    @PostMapping("/work/update")
    public void update(@RequestBody WorkRequestDto workRequestDto,@RequestParam Long id){
        workService.updateWork(workRequestDto,id);
    }

    //작품 등록
    @PostMapping("/work/insert")
    public  void saveWork(@RequestBody WorkRequestDto workRequestDto){
        Work work = new Work();
        work.workSaveInfo(workRequestDto);
        workRepository.save(work);
    }

    //작품 삭제
    @PostMapping("/work/delete")
    public void delete(@RequestParam Long workId){
        Work work = workRepository.findById(workId)
                .orElseThrow(()-> new IllegalArgumentException("해당 작품을 찾을 수 없습니다."));
        workRepository.delete(work);
    }
}
