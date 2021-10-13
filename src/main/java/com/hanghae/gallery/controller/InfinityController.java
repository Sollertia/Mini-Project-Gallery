package com.hanghae.gallery.controller;

import com.hanghae.gallery.dto.InfinityResponseDto;
import com.hanghae.gallery.model.Work;
import com.hanghae.gallery.service.InfinityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class InfinityController {

    private final InfinityService infinityService;

    // 보여주는 작품의 개수 지정
    public static final int PageSize = 6;
    // 응답 list 객체 생성
    List<InfinityResponseDto> data = new ArrayList<>();


    // 모든 작품을 보여주는 메인 페이지 컨트롤러 - 최초 접근시 size 값을 받아 데이터 가공
    @GetMapping("/")
    public List<InfinityResponseDto> main(@PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = PageSize) Pageable pageable){

        Page<Work> list = infinityService.main(pageable);

        for (Work ls : list.getContent()){
                data.add(new InfinityResponseDto(ls));
        }
        return data;
    }

    // 메인페이지에서 무한스크롤 발생시 반응하는 컨트롤러 - 현재 마지막 작품 id 와 size 값을 받아 데이터 가공
    @GetMapping("/infinity")
    public List<InfinityResponseDto> mainInfinity(@RequestParam Long lastWorkId,
                                            @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = PageSize) Pageable pageable){

        Page<Work> list = infinityService.mainInfinity(lastWorkId,pageable);
        for (Work ls : list.getContent()){
            data.add(new InfinityResponseDto(ls));
        }
        return data;
    }

    // 해당 작가 페이지 접근 시 반응하는 컨트롤러 - 해당 작가 id, size 값을 받아 데이터 가공
    @GetMapping("/artist")
    public List<InfinityResponseDto> artist(@RequestParam Long artistId,
                                      @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = PageSize) Pageable pageable){

        Page<Work> list = infinityService.artist(artistId,pageable);
        for (Work ls : list.getContent()){
            data.add(new InfinityResponseDto(ls));
        }
        return data;
    }

    // 작가 페이지에서 무한스크롤 발생시 반응하는 컨트롤러 - 해당 작가 id, 현재 마지막 작품 id , size 값을 받아 데이터 가공
    @GetMapping("/artist/infinity")
    public List<InfinityResponseDto> artistInfinity(@RequestParam Long artistId, @RequestParam Long lastWorkId,
                                              @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = PageSize) Pageable pageable){

        Page<Work> list = infinityService.artistInfinity(artistId,lastWorkId,pageable);
        for (Work ls : list.getContent()){
            data.add(new InfinityResponseDto(ls));
        }
        return data;
    }




}
