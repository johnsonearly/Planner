package com.mycompany.myapp.extended.Services;

import com.mycompany.myapp.domain.enumeration.Difficulty;
import com.mycompany.myapp.extended.dto.DefaultResponseDTO;
import com.mycompany.myapp.extended.dto.FreeTime;
import com.mycompany.myapp.service.CourseQueryService;
import com.mycompany.myapp.service.TimetableQueryService;
import com.mycompany.myapp.service.criteria.CourseCriteria;
import com.mycompany.myapp.service.criteria.TimetableCriteria;
import com.mycompany.myapp.service.dto.CourseDTO;
import com.mycompany.myapp.service.dto.TimetableDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tech.jhipster.service.filter.LongFilter;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
@Service
public class ReadingPlanServiceImplementation {
//    @Autowired
//    private ReadingStrategyImplementation readingStrategyImplementation;
//    @Autowired
//    private  FreeTimeServiceImplmplementation freeTimeServiceImplmplementation;
//    @Autowired
//    private  CoursesImpl courses;
//
//    @Autowired
//    private CourseQueryService courseQueryService;
//    @Autowired
//    private TimetableQueryService timetableQueryService;
//
//    @Autowired
//    private ActivityImpl activity;

//    public void generateReadingPlan(Long appUserId) {
//        List<FreeTime> freeTimeList = getFreeTime(appUserId);
//        List<CourseDTO> coursesList = getCoursesByAppUserId(appUserId);
//        LocalDate currentDate = LocalDate.now();
//
//        // Debugging: Print sizes of lists
//        System.out.println("Free time list size: " + freeTimeList.size());
//        System.out.println("Courses list size: " + coursesList.size());
//
//        for (int i = 0; i < freeTimeList.size(); i++) {
//            FreeTime freeTime = freeTimeList.get(i);
//            Long hours = getDifferenceInHours(freeTime.getStartTime(), freeTime.getEndTime());
//
//            // Debugging: Print current indices and values
//            System.out.println("Free time index: " + i + ", Hours: " + hours);
//
//            for (int j = 0; j < coursesList.size(); j++) {
//                CourseDTO courseDTO = coursesList.get(j);
//
//                // Debugging: Print current course index and difficulty
//                System.out.println("Course index: " + j + ", Course difficulty: " + courseDTO.getDifficulty());
//
//                TimetableDTO timetableDTO = new TimetableDTO();
//                boolean shouldAdd = false;
//
//                if (hours >= 3 && courseDTO.getDifficulty() == Difficulty.HIGH) {
//                    shouldAdd = true;
//                } else if (hours == 2 && courseDTO.getDifficulty() == Difficulty.MEDIUM) {
//                    shouldAdd = true;
//                } else if (hours < 2 && courseDTO.getDifficulty() == Difficulty.LOW) {
//                    shouldAdd = true;
//                }
//
//                if (shouldAdd) {
//                    timetableDTO.setActivity("Read " + courseDTO.getCourseName());
//                    timetableDTO.setStartTime(freeTime.getStartTime());
//                    timetableDTO.setEndTime(freeTime.getEndTime());
//                    timetableDTO.setDateOfActivity(currentDate);
//                    activity.createTable(timetableDTO);
//                    break; // Move to the next free time slot once an activity is scheduled
//                }
//            }
//        }
//    }
//
//
//
//
//    private List<FreeTime> getFreeTime(Long appUserId){
//
//        LongFilter appUserIdFilter = new LongFilter();
//        appUserIdFilter.setEquals(appUserId);
//        TimetableCriteria timetableCriteria = new TimetableCriteria();
//        timetableCriteria.setAppUserId(appUserIdFilter);
//        Page<TimetableDTO> timetableDTOPage = timetableQueryService.findByCriteria(timetableCriteria, Pageable.unpaged());
//        List<TimetableDTO> timetableDTOList = timetableDTOPage.stream().toList();
//        ZonedDateTime startOfDay = ZonedDateTime.parse("2024-07-20T00:00:00Z");
//        ZonedDateTime endOfDay = ZonedDateTime.parse("2024-07-20T23:59:59Z");
//        return calculateFreeTime(timetableDTOList,startOfDay,endOfDay);
//    }
//    private List<FreeTime> calculateFreeTime(List<TimetableDTO> timetableDTOList, ZonedDateTime startTime, ZonedDateTime endTime) {
//        // Check if timetable is null or empty
//        DefaultResponseDTO<Object> message = new DefaultResponseDTO<>();
//        List<FreeTime> freeTimes = new ArrayList<>();
//        if (timetableDTOList == null || timetableDTOList.isEmpty()) {
//            freeTimes.add(new FreeTime(startTime, endTime));
//            return freeTimes;
//        }
//
//        // Sort the timetable by start time
//        List<TimetableDTO> sortedTimetable = new ArrayList<>(timetableDTOList);
//        sortedTimetable.sort(Comparator.comparing(TimetableDTO::getStartTime));
//
//        // Check for free time before the first activity
//        if (startTime.isBefore(sortedTimetable.get(0).getStartTime())) {
//            freeTimes.add(new FreeTime(startTime, sortedTimetable.get(0).getStartTime()));
//        }
//
//        // Find the gaps between activities
//        for (int i = 0; i < sortedTimetable.size() - 1; i++) {
//            ZonedDateTime endCurrent = sortedTimetable.get(i).getEndTime();
//            ZonedDateTime startNext = sortedTimetable.get(i + 1).getStartTime();
//
//            if (endCurrent.isBefore(startNext)) {
//                freeTimes.add(new FreeTime(endCurrent, startNext));
//            }
//        }
//
//        // Check for free time after the last activity
//        if (endTime.isAfter(sortedTimetable.get(sortedTimetable.size() - 1).getEndTime())) {
//            freeTimes.add(new FreeTime(sortedTimetable.get(sortedTimetable.size() - 1).getEndTime(), endTime));
//        }
//
//
//        return freeTimes;
//    }
//    private List<CourseDTO> getCoursesByAppUserId(Long appUserId) {
//            LongFilter appUserIdFilter = new LongFilter();
//            appUserIdFilter.setEquals(appUserId);
//            CourseCriteria criteria = new CourseCriteria();
//            criteria.setAppUserId(appUserIdFilter);
//            Sort sort = Sort.by(Sort.Direction.ASC, "courseName");
//            Pageable pageable = PageRequest.of(0, 10, sort);
//            Page<CourseDTO> courseDTOPage = courseQueryService.findByCriteria(criteria, pageable);
//        return courseDTOPage.stream().toList();
//
//
//    }
//    private long getDifferenceInHours(ZonedDateTime startTime, ZonedDateTime endTime) {
//        Duration duration = Duration.between(startTime, endTime);
//        return duration.toHours();}
}
