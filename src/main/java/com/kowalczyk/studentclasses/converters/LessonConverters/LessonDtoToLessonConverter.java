package com.kowalczyk.studentclasses.converters.LessonConverters;

import com.kowalczyk.studentclasses.converters.Converter;
import com.kowalczyk.studentclasses.dto.LessonDto;
import com.kowalczyk.studentclasses.model.Lesson;

public enum LessonDtoToLessonConverter implements Converter<LessonDto, Lesson> {

    INSTANCE;

    @Override
    public Lesson convert(LessonDto lessonDto) {
        return Lesson.builder()
                .id(lessonDto.getId())
                .schoolNumber(lessonDto.getSchoolNumber())
                .subject(lessonDto.getSubject())
                .term(lessonDto.getTerm())
                .student(lessonDto.getStudent())
                .build();
    }
}
