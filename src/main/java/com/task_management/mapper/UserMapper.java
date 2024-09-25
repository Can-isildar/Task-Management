package com.task_management.mapper;

import com.task_management.dto.UserRequestDTO;
import com.task_management.dto.UserResponseDTO;
import com.task_management.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring") // Spring ile entegre çalşmasını sağlar DI ile bean oluşturur.
public interface UserMapper {
    UserEntity toEntity(UserRequestDTO dto);

    UserResponseDTO toResponseDTO(UserEntity entity);
}
