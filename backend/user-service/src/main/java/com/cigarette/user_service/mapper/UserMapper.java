package com.cigarette.user_service.mapper;

import com.cigarette.user_service.dto.UserResponseDTO;
import com.cigarette.user_service.entity.User;

public class UserMapper {

    public static UserResponseDTO mapToDTO(User user){
        return new UserResponseDTO(
               user.getId(),
               user.getUsername(),
               user.getImageUrl(),
               user.getBio(),
               user.getUpdatedAt()
        );
    }
}
