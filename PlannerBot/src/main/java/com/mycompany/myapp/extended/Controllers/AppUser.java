package com.mycompany.myapp.extended.Controllers;

import com.mycompany.myapp.extended.Services.AppUserServiceImplementation;
import com.mycompany.myapp.extended.dto.DefaultResponseDTO;
import com.mycompany.myapp.service.dto.AppUserDTO;
import com.mycompany.myapp.service.impl.AppUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class AppUser {

    @Autowired
    private AppUserServiceImpl userService;

    @Autowired
    private AppUserServiceImplementation implementation;

//    Endpoint to create a user
    @PostMapping("/create-user")
    public DefaultResponseDTO<AppUserDTO> createNewUser(@RequestBody AppUserDTO appUserDTO){

     return implementation.createUser(appUserDTO);
    }
    @GetMapping("/get-user/{id}")
    public ResponseEntity<Optional<AppUserDTO>> getUser(@PathVariable Long id){
          return ResponseEntity.status(HttpStatus.OK).body(userService.findOne(id));
        }
    }



