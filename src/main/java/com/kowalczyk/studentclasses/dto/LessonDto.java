package com.kowalczyk.studentclasses.dto;

import com.kowalczyk.studentclasses.enums.Subject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonDto {

    private Long id;
    private int schoolNumber;
    private Subject subject;
    private LocalDateTime term;
    private String student;
}