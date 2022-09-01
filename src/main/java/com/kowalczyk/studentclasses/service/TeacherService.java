package com.kowalczyk.studentclasses.service;

import com.kowalczyk.studentclasses.converters.TeacherConverters.TeacherDtoToTeacherConverter;
import com.kowalczyk.studentclasses.converters.TeacherConverters.TeacherToTeacherDtoConverter;
import com.kowalczyk.studentclasses.dto.TeacherDto;
import com.kowalczyk.studentclasses.entity.Teacher;
import com.kowalczyk.studentclasses.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;

    public List<TeacherDto> getAll() {
        return teacherRepository.findAll().stream()
                .map(TeacherToTeacherDtoConverter.INSTANCE::convert)
                .toList();
    }

    public void addTeacher(TeacherDto teacherDto) {
        Teacher teacher = TeacherDtoToTeacherConverter.INSTANCE.convert(teacherDto);
        teacherRepository.save(teacher);
    }
}
