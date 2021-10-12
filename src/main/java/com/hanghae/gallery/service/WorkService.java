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
    public Optional<Follow> getUserAndArtist(Long workId, User user) {
        Work work = workRepository.findById(workId).orElseThrow(
                ()-> new IllegalArgumentException("해당 작품을 찾을 수 없습니다."));
        Long artistId =work.getArtistId();
        Artist artist = artistRepository.getById(artistId);

        return Optional.ofNullable(followRepository.findByArtistAndUser(artist, user));

    }
}
