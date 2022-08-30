package com.kowalczyk.studentclasses.controller;

import com.kowalczyk.studentclasses.converters.TutoringConverters.TutoringDtoToTutoringConverter;
import com.kowalczyk.studentclasses.dto.TeacherDto;
import com.kowalczyk.studentclasses.dto.TutoringDto;
import com.kowalczyk.studentclasses.model.Tutoring;
import com.kowalczyk.studentclasses.service.TutoringService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tutoring")
public class TutoringController {

    private final TutoringService tutoringService;

    @GetMapping
    public ResponseEntity<List<TutoringDto>> getAll() {
        List<TutoringDto> tutorings = tutoringService.getAll();
        return ResponseEntity.ok(tutorings);

    }

    @PostMapping
    public ResponseEntity<String> addTutoring(@RequestBody TutoringDto tutoringDto) {
        tutoringService.addTutoring(tutoringDto);
        return ResponseEntity.ok("Tutoring Added");
    }
}
