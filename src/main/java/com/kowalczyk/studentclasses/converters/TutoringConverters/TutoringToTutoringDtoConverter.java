package com.kowalczyk.studentclasses.converters.TutoringConverters;

import com.kowalczyk.studentclasses.converters.Converter;
import com.kowalczyk.studentclasses.dto.TutoringDto;
import com.kowalczyk.studentclasses.model.Tutoring;

public enum TutoringToTutoringDtoConverter implements Converter<Tutoring, TutoringDto> {

    INSTANCE;

    @Override
    public TutoringDto convert(Tutoring tutoring) {
        return TutoringDto.builder()
                .id(tutoring.getId())
                .price(tutoring.getPrice())
                .subject(tutoring.getSubject())
                .term(tutoring.getTerm())
                .student(tutoring.getStudent())
                .build();
    }
}
