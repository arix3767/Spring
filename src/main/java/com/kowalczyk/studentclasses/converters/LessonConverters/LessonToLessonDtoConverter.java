package com.kowalczyk.studentclasses.converters.LessonConverters;

import com.kowalczyk.studentclasses.converters.Converter;
import com.kowalczyk.studentclasses.dto.LessonDto;
import com.kowalczyk.studentclasses.model.Lesson;

public enum LessonToLessonDtoConverter implements Converter<Lesson, LessonDto> {

    INSTANCE;

    @Override
    public LessonDto convert(Lesson lesson) {
        return LessonDto.builder()
                .id(lesson.getId())
                .schoolNumber(lesson.getSchoolNumber())
                .subject(lesson.getSubject())
                .term(lesson.getTerm())
                .student(lesson.getStudent())
                .build();
    }
}
