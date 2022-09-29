package com.kowalczyk.studentclasses.enums;

import com.kowalczyk.studentclasses.entity.Admin;
import com.kowalczyk.studentclasses.entity.Student;
import com.kowalczyk.studentclasses.entity.Teacher;
import com.kowalczyk.studentclasses.entity.UserData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public enum Role {

    STUDENT(Student.class),
    TEACHER(Teacher.class),
    ADMIN(Admin.class);

    private static final Map<Class<? extends UserData>, Role> typesMap = new HashMap<>();

    private final Class<? extends UserData> type;

    @Getter
    private final String authority = "ROLE_" + name();

    static {
        for (Role role : values()) {
            typesMap.put(role.type, role);
        }
    }

    public static Role from(Class<? extends UserData> type) {
        return typesMap.get(type);
    }
}
