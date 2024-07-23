package com.mycompany.myapp.extended.Controllers;

import com.mycompany.myapp.extended.Services.ReadingPlanServiceImplementation;
import com.mycompany.myapp.extended.dto.FreeTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class GenerateReadingPlan {
    @Autowired
    private ReadingPlanServiceImplementation readingPlanServiceImplementation;

    @GetMapping("/generate/{appUserId}")
    public void generateReadingPlan(Long appUserId){
        readingPlanServiceImplementation.generateReadingPlan(appUserId);
    }
}
