package com.hanghae.gallery.controller;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.hanghae.gallery.dto.FollowDto;
import com.hanghae.gallery.dto.StatusMsgDto;
import com.hanghae.gallery.dto.WorkRequestDto;
import com.hanghae.gallery.exception.NoFoundException;
import com.hanghae.gallery.exception.UserSignException;
import com.hanghae.gallery.model.*;
import com.hanghae.gallery.repository.ArtistRepository;
import com.hanghae.gallery.repository.WorkRepository;
import com.hanghae.gallery.security.UserDetailsImpl;
import com.hanghae.gallery.service.WorkService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class WorkRestController {

    private final WorkRepository workRepository;
    private final WorkService workService;
    private final ArtistRepository artistRepository;
    private final AmazonS3Client amazonS3Client;
    private final String bucket = "sollertia";


    public WorkRestController(WorkRepository workRepository, WorkService workService,
                              ArtistRepository artistRepository, AmazonS3Client amazonS3Client) {

        this.workRepository = workRepository;
        this.workService = workService;
        this.artistRepository = artistRepository;
        this.amazonS3Client = amazonS3Client;
    }

    //작품 상세
    @GetMapping("/work/detail")
    public FollowDto getWork(@RequestParam Long workId, @AuthenticationPrincipal UserDetailsImpl userDetails) { // User user부분 나중에 Userdetails로 변경
        User user = userDetails.getUser();
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
                               @RequestParam Long id, Errors errors) {
        StatusMsgDto statusMsgDto;
        //입력값이 옳지 않을 때
        if (errors.hasErrors()) {
            statusMsgDto = new StatusMsgDto(StatusEnum.STATUS_FAILE, workRequestDto);
        }
        // 수정할 작품이 존재할 때
        // 수정하기 전에 기존에 등록한 사진 S3에서 삭제하기
        // 삭제할 기존 이미지 경로 가져오기
        Work findWork = workRepository.findById(id).orElseThrow(()->
                new NoFoundException("해당 작품을 찾을 수 없습니다."));
        // S3에서 등록한 사진 삭제
        deleteS3(findWork.getImage());

        Optional<Work> work = workService.updateWork(workRequestDto);
        if (work.isPresent()) {
            workRequestDto.setImage(work.get().getImage());
            statusMsgDto = new StatusMsgDto(StatusEnum.STATUS_SUCCESS, workRequestDto);
        } else {
            statusMsgDto = new StatusMsgDto(StatusEnum.STATUS_FAILE, workRequestDto);
        }

        return statusMsgDto;
    }

    //작품 등록
    @PostMapping(value = "/work/insert")
    public StatusMsgDto saveWork(@Validated @RequestBody WorkRequestDto workRequestDto,
             @AuthenticationPrincipal UserDetailsImpl userDetails , Errors errors) {


        if(errors.hasErrors()){
            return new StatusMsgDto(StatusEnum.STATUS_FAILE,workRequestDto);
        }else{
            Work work = new Work();
            work.workSaveInfo(workRequestDto);

            // 작품등록은 현재 로그인한 작가만 작성할 수 있기 때문에 현재 로그인한 작가의 id를 가져와서 Work의 artistID에 등록해준다.
            //현재 userDetails 에 작가의 id값이 없어서 찾아온다. 리팩토링이 필요한 부분
            Artist arst = artistRepository.findByUsername(userDetails.getUsername()).orElseThrow(
                    ()-> new UserSignException("해당 작가가 없습니다.")
            );
            work.setArtistId(arst.getId());
            workRepository.save(work);
            return new StatusMsgDto(StatusEnum.STATUS_SUCCESS,workRequestDto);
        }

    }

    //작품 삭제
    @PostMapping("/work/delete")
    public void delete(@RequestParam Long workId){
        Work work = workRepository.findById(workId).orElseThrow(()->
                new NoFoundException("해당 작품을 찾을 수 없습니다."));

        // 삭제할 작품의 S3 원본 삭제하기
        deleteS3(work.getImage());

        workRepository.delete(work);
    }

    // S3에 업로드한 사진 삭제
    public void deleteS3(@RequestParam String imageName){
        // imageName split
        //https://S3 버킷 URL/버킷에 생성한 폴더명/이미지이름
        String keyName = imageName.split("/")[4]; // 이미지이름만 추출

        try {
            amazonS3Client.deleteObject(
                    bucket + "/image",
                    keyName
            );

        }catch (AmazonServiceException e){
            e.printStackTrace();
            throw new AmazonServiceException(e.getMessage());
        }
    }
}
