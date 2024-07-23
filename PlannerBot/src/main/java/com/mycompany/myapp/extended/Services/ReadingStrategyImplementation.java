package com.mycompany.myapp.extended.Services;

import com.mycompany.myapp.domain.enumeration.ReadingStrategy;
import com.mycompany.myapp.extended.dto.ReadingStrategyDTO;
import com.mycompany.myapp.service.AppUserQueryService;
import com.mycompany.myapp.service.criteria.AppUserCriteria;
import com.mycompany.myapp.service.dto.AppUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tech.jhipster.service.filter.LongFilter;


@Service
public class ReadingStrategyImplementation {
    @Autowired
    private AppUserQueryService appUserQueryService;
    public ReadingStrategyDTO displayInfo(Long appUserId){
        LongFilter appUserIdFilter = new LongFilter();
        appUserIdFilter.setEquals(appUserId);
        AppUserCriteria appUserCriteria = new AppUserCriteria();
        appUserCriteria.setAppUserId(appUserIdFilter);
        Page<AppUserDTO> appUserExists = appUserQueryService.findByCriteria(appUserCriteria, Pageable.unpaged());
        AppUserDTO appUserDTO = appUserExists.getContent().get(0);
        if(appUserDTO == null){
           return null;
       }
        ReadingStrategyDTO readingStrategyDTO = new ReadingStrategyDTO();
       if(appUserDTO.getReadingStrategy() == ReadingStrategy.ACTIVE_RECALL){
           readingStrategyDTO.setStrategy(ReadingStrategy.ACTIVE_RECALL);
           readingStrategyDTO.setDescription("Active Recall is the practice of actively trying to" +
                     " remember information without looking at the material. " +
                     "This technique helps strengthen memory by forcing the brain to retrieve information." +
                    "After reading a section, close the book and try to recall the main points or " +
                     "explain the material to someone else.");
           return readingStrategyDTO;
       } else if (appUserDTO.getReadingStrategy() == ReadingStrategy.SQ3R_METHOD) {
           readingStrategyDTO.setStrategy(ReadingStrategy.SQ3R_METHOD);
           readingStrategyDTO.setDescription(" SQ3R stands for Survey, Question, Read, Recite, and Review. This structured approach to reading helps improve comprehension and retention by engaging with the text in multiple ways.");
           return readingStrategyDTO;

       } else if (appUserDTO.getReadingStrategy() == ReadingStrategy.POMODORO_TECHNIQUE) {
           readingStrategyDTO.setStrategy(ReadingStrategy.POMODORO_TECHNIQUE);
           readingStrategyDTO.setDescription("Set a timer for 25 minutes and focus on your reading or study task. When the timer goes off, take a 5-minute break. After four Pomodoro sessions, take a longer break (15-30 minutes).");
           return  readingStrategyDTO;
       }
       else{
           readingStrategyDTO.setStrategy(ReadingStrategy.SPACED_REPETITION);
           readingStrategyDTO.setDescription("Spaced Repetition involves reviewing material at increasing intervals over time. This technique is based on the principle that information is more easily remembered if it's studied a few times over a long period rather than crammed in a short period.");
           return readingStrategyDTO;

       }
    }

}
