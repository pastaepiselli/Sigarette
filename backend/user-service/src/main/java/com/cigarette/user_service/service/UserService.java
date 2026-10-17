package com.cigarette.user_service.service;


import com.cigarette.user_service.dto.UserRequestDTO;
import com.cigarette.user_service.dto.UserResponseDTO;
import com.cigarette.user_service.entity.User;
import com.cigarette.user_service.mapper.UserMapper;
import com.cigarette.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public UserResponseDTO createUser(Jwt jwt){
        UUID uuid = UUID.fromString(jwt.getSubject());
        String username = jwt.getClaimAsString("preferred_username");

        User user = User.builder()
                .id(uuid)
                .username(username)
                .updatedAt(LocalDateTime.now())
                .build();

        return UserMapper.mapToDTO(userRepository.save(user));
    }

    public UserResponseDTO getCurrentUser(Jwt jwt){
        UUID uuid = UUID.fromString(jwt.getSubject());
        return getUserById(uuid);
    }

    public UserResponseDTO getUserById(UUID uuid){
        return userRepository.findById(uuid)
                .map(UserMapper::mapToDTO).orElseThrow(
                () -> new IllegalArgumentException("User con uuid: " + uuid + " non trovato")
        );
    }

    @Transactional
    public UserResponseDTO updateUser(Jwt jwt, UserRequestDTO dto){
        UUID uuid = UUID.fromString(jwt.getSubject());

        User user = userRepository.findById(uuid).orElseThrow(
                () -> new IllegalArgumentException("User con uuid: " + uuid + " non trovato")
        );

        user.setUsername(dto.username());
        user.setBio(dto.bio());
        user.setImageUrl(dto.imageUrl());
        user.setUpdatedAt(LocalDateTime.now());

        User updatedUser = userRepository.save(user);
        return UserMapper.mapToDTO(updatedUser);

    }

}
