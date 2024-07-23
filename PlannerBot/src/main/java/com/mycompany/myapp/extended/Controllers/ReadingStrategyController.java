package com.mycompany.myapp.extended.Controllers;

import com.mycompany.myapp.extended.Services.ReadingStrategyImplementation;
import com.mycompany.myapp.extended.dto.ReadingStrategyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")

public class ReadingStrategyController {
    @Autowired
    private ReadingStrategyImplementation strategyImplementation;

    @GetMapping("/strategy/{appUserId}")
    public ReadingStrategyDTO displayInfo(@PathVariable Long appUserId) {
        return strategyImplementation.displayInfo(appUserId);
    }

}
