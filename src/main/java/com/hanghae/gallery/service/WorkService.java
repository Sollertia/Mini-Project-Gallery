package com.hanghae.gallery.service;

import com.hanghae.gallery.dto.WorkRequestDto;
import com.hanghae.gallery.model.Work;
import com.hanghae.gallery.repository.WorkRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class WorkService {

    private final WorkRepository workRepository;
    public WorkService(WorkRepository workRepository){
        this.workRepository = workRepository;
    }

    //작품 수정
    @Transactional
    public void  updateWork(WorkRequestDto workRequestDto,Long id){
        Work work = workRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 작품이 없습니다."));
        work.workSaveInfo(workRequestDto);
    }
}
