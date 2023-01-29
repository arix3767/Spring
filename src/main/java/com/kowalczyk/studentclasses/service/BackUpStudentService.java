package com.kowalczyk.studentclasses.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;

@ConditionalOnMissingBean(RealStudentService.class)
@Service
public class BackUpStudentService implements StudentService {
}
