//package com.hanghae.gallery.testdata;
//
//import com.hanghae.gallery.dto.WorkRequestDto;
//import com.hanghae.gallery.model.Artist;
//import com.hanghae.gallery.model.RoleEnum;
//import com.hanghae.gallery.model.Work;
//import com.hanghae.gallery.repository.ArtistRepository;
//import com.hanghae.gallery.repository.WorkRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//
//@Component
//public class TestDataRunner implements ApplicationRunner {
//
//    @Autowired
//    ArtistRepository artistRepository;
//
//    @Autowired
//    WorkRepository workRepository;
//
//
//   @Override
//    public void run(ApplicationArguments args) throws Exception {
//
//
//       Artist testArtist = new Artist("username", "password", "nickname", RoleEnum.ARTIST);
//       testArtist = artistRepository.save(testArtist);
//
//       for(int i = 0; i<20; i++){
//           createTestWork(testArtist);
//       }
//
//
//
//
//
//      }
//
//    private void createTestWork(Artist testArtist) {
//
//        Work work = new Work();
//
//        work.setWorkDesc("설명");
//        work.setWorkMade("제작일");
//        work.setWorkSize("작품크기");
//        work.setWorkTitle("제목");
//        work.setImage("https://sollertia.s3.ap-northeast-2.amazonaws.com/image/2002f4b1-7e90-45fd-8078-90d5ecd5d6cc111.png");
//        work.setWorkMaterial("재료");
//        work.setArtistId(testArtist.getId());
//        workRepository.save(work);
//
//    }
//
//}