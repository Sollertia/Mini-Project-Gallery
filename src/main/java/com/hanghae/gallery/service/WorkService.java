package com.hanghae.gallery.service;

import com.hanghae.gallery.dto.WorkRequestDto;
import com.hanghae.gallery.model.Artist;
import com.hanghae.gallery.model.Follow;
import com.hanghae.gallery.model.User;
import com.hanghae.gallery.model.Work;
import com.hanghae.gallery.repository.ArtistRepository;
import com.hanghae.gallery.repository.FollowRepository;
import com.hanghae.gallery.repository.UserRepository;
import com.hanghae.gallery.repository.WorkRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class WorkService {

    private final WorkRepository workRepository;
    private final ArtistRepository artistRepository;
    private final FollowRepository followRepository;


    public WorkService(ArtistRepository artistRepository,WorkRepository workRepository,FollowRepository followRepository){
        this.followRepository = followRepository;
        this.artistRepository = artistRepository;
        this.workRepository = workRepository;
    }

    //작품 수정
    @Transactional
    public void  updateWork(WorkRequestDto workRequestDto,Long id){
        Work work = workRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 작품이 없습니다."));
        work.workSaveInfo(workRequestDto);
    }

    // 유저 팔로우 목록에 해당 작가가 있는 지 판단 후 있으면 유저와 작가를, 없으면  null을 리턴
    //팔로우 리스트에서 유저랑 워크아이디를 찾음
    public Optional<Follow> getUserAndArtist(Long workId, User user) {
        // 워크레포지토리에서 워크 아이디를 찾고 없으면 예외처리
        Work work = workRepository.findById(workId).orElseThrow(
                ()-> new IllegalArgumentException("해당 작품을 찾을 수 없습니다."));
        // 워크레포지토리의 아티스트 아이디를 찾음
        Long artistId =work.getArtistId();
        // 아티스트는 아티스트 레포지토리에서 찾은 아티스트 아이디
        Artist artist = artistRepository.getById(artistId);
        // 팔로우
        return Optional.ofNullable(followRepository.findByArtistAndUser(artist, user));

    }
}
