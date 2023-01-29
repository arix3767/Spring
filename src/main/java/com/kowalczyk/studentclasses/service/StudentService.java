package com.kowalczyk.studentclasses.service;

import com.kowalczyk.studentclasses.converters.StudentConverters.StudentDtoToStudentConverter;
import com.kowalczyk.studentclasses.converters.StudentConverters.StudentToStudentDtoConverter;
import com.kowalczyk.studentclasses.dto.StudentDto;
import com.kowalczyk.studentclasses.entity.Student;
import com.kowalczyk.studentclasses.enums.Messages;
import com.kowalczyk.studentclasses.exception.InvalidEmailException;
import com.kowalczyk.studentclasses.exception.MissingDataException;
import com.kowalczyk.studentclasses.exception.StudentAlreadyExistsException;
import com.kowalczyk.studentclasses.exception.UserNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Optional;

public interface StudentService {
     List<StudentDto> getAll() {

    }

     StudentDto addStudent(StudentDto studentDto) {

    }

    private void logSecurityInfo() {

    }

    private void encodePassword(Student student) {

    }

     StudentDto findStudent(long id) {

    }

     String editStudent(long id, StudentDto newStudentData) {

    }

     String deleteStudent(long id) {

    }

     String updateRate(long id, float rate) {

    }

     List<StudentDto> findAllLessThan(float rate) {

    }

     List<StudentDto> findAllByRateGreaterThan(float rate) {

    }

     List<StudentDto> findAllByRateBetween(float lowLevel, float highLevel) {

    }

     List<StudentDto> findAllByRateGreaterThanEqual(float rate) {

    }

    public List<StudentDto> findAllByRateLessThanEqual(float rate) {

    }

}
