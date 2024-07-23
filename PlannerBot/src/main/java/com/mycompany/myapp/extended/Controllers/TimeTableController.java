package com.mycompany.myapp.extended.Controllers;

import com.mycompany.myapp.domain.Timetable;
import com.mycompany.myapp.domain.enumeration.Importance;
import com.mycompany.myapp.extended.Services.ActivityImpl;
import com.mycompany.myapp.extended.Services.FreeTimeServiceImplmplementation;
import com.mycompany.myapp.extended.dto.DefaultResponseDTO;
import com.mycompany.myapp.service.TimetableQueryService;
import com.mycompany.myapp.service.criteria.TimetableCriteria;
import com.mycompany.myapp.service.dto.TimetableDTO;
import com.mycompany.myapp.service.impl.TimetableServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
public class TimeTableController {
//    Dependency Injection
    @Autowired
    private ActivityImpl activity;
    @Autowired
    private FreeTimeServiceImplmplementation freeTimeServiceImplmplementation;

    @PostMapping("/create-timetable")
    public DefaultResponseDTO<Object> createNewTable(@RequestBody TimetableDTO timetableDTO){
       return activity.createTable(timetableDTO);
    }

    @GetMapping("/get-timetable/{appUserId}")
    public DefaultResponseDTO<Object> showTimetable(@PathVariable Long appUserId){
        return activity.displayTimetable(appUserId);
    }

    @PutMapping("/update-activity/{id}")
    public DefaultResponseDTO<Object> updateActivityById(@PathVariable Long id, @RequestBody TimetableDTO timetableDTO){
        return activity.updateActivity(id,timetableDTO);
    }
    @GetMapping("/sort-by-importance/{levelOfImportance}")
    public DefaultResponseDTO<Object> showLevelOfImportance(@PathVariable Importance levelOfImportance){
        return  activity.displayTimetableByImportance(levelOfImportance);
    }
    @DeleteMapping("/delete-activity/{id}")
    public DefaultResponseDTO<TimetableDTO> deleteCourse(@PathVariable Long id){
        return  activity.deleteTable(id);

    }
    @GetMapping("/free-time/{appUserId}")
    public DefaultResponseDTO<Object> showLevelOfImportance(@PathVariable Long appUserId){
        return  freeTimeServiceImplmplementation.getFreeTime(appUserId);
    }





}
