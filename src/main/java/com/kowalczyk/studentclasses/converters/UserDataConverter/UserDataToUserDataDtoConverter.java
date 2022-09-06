package com.kowalczyk.studentclasses.converters.UserDataConverter;

import com.kowalczyk.studentclasses.converters.Converter;
import com.kowalczyk.studentclasses.dto.UserDataDto;
import com.kowalczyk.studentclasses.entity.UserData;

public enum UserDataToUserDataDtoConverter implements Converter<UserData, UserDataDto> {

    INSTANCE;

    @Override
    public UserDataDto convert(UserData userData) {
        return UserDataDto.builder()
                .id(userData.getId())
                .email(userData.getEmail())
                .password(userData.getPassword())
                .build();
    }
}
