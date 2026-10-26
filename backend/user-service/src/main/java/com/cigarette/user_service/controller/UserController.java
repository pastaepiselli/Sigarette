package com.cigarette.user_service.controller;

import com.cigarette.user_service.dto.UserRequestDTO;
import com.cigarette.user_service.dto.UserResponseDTO;
import com.cigarette.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(path = "/create")
    public ResponseEntity<UserResponseDTO> createUser(
            @AuthenticationPrincipal Jwt jwt
    ){
        UserResponseDTO user = userService.createUser(jwt);
        return ResponseEntity.ok(user);
    }

    @GetMapping(path = "/me")
    public ResponseEntity<UserResponseDTO> getCurrentUser(
            @AuthenticationPrincipal Jwt jwt)
    {
        UserResponseDTO user = userService.getCurrentUser(jwt);
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

    @GetMapping(path = "/{username}")
    public ResponseEntity<UserResponseDTO> getUserByUsername(@PathVariable String username){
        UserResponseDTO user = userService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }
}
