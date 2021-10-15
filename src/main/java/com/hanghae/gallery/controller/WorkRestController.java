package com.hanghae.gallery.controller;

import com.hanghae.gallery.dto.FollowDto;
import com.hanghae.gallery.dto.StatusMsgDto;
import com.hanghae.gallery.dto.WorkRequestDto;
import com.hanghae.gallery.exception.NoFoundException;
import com.hanghae.gallery.model.*;
import com.hanghae.gallery.repository.ArtistRepository;
import com.hanghae.gallery.repository.WorkRepository;
import com.hanghae.gallery.service.WorkService;
import com.hanghae.gallery.util.ImgStore;
import com.hanghae.gallery.util.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@RestController
public class WorkRestController {

    private final WorkRepository workRepository;
    private final WorkService workService;
    private final ArtistRepository artistRepository;
    private final ImgStore imgStore;


    public WorkRestController(WorkRepository workRepository, WorkService workService,
                              ArtistRepository artistRepository, ImgStore imgStore) {

        this.workRepository = workRepository;
        this.workService = workService;
        this.artistRepository = artistRepository;
        this.imgStore = imgStore;
    }

    @Value("${file.dir}")
    private String fileDir;


    //작품 상세
    @GetMapping("/work/detail")
    public FollowDto getWork(@RequestParam Long workId, User user) { // User user부분 나중에 Userdetails로 변경
        Work work = workRepository.findById(workId).orElseThrow(()->
                new NoFoundException("해당 작품을 찾을 수 없습니다."));
        Long artistId =work.getArtistId();
        Artist artist = artistRepository.getById(artistId);

        Optional<Follow> follow = workService.getUserAndArtist(artist, user);

        FollowEnum responseCodeSet = workService.codeSetHandler(follow, user);

        return new FollowDto(artist,work,responseCodeSet);
    }


    //작품 수정
    @PostMapping("/work/update")
    public StatusMsgDto update(@Validated @RequestPart(value="key", required=false) WorkRequestDto workRequestDto,
                               @RequestParam Long id,Errors errors, @RequestPart(value="file", required=true) MultipartFile file) throws IOException {
        StatusMsgDto statusMsgDto;
        //입력값이 옳지 않을 때
        if (errors.hasErrors()) {
            statusMsgDto = new StatusMsgDto(StatusEnum.STATUS_FAILE, workRequestDto);
        }
        //수정할 작품이 존재할 때
        Optional<Work> work = workService.updateWork(workRequestDto,file);
        if (work.isPresent()) {
            workRequestDto.setImage(fileDir+work.get().getImage());
            statusMsgDto = new StatusMsgDto(StatusEnum.STATUS_SUCCESS, workRequestDto);
        } else {
            statusMsgDto = new StatusMsgDto(StatusEnum.STATUS_FAILE, workRequestDto);
        }

        return statusMsgDto;
    }

    // 저장된 이미지 반환 - 작품 등록할 때 사용
    @PostMapping("/image")
    public String imgUpload(@RequestPart(value="file") MultipartFile file) throws IOException{
        UploadFile uploadFile = imgStore.storeFile(file);
        return uploadFile.getStoredFileName();
    }

    //작품 등록
    @PostMapping(value = "/work/insert", consumes = {"multipart/form-data"})
    public StatusMsgDto saveWork(@Validated @RequestBody WorkRequestDto workRequestDto, Errors errors) throws IOException {
        if(errors.hasErrors()){
            return new StatusMsgDto(StatusEnum.STATUS_FAILE,workRequestDto);
        }else{
            Work work = new Work();
            work.workSaveInfo(workRequestDto);
            workRepository.save(work);
            workRequestDto.setImage(fileDir+workRequestDto.getImage()); // 풀 경로 넣어주기
            return new StatusMsgDto(StatusEnum.STATUS_SUCCESS,workRequestDto);
        }

    }

    //작품 삭제
    @PostMapping("/work/delete")
    public void delete(@RequestParam Long workId){
        Work work = workRepository.findById(workId).orElseThrow(()->
                new NoFoundException("해당 작품을 찾을 수 없습니다."));

        // EC2 image폴더 해당 작품 삭제
        File file = new File(imgStore.getFullPath(work.getImage()));
        if (file.exists()) {
            if (file.delete()) {
            } else {
                throw new NoFoundException("파일 삭제 실패");
            }
        } else {
            throw new NoFoundException("파일이 존재하지 않음");
        }

        workRepository.delete(work);
    }
}
