package com.cigarette.user_service.controller;

import com.cigarette.user_service.dto.UserRequestDTO;
import com.cigarette.user_service.dto.UserResponseDTO;
import com.cigarette.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(path = "/me")
    public ResponseEntity<UserResponseDTO> createUser(
            @AuthenticationPrincipal Jwt jwt
    ){
        UserResponseDTO user = userService.createUser(jwt);
        return ResponseEntity.ok(user);
    }

    @PutMapping(path = "/me")
    public ResponseEntity<UserResponseDTO> updateUser(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody UserRequestDTO dto
    )
    {
        UserResponseDTO user = userService.updateUser(jwt, dto);
        return ResponseEntity.ok(user);
    }

    @GetMapping(path = "/{uuid}")
    public ResponseEntity<UserResponseDTO> getUserByID(@PathVariable UUID uuid){
        UserResponseDTO user = userService.getUserById(uuid);
        return ResponseEntity.ok(user);
    }

}
