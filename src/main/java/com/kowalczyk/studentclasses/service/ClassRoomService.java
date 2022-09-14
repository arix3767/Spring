package com.kowalczyk.studentclasses.service;


import com.kowalczyk.studentclasses.entity.ClassRoom;
import com.kowalczyk.studentclasses.repository.ClassRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ClassRoomService {

    private final ClassRoomRepository classRoomRepository;

    public List<ClassRoom> getAll() {
        return classRoomRepository.findAll();
    }

    public void addClassRoom(ClassRoom classRoom) {
        classRoomRepository.save(classRoom);
    }
}
