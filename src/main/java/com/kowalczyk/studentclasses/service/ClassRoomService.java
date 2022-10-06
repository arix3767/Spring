package com.kowalczyk.studentclasses.service;


import com.kowalczyk.studentclasses.entity.ClassRoom;
import com.kowalczyk.studentclasses.entity.School;
import com.kowalczyk.studentclasses.exception.InvalidUrlException;
import com.kowalczyk.studentclasses.exception.MissingDataException;
import com.kowalczyk.studentclasses.exception.MissingWebsiteException;
import com.kowalczyk.studentclasses.repository.ClassRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Service
public class ClassRoomService {

    private final ClassRoomRepository classRoomRepository;

    public List<ClassRoom> getAll() {
        return classRoomRepository.findAll();
    }

    public void addClassRoom(ClassRoom classRoom) {
        log.info("Adding new classroom...");
        String schoolWebsiteUrl = Optional.ofNullable(classRoom)
                .map(ClassRoom::getSchool)
                .map(School::getWebsiteUrl)
                .orElseThrow(MissingWebsiteException::new);
        try {
            validateUrl(schoolWebsiteUrl);
        } catch (MalformedURLException e) {
            log.error("Invalid school website's url", e);
            throw new InvalidUrlException();
        }
        classRoomRepository.save(classRoom);
    }

    private void validateUrl(String schoolWebsiteUrl) throws MalformedURLException {
        log.info("Checking school website's url ({}) correctness...", schoolWebsiteUrl);
        new URL(schoolWebsiteUrl);
    }
}
