package com.mycompany.myapp.extended.Services;

import com.mycompany.myapp.domain.enumeration.Importance;
import com.mycompany.myapp.extended.dto.DefaultResponseDTO;
import com.mycompany.myapp.extended.exceptions.ActivityNotFoundException;
import com.mycompany.myapp.service.TimetableQueryService;
import com.mycompany.myapp.service.criteria.TimetableCriteria;
import com.mycompany.myapp.service.dto.TimetableDTO;
import com.mycompany.myapp.service.impl.TimetableServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import tech.jhipster.service.filter.LongFilter;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@Service
public class ActivityImpl {
    @Autowired
    private TimetableServiceImpl timetableService;

    @Autowired
    private TimetableQueryService timetableQueryService;

//    Allows me to create-activity first
    public DefaultResponseDTO<Object> createTable(@RequestBody TimetableDTO timetableDTO){
        timetableService.save(timetableDTO);
        DefaultResponseDTO<Object> message = new DefaultResponseDTO<>();
        message.setStatusCode(201); // 201 for created
        message.setMessage("Timetable has been created successfully");
        message.setData(timetableDTO);
        return message;
    }
    public DefaultResponseDTO<TimetableDTO> deleteTable(Long id){
        DefaultResponseDTO<TimetableDTO> message = new DefaultResponseDTO<>();
        try{
        TimetableDTO timetableDTO = timetableService.findOne(id).orElseThrow(
            ()-> new ActivityNotFoundException("The activity doesn't exist")
        );

        message.setStatusCode(201); // 201 for created
        message.setMessage("Activity has been deleted successfully");
        message.setData(timetableDTO);
        }catch (ActivityNotFoundException ex){
            message.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            message.setMessage(ex.getMessage());

        }
        timetableService.delete(id);
        return message;
    }

//    To display the timetable of the user

    public DefaultResponseDTO<Object> displayTimetable(@PathVariable Long appUserId){
        LongFilter appUserIdFilter = new LongFilter();
        appUserIdFilter.setEquals(appUserId);
        TimetableCriteria timetableCriteria = new TimetableCriteria();
        timetableCriteria.setAppUserId(appUserIdFilter);
        Page<TimetableDTO> timetableDTOPage = timetableQueryService.findByCriteria(timetableCriteria, Pageable.unpaged());
        DefaultResponseDTO<Object> message = new DefaultResponseDTO<>();
        message.setStatusCode(200);
        message.setMessage("Timetable for user " + appUserId);
        message.setData(timetableDTOPage);
        return message;
    }
    public DefaultResponseDTO<Object> displayTimetableByImportance(@PathVariable Importance activityImportance){

        TimetableCriteria.ImportanceFilter appUserImportanceFilter = new TimetableCriteria.ImportanceFilter();
        appUserImportanceFilter.setEquals(activityImportance);
        TimetableCriteria timetableCriteria = new TimetableCriteria();
        timetableCriteria.setLevelOfImportance(appUserImportanceFilter);
        Page<TimetableDTO> timetableDTOPage = timetableQueryService.findByCriteria(timetableCriteria, Pageable.unpaged());
        DefaultResponseDTO<Object> message = new DefaultResponseDTO<>();
        message.setStatusCode(200);
        message.setMessage("LevelOfImportance Data " + activityImportance);
        message.setData(timetableDTOPage);
        return message;
    }




    public DefaultResponseDTO<Object> updateActivity(@PathVariable Long id, @RequestBody TimetableDTO timetableDTO){
        DefaultResponseDTO<Object> message = new DefaultResponseDTO<>();

        try{
        TimetableDTO table = timetableService.findOne(id).orElseThrow(
            () -> new ActivityNotFoundException("Activity with" + id + "doesn't exist")
        );



        table.setActivity(timetableDTO.getActivity());
        table.setEndTime(timetableDTO.getEndTime());
        table.setStartTime(timetableDTO.getStartTime());
        table.setDateOfActivity(timetableDTO.getDateOfActivity());


        table.setDayOfWeek("");
        timetableService.save(table);


        message.setStatusCode(200);
        message.setMessage("Timetable updated successfully");
        message.setData(table);
        return message;
        }catch (ActivityNotFoundException ex){
          message.setStatusCode(500);
          message.setMessage("Something went wrong");
          message.setData(ex);
        }
        return message;
    }



}
