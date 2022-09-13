package com.kowalczyk.studentclasses.service;


import com.kowalczyk.studentclasses.converters.ClassRoomConverters.ClassRoomDtoToClassRoomConverter;
import com.kowalczyk.studentclasses.converters.ClassRoomConverters.ClassRoomToClassRoomDtoConverter;
import com.kowalczyk.studentclasses.dto.ClassRoomDto;
import com.kowalczyk.studentclasses.entity.ClassRoom;
import com.kowalczyk.studentclasses.repository.ClassRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ClassRoomService {

    private final ClassRoomRepository classRoomRepository;

    public List<ClassRoomDto> getAll() {
        return classRoomRepository.findAll().stream()
                .map(ClassRoomToClassRoomDtoConverter.INSTANCE::convert)
                .toList();
    }

    public void addClassRoom(ClassRoomDto classRoomDto) {
        ClassRoom classRoom = ClassRoomDtoToClassRoomConverter.INSTANCE.convert(classRoomDto);
        classRoomRepository.save(classRoom);
    }
}
