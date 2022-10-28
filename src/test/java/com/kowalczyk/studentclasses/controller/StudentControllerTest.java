package com.kowalczyk.studentclasses.controller;

import com.google.gson.Gson;
import com.kowalczyk.studentclasses.dto.StudentDto;
import com.kowalczyk.studentclasses.enums.Messages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = "STUDENT")
class StudentControllerTest {

    private static final String EMAIL = "janek";
    private static final String STUDENT_PATH = "/student";
    public static final String SPECIFIC_STUDENT_PATH = STUDENT_PATH + "/" + EMAIL;
    private static final String ROOT_JSON_PATH = "$";
    private static final String STUDENT_JSON_PATH = "$[0]";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private StudentController studentController;
    @Autowired
    private Gson gson;

    @BeforeEach
    void setup() {
        Optional.ofNullable(studentController.getAll())
                .map(HttpEntity::getBody)
                .filter(students -> students.size() > 0)
                .map(studentDtoList -> studentDtoList.stream()
                        .map(StudentDto::getEmail)
                        .toList())
                .ifPresent(emails -> emails.forEach(studentController::deleteStudent));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllStudentsWithAdminUser() throws Exception {
        testGetAllStudentsWithAnyRole();
    }

    private void testGetAllStudentsWithAnyRole() throws Exception {
        mockMvc.perform(get(STUDENT_PATH))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "TEACHER")
    void getAllStudentsWithTeacherUser() throws Exception {
        testGetAllStudentsWithAnyRole();
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "TEACHER"})
    void getAllStudentsWhenNoStudentExists() throws Exception {
        mockMvc.perform(get(STUDENT_PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(ROOT_JSON_PATH).isEmpty())
                .andExpect(jsonPath(ROOT_JSON_PATH).value(new ArrayList<>()));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "TEACHER"})
    void getAllStudents() throws Exception {
        StudentDto student = buildStudentDto();
        studentController.addStudent(student);
        mockMvc.perform(get(STUDENT_PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(ROOT_JSON_PATH).isNotEmpty())
                .andExpect(jsonPath(STUDENT_JSON_PATH).exists())
                .andExpect(jsonPath(STUDENT_JSON_PATH + ".email").value(EMAIL))
                .andExpect(jsonPath(STUDENT_JSON_PATH + ".name").value(student.getName()))
                .andExpect(jsonPath(STUDENT_JSON_PATH + ".teacher").value(student.getTeacher()))
                .andExpect(jsonPath(STUDENT_JSON_PATH + ".rate").value(student.getRate()));
    }

    private StudentDto buildStudentDto() {
        return StudentDto.builder()
                .name("Janek")
                .email(EMAIL)
                .password("1234")
                .teacher("Mati")
                .rate(5.0f)
                .build();
    }

    @Test
    @WithAnonymousUser
    void addStudent() throws Exception {
        String json = gson.toJson(buildStudentDto());
        mockMvc.perform(post(STUDENT_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(ROOT_JSON_PATH).isNotEmpty())
                .andExpect(jsonPath(ROOT_JSON_PATH).isString())
                .andExpect(jsonPath(ROOT_JSON_PATH).value(Messages.STUDENT_ADD_SUCCESS.getText()));
    }

    @Test
    @WithAnonymousUser
    void shouldNotAddStudentWhenStudentAlreadyExists() throws Exception {
        StudentDto student = buildStudentDto();
        studentController.addStudent(student);
        String json = gson.toJson(student);
        mockMvc.perform(post(STUDENT_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(ROOT_JSON_PATH).isNotEmpty())
                .andExpect(jsonPath(ROOT_JSON_PATH).isString())
                .andExpect(jsonPath(ROOT_JSON_PATH).value(Messages.STUDENT_ADD_FAILED.getText()));
    }

    @Test
    void deleteStudent() throws Exception {
        studentController.addStudent(buildStudentDto());
        mockMvc.perform(delete(SPECIFIC_STUDENT_PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(ROOT_JSON_PATH).isNotEmpty())
                .andExpect(jsonPath(ROOT_JSON_PATH).isString())
                .andExpect(jsonPath(ROOT_JSON_PATH).value(Messages.STUDENT_DELETE_SUCCESS.getText()));
    }

    @Test
    void shouldNotDeleteStudentWhenStudentNotExists() throws Exception {
        mockMvc.perform(delete(SPECIFIC_STUDENT_PATH))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(ROOT_JSON_PATH).isNotEmpty())
                .andExpect(jsonPath(ROOT_JSON_PATH).isString())
                .andExpect(jsonPath(ROOT_JSON_PATH).value(Messages.USER_NOT_FOUND.getText()));
    }

    @Test
    @WithMockUser(roles = "TEACHER")
    void updateRate() throws Exception {
        studentController.addStudent(buildStudentDto());
        mockMvc.perform(patch(SPECIFIC_STUDENT_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(1.0)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(ROOT_JSON_PATH).isNotEmpty())
                .andExpect(jsonPath(ROOT_JSON_PATH).isString())
                .andExpect(jsonPath(ROOT_JSON_PATH).value(Messages.STUDENT_UPDATE_RATE_SUCCESS.getText()));
    }

    @Test
    @WithMockUser(roles = "TEACHER")
    void shouldNotUpdateRateWhenStudentNotExists() throws Exception {
        mockMvc.perform(patch(SPECIFIC_STUDENT_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(1.0)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(ROOT_JSON_PATH).isNotEmpty())
                .andExpect(jsonPath(ROOT_JSON_PATH).isString())
                .andExpect(jsonPath(ROOT_JSON_PATH).value(Messages.USER_NOT_FOUND.getText()));
    }

    @Test
    void editStudent() throws Exception {
        StudentDto updatedStudent = StudentDto.builder()
                .name("Marek")
                .email("marek123")
                .password("1234")
                .teacher("Maciek")
                .rate(2)
                .build();
        String json = gson.toJson(updatedStudent);
        studentController.addStudent(buildStudentDto());
        mockMvc.perform(put(SPECIFIC_STUDENT_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(ROOT_JSON_PATH).isNotEmpty())
                .andExpect(jsonPath(ROOT_JSON_PATH).isString())
                .andExpect(jsonPath(ROOT_JSON_PATH).value(Messages.STUDENT_EDIT_SUCCESS.getText()));
    }

    @Test
    void shouldNotEditStudentWhenStudentNotExists() throws Exception {
        StudentDto updatedStudent = StudentDto.builder()
                .name("Marek")
                .email("marek123")
                .teacher("Maciek")
                .rate(2)
                .build();
        String json = gson.toJson(updatedStudent);
        mockMvc.perform(put(SPECIFIC_STUDENT_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(ROOT_JSON_PATH).isNotEmpty())
                .andExpect(jsonPath(ROOT_JSON_PATH).isString())
                .andExpect(jsonPath(ROOT_JSON_PATH).value(Messages.USER_NOT_FOUND.getText()));
    }

    @Test
    void shouldNotEditStudentWhenNewEmailIsNull() throws Exception {
        StudentDto updatedStudent = StudentDto.builder()
                .name("Marek")
                .email("marek123")
                .teacher("Maciek")
                .rate(2)
                .build();
        updatedStudent.setEmail(null);
        String json = gson.toJson(updatedStudent);
        studentController.addStudent(buildStudentDto());
        mockMvc.perform(put(SPECIFIC_STUDENT_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(ROOT_JSON_PATH).isNotEmpty())
                .andExpect(jsonPath(ROOT_JSON_PATH).isString())
                .andExpect(jsonPath(ROOT_JSON_PATH).value(Messages.INVALID_EMAIL.getText()));
    }

    @Test
    void getByEmailWithStudentRole() throws Exception {
        testGetByEmailWithAnyRole();
    }

    private void testGetByEmailWithAnyRole() throws Exception {
        StudentDto studentDto = buildStudentDto();
        studentController.addStudent(studentDto);
        mockMvc.perform(get(SPECIFIC_STUDENT_PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(ROOT_JSON_PATH).isNotEmpty())
                .andExpect(jsonPath(ROOT_JSON_PATH).exists())
                .andExpect(jsonPath(ROOT_JSON_PATH + ".email").value(EMAIL))
                .andExpect(jsonPath(ROOT_JSON_PATH + ".name").value(studentDto.getName()))
                .andExpect(jsonPath(ROOT_JSON_PATH + ".teacher").value(studentDto.getTeacher()))
                .andExpect(jsonPath(ROOT_JSON_PATH + ".rate").value(studentDto.getRate()));
    }

    @Test
    @WithMockUser(roles = "TEACHER")
    void getByEmailWithTeacherRole() throws Exception {
        testGetByEmailWithAnyRole();
    }

    @Test
    void shouldNotGetByEmailWhenStudentNotExists() throws Exception {
        mockMvc.perform(get(SPECIFIC_STUDENT_PATH))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(ROOT_JSON_PATH).value(Messages.USER_NOT_FOUND.getText()));

    }
}
