package com.hanghae.gallery.controller;

import com.hanghae.gallery.dto.WorkRequestDto;
import com.hanghae.gallery.dto.WorkResponseDto;
import com.hanghae.gallery.model.Work;
import com.hanghae.gallery.repository.WorkRepository;
import com.hanghae.gallery.service.WorkService;
import org.springframework.web.bind.annotation.*;

@RestController
public class WorkController {

    private final WorkRepository workRepository;
    private final WorkService workService;

    public WorkController(WorkRepository workRepository,WorkService workService){
        this.workRepository = workRepository;
        this.workService = workService;
    }


    //작품 상세
    @GetMapping("/work/detail")
    public WorkResponseDto getWork(@RequestParam Long id){
        Work work = workRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("해당 작품을 찾을 수 없습니다."));

        return new WorkResponseDto(work);

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
