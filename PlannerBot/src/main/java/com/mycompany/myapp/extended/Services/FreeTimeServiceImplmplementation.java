package com.mycompany.myapp.extended.Services;


import com.mycompany.myapp.extended.dto.DefaultResponseDTO;
import com.mycompany.myapp.extended.dto.FreeTime;
import com.mycompany.myapp.service.TimetableQueryService;
import com.mycompany.myapp.service.criteria.TimetableCriteria;

import com.mycompany.myapp.service.dto.TimetableDTO;
import com.mycompany.myapp.service.impl.FreeTimeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tech.jhipster.service.filter.LongFilter;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class FreeTimeServiceImplmplementation {
    @Autowired
    private FreeTimeServiceImpl freeTimeService;
    @Autowired
    private ActivityImpl activity;

    @Autowired
    TimetableQueryService timetableQueryService;

    public DefaultResponseDTO<Object> getFreeTime(Long appUserId){

        LongFilter appUserIdFilter = new LongFilter();
        appUserIdFilter.setEquals(appUserId);
        TimetableCriteria timetableCriteria = new TimetableCriteria();
        timetableCriteria.setAppUserId(appUserIdFilter);
        Page<TimetableDTO> timetableDTOPage = timetableQueryService.findByCriteria(timetableCriteria, Pageable.unpaged());
        List<TimetableDTO> timetableDTOList = timetableDTOPage.stream().toList();
        ZonedDateTime startOfDay = ZonedDateTime.parse("2024-07-20T00:00:00Z");
        ZonedDateTime endOfDay = ZonedDateTime.parse("2024-07-20T23:59:59Z");
        return calculateFreeTime(timetableDTOList,startOfDay,endOfDay);
    }
    private DefaultResponseDTO<Object> calculateFreeTime(List<TimetableDTO> timetableDTOList, ZonedDateTime startTime, ZonedDateTime endTime) {
        // Check if timetable is null or empty
        DefaultResponseDTO<Object> message = new DefaultResponseDTO<>();
        List<FreeTime> freeTimes = new ArrayList<>();
        if (timetableDTOList == null || timetableDTOList.isEmpty()) {
            freeTimes.add(new FreeTime(startTime, endTime));
            message.setStatusCode(200);
            message.setMessage("You have no activity today");
            message.setData(freeTimes);
            return message;
        }

        // Sort the timetable by start time
        List<TimetableDTO> sortedTimetable = new ArrayList<>(timetableDTOList);
        sortedTimetable.sort(Comparator.comparing(TimetableDTO::getStartTime));

        // Check for free time before the first activity
        if (startTime.isBefore(sortedTimetable.get(0).getStartTime())) {
            freeTimes.add(new FreeTime(startTime, sortedTimetable.get(0).getStartTime()));
        }

        // Find the gaps between activities
        for (int i = 0; i < sortedTimetable.size() - 1; i++) {
            ZonedDateTime endCurrent = sortedTimetable.get(i).getEndTime();
            ZonedDateTime startNext = sortedTimetable.get(i + 1).getStartTime();

            if (endCurrent.isBefore(startNext)) {
                freeTimes.add(new FreeTime(endCurrent, startNext));
            }
        }

        // Check for free time after the last activity
        if (endTime.isAfter(sortedTimetable.get(sortedTimetable.size() - 1).getEndTime())) {
            freeTimes.add(new FreeTime(sortedTimetable.get(sortedTimetable.size() - 1).getEndTime(), endTime));
        }

        message.setStatusCode(200);
        message.setMessage("Free Times");
        message.setData(freeTimes);
        return message;
    }
}
