package com.hanghae.gallery.service;

import com.hanghae.gallery.dto.WorkRequestDto;
import com.hanghae.gallery.exception.NoFoundException;
import com.hanghae.gallery.model.*;
import com.hanghae.gallery.repository.FollowRepository;
import com.hanghae.gallery.repository.WorkRepository;
import com.hanghae.gallery.util.ImgStore;
import com.hanghae.gallery.util.UploadFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
public class WorkService {

    private final WorkRepository workRepository;
    private final FollowRepository followRepository;
    private final ImgStore imgStore;


    public WorkService(WorkRepository workRepository,FollowRepository followRepository,ImgStore imgStore){
        this.followRepository = followRepository;
        this.workRepository = workRepository;
        this.imgStore = imgStore;
    }

    //작품 수정
    @Transactional
    public void updateWork(WorkRequestDto workRequestDto, Long id, MultipartFile img) throws IOException {
        Work work = workRepository.findById(id).orElseThrow(()->
                new NoFoundException("해당 작품이 없습니다."));

        // 기존 작품 삭제
        File file = new File(imgStore.getFullPath(work.getImage()));
        if (file.exists()) {
            if (file.delete()) {
            } else {
                throw new NoFoundException("파일 삭제 실패");
            }
        } else {
            throw new NoFoundException("파일이 존재하지 않음");
        }

        // 새롭게 수정된 작품 등록
        UploadFile uploadFile = imgStore.storeFile(img);
        // 새롭게 수정된 작품 이름 DB저장
        workRequestDto.setImage(uploadFile.getStoredFileName());
        work.workSaveInfo(workRequestDto);
    }

    // 유저 팔로우 목록에 해당 작가가 있는 지 판단 후 있으면 유저와 작가를, 없으면  null을 리턴
    public Optional<Follow> getUserAndArtist(Artist artist, User user) {
        return Optional.ofNullable(followRepository.findByArtistAndUser(artist, user));
    }

    public FollowEnum codeSetHandler(Optional<Follow> follow, User user){
        if (user == null) { //비로그인 유저
            //("N","false")
            return FollowEnum.NON_USER_UNFOLLOW;
        }else if (follow.isPresent()) {//값이 있으면
            //("Y","true")
            return FollowEnum.USER_FOLLOW;
        } else { // 값이 null이면
            //("N","true")
            return FollowEnum.USER_UNFOLLOW;
        }
    }
}
