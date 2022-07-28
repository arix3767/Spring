package com.kowalczyk.studentclasses.controller;

import com.google.gson.Gson;
import com.kowalczyk.studentclasses.enums.Messages;
import com.kowalczyk.studentclasses.exception.StudentNotFoundException;
import com.kowalczyk.studentclasses.model.Student;
import com.kowalczyk.studentclasses.repository.InMemoryStudentRepository;
import com.kowalczyk.studentclasses.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {

    private static final String EMAIL = "janek";
    private static final String STUDENT_PATH = "/student";
    public static final String SPECIFIC_STUDENT_PATH = STUDENT_PATH + "/" + EMAIL;
    private static final String ROOT_JSON_PATH = "$";
    private static final String STUDENT_JSON_PATH = "$." + EMAIL;

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
                .map(Map::entrySet)
                .ifPresent(entries -> entries.removeAll(Set.copyOf(entries)));
    }

    @Test
    void getAllStudentsWhenNoStudentExists() throws Exception {
        mockMvc.perform(get(STUDENT_PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(ROOT_JSON_PATH).isEmpty())
                .andExpect(jsonPath(ROOT_JSON_PATH).isMap())
                .andExpect(jsonPath(ROOT_JSON_PATH).value(new HashMap<>()));
    }

    @Test
    void getAllStudents() throws Exception {
        Student student = buildStudent();
        studentController.addStudent(student);
        mockMvc.perform(get(STUDENT_PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(ROOT_JSON_PATH).isNotEmpty())
                .andExpect(jsonPath(ROOT_JSON_PATH).isMap())
                .andExpect(jsonPath(STUDENT_JSON_PATH).exists())
                .andExpect(jsonPath(STUDENT_JSON_PATH + ".email").value(EMAIL))
                .andExpect(jsonPath(STUDENT_JSON_PATH + ".name").value(student.getName()))
                .andExpect(jsonPath(STUDENT_JSON_PATH + ".teacher").value(student.getTeacher()))
                .andExpect(jsonPath(STUDENT_JSON_PATH + ".rate").value(student.getRate()));
    }

    private Student buildStudent() {
        return Student.builder()
                .name("Janek")
                .email(EMAIL)
                .teacher("Mati")
                .rate(5.0f)
                .build();
    }

    @Test
    void addStudent() throws Exception {
        String json = gson.toJson(buildStudent());
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
    void deleteStudent() throws Exception {
        studentController.addStudent(buildStudent());
        mockMvc.perform(delete(SPECIFIC_STUDENT_PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(ROOT_JSON_PATH).isNotEmpty())
                .andExpect(jsonPath(ROOT_JSON_PATH).isString())
                .andExpect(jsonPath(ROOT_JSON_PATH).value(Messages.STUDENT_DELETE_SUCCESS.getText()));
    }

    @Test
    void updateRate() throws Exception {
        studentController.addStudent(buildStudent());
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
    void editStudent() throws Exception {
        Student updatedStudent = Student.builder()
                .name("Marek")
                .email("marek123")
                .teacher("Maciek")
                .rate(2)
                .build();
        String json = gson.toJson(updatedStudent);
        studentController.addStudent(buildStudent());
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
    void editStudent_studentNotFound() throws Exception {
        Student updatedStudent = Student.builder()
                .name("Marek")
                .email("marek123")
                .teacher("Maciek")
                .rate(2)
                .build();

        Mockito.when(studentController.findStudent(updatedStudent.getEmail())).thenReturn(null);

        String json = gson.toJson(updatedStudent);
        studentController.addStudent(buildStudent());
        mockMvc.perform(put(SPECIFIC_STUDENT_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof StudentNotFoundException))
                .andExpect(result ->
                        assertEquals("Student not found!", result.getResolvedException().getMessage()));


    }
}
