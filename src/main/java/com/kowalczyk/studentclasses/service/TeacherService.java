package com.kowalczyk.studentclasses.service;

import com.kowalczyk.studentclasses.converters.TeacherConverters.TeacherDtoToTeacherConverter;
import com.kowalczyk.studentclasses.converters.TeacherConverters.TeacherToTeacherDtoConverter;
import com.kowalczyk.studentclasses.dto.TeacherDto;
import com.kowalczyk.studentclasses.entity.Student;
import com.kowalczyk.studentclasses.entity.Teacher;
import com.kowalczyk.studentclasses.enums.Messages;
import com.kowalczyk.studentclasses.exception.InvalidEmailException;
import com.kowalczyk.studentclasses.exception.UserNotFoundException;
import com.kowalczyk.studentclasses.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;

    public List<TeacherDto> getAll() {
        return teacherRepository.findAll().stream()
                .map(TeacherToTeacherDtoConverter.INSTANCE::convert)
                .toList();
    }

    public void addTeacher(TeacherDto teacherDto) {
        Teacher teacher = TeacherDtoToTeacherConverter.INSTANCE.convert(teacherDto);
        teacherRepository.save(teacher);
    }

    public TeacherDto findTeacher(long id) {
        logSecurityInfo();
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        return TeacherToTeacherDtoConverter.INSTANCE.convert(teacher);
    }

    private void logSecurityInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info(authentication.getName());
        log.info(authentication.getPrincipal().toString());
        log.info(authentication.isAuthenticated());
        Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .map(requestAttributes -> (ServletRequestAttributes) requestAttributes)
                .map(ServletRequestAttributes::getRequest)
                .map(httpServletRequest -> httpServletRequest.getHeader("Authorization"))
                .ifPresent(log::info);
    }

    private void encodePassword(Teacher teacher) {
        String encodedPassword = passwordEncoder.encode(teacher.getPassword());
        teacher.setPassword(encodedPassword);
    }

    public String updateTeacher(long id, TeacherDto newTeacherData) {
        if (newTeacherData.getEmail() == null) {
            throw new InvalidEmailException();
        }
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(UserNotFoundException::new)
                .toBuilder()
                .email(newTeacherData.getEmail())
                .password(newTeacherData.getPassword())
                .name(newTeacherData.getName())
                .build();
        encodePassword(teacher);
        teacherRepository.save(teacher);

        return Messages.TEACHER_UPDATE_SUCCESS.getText();
    }
}
