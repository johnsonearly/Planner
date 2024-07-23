package com.mycompany.myapp.extended.Services;


import com.mycompany.myapp.extended.dto.DefaultResponseDTO;
import com.mycompany.myapp.extended.exceptions.ActivityNotFoundException;
import com.mycompany.myapp.service.AppUserQueryService;
import com.mycompany.myapp.service.CourseQueryService;
import com.mycompany.myapp.service.criteria.AppUserCriteria;
import com.mycompany.myapp.service.criteria.CourseCriteria;
import com.mycompany.myapp.service.dto.AppUserDTO;
import com.mycompany.myapp.service.dto.CourseDTO;
import com.mycompany.myapp.service.impl.AppUserServiceImpl;
import com.mycompany.myapp.service.impl.CourseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tech.jhipster.service.filter.LongFilter;

@Service
@Slf4j
public class CoursesImpl {
    @Autowired
    private CourseServiceImpl courseService;
    @Autowired
    private AppUserServiceImpl appUserService;
    @Autowired
    private AppUserQueryService appUserQueryService;
    @Autowired
    private CourseQueryService courseQueryService;

    public DefaultResponseDTO<Object> getCoursesByAppUserId(Long appUserId) {
        DefaultResponseDTO<Object> message = new DefaultResponseDTO<>();
        try {
            LongFilter appUserIdFilter = new LongFilter();
            appUserIdFilter.setEquals(appUserId);
            CourseCriteria criteria = new CourseCriteria();
            criteria.setAppUserId(appUserIdFilter);
            Sort sort = Sort.by(Sort.Direction.ASC, "courseName");
            Pageable pageable = PageRequest.of(0, 10, sort);
            Page<CourseDTO> courseDTOPage = courseQueryService.findByCriteria(criteria, pageable);
//          To set the response message
            message.setStatusCode(200);
            message.setMessage("Courses for AppUser" + appUserId);
            message.setData(courseDTOPage);
        }catch (Exception ex){
            message.setMessage(ex.getMessage());
            message.setStatusCode(500);
        }

        return message;
    }
//    Deleting a course
    public DefaultResponseDTO<Object> deleteCourseService(Long id) {
        DefaultResponseDTO<Object> message = new DefaultResponseDTO<>();
        CourseDTO course = courseService.findOne(id).orElseThrow(
            () -> new ActivityNotFoundException("Course Not Found")

        );
        message.setStatusCode(200);
        message.setMessage("Course has been Deleted");
        message.setData(course);
        courseService.delete(id);
        return message;
    }
//    Add a course
    public DefaultResponseDTO<Object> addCourse(CourseDTO courseDTO,Long appUserId) {


        DefaultResponseDTO<Object> message = new DefaultResponseDTO<>();
        try {
            LongFilter appUserIdFilter = new LongFilter();
            appUserIdFilter.setEquals(appUserId);
            AppUserCriteria appUserCriteria = new AppUserCriteria();
            appUserCriteria.setAppUserId(appUserIdFilter);
            Page<AppUserDTO> appUserExists = appUserQueryService.findByCriteria(appUserCriteria,Pageable.unpaged());

            System.out.println("Is empty" + appUserExists.getContent().isEmpty());
            if(appUserExists.getContent().isEmpty()){
                message.setStatusCode(500);
                message.setMessage("Invalid ID");
                return message;
            }
            courseDTO.setAppUserId(appUserId);
            message.setStatusCode(200);
            message.setMessage("Course has been Added Successfully");
            message.setData(courseDTO);
            courseService.save(courseDTO);

        }catch (Exception exception){
            message.setStatusCode(500);
            message.setMessage(exception.getMessage());

        }
        return  message;
    }

}
