package com.mycompany.myapp.extended.Controllers;

import com.mycompany.myapp.extended.Services.CoursesImpl;
import com.mycompany.myapp.extended.dto.DefaultResponseDTO;
import com.mycompany.myapp.service.dto.CourseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class CoursesController {
    @Autowired
    private CoursesImpl courses;

    @PostMapping("/add-course/{appUserId}")
    public DefaultResponseDTO<Object> addCourses(@RequestBody CourseDTO courseDTO, @PathVariable Long appUserId){
        return courses.addCourse(courseDTO,appUserId);
    }
    @GetMapping("/get-courses-by/{appUserId}")
    public DefaultResponseDTO<Object> getCoursesByAppUserId(@PathVariable Long appUserId){
        return courses.getCoursesByAppUserId(appUserId);
   }
   @DeleteMapping("/delete-course/{appUserId}")
    public  DefaultResponseDTO<Object> deleteCourse(@PathVariable Long appUserId){
        return courses.deleteCourseService(appUserId);
   }

}
