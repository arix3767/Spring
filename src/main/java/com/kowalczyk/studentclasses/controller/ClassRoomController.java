package com.kowalczyk.studentclasses.controller;

import com.kowalczyk.studentclasses.converters.ClassRoomConverters.ClassRoomDtoToClassRoomConverter;
import com.kowalczyk.studentclasses.dto.ClassRoomDto;
import com.kowalczyk.studentclasses.model.ClassRoom;
import com.kowalczyk.studentclasses.service.ClassRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/classRoom")
public class ClassRoomController {

    private final ClassRoomService classRoomService;

    @GetMapping
    public ResponseEntity<List<ClassRoom>> getAll() {
        List<ClassRoom> classRooms = classRoomService.getAll();
        return ResponseEntity.ok(classRooms);
    }

    @PostMapping
    public ResponseEntity<String> addClassRoom(@RequestBody ClassRoomDto classRoomDto) {
        classRoomService.addClassRoom(ClassRoomDtoToClassRoomConverter.INSTANCE.convert(classRoomDto));
        return ResponseEntity.ok("ClassRoom Added");
    }

}
