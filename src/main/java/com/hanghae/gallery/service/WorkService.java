package com.hanghae.gallery.service;

import com.hanghae.gallery.dto.WorkRequestDto;
import com.hanghae.gallery.exception.NoFoundException;
import com.hanghae.gallery.model.*;
import com.hanghae.gallery.repository.FollowRepository;
import com.hanghae.gallery.repository.WorkRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class WorkService {

    private final WorkRepository workRepository;


    private final FollowRepository followRepository;


    public WorkService(WorkRepository workRepository,FollowRepository followRepository){
        this.followRepository = followRepository;

        this.workRepository = workRepository;
    }

    //작품 수정
    @Transactional
    public void  updateWork(WorkRequestDto workRequestDto,Long id){
        Work work = workRepository.findById(id).orElseThrow(()->
                new NoFoundException("해당 작품이 없습니다."));
        work.workSaveInfo(workRequestDto);
    }

    // 유저 팔로우 목록에 해당 작가가 있는 지 판단 후 있으면 유저와 작가를, 없으면  null을 리턴
    public Optional<Follow> getUserAndArtist(Artist artist, User user) {

        return Optional.ofNullable(  followRepository.findByArtistAndUser(artist, user)  );
    }



    public FollowEnum codeSetHeandler(Optional<Follow> follow, User user){

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
