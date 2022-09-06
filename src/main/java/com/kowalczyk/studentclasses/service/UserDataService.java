package com.kowalczyk.studentclasses.service;

import com.kowalczyk.studentclasses.converters.UserDataConverter.UserDataDtoToUserDataConverter;
import com.kowalczyk.studentclasses.converters.UserDataConverter.UserDataToUserDataDtoConverter;
import com.kowalczyk.studentclasses.dto.UserDataDto;
import com.kowalczyk.studentclasses.repository.UserDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserDataService {

    private final UserDataRepository userDataRepository;

    public List<UserDataDto> getAll() {
        return userDataRepository.findAll().stream()
                .map(UserDataToUserDataDtoConverter.INSTANCE::convert)
                .toList();
    }

    public void addUserData(UserDataDto userDataDto) {
        userDataRepository.save(UserDataDtoToUserDataConverter.INSTANCE.convert(userDataDto));
    }
}
