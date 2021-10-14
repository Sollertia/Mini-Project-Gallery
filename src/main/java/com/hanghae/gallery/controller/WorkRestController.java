package com.hanghae.gallery.controller;

import com.hanghae.gallery.dto.FollowDto;
import com.hanghae.gallery.dto.StatusMsgDto;
import com.hanghae.gallery.dto.WorkRequestDto;
import com.hanghae.gallery.exception.NoFoundException;
import com.hanghae.gallery.model.*;
import com.hanghae.gallery.repository.ArtistRepository;
import com.hanghae.gallery.repository.WorkRepository;
import com.hanghae.gallery.service.WorkService;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
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
        Work work = workRepository.findById(workId).orElseThrow(() ->
                new NoFoundException("해당 작품을 찾을 수 없습니다."));
        Long artistId = work.getArtistId();
        Artist artist = artistRepository.getById(artistId);

        Optional<Follow> follow = workService.getUserAndArtist(artist, user);

        FollowEnum responseCodeSet = workService.codeSetHandler(follow, user);

        return new FollowDto(artist, work, responseCodeSet);
    }


    //작품 수정
    @PostMapping("/work/update")
    public StatusMsgDto update(@Validated @RequestBody WorkRequestDto workRequestDto, Errors errors) {
        StatusMsgDto statusMsgDto;
        //입력값이 옳지 않을 때
        if (errors.hasErrors()) {
            statusMsgDto = new StatusMsgDto(StatusEnum.STATUS_FAILE, workRequestDto);
        }
        //수정할 작품이 존재할 때
        else if (workService.updateWork(workRequestDto).isPresent()) {
            statusMsgDto = new StatusMsgDto(StatusEnum.STATUS_SUCCESS, workRequestDto);
        } else {
            statusMsgDto = new StatusMsgDto(StatusEnum.STATUS_FAILE, workRequestDto);
        }
        return statusMsgDto;
    }

    //작품 등록
    @PostMapping("/work/insert")
    public StatusMsgDto saveWork(@Validated @RequestBody WorkRequestDto workRequestDto, Errors errors) {
        if (errors.hasErrors()) {
            return new StatusMsgDto(StatusEnum.STATUS_FAILE, workRequestDto);
        } else {
            Work work = new Work();
            work.workSaveInfo(workRequestDto);
            workRepository.save(work);
            return new StatusMsgDto(StatusEnum.STATUS_SUCCESS, work);
        }

    }

    //작품 삭제
    @PostMapping("/work/delete")
    public void delete(@RequestParam Long workId) {
        Work work = workRepository.findById(workId).orElseThrow(() ->
                new NoFoundException("해당 작품을 찾을 수 없습니다."));
        workRepository.delete(work);
    }
}
