package com.mycompany.myapp.extended.Services;

import com.mycompany.myapp.domain.enumeration.AttentionSpan;
import com.mycompany.myapp.domain.enumeration.Chronotype;
import com.mycompany.myapp.domain.enumeration.ReadingStrategy;
import com.mycompany.myapp.domain.enumeration.ReadingType;
import com.mycompany.myapp.extended.dto.DefaultResponseDTO;
import com.mycompany.myapp.service.dto.AppUserDTO;
import com.mycompany.myapp.service.impl.AppUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service


public class AppUserServiceImplementation {

    @Autowired
    private AppUserServiceImpl userService;
    public DefaultResponseDTO<AppUserDTO> createUser(@RequestBody AppUserDTO appUserDTO){
        assignReadingStrategy(appUserDTO);
        userService.save(appUserDTO);
        DefaultResponseDTO<AppUserDTO> message = new DefaultResponseDTO<>();

        message.setStatusCode(201);
        message.setMessage("User has been successfully created");
        message.setData(appUserDTO);
        return  message;
    }
    private void assignReadingStrategy( AppUserDTO appUserDTO){
//        Get each condition for the reading strategy
        if((appUserDTO.getChronotype() == Chronotype.MORNING)&&(appUserDTO.getAttentionSpan() == AttentionSpan.SHORT)){
            appUserDTO.setReadingStrategy(ReadingStrategy.SPACED_REPETITION);
        } else if ((appUserDTO.getReadingType() == ReadingType.INTENSIVE)&&(appUserDTO.getChronotype() == Chronotype.NIGHT)) {
            appUserDTO.setReadingStrategy(ReadingStrategy.ACTIVE_RECALL);

        } else if (appUserDTO.getReadingType() == ReadingType.EXTENSIVE) {
            appUserDTO.setReadingStrategy(ReadingStrategy.SQ3R_METHOD);
        }
        else{
            appUserDTO.setReadingStrategy(ReadingStrategy.POMODORO_TECHNIQUE);
        }

    }
}
