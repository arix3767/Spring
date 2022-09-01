package com.kowalczyk.studentclasses.controller;

import com.kowalczyk.studentclasses.dto.LessonDto;
import com.kowalczyk.studentclasses.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/lesson")
public class LessonController {

    private final LessonService lessonService;

    @GetMapping
    public ResponseEntity<List<LessonDto>> getAll() {
        List<LessonDto> lessons = lessonService.getAll();
        return ResponseEntity.ok(lessons);
    }

    @PostMapping
    public ResponseEntity<String> addLesson(@RequestBody LessonDto lessonDto) {
        lessonService.addLesson(lessonDto);
        return ResponseEntity.ok("Lesson added");
    }
}
