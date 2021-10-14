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
        if (errors.hasErrors()){
            return new StatusMsgDto(StatusEnum.STATUS_FAILE,workRequestDto);
        }else{
            workService.updateWork(workRequestDto,id, file);
            return new StatusMsgDto(StatusEnum.STATUS_SUCCESS,workRequestDto);
        }


    }

    //작품 등록
    @PostMapping("/work/insert")
    public StatusMsgDto saveWork(@Validated @RequestPart(value="key", required=false) WorkRequestDto workRequestDto, Errors errors,
                                 @RequestPart(value="file", required=true) MultipartFile file) throws IOException {
        if(errors.hasErrors()){
            return new StatusMsgDto(StatusEnum.STATUS_FAILE,workRequestDto);
        }else{
            // 이미지 EC2 image폴더에 저장
            UploadFile uploadFile = imgStore.storeFile(file);

            Work work = new Work();
            // 작품 이름 저장
            workRequestDto.setImage(uploadFile.getStoredFileName());
            work.workSaveInfo(workRequestDto);
            workRepository.save(work);
            workRequestDto.setImage(fileDir+uploadFile.getStoredFileName());
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
