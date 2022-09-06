package com.kowalczyk.studentclasses.converters.UserDataConverter;

import com.kowalczyk.studentclasses.converters.Converter;
import com.kowalczyk.studentclasses.dto.UserDataDto;
import com.kowalczyk.studentclasses.entity.UserData;

public enum UserDataDtoToUserDataConverter implements Converter<UserDataDto, UserData> {

    INSTANCE;

    @Override
    public UserData convert(UserDataDto userDataDto) {
        return UserData.builder()
                .id(userDataDto.getId())
                .email(userDataDto.getEmail())
                .password(userDataDto.getPassword())
                .build();
    }
}
