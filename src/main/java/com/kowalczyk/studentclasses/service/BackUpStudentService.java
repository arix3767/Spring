package com.kowalczyk.studentclasses.service;

import com.kowalczyk.studentclasses.dto.StudentDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

@ConditionalOnMissingBean(RealStudentService.class)
@Service
@Log4j2
public class BackUpStudentService implements StudentService {

    @PostConstruct
    private void setUp() {
        log.info("{} has been initialized", this.getClass().getSimpleName());
    }

    @Override
    public List<StudentDto> getAll() {
        return Collections.emptyList();
    }

    @Override
    public StudentDto addStudent(StudentDto studentDto) {
        return new StudentDto();
    }

    @Override
    public StudentDto findStudent(long id) {
        return new StudentDto();
    }

    @Override
    public String editStudent(long id, StudentDto newStudentData) {
        return "";
    }

    @Override
    public String deleteStudent(long id) {
        return "";
    }

    @Override
    public String updateRate(long id, float rate) {
        return "";
    }

    @Override
    public List<StudentDto> findAllLessThan(float rate) {
        return Collections.emptyList();
    }

    @Override
    public List<StudentDto> findAllByRateGreaterThan(float rate) {
        return Collections.emptyList();
    }

    @Override
    public List<StudentDto> findAllByRateBetween(float lowLevel, float highLevel) {
        return Collections.emptyList();
    }

    @Override
    public List<StudentDto> findAllByRateGreaterThanEqual(float rate) {
        return Collections.emptyList();
    }

    @Override
    public List<StudentDto> findAllByRateLessThanEqual(float rate) {
        return Collections.emptyList();
    }
}
