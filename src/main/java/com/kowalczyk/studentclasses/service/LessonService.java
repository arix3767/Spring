package com.kowalczyk.studentclasses.service;

import com.kowalczyk.studentclasses.converters.LessonConverters.LessonDtoToLessonConverter;
import com.kowalczyk.studentclasses.converters.LessonConverters.LessonToLessonDtoConverter;
import com.kowalczyk.studentclasses.dto.LessonDto;
import com.kowalczyk.studentclasses.entity.Lesson;
import com.kowalczyk.studentclasses.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LessonService {

    private final LessonRepository lessonRepository;

    public List<LessonDto> getAll() {
        return lessonRepository.findAll().stream()
                .map(LessonToLessonDtoConverter.INSTANCE::convert)
                .toList();
    }

    public void addLesson(LessonDto lessonDto) {
        Lesson lesson = LessonDtoToLessonConverter.INSTANCE.convert(lessonDto);
        lessonRepository.save(lesson);
    }
}
